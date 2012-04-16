package org.openmrs.module.plm;

import java.util.Collection;
import java.util.Stack;

public class StackList extends ListBase<Stack<ListItem>> {
	public StackList(String key, ListProvider provider) {
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
	protected Stack<ListItem> initializeCache() {
		return new Stack<ListItem>();
	}

	@Override
	protected int getItemOrdinal(ListItem item) {
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
