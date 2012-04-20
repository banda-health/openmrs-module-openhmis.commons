package org.openmrs.module.plm.model;

import org.openmrs.User;
import org.openmrs.module.plm.PersistentList;
import org.openmrs.module.plm.PersistentListItem;

import java.util.Date;

public class PersistentListItemModel {
	private int listId;
	private Integer itemId;
	private int primaryOrder;
	private Integer secondaryOrder;
	private Integer tertiaryOrder;
	private String itemKey;
	private User createdBy;
	private Date createdOn;

	public PersistentListItemModel(PersistentList list, String key, int primaryOrder, User createdBy) {
		this(list.getId(), key, primaryOrder, null, null, createdBy, new Date());
	}

	public PersistentListItemModel(int listId, String key, int primaryOrder, User createdBy) {
		this(listId, key, primaryOrder, null, null, createdBy, new Date());
	}

	public PersistentListItemModel(PersistentList list, String key, int primaryOrder, Integer secondaryOrder, User createdBy) {
		this(list.getId(), key, primaryOrder, secondaryOrder, null, createdBy, new Date());
	}

	public PersistentListItemModel(PersistentList list, String key, int primaryOrder, Integer secondaryOrder, Integer tertiaryOrder, User createdBy) {
		this(list.getId(), key, primaryOrder, secondaryOrder, tertiaryOrder, createdBy, new Date());
	}

	public PersistentListItemModel(PersistentList list, String key, int primaryOrder, Integer secondaryOrder, Integer tertiaryOrder,
	                               User createdBy, Date createdOn) {
		this(list.getId(), key, primaryOrder, secondaryOrder, tertiaryOrder, createdBy, createdOn);
	}

	public PersistentListItemModel(int listId, String key, int primaryOrder, Integer secondaryOrder, Integer tertiaryOrder,
	                               User createdBy, Date createdOn) {
		this.listId = listId;
		this.itemKey = key;
		this.primaryOrder = primaryOrder;
		this.secondaryOrder = secondaryOrder;
		this.tertiaryOrder = tertiaryOrder;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public int getPrimaryOrder() {
		return primaryOrder;
	}

	public void setPrimaryOrder(int primaryOrder) {
		this.primaryOrder = primaryOrder;
	}

	public int getSecondaryOrder() {
		return secondaryOrder;
	}

	public void setSecondaryOrder(int secondaryOrder) {
		this.secondaryOrder = secondaryOrder;
	}

	public int getTertiaryOrder() {
		return tertiaryOrder;
	}

	public void setTertiaryOrder(int tertiaryOrder) {
		this.tertiaryOrder = tertiaryOrder;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
