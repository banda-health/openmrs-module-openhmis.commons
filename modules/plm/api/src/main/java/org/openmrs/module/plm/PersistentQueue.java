package org.openmrs.module.plm;

import java.util.LinkedList;

public class PersistentQueue extends PersistentListBase<LinkedList<PersistentListItem>> {
	public PersistentQueue(String key, PersistentListProvider provider) {
		super(key, provider);
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
		// New items go to the end of the queue
		return cachedItems.size();
	}
}

