package org.openmrs.module.plm;

import org.openmrs.api.OpenmrsService;

import java.util.Collection;

public interface PersistentListService extends OpenmrsService {
    void ensureList(PersistentList list);
    void removeList(String key);
    Collection<PersistentList> getLists();
    PersistentList getList(String key);
}

