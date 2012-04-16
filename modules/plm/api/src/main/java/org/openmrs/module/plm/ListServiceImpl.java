package org.openmrs.module.plm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhmis.common.Utility;

import java.util.*;

/*
	Synchronized list manager.  Read operations are not	synchronized so that they operate as fast as possible while
	ensureList may acquire a lock if the list is new and removeList will always acquire a lock if the list is found.
 */
public class ListServiceImpl implements ListService {
	private Log log = LogFactory.getLog(ListServiceImpl.class);

	private Map<String, List> lists = new HashMap<String, List>();
    private final Object syncLock = new Object();

	ListServiceProvider provider;

	public ListServiceImpl(ListServiceProvider provider) {
		this.provider = provider;
	}

    @Override
    public void ensureList(List list) {
        if (list == null) {
	        throw new IllegalArgumentException("The list to ensure must be defined.");
        }

	    String key = list.getKey();
	    if (Utility.isNullOrEmpty(key)) {
		    throw new IllegalArgumentException("The list must have a key.");
	    }

        //Check to see if plm has already been defined
	    List temp = lists.get(key);

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
        List temp = lists.get(key);
	    if (temp != null) {
		    log.debug("Deleting the " + key + "list...");

		    synchronized (syncLock) {
			    deleteList(temp);
		    }

		    log.debug("The '" + key + "' list was deleted.");
	    }
    }

    @Override
    public Collection<List> getLists() {
	    return new ArrayList<List>(lists.values());
    }

    @Override
    public List getList(String key) {
        if (Utility.isNullOrEmpty(key)) {
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

		Collection<List> providerLists = provider.getLists();
		for (Iterator<List> iterator = providerLists.iterator(); iterator.hasNext(); ) {
			List list = iterator.next();

			// Make sure the list is initialized if required
			Initializable init = Utility.as(Initializable.class, list);
			if (init != null) {
				init.initialize();
			}

			lists.put(list.getKey(), list);
		}

		log.debug("Loaded " + lists.size() + " lists.");
	}

	private void createList(String key, List list) {
		provider.addList(list);
		lists.put(list.getKey(), list);
	}
	
	private void deleteList(List list) {
		provider.removeList(list.getKey());
		lists.remove(list.getKey());
	}
}

