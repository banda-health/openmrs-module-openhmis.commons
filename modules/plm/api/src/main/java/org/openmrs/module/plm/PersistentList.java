package org.openmrs.module.plm;

public interface PersistentList {
    String getKey();
	PersistentListProvider getProvider();
    int getCount();
    void add(PersistentListItem... items);
    boolean remove(PersistentListItem item);
    void clear();
    PersistentListItem[] getItems();
    PersistentListItem getNext();
    PersistentListItem getNextAndRemove();
}

