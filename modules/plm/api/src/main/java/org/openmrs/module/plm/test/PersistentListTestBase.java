package org.openmrs.module.plm.test;

import junit.framework.Assert;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListException;
import org.openmrs.module.plm.PersistentListItem;
import org.openmrs.module.plm.PersistentListProvider;

import java.util.Arrays;
import java.util.List;

public abstract class PersistentListTestBase {
	protected PersistentListProvider provider;
	protected PersistentList list;

	protected abstract PersistentList createList(PersistentListProvider provider);

	@Before
	public void before() {
		provider = new TestPersistentListProvider();
		list = createList(provider);
	}

	@Test
	public void getCountShouldReturnNumberOfItems() {
		Assert.assertEquals(0, list.getCount());

		list.add(new PersistentListItem("1"));
		Assert.assertEquals(1, list.getCount());

		list.add(new PersistentListItem("2"));
		Assert.assertEquals(2, list.getCount());

		list.add(new PersistentListItem("3"));
		Assert.assertEquals(3, list.getCount());
	}

	@Test
	public void shouldReturnEmptyArrayWhenNoItems() {
		Assert.assertEquals(0, list.getCount());

		PersistentListItem[] items =  list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(0, items.length);
	}

	@Test
	public void shouldReturnItemWhenAdded() {
		PersistentListItem item = new PersistentListItem("1");
		list.add(item);

		Assert.assertEquals(1, list.getCount());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);
	}

	@Test
	public void shouldAddMultipleItems() {
		PersistentListItem item = new PersistentListItem("1");
		PersistentListItem item2 = new PersistentListItem("2");
		list.add(item, item2);

		Assert.assertEquals(2, list.getCount());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(2, items.length);

		// Just check that the items are in the list, order is not important
		List<PersistentListItem> itemList = Arrays.asList(items);
		assertThat(itemList, hasItems(item, item2));
	}

	@Test(expected = PersistentListException.class)
	public void shouldThrowExceptionWhenDuplicateItemsAreAdded() {
		PersistentListItem item1 = new PersistentListItem("1");
		PersistentListItem item2 = new PersistentListItem("1");

		list.add(item1);
		list.add(item2);
	}

	@Test
	public void getItemsShouldReturnAllItems() {
		PersistentListItem item1 = new PersistentListItem("1");
		PersistentListItem item2 = new PersistentListItem("2");
		PersistentListItem item3 = new PersistentListItem("3");

		list.add(item1, item2, item3);

		Assert.assertEquals(3, list.getCount());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(3, items.length);

		// Just check that the items are in the list, order is not important
		List<PersistentListItem> itemList = Arrays.asList(items);
		assertThat(itemList, hasItems(item1, item2, item3));
	}

	@Test
	public void shouldRemoveItem() {
		PersistentListItem item = new PersistentListItem("1");
		list.add(item);

		PersistentListItem[] items = list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);

		boolean result = list.remove(item);

		Assert.assertEquals(0, list.getCount());
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldReturnFalseIfItemNotRemoved() {
		boolean result = list.remove(new PersistentListItem("1"));

		Assert.assertEquals(0, list.getCount());
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRemoveAllItemsWhenCleared() {
		PersistentListItem item = new PersistentListItem("1");
		PersistentListItem item2 = new PersistentListItem("2");
		list.add(item);
		list.add(item2);

		Assert.assertEquals(2, list.getCount());

		list.clear();

		Assert.assertEquals(0, list.getCount());
	}

	@Test
	public void shouldNotThrowExceptionWhenEmptyListIsCleared() {
		list.clear();
		Assert.assertEquals(0, list.getCount());
	}
}

