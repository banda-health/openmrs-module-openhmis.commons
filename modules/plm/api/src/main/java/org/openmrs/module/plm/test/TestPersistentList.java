package org.openmrs.module.plm.test;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListItem;
import org.openmrs.module.plm.PersistentListProvider;

public class TestPersistentList implements PersistentList {
	public TestPersistentList(String key) {
		this(key, null, 0);
	}

	public TestPersistentList(String key, PersistentListProvider provider) {
		this(key, provider, 0);
	}

	public TestPersistentList(String key, PersistentListProvider provider, int count) {
		this.key = key;
		this.provider = provider;
		this.count = count;
	}

	String key;
	PersistentListProvider provider;
	int count;

	@Override
	public String getKey() {
		return key;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public PersistentListProvider getProvider() {
		return provider;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getCount() {
		return count;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void add(PersistentListItem... item) {
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
