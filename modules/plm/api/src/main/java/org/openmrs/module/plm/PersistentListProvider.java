package org.openmrs.module.plm;

import org.openmrs.module.plm.model.PersistentListItemModel;

/**
 *  Represents classes that handle loading and saving list items.
 */
public interface PersistentListProvider {
	/**
	 * Gets the name of the provider.
	 * @return The name of the provider.
	 */
    String getName();

	/**
	 * Gets a description of the provider.
	 * @return The description of the provider.
	 */
    String getDescription();

	/**
	 * Adds a new item to the list.
	 * @param item The item to add.
	 */
    void add(PersistentListItemModel item);

	/**
	 * Removes the specified item from the list.
	 * @param item The item to remove.
	 * @return {@code true} if the item was removed; otherwise, {@code false}.
	 */
    boolean remove(PersistentListItemModel item);

	/**
	 * Removes all items from the specified list.
	 * @param list The list to clear.
	 */
    void clear(PersistentList list);

	/**
	 * Gets all the items from the list in order.
	 * @param list The @see PersistentList to get.
	 * @return The items in the list.
	 */
	PersistentListItemModel[] getItems(PersistentList list);
}

