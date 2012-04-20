package org.openmrs.module.plm;

import java.util.Stack;

public class PersistentStack extends PersistentListBase<Stack<PersistentListItem>> {
	public PersistentStack(String key, PersistentListProvider provider) {
		super(key, provider);
	}

	public PersistentStack(int id, String key, PersistentListProvider provider) {
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
	protected Stack<PersistentListItem> initializeCache() {
		return new Stack<PersistentListItem>();
	}

	@Override
	protected int getItemIndex(PersistentListItem item) {
		/*
			New items are added to the front of the list.
		    Rather than have to reorder each item order every time a new item is added
		    simply negate the size:
		        Item 1: 0
				Item 2: -1
				Item 3: -2
		*/
		int index = cachedItems.indexOf(item);
		if (index < 0) {
			// New items go to the end of the queue
			index = cachedItems.size();
		}

		return index * -1;
	}
}
