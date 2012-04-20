package org.openmrs.module.plm;

public interface PersistentList {
    String getKey();
	Integer getId();
	PersistentListProvider getProvider();

	void add(PersistentListItem... items);
    boolean remove(PersistentListItem item);
    void clear();

	PersistentListItem[] getItems();
    PersistentListItem getNext();
    PersistentListItem getNextAndRemove();
	int getCount();
}

