package org.openmrs.module.plm;

import java.util.Collection;

public interface PersistentListServiceProvider {
	Collection<PersistentList> getLists();
	void addList(PersistentList list);
	void removeList(String key);
}
