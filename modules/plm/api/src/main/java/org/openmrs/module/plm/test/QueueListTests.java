package org.openmrs.module.plm.test;

import org.junit.Test;
import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListItem;
import org.openmrs.module.plm.QueuePersistentList;

public class QueueListTests extends PersistentListTestBase {
	@Override
	protected PersistentList createList() {
		return new QueuePersistentList("test", new TestPersistentListProvider());
	}

	@Test
	public void shouldReturnItemsInLIFOOrder() {
		PersistentListItem item1 = new PersistentListItem("1");
		PersistentListItem item2 = new PersistentListItem("2");
		PersistentListItem item3 = new PersistentListItem("3");

		list.add(item1);
	}
}
