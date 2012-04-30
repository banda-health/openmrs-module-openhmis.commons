package org.openmrs.module.plm;

import org.openmrs.module.plm.PersistentListServiceProvider;
import org.openmrs.module.plm.model.PersistentListModel;

public class TestPersistentServiceProvider implements PersistentListServiceProvider {
	@Override
	public PersistentListModel[] getLists() {
		return new PersistentListModel[0];
	}

	@Override
	public void addList(PersistentListModel list) {

	}

	@Override
	public void removeList(String key) {

	}
}
