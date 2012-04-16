package org.openmrs.module.plm;

import org.apache.commons.lang.NotImplementedException;

public class DatabaseListProvider implements ListProvider {
	@Override
	public String getName() {
		return "Database List Provider";
	}

	@Override
	public String getDescription() {
		return "A list provider that stores list items in an OpenMRS database.";
	}

	@Override
	public void add(ListItem item, int index) {
		throw new NotImplementedException();
	}

	@Override
	public boolean remove(ListItem item) {
		throw new NotImplementedException();
	}

	@Override
	public void clear() {
		throw new NotImplementedException();
	}

	@Override
	public ListItem[] getItems() {
		throw new NotImplementedException();
	}
}
