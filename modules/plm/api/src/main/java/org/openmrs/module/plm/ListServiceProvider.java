package org.openmrs.module.plm;

import java.util.Collection;

public interface ListServiceProvider {
	Collection<List> getLists();
	void addList(List list);
	void removeList(String key);
}
