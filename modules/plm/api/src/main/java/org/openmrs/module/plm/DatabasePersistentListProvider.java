package org.openmrs.module.plm;

import org.apache.commons.lang.NotImplementedException;

public class DatabasePersistentListProvider implements PersistentListProvider {
	@Override
	public String getName() {
		return "Database PersistentList Provider";
	}

	@Override
	public String getDescription() {
		return "A list provider that stores list items in an OpenMRS database.";
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
