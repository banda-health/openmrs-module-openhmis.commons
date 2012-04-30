package org.openmrs.module.plm;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.plm.model.PersistentListModel;

public class TestPersistentList implements PersistentList {
	public TestPersistentList(String key) {
		this(key, new TestPersistentListProvider(), 0);
	}

	public TestPersistentList(String key, PersistentListProvider provider) {
		this(key, provider, 0);
	}

	public TestPersistentList(String key, PersistentListProvider provider, int count) {
		this.key = key;
		this.provider = provider;
		this.count = count;
	}

	Integer id;
	String key;
	String description;
	PersistentListProvider provider;
	int count;

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public PersistentListProvider getProvider() {
		return provider;
	}

	@Override
	public void setProvider(PersistentListProvider provider) {
		this.provider = provider;
	}

	@Override
	public int getSize() {
		return count;
	}

	@Override
	public void load(PersistentListModel model) {
		throw new NotImplementedException();
	}

	@Override
	public void add(PersistentListItem... items) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remove(PersistentListItem item) {
		throw new NotImplementedException();
	}

	@Override
	public void clear() {
		throw new NotImplementedException();
	}

	@Override
	public PersistentListItem[] getItems() {
		throw new NotImplementedException();
	}

	@Override
	public PersistentListItem getNext() {
		throw new NotImplementedException();
	}

	@Override
	public PersistentListItem getNextAndRemove() {
		throw new NotImplementedException();
	}
}
