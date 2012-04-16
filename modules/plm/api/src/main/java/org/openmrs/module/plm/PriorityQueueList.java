package org.openmrs.module.plm;

import org.openhmis.common.Utility;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueList extends ListBase<PriorityQueue<ListItem>> {
	public PriorityQueueList(String key, ListProvider provider) {
		super(key, provider);
	}

	public static final Comparator<ListItem> priorityComparer = new Comparator<ListItem>() {
		@Override
		public int compare(ListItem o1, ListItem o2) {
			PriorityListItem p1 = Utility.as(PriorityListItem.class, o1);
			PriorityListItem p2 = Utility.as(PriorityListItem.class, o2);

			if (p1 == null && p2 == null) {
				// Both items are not priority items: just use item order
				return o1.getOrder().compareTo(o2.getOrder());
			}
			else if (p1 != null && p2 == null) {
				// item1 is priority item, item2 is not: item 1 is first
				return 1;
			}
			else if (p1 == null) {
				// item2 is a priority item, item1 is not; item 2 is first
				return -1;
			}
			else if (p1.getPriority() > p2.getPriority()) {
				// Both items are priority items: use priority order
				return 1;
			}
			else if (p1.getPriority() < p2.getPriority()) {
				// Both items are priority items: use priority order
				return -1;
			}
			else {
				// Both items are priority items with the same priority, use item order
				return o1.getOrder().compareTo(o2.getOrder());
			}
		}
	};

	@Override
	public ListItem getNext() {
		return cachedItems.peek();
	}

	@Override
	public ListItem getNextAndRemove() {
		return cachedItems.remove();
	}

	@Override
	protected PriorityQueue<ListItem> initializeCache() {
		return new PriorityQueue<ListItem>(11, priorityComparer);
	}

	@Override
	protected ListItem createNewItem() {
		return new PriorityListItem();
	}

	@Override
	protected int getItemIndex(ListItem item) {
		/*
			There is no good way to get the index where this item was added to the priority queue.  So the (crappy)
			solution is to simply iterate through the queue and find it.  The lookup time is thus O(n).
		 */

		// Try to find the item
		int i = 0;
		Iterator it = cachedItems.iterator();
		while (it.hasNext()) {
			if (it.next() == item) {
				break;
			}

			i++;
		}

		// Return the item index or one past the size of the queue if not found
		return i;
	}
}
