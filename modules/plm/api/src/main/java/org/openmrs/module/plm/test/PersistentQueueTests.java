package org.openmrs.module.plm.test;

import org.junit.Test;
import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListItem;
import org.openmrs.module.plm.PersistentListProvider;
import org.openmrs.module.plm.PersistentQueue;

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

