package org.openmrs.module.plm.db;

import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListServiceProvider;

import java.util.Collection;

public class DatabaseServiceProvider implements PersistentListServiceProvider {

	@Override
	public Collection<PersistentList> getLists() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void addList(PersistentList list) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void removeList(String key) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
