package org.openmrs.module.plm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhmis.common.Initializable;
import org.openmrs.module.plm.model.PersistentListItemModel;
import org.openmrs.module.plm.model.PersistentListModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Base type for Persistent List Manager lists.  Provides a thread-safe list implementation base that caches the items
 * in the defined Collection<PersistentListItem> subtype.
 *
 * @param <T> The collection type for the list implementation.
 */
public abstract class PersistentListBase<T extends Collection<PersistentListItem>> implements PersistentList, Initializable {
	private Log log = LogFactory.getLog(PersistentListBase.class);

	protected final Object syncLock = new Object();

	protected Integer id;
	protected String key;
	protected String description;
	protected PersistentListProvider provider;
	protected T cachedItems;
	protected ArrayList<String> itemKeys = new ArrayList<String>();

	protected PersistentListBase() {
	}

	protected PersistentListBase(String key, PersistentListProvider provider) {
		this(null, key, provider);
	}

	protected PersistentListBase(Integer id, String key, PersistentListProvider provider) {
		if (key == null || key.trim().equals("")) {
			throw new IllegalArgumentException("Key has no content.");
		}
		if (provider == null) {
			throw new IllegalArgumentException("Provider is not defined.");
		}

		this.id = id;
		this.key = key;
		this.provider = provider;
	}

	@Override
	public abstract PersistentListItem getNext();

	@Override
	public abstract PersistentListItem getNextAndRemove();

	protected abstract T initializeCache();
	protected abstract int getItemIndex(PersistentListItem item);

	@Override
	public void initialize() {
		log.debug("Initializing the '" + key + "' list...");

		synchronized (syncLock) {
			cachedItems = initializeCache();

			Collections.addAll(cachedItems, loadList());
		}

		log.debug("The '" + key + "' has been initialized.");
	}

	@Override
	public void load(PersistentListModel model) {
		this.id = model.getListId();
		this.key = model.getKey();
		this.description = model.getDescription();
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public PersistentListProvider getProvider() {
		return provider;
	}

	@Override
	public void setProvider(PersistentListProvider provider) {
		this.provider = provider;
	}

	@Override
	public int getSize() {
		return cachedItems.size();
	}

	@Override
	public void add(PersistentListItem... items) {
		PersistentListItem item = null;
		try{
			synchronized (syncLock) {
				for (PersistentListItem listItem : items) {
					// Store the reference to the current item (in case of an exception)
					item = listItem;

					if (itemKeys.contains(item.getKey())) {
						throw new IllegalArgumentException("An item with the key '" + item.getKey() + "' has already been added to this persistent list.");
					}

					// Add the item to the cached items
					itemKeys.add(item.getKey());
					cachedItems.add(item);

					// Add the item to the provider at the specified index
					PersistentListItemModel modelItem = createItemModel(item);
					provider.add(modelItem);
				}
			}
		} catch (Exception ex) {
			// If there was an exception while trying to add an item ensure that it is no longer in the cache.
			cachedItems.remove(item);

			/*
			TODO: Should the item be removed from the provider as well, or it is safe to assume that exceptions
				can only occur before the item is added to the provider?
			*/

			/*
			 TODO: Given that a provider could throw pretty much any exception type (DB, file system, network, etc),
			    is it ok to just rethrow it as an Exception?
			*/

			throw new PersistentListException(ex);
		}
	}

	@Override
	public boolean remove(PersistentListItem item) {
		synchronized (syncLock) {
			Boolean wasRemovedFromProvider = provider.remove(createItemModel(item));
			Boolean wasRemovedFromCache = cachedItems.remove(item);
			itemKeys.remove(item.getKey());

			return wasRemovedFromProvider || wasRemovedFromCache;
		}
	}

	@Override
	public void clear() {
		synchronized (syncLock) {
			provider.clear(this);
			cachedItems.clear();
			itemKeys.clear();
		}
	}

	@Override
	public PersistentListItem[] getItems() {
		return cachedItems.toArray(new PersistentListItem[cachedItems.size()]);
	}

	protected PersistentListItem[] loadList() {
		PersistentListItemModel[] modelItems = provider.getItems(this);

		PersistentListItem[] items = new PersistentListItem[modelItems.length];
		int i = 0;
		for (PersistentListItemModel model : modelItems) {
			items[i++] = createItem(model);
		}

		return items;
	}

	protected PersistentListItem createItem(PersistentListItemModel model) {
		return new PersistentListItem(model.getItemId(), model.getItemKey(),
				model.getCreator(), model.getCreatedOn());
	}

	protected PersistentListItemModel createItemModel(PersistentListItem item) {
		return new PersistentListItemModel(this, item.getKey(), getItemIndex(item), null, null,
				item.getCreator(), item.getCreatedOn());
	}
}

