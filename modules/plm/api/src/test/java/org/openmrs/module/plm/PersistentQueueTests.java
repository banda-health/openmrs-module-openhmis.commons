package org.openmrs.module.plm;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersistentQueueTests extends PersistentListTestBase {
	@Override
	protected PersistentList createList(PersistentListProvider provider) {
		PersistentQueue queue = new PersistentQueue(1, "test", provider);
		queue.initialize();

		return queue;
	}

	@Test
	public void shouldReturnItemsInLIFOOrder() {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		PersistentListItem item3 = new PersistentListItem("3", null);

		list.add(item1, item2, item3);

		PersistentListItem[] items = list.getItems();
		assertNotNull(items);
		assertEquals(3, items.length);

		assertEquals(item1, items[0]);
		assertEquals(item2, items[1]);
		assertEquals(item3, items[2]);
	}

	@Test
	public void getNextAndRemoveShouldReturnItemsInLIFOOrder() {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		PersistentListItem item3 = new PersistentListItem("3", null);

		list.add(item1, item2, item3);

		PersistentListItem[] items = list.getItems();
		assertNotNull(items);
		assertEquals(3, items.length);

		PersistentListItem item = list.getNextAndRemove();
		assertNotNull(item);
		assertEquals(item1, item);

		item = list.getNextAndRemove();
		assertNotNull(item);
		assertEquals(item2, item);

		item = list.getNextAndRemove();
		assertNotNull(item);
		assertEquals(item3, item);
	}
}

