package org.openmrs.module.plm.test;

import org.openmrs.module.plm.PersistentListItem;
import org.openmrs.module.plm.PersistentListProvider;

public class TestPersistentListProvider implements PersistentListProvider {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public void add(PersistentListItem item, int index) {
	}

	@Override
	public boolean remove(PersistentListItem item) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public PersistentListItem[] getItems() {
		return new PersistentListItem[0];
	}
}
