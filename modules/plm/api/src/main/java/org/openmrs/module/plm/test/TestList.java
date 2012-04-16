package org.openmrs.module.plm.test;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.plm.List;
import org.openmrs.module.plm.ListItem;
import org.openmrs.module.plm.ListProvider;

public class TestList implements List {
	public TestList(String key) {
		this(key, null, 0);
	}

	public TestList(String key, ListProvider provider) {
		this(key, provider, 0);
	}

	public TestList(String key, ListProvider provider, int count) {
		this.key = key;
		this.provider = provider;
		this.count = count;
	}

	String key;
	ListProvider provider;
	int count;

	@Override
	public String getKey() {
		return key;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ListProvider getProvider() {
		return provider;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getCount() {
		return count;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void add(ListItem item) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public boolean remove(ListItem item) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public void clear() throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public ListItem[] getItems() {
		throw new NotImplementedException();
	}

	@Override
	public ListItem getNext() {
		throw new NotImplementedException();
	}

	@Override
	public ListItem getNextAndRemove() {
		throw new NotImplementedException();
	}
}
