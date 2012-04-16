package org.openmrs.module.plm;

import org.openmrs.api.OpenmrsService;

import java.util.Collection;

public interface ListService extends OpenmrsService {
    void ensureList(List list);
    void removeList(String key);
    Collection<List> getLists();
    List getList(String key);
}

