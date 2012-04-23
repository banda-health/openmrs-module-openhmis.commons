package org.openmrs.module.plm.test;

import org.junit.Test;
import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListItem;
import org.openmrs.module.plm.PersistentListProvider;
import org.openmrs.module.plm.PersistentStack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersistentStackTests extends PersistentListTestBase {

	@Override
	protected PersistentList createList(PersistentListProvider provider) {
		PersistentStack stack = new PersistentStack(1, "test", provider);
		stack.initialize();

		return stack;
	}

	@Test
	public void shouldReturnItemsInFIFOOrder() {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		PersistentListItem item3 = new PersistentListItem("3", null);

		list.add(item1, item2, item3);

		PersistentListItem[] items = list.getItems();
		assertNotNull(items);
		assertEquals(3, items.length);

		assertEquals(item3, items[0]);
		assertEquals(item2, items[1]);
		assertEquals(item1, items[2]);
	}

	@Test
	public void getNextAndRemoveShouldReturnItemsInFIFOOrder() {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		PersistentListItem item3 = new PersistentListItem("3", null);

		list.add(item1, item2, item3);

		PersistentListItem[] items = list.getItems();
		assertNotNull(items);
		assertEquals(3, items.length);

		PersistentListItem item = list.getNextAndRemove();
		assertNotNull(item);
		assertEquals(item3, item);

		item = list.getNextAndRemove();
		assertNotNull(item);
		assertEquals(item2, item);

		item = list.getNextAndRemove();
		assertNotNull(item);
		assertEquals(item1, item);
	}
}
