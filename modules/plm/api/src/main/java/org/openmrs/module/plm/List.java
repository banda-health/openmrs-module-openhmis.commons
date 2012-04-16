package org.openmrs.module.plm;

public interface List {
    String getKey();
	ListProvider getProvider();
    int getCount();
    void add(ListItem item) throws Exception;
    boolean remove(ListItem item) throws Exception;
    void clear() throws Exception;
    ListItem[] getItems();
    ListItem getNext();
    ListItem getNextAndRemove();
}

