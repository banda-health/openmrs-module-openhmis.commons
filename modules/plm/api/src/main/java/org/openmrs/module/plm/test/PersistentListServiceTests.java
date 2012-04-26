package org.openmrs.module.plm.test;

import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;
import org.openmrs.module.plm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersistentListServiceTests {
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
		PersistentList list = new TestPersistentList("test");

		Assert.assertNotNull(service.getLists());
		Assert.assertEquals(0, service.getLists().length);
	}

	@Test
	public void shouldAddNewListWhenNotExisting() {
		String key = "test";
		PersistentList list = new TestPersistentList(key);

		PersistentList search = service.getList(key);
		Assert.assertNull(search);
		Assert.assertEquals(0, service.getLists().length);

		service.ensureList(list);

		Assert.assertEquals(1, service.getLists().length);
		Assert.assertEquals(list, service.getList(key));
	}

	@Test
	public void shouldNotAddOrUpdateNewListWhenExistingKey() {
		String key = "test";
		PersistentList list = new TestPersistentList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertEquals(list, service.getList(key));

		// Re-add the list to the service, no change should be made
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertEquals(list, service.getList(key));

		// Add a new list with a different key, no change should be made
		PersistentList list2 = new TestPersistentList(key);
		service.ensureList(list2);
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertEquals(list, service.getList(key));
	}

	@Test
	public void shouldRemoveListWhenExisting() {
		String key = "test";
		PersistentList list = new TestPersistentList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertEquals(list, service.getList(key));

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
		PersistentList list = new TestPersistentList(key);
		PersistentList list2 = new TestPersistentList(key2);
		PersistentList list3 = new TestPersistentList(key3);

		// Add the lists to the service
		service.ensureList(list);
		service.ensureList(list2);
		service.ensureList(list3);

		PersistentList[] lists = service.getLists();

		Assert.assertNotNull(lists);
		Assert.assertEquals(3, lists.length);
		Assert.assertEquals(list, service.getList(key));
		Assert.assertEquals(list2, service.getList(key2));
		Assert.assertEquals(list3, service.getList(key3));
	}

	@Test
	public void shouldNotReturnReferenceToInternalCollection() {
		String key = "test";
		String key2 = "test2";
		String key3 = "test3";
		PersistentList list = new TestPersistentList(key);
		PersistentList list2 = new TestPersistentList(key2);
		PersistentList list3 = new TestPersistentList(key3);

		// Add the lists to the service
		service.ensureList(list);
		service.ensureList(list2);

		PersistentList[] lists = service.getLists();

		Assert.assertNotNull(lists);
		Assert.assertEquals(2, lists.length);
		Assert.assertEquals(list, service.getList(key));
		Assert.assertEquals(list2, service.getList(key2));

		// Now add another list to the service
		service.ensureList(list3);

		// The list should not be in the list previously returned
		Assert.assertEquals(2, lists.length);
		List<PersistentList> listCollection = Arrays.asList(lists);
		Assert.assertFalse(listCollection.contains(list3));
	}

	@Test
	public void shouldReturnListByKey() {
		String key = "test";
		PersistentList list = new TestPersistentList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(list, service.getList(key));
	}

	@Test
	public void shouldReturnNullForUndefinedKeys() {
		String key = "test";
		PersistentList list = new TestPersistentList(key);

		// Add the list to the service
		service.ensureList(list);
		Assert.assertEquals(1, service.getLists().length);
		Assert.assertNull(service.getList("other"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwIllegalArgumentExceptionInEnsureListWithNullKey() {
		PersistentList list = new TestPersistentList(null);

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

