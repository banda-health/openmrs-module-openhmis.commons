package org.openmrs.module.plm.test;

import org.openmrs.module.plm.ListItem;
import org.openmrs.module.plm.ListProvider;

public class TestListProvider implements ListProvider {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public void add(ListItem item, int index) {
	}

	@Override
	public boolean remove(ListItem item) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public ListItem[] getItems() {
		return new ListItem[0];
	}
}
