package org.openmrs.module.plm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Collections;

/**
 * Base type for Persistent List Manager lists.  Provides a thread-safe list implementation base that caches the items
 * in the defined Collection<ListItem> subtype.
 *
 * @param <T> The collection type for the list implementation.
 */
public abstract class ListBase<T extends Collection<ListItem>> implements List, Initializable {
	protected ListBase(String key, ListProvider provider) {
		if (key == null || key.trim().equals("")) {
			throw new IllegalArgumentException("Key has no content.");
		}
		if (provider == null) {
			throw new IllegalArgumentException("Provider is not defined.");
		}

		this.key = key;
		this.provider = provider;
	}

	private Log log = LogFactory.getLog(ListServiceImpl.class);

	protected Object syncLock = new Object();
	protected String key;
	protected ListProvider provider;
	protected T cachedItems;

	@Override
	public abstract ListItem getNext();

	@Override
	public abstract ListItem getNextAndRemove();

	protected abstract T initializeCache();
	protected abstract int getItemOrdinal(ListItem item);

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
	public ListProvider getProvider() {
		return provider;
	}

	@Override
	public int getCount() {
		return cachedItems.size();
	}

	@Override
	public void add(ListItem item) throws Exception {
		try{
			synchronized (syncLock) {
				// Add the item to the cached items
				cachedItems.add(item);

				// Get the item order for the added item
				item.setOrder(getItemOrdinal(item));

				// Add the item to the provider
				provider.add(item);
			}
		} catch (Exception ex) {
			// If there was an exception while trying to add the item ensure that it is no longer in the cache.
			cachedItems.remove(item);

			/*
			TODO: Should the item be removed from the provider as well, or it is safe to assume that exceptions
				can only occur before the item is added to the provider?
			*/

			/*
			 TODO: Given that a provider could throw pretty much any exception type (DB, file system, network, etc),
			    is it ok to just rethrow it as an Exception?
			*/

			throw ex;
		}
	}

	@Override
	public boolean remove(ListItem item) throws Exception {
		synchronized (syncLock) {
			Boolean wasRemovedFromProvider = provider.remove(item);
			Boolean wasRemovedFromCache = cachedItems.remove(item);

			return wasRemovedFromProvider || wasRemovedFromCache;
		}
	}

	@Override
	public void clear() throws Exception {
		synchronized (syncLock) {
			provider.clear();
			cachedItems.clear();
		}
	}

	@Override
	public ListItem[] getItems() {
		return cachedItems.toArray(new ListItem[cachedItems.size()]);
	}

	protected ListItem[] loadList() {
		return provider.getItems();
	}

	protected ListItem createNewItem() {
		return new ListItem();
	}
}

