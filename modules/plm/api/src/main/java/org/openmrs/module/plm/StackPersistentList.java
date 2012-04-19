package org.openmrs.module.plm;

import java.util.Stack;

public class StackPersistentList extends PersistentListBase<Stack<PersistentListItem>> {
	public StackPersistentList(String key, PersistentListProvider provider) {
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
		return cachedItems.size() * -1;
	}
}
