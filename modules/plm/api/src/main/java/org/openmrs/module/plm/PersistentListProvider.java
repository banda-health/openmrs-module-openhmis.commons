package org.openmrs.module.plm;

public interface PersistentListProvider {
    String getName();
    String getDescription();
    void add(PersistentListItem item, int index) throws PersistentListException;
    boolean remove(PersistentListItem item) throws PersistentListException;
    void clear() throws PersistentListException;
    PersistentListItem[] getItems() throws PersistentListException;
}

