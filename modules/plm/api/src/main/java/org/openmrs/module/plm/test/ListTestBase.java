package org.openmrs.module.plm.test;

import junit.framework.Assert;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.plm.List;
import org.openmrs.module.plm.ListItem;
import org.openmrs.module.plm.ListProvider;

public abstract class ListTestBase {
	protected ListProvider provider;
	protected List list;

	protected abstract List createList();

	@Before
	public void before() {
		provider = new TestListProvider();
		list = createList();
	}

	@Test
	public void shouldReturnEmptyArrayWhenNoItems() {
		ListItem[] items =  list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(0, items.length);
	}

	@Test
	public void shouldReturnItemWhenAdded() {
		ListItem item = new ListItem("1");
		list.add(item);

		ListItem[] items = list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);
	}
}

