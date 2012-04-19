package org.openmrs.module.plm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhmis.common.Initializable;
import org.openhmis.common.Utility;

import java.util.*;

/*
	Synchronized list manager.  Read operations are not	synchronized so that they operate as fast as possible while
	ensureList may acquire a lock if the list is new and removeList will always acquire a lock if the list is found.
 */
public class PersistentListServiceImpl implements PersistentListService {
	private Log log = LogFactory.getLog(PersistentListServiceImpl.class);

	private Map<String, PersistentList> lists = new HashMap<String, PersistentList>();
    private final Object syncLock = new Object();

	PersistentListServiceProvider provider;

	public PersistentListServiceImpl(PersistentListServiceProvider provider) {
		this.provider = provider;
	}

    @Override
    public void ensureList(PersistentList list) {
        if (list == null) {
	        throw new IllegalArgumentException("The list to ensure must be defined.");
        }

	    String key = list.getKey();
	    if (StringUtils.isEmpty(key)) {
		    throw new IllegalArgumentException("The list must have a key.");
	    }

        //Check to see if plm has already been defined
	    PersistentList temp = lists.get(key);

        //If not defined, synchronize and check again (double-check locking)
        if (temp == null) {
	        synchronized (syncLock){
		        temp = lists.get(key);
		        if (temp == null) {
			        log.debug("Could not find the '" + key + "' list.  Creating a new list...");

			        //  If still not found, create a new plm in db
					createList(key, list);
			        
			        //  Add to map
			        lists.put(key, list);

			        log.debug("The '" + key + "' list was created.");
		        }
	        }
        }
    }

    @Override
    public void removeList(String key) {
        PersistentList temp = lists.get(key);
	    if (temp != null) {
		    log.debug("Deleting the " + key + "list...");

		    synchronized (syncLock) {
			    deleteList(temp);
		    }

		    log.debug("The '" + key + "' list was deleted.");
	    }
    }

    @Override
    public Collection<PersistentList> getLists() {
	    return new ArrayList<PersistentList>(lists.values());
    }

    @Override
    public PersistentList getList(String key) {
        if (StringUtils.isEmpty(key)) {
	        throw new IllegalArgumentException("The list key must be defined");
        }

	    return lists.get(key);
    }

    @Override
    public void onStartup() {
        // Load lists from the database
	    loadLists();
    }

    @Override
    public void onShutdown() {

    }

	private void loadLists() {
		// Load lists from the database
		log.debug("Loading the configured lists from the database...");

		Collection<PersistentList> providerLists = provider.getLists();
		for (PersistentList list : providerLists) {
			// Make sure the list is initialized if required
			Initializable init = Utility.as(Initializable.class, list);
			if (init != null) {
				init.initialize();
			}

			lists.put(list.getKey(), list);
		}

		log.debug("Loaded " + lists.size() + " lists.");
	}

	private void createList(String key, PersistentList list) {
		provider.addList(list);
		lists.put(list.getKey(), list);
	}
	
	private void deleteList(PersistentList list) {
		provider.removeList(list.getKey());
		lists.remove(list.getKey());
	}
}

