package org.openmrs.module.plm;

import java.util.LinkedList;

public class PersistentQueue extends PersistentListBase<LinkedList<PersistentListItem>> {
	public PersistentQueue(String key, PersistentListProvider provider) {
		super(key, provider);
	}

	public PersistentQueue(int id, String key, PersistentListProvider provider) {
		super(id, key, provider);
	}

	@Override
	public PersistentListItem getNext() {
		return cachedItems.peek();
	}

	@Override
	public PersistentListItem getNextAndRemove() {
		return cachedItems.pop();
	}

	@Override
	protected LinkedList<PersistentListItem> initializeCache() {
		return new LinkedList<PersistentListItem>();
	}

	@Override
	protected int getItemIndex(PersistentListItem item) {
		int index = cachedItems.indexOf(item);
		if (index < 0) {
			// New items go to the end of the queue
			index = cachedItems.size();
		}

		return index;
	}
}

