package org.openmrs.module.plm;

public interface ListProvider {
    String getName();
    String getDescription();
    void add(ListItem item);
    boolean remove(ListItem item);
    void clear();
    ListItem[] getItems();
}

