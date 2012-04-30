package org.openmrs.module.plm;

import com.sun.org.apache.xml.internal.security.Init;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhmis.common.Initializable;
import org.openhmis.common.Utility;
import org.openmrs.api.context.Context;
import org.openmrs.module.plm.model.PersistentListModel;

import java.util.*;

/*
	This type is a synchronized list manager.  Read operations are not synchronized so that they operate as fast as
	possible while ensureList may acquire a lock if the list is new and removeList will always acquire a lock if the
	list is found.
 */
public class PersistentListServiceImpl implements PersistentListService {
	private final Log log = LogFactory.getLog(PersistentListServiceImpl.class);
	private final Object syncLock = new Object();

	private Map<String, PersistentList> lists = new HashMap<String, PersistentList>();
    private boolean isLoaded = false;

	PersistentListServiceProvider provider;

	public PersistentListServiceImpl(PersistentListServiceProvider provider) {
		this.provider = provider;
		this.isLoaded = true;
	}

    @Override
    public <T extends PersistentList> PersistentList ensureList(Class<T> listClass, String key, String description) {
        if (listClass == null) {
	        throw new IllegalArgumentException("The list class must be defined.");
        }
	    if (StringUtils.isEmpty(key)) {
		    throw new IllegalArgumentException("The list must have a key.");
	    }

        //Check to see if plm has already been defined
	    PersistentList list = lists.get(key);

        //If not defined, synchronize and check again (double-check locking)
        if (list == null) {
	        synchronized (syncLock){
		        list = lists.get(key);
		        if (list == null) {
			        log.debug("Could not find the '" + key + "' list.  Creating a new list...");

			        createList(listClass, key, description);

			        log.debug("The '" + key + "' list was created.");
		        }
	        }
        }

	    return list;
    }

	@Override
	public <T extends PersistentList> PersistentList createList(Class<T> listClass, String key, String description) {
		if (listClass == null) {
			throw new IllegalArgumentException("The list class must be defined.");
		}
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("The list must have a key.");
		}

		synchronized (syncLock) {
			// Make sure no other list with the specified key exists
			if (lists.containsKey(key)) {
				throw new IllegalArgumentException("A list with the key '" + key + "' has already been added to this service.");
			}

			// Create list provider instance
			PersistentListProvider listProvider = Context.getService(PersistentListProvider.class);

			// Create list model
			PersistentListModel model = new PersistentListModel(null, key, listProvider.getClass().getName(), listClass.getName(),
					description, new Date());

			// Persist the list model
			provider.addList(model);

			// Create list instance and load properties from model
			PersistentList list = createList(model);

			// Add the list to the service list and key caches
			lists.put(list.getKey(), list);
			lists.put(key, list);

			return list;
		}
	}

    @Override
    public void removeList(String key) {
        PersistentList list = lists.get(key);
	    if (list != null) {
		    log.debug("Deleting the " + key + "list...");

		    synchronized (syncLock) {
			    provider.removeList(key);
			    lists.remove(key);
		    }

		    log.debug("The '" + key + "' list was deleted.");
	    }
    }

    @Override
    public PersistentList[] getLists() {
	    return lists.values().toArray(new PersistentList[0]);
    }

    @Override
    public PersistentList getList(String key) {
		if (StringUtils.isEmpty(key)) {
	        throw new IllegalArgumentException("The list key must be defined");
        }
	    if (!isLoaded) {
		    return null;
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

		// Lock access so that list requests will not proceed until loaded
		synchronized (syncLock) {
			PersistentListModel[] listModels = provider.getLists();
			for (PersistentListModel listModel : listModels) {
				PersistentList list = createList(listModel);

				if (list != null) {
					// Make sure the list is initialized if required
					Initializable init = Utility.as(Initializable.class, list);
					if (init != null) {
						init.initialize();
					}

					lists.put(list.getKey(), list);
				}
			}

			isLoaded = true;
		}

		log.debug("Loaded " + lists.size() + " lists.");
	}

	protected PersistentList createList(PersistentListModel model) {
		Class providerClass = null;
		Class listClass = null;

		// Load the provider and list classes
		try {

			providerClass = Class.forName(model.getProviderClass());
		} catch (Exception ex) {
			log.error("Could not load the '" + model.getProviderClass() + "' provider type.", ex);
		}

		try {
		listClass = Class.forName(model.getListClass());
		} catch (Exception ex) {
			log.error("Could not load the '" + model.getListClass() + "' list type.", ex);
		}

		if (providerClass == null || listClass == null) {
			// Either the provider or list class could not be loaded so just return null
			return null;
		}

		// Create instances of those classes
		PersistentListProvider provider = (PersistentListProvider)Context.getService(providerClass);
		Initializable init = Utility.as(Initializable.class, provider);
		if (init != null) {
			init.initialize();
		}

		PersistentList list = (PersistentList)Context.getService(listClass);
		list.load(model);
		list.setProvider(provider);

		init = Utility.as(Initializable.class, list);
		if (init != null) {
			init.initialize();
		}

		return list;

	}
}

