package org.openmrs.module.plm.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.plm.List;
import org.openmrs.module.plm.ListService;
import org.openmrs.module.plm.ListServiceImpl;

import java.util.Collection;

public class ListServiceTests {
	protected ListService service;

	@Before
	public void before() {
		service = new ListServiceImpl();
	}

	@Test
	public void shouldReturnEmptyListWhenNoListsDefined() {
		List list = new TestList("test");

		Assert.assertNotNull(service.getLists());
		Assert.assertEquals(0, service.getLists().size());
	}

	@Test
	public void shouldAddNewListWhenNotExisting() {
		String key = "test";
		List list = new TestList(key);

		List search = service.getList(key);
		Assert.assertNull(search);
		Assert.assertEquals(0, service.getLists().size());

		service.ensureList(list);

		Assert.assertEquals(1, service.getLists().size());
		Assert.assertEquals(list, service.getList(key));
	}

	@Test
	public void shouldNotAddOrUpdateNewListWhenExistingKey() {
		String key = "test";
		List list = new TestList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().size());
		Assert.assertEquals(list, service.getList(key));

		// Re-add the list to the service, no change should be made
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().size());
		Assert.assertEquals(list, service.getList(key));

		// Add a new list with a different key, no change should be made
		List list2 = new TestList(key);
		service.ensureList(list2);
		Assert.assertEquals(1, service.getLists().size());
		Assert.assertEquals(list, service.getList(key));
	}

	@Test
	public void shouldRemoveListWhenExisting() {
		String key = "test";
		List list = new TestList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().size());
		Assert.assertEquals(list, service.getList(key));

		// Now remove that list
		service.removeList(list.getKey());
		Assert.assertEquals(0, service.getLists().size());
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
		List list = new TestList(key);
		List list2 = new TestList(key2);
		List list3 = new TestList(key3);

		// Add the lists to the service
		service.ensureList(list);
		service.ensureList(list2);
		service.ensureList(list3);

		Collection<List> lists = service.getLists();

		Assert.assertNotNull(lists);
		Assert.assertEquals(3, lists.size());
		Assert.assertEquals(list, service.getList(key));
		Assert.assertEquals(list2, service.getList(key2));
		Assert.assertEquals(list3, service.getList(key3));
	}

	@Test
	public void shouldNotReturnReferenceToInternalCollection() {
		String key = "test";
		String key2 = "test2";
		String key3 = "test3";
		List list = new TestList(key);
		List list2 = new TestList(key2);
		List list3 = new TestList(key3);

		// Add the lists to the service
		service.ensureList(list);
		service.ensureList(list2);

		Collection<List> lists = service.getLists();

		Assert.assertNotNull(lists);
		Assert.assertEquals(3, lists.size());
		Assert.assertEquals(list, service.getList(key));
		Assert.assertEquals(list2, service.getList(key2));

		// Now add another list to the service
		service.ensureList(list3);

		// The list should not be in the list previously returned
		Assert.assertNull(service.getList(key3));
	}

	@Test
	public void shouldReturnListByKey() {
		String key = "test";
		List list = new TestList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(list, service.getList(key));
	}

	@Test
	public void shouldReturnNullForUndefinedKeys() {
		String key = "test";
		List list = new TestList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().size());
		Assert.assertNull(service.getList("other"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionInEnsureListWithNullKey() {
		List list = new TestList(null);

		// This should throw
		service.ensureList(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionInEnsureListWithNullList() {
		// This should throw
		service.ensureList(null);
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

	}
}

