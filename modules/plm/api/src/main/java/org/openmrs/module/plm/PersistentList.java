package org.openmrs.module.plm;

import org.openmrs.module.plm.model.PersistentListModel;

public interface PersistentList {
	String getKey();
	String getDescription();
	void setDescription(String description);
	Integer getId();
	PersistentListProvider getProvider();
	void setProvider(PersistentListProvider provider);

	void load(PersistentListModel model);
	void add(PersistentListItem... items);
	boolean remove(PersistentListItem item);
	void clear();

	PersistentListItem[] getItems();
	PersistentListItem getNext();
	PersistentListItem getNextAndRemove();
	int getCount();
}

