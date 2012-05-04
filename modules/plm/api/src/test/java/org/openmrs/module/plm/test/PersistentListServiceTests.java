package org.openmrs.module.plm.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openmrs.module.plm.*;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class PersistentListServiceTests extends BaseModuleContextSensitiveTest {
	protected PersistentListServiceProvider provider;
	protected PersistentListService service;

	@Before
	public void before() {
		provider = new TestPersistentServiceProvider();
		service = new PersistentListServiceImpl(provider);
		service.onStartup();
	}

	@Test
	public void shouldReturnEmptyListWhenNoListsDefined() {
		Assert.assertNotNull(service.getLists());
		Assert.assertEquals(0, service.getLists().length);
	}

	@Test
	public void shouldAddNewListWhenNotExisting() {
		String key = "test";
		String desc = "desc";

		PersistentList search = service.getList(key);
		Assert.assertNull(search);
		Assert.assertEquals(0, service.getLists().length);

		service.ensureList(TestPersistentList.class, key, desc);

		assertList(TestPersistentList.class, key, desc);
	}

	@Test
	public void shouldNotAddOrUpdateNewListWhenExistingKey() {
		String key = "test";
		String desc = "desc";

		// Add the list to the service
		service.ensureList(TestPersistentList.class, key, desc);
		Assert.assertEquals(1, service.getLists().length);
		PersistentList list = assertList(TestPersistentList.class, key, desc);

		// Re-add the list to the service, no change should be made
		service.ensureList(TestPersistentList.class, key, "something else");
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertEquals(list, service.getList(key));
		Assert.assertEquals(desc, service.getList(key).getDescription());
	}

	@Test
	public void shouldAddNewListWhenCreateList() {
		String key = "test";
		String desc = "desc";

		Assert.assertEquals(0, service.getLists().length);

		service.createList(TestPersistentList.class, key, desc);

		assertList(TestPersistentList.class, key, desc);
	}

	@Test
	public void shouldRemoveListWhenExisting() {
		String key = "test";

		// Add the list to the service
		service.ensureList(TestPersistentList.class, key, null);
		PersistentList list = assertList(TestPersistentList.class, key, null);

		// Now remove that list
		service.removeList(list.getKey());
		Assert.assertEquals(0, service.getLists().length);
		Assert.assertNull(service.getList(key));
	}

	@Test
	public void shouldNotThrowWhenRemovingMissingList() {
		service.removeList("test");
	}

	@Test
	public void shouldReturnAllDefinedLists() {
		String key = "test";
		String key2 = "test2";
		String key3 = "test3";

		// Add the lists to the service
		service.ensureList(TestPersistentList.class, key, null);
		service.ensureList(TestPersistentList.class, key2, null);
		service.ensureList(TestPersistentList.class, key3, null);

		PersistentList[] lists = service.getLists();

		Assert.assertNotNull(lists);
		Assert.assertEquals(3, lists.length);
	}

	@Test
	public void shouldNotReturnReferenceToInternalCollection() {
		String key = "test";
		String key2 = "test2";
		String key3 = "test3";

		// Add the lists to the service
		service.ensureList(TestPersistentList.class, key, null);
		service.ensureList(TestPersistentList.class, key2, null);

		PersistentList[] lists = service.getLists();

		Assert.assertNotNull(lists);
		Assert.assertEquals(2, lists.length);

		// Now add another list to the service
		service.ensureList(TestPersistentList.class, key3, null);

		// The list should not be in the list previously returned
		Assert.assertEquals(2, lists.length);
	}

	@Test
	public void shouldReturnListByKey() {
		String key = "test";

		// Add the list to the service
		service.ensureList(TestPersistentList.class, key, null);
		assertList(TestPersistentList.class, key, null);
	}

	@Test
	public void shouldReturnNullForUndefinedKeys() {
		String key = "test";

		// Add the list to the service
		service.ensureList(TestPersistentList.class, key, null);
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertNull(service.getList("other"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionInEnsureListWithNullKey() {
		// This should throw
		service.ensureList(TestPersistentList.class, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionInGetListWithEmptyKey() {
		// This should throw
		service.getList("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionInGetListWithNullKey() {
		// This should throw
		service.getList(null);
	}

	@Test
	public void shouldLoadListsDuringStartup() {
		Assert.fail("Not implemented");
	}

	@Test
	public void shouldFireListAddedEvent() {
		TestListServiceEventListener listener = new TestListServiceEventListener();
		service.addEventListener(listener);

		service.createList(TestPersistentList.class, "test", null);

		Assert.assertEquals(1, listener.added);
		Assert.assertEquals(0, listener.added);
	}

	@Test
	public void shouldNotFireListAddedEventWhenAlreadyExists() {
		// First, create list
		service.createList(TestPersistentList.class, "test", null);

		// Now attach the listener
		TestListServiceEventListener listener = new TestListServiceEventListener();
		service.addEventListener(listener);

		// And attempt to add a list with the same key (this won't do anything)
		service.ensureList(TestPersistentList.class, "test", null);

		// No additions should have been fired
		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(0, listener.added);
	}

	@Test
	public void shouldReferenceCorrectServiceAndListWhenListAddedEvent() {
		final String key = "test";

		service.addEventListener(new ListServiceEventListenerAdapter() {
			@Override
			public void listAdded(ListServiceEvent event) {
				Assert.assertEquals(ListServiceEvent.ServiceOperation.ADDED, event.getOperation());
				Assert.assertEquals(service, event.getSource());
				Assert.assertEquals(TestPersistentList.class, event.getList().getClass());
				Assert.assertEquals(key, event.getList().getKey());
			}
		});

		service.createList(TestPersistentList.class, key, null);
	}

	@Test
	public void shouldFireListRemovedEvent() {
		service.createList(TestPersistentList.class, "test", null);

		TestListServiceEventListener listener = new TestListServiceEventListener();
		service.addEventListener(listener);

		service.removeList("test");

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(1, listener.added);
	}

	@Test
	public void shouldNotFireListRemovedEventWhenListNotFound() {
		TestListServiceEventListener listener = new TestListServiceEventListener();
		service.addEventListener(listener);

		service.removeList("test");

		Assert.assertEquals(0, listener.added);
		Assert.assertEquals(0, listener.added);
	}

	@Test
	public void shouldReferenceCorrectServiceAndListWhenListRemovedEvent() {
		final String key = "test";
		service.createList(TestPersistentList.class, key, null);

		service.addEventListener(new ListServiceEventListenerAdapter() {
			@Override
			public void listRemoved(ListServiceEvent event) {
				Assert.assertEquals(ListServiceEvent.ServiceOperation.REMOVED, event.getOperation());
				Assert.assertEquals(service, event.getSource());
				Assert.assertEquals(TestPersistentList.class, event.getList().getClass());
				Assert.assertEquals(key, event.getList().getKey());
			}
		});

		service.removeList(key);
	}

	private PersistentList assertList(Class cls, String key, String desc) {
		PersistentList list = service.getList(key);
		Assert.assertNotNull(list);
		Assert.assertEquals(cls, list.getClass());
		Assert.assertEquals(key, list.getKey());
		Assert.assertEquals(desc, list.getDescription());

		return list;
	}

	public class TestListServiceEventListener implements ListServiceEventListener {
		public int added;
		public int removed;

		@Override
		public void listAdded(ListServiceEvent event) {
			added++;
		}

		@Override
		public void listRemoved(ListServiceEvent event) {
			removed++;
		}
	}
}
