package org.openmrs.module.plm;

import java.util.Collection;
import java.util.LinkedList;

public class QueueList extends ListBase<LinkedList<ListItem>> {
	public QueueList(String key, ListProvider provider) {
		super(key, provider);
	}

	@Override
	public ListItem getNext() {
		return cachedItems.peek();
	}

	@Override
	public ListItem getNextAndRemove() {
		return cachedItems.pop();
	}

	@Override
	protected LinkedList<ListItem> initializeCache() {
		return new LinkedList<ListItem>();
	}

	@Override
	protected int getItemIndex(ListItem item) {
		// New items go to the end of the queue
		return cachedItems.size();
	}
}

