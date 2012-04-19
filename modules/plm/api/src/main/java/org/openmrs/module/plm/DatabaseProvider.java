package org.openmrs.module.plm;

import org.apache.commons.lang.NotImplementedException;

public class DatabaseProvider implements PersistentListProvider {
	@Override
	public String getName() {
		return "Database List Provider";
	}

	@Override
	public String getDescription() {
		return "A persistent list provider that stores list items in an OpenMRS database.";
	}

	@Override
	public void add(PersistentListItem item, int index) {
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
}
