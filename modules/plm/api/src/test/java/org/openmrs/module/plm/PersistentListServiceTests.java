package org.openmrs.module.plm;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		throw new NotImplementedException();
	}

	private PersistentList assertList(Class cls, String key, String desc) {
		PersistentList list = service.getList(key);
		Assert.assertNotNull(list);
		Assert.assertEquals(cls, list.getClass());
		Assert.assertEquals(key, list.getKey());
		Assert.assertEquals(desc, list.getDescription());

		return list;
	}
}

