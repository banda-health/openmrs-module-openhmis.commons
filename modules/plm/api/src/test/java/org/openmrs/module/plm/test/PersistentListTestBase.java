package org.openmrs.module.plm.test;

import junit.framework.Assert;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.plm.*;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

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
		Assert.assertEquals(0, list.getSize());

		list.add(new PersistentListItem("1", null));
		Assert.assertEquals(1, list.getSize());

		list.add(new PersistentListItem("2", null));
		Assert.assertEquals(2, list.getSize());

		list.add(new PersistentListItem("3", null));
		Assert.assertEquals(3, list.getSize());
	}

	@Test
	public void shouldReturnEmptyArrayWhenNoItems() {
		Assert.assertEquals(0, list.getSize());

		PersistentListItem[] items =  list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(0, items.length);
	}

	@Test
	public void shouldReturnItemWhenAdded() {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		Assert.assertEquals(1, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);
	}

	@Test
	public void shouldAddMultipleItems() {
		PersistentListItem item = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		list.add(item, item2);

		Assert.assertEquals(2, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(2, items.length);

		// Just check that the items are in the list, order is not important
		List<PersistentListItem> itemList = Arrays.asList(items);
		assertThat(itemList, hasItems(item, item2));
	}

	@Test(expected = PersistentListException.class)
	public void shouldThrowExceptionWhenDuplicateItemsAreAdded() {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("1", null);

		list.add(item1);
		list.add(item2);
	}

	@Test
	public void getItemsShouldReturnAllItems() {
		PersistentListItem item1 = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		PersistentListItem item3 = new PersistentListItem("3", null);

		list.add(item1, item2, item3);

		Assert.assertEquals(3, list.getSize());

		PersistentListItem[] items = list.getItems();
		Assert.assertNotNull(items);
		Assert.assertEquals(3, items.length);

		// Just check that the items are in the list, order is not important
		List<PersistentListItem> itemList = Arrays.asList(items);
		assertThat(itemList, hasItems(item1, item2, item3));
	}

	@Test
	public void shouldRemoveItem() {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		PersistentListItem[] items = list.getItems();

		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.length);
		Assert.assertEquals(item, items[0]);

		boolean result = list.remove(item);

		Assert.assertEquals(0, list.getSize());
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldReturnFalseIfItemNotRemoved() {
		boolean result = list.remove(new PersistentListItem("1", null));

		Assert.assertEquals(0, list.getSize());
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRemoveAllItemsWhenCleared() {
		PersistentListItem item = new PersistentListItem("1", null);
		PersistentListItem item2 = new PersistentListItem("2", null);
		list.add(item);
		list.add(item2);

		Assert.assertEquals(2, list.getSize());

		list.clear();

		Assert.assertEquals(0, list.getSize());
	}

	@Test
	public void shouldNotThrowExceptionWhenEmptyListIsCleared() {
		list.clear();
		Assert.assertEquals(0, list.getSize());
	}

	@Test
	public void getNextShouldNotRemoveItem() {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		assertEquals(1, list.getSize());
		item = list.getNext();

		assertNotNull(item);
		assertEquals(1, list.getSize());
	}

	@Test
	public void getNextShouldReturnNullWhenEmpty() {
		PersistentListItem item = list.getNext();

		assertNull(item);
	}

	@Test
	public void getNextAndRemoveShouldRemoveItem() {
		PersistentListItem item = new PersistentListItem("1", null);
		list.add(item);

		assertEquals(1, list.getSize());

		item = list.getNextAndRemove();

		assertNotNull(item);
		assertEquals(0, list.getSize());
	}

	@Test
	public void getNextAndRemoveShouldReturnNullWhenEmpty() {
		PersistentListItem item = list.getNextAndRemove();

		assertNull(item);
	}

	@Test
	public void shouldFireAddEvent() {
		TestListEventListener listener = new TestListEventListener();
		list.addListEventListener(listener);

		PersistentListItem item = new PersistentListItem("key", null);
		list.add(item);

		Assert.assertEquals(1, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	@Test
	public void shouldFireAddEventForEachItemAdded() {
		TestListEventListener listener = new TestListEventListener();
		list.addListEventListener(listener);

		PersistentListItem item = new PersistentListItem("key", null);
		PersistentListItem item2 = new PersistentListItem("key2", null);
		PersistentListItem item3 = new PersistentListItem("key3", null);
		list.add(item, item2, item3);

		Assert.assertEquals(3, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	@Test
	public void shouldReferenceCorrectListAndItemWhenItemAddedEvent() {
		final PersistentListItem item = new PersistentListItem("key", null);

		list.addListEventListener(new ListEventListenerAdapter() {
			@Override
			public void itemAdded(ListEvent event) {
				Assert.assertEquals(list, event.getSource());
				Assert.assertEquals(item, event.getItem());
				Assert.assertEquals(ListEvent.ListOperation.ADDED, event.getOperation());
			}
		});

		list.add(item);
	}

	@Test
	public void shouldFireRemoveEvent() {
		PersistentListItem item = new PersistentListItem("key", null);
		list.add(item);

		TestListEventListener listener = new TestListEventListener();
		list.addListEventListener(listener);

		list.remove(item);

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(1, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	@Test
	public void shouldNotFireRemoveEventForItemsNotInList() {
		TestListEventListener listener = new TestListEventListener();
		list.addListEventListener(listener);

		PersistentListItem item = new PersistentListItem("key", null);
		list.remove(item);

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(0, listener.cleared);
	}

	@Test
	public void shouldReferenceCorrectListAndItemWhenItemRemovedEvent() {
		final PersistentListItem item = new PersistentListItem("key", null);

		list.addListEventListener(new ListEventListenerAdapter() {
			@Override
			public void itemRemoved(ListEvent event) {
				Assert.assertEquals(list, event.getSource());
				Assert.assertEquals(item, event.getItem());
				Assert.assertEquals(ListEvent.ListOperation.REMOVED, event.getOperation());
			}
		});

		list.remove(item);
	}

	@Test
	public void shouldFireClearEvent() {
		TestListEventListener listener = new TestListEventListener();
		list.addListEventListener(listener);

		list.clear();

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(0, listener.removed);
		Assert.assertEquals(1, listener.cleared);
	}

	@Test
	public void shouldReferenceCorrectListAndNullItemWhenListClearedEvent() {
		list.addListEventListener(new ListEventListenerAdapter() {
			@Override
			public void listCleared(ListEvent event) {
				Assert.assertEquals(list, event.getSource());
				Assert.assertNull(event.getItem());
				Assert.assertEquals(ListEvent.ListOperation.CLEARED, event.getOperation());
			}
		});

		list.clear();
	}

	private class TestListEventListener implements ListEventListener {
		public int added;
		public int removed;
		public int cleared;

		public void reset() {
			added = 0;
			removed = 0;
			cleared = 0;
		}

		@Override
		public void itemAdded(ListEvent event) {
			added++;
		}

		@Override
		public void itemRemoved(ListEvent event) {
			removed++;
		}

		@Override
		public void listCleared(ListEvent event) {
			cleared++;
		}
	}
}

