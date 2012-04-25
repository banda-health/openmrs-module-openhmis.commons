package org.openmrs.module.plm;

import org.openhmis.common.Utility;
import org.openmrs.module.plm.model.PersistentListItemModel;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PersistentPriorityQueue extends PersistentListBase<PriorityQueue<PersistentListItem>> {
	public PersistentPriorityQueue(String key, PersistentListProvider provider) {
		super(key, provider);
	}

	public static final Comparator<PersistentListItem> priorityComparator = new Comparator<PersistentListItem>() {
		@Override public int compare(PersistentListItem o1, PersistentListItem o2) {
			PriorityPersistentListItem p1 = Utility.as(PriorityPersistentListItem.class, o1);
			PriorityPersistentListItem p2 = Utility.as(PriorityPersistentListItem.class, o2);

			if (p1 == null && p2 == null) {
				// Both items are not priority items: just use item order
				return o1.getCreatedOn().compareTo(o2.getCreatedOn());
			} else if (p1 != null && p2 == null) {
				// item1 is priority item, item2 is not: item 1 is first
				return 1;
			} else if (p1 == null) {
				// item2 is a priority item, item1 is not; item 2 is first
				return -1;
			} else if (p1.getPriority() > p2.getPriority()) {
				// Both items are priority items: use priority order
				return 1;
			} else if (p1.getPriority() < p2.getPriority()) {
				// Both items are priority items: use priority order
				return -1;
			} else {
				// Both items are priority items with the same priority, use item order
				if (p1.getOrder() == null && p2.getOrder() == null) {
					return p1.getCreatedOn().compareTo(p2.getCreatedOn());
				} else if (p1.getOrder() == null) {
					return 1;
				} else if (p2.getOrder() == null) {
					return -1;
				} else {
					return p1.getCreatedOn().compareTo(p2.getCreatedOn());
				}
			}
		}
	};

	@Override
	public PersistentListItem getNext() {
		return cachedItems.peek();
	}

	@Override
	public PersistentListItem getNextAndRemove() {
		return cachedItems.remove();
	}

	@Override
	protected PriorityQueue<PersistentListItem> initializeCache() {
		return new PriorityQueue<PersistentListItem>(11, priorityComparator);
	}

	@Override
	protected PersistentListItem createItem(PersistentListItemModel model) {
		return new PriorityPersistentListItem(model);
	}

	@Override
	protected PersistentListItemModel createItemModel(PersistentListItem item) {
		PriorityPersistentListItem priorityItem = Utility.as(PriorityPersistentListItem.class, item);

		if (priorityItem == null) {
			return super.createItemModel(item);
		} else {
			return new PersistentListItemModel(this, priorityItem.getKey(), getItemIndex(item), priorityItem.getPriority(),
				priorityItem.getOrder(), item.getCreator(), item.getCreatedOn());
		}
	}

	@Override
	protected int getItemIndex(PersistentListItem item) {
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

		// Return the item index or the size of the queue plus 1 if the item was not found
		return i;
	}
}
