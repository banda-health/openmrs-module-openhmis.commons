package org.openmrs.module.plm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhmis.common.Initializable;

import java.util.Collection;
import java.util.Collections;

/**
 * Base type for Persistent List Manager lists.  Provides a thread-safe list implementation base that caches the items
 * in the defined Collection<PersistentListItem> subtype.
 *
 * @param <T> The collection type for the list implementation.
 */
public abstract class PersistentListBase<T extends Collection<PersistentListItem>> implements PersistentList, Initializable {
	protected PersistentListBase(String key, PersistentListProvider provider) {
		if (key == null || key.trim().equals("")) {
			throw new IllegalArgumentException("Key has no content.");
		}
		if (provider == null) {
			throw new IllegalArgumentException("Provider is not defined.");
		}

		this.key = key;
		this.provider = provider;
	}

	private Log log = LogFactory.getLog(PersistentListServiceImpl.class);

	protected Object syncLock = new Object();
	protected String key;
	protected PersistentListProvider provider;
	protected T cachedItems;

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
	public String getKey() {
		return key;
	}

	@Override
	public PersistentListProvider getProvider() {
		return provider;
	}

	@Override
	public int getCount() {
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

					// Add the item to the cached items
					cachedItems.add(item);

					// Add the item to the provider at the specified index
					provider.add(item, getItemIndex(item));
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
			Boolean wasRemovedFromProvider = provider.remove(item);
			Boolean wasRemovedFromCache = cachedItems.remove(item);

			return wasRemovedFromProvider || wasRemovedFromCache;
		}
	}

	@Override
	public void clear() {
		synchronized (syncLock) {
			provider.clear();
			cachedItems.clear();
		}
	}

	@Override
	public PersistentListItem[] getItems() {
		return cachedItems.toArray(new PersistentListItem[cachedItems.size()]);
	}

	protected PersistentListItem[] loadList() {
		return provider.getItems();
	}

	protected PersistentListItem createNewItem() {
		return new PersistentListItem();
	}
}

