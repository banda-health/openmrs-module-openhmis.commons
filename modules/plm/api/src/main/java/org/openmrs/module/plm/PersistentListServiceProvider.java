package org.openmrs.module.plm;

import org.openmrs.module.plm.model.PersistentListModel;

public interface PersistentListServiceProvider {
	PersistentListModel[] getLists();
	void addList(PersistentListModel list);
	void removeList(String key);
}
