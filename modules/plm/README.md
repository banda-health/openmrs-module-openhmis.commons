#The Persistent List Manager Module
The persisent list manager (plm) is a module that allows other modules to store and retrive items that are stored persistently.  If persistent storage is not required, this module should *not* be used.  The intent for this module is to provide a very basic workflow-like process so that other modules can put patients or other entities into persistent lists as needed.  These lists can be implemented as any data structure so that retrieval can be automatic; for example, a waiting room may need to have a persistent list that is implemented as a queue (first in, first out).

##Example Usage
The general usage is as follows:

####From module 1
    //Get the module service from the OpenMRS service context
    PersistentListService service = Context.getService(PersistentListService.class);
    
    // Ensure the list exists (creating it if it does not)
    PersistentList list = service.ensureList(PersistentQueue.class, "waitingRoom");
    
    // OR Get and Create the list
    PersistentList list = service.getList("waitingRoom");
    if (list == null) {
    	list = service.createList(PersistentQueue.class, "waitingRoom");
    }
    
    // Create some new items for the list
    PersistentListItem item1 = new PersistentListItem("person1_id")
    PersistentListItem item2 = new PersistentListItem("person2_id")
    PersistentListItem item3 = new PersistentListItem("person3_id")
    
    //Add the items to the list
    list.add(item1, item2, item3);

###From module 2
    // Get the service and list as above
    PersistentList list = ...
    
    // Get the list items (in the order defined by the list type)
    PersistentListItem[] items = list.getItems();
    
    // You can also just get the next item
    PersistentListItem item1 = list.getNext();
    
    // Or get the next item and remove it
    PersistentListItem item1Again = list.getNextAndRemove();

##Events
*Not yet implmeneted*

    PersistentListService service = ...
    service.onListAdded(...);
    service.onListRemoved(...);
    
    PersistentList list = ...
    list.onItemAdded(...);
    list.onItemRemoved(...);
    list.onCleared(...);