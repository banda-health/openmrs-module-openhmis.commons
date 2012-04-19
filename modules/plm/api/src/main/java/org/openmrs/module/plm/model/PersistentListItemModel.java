package org.openmrs.module.plm.model;

import org.openmrs.User;

import java.util.Date;

public class PersistentListItemModel {
	private int itemId;
	private int listId;
	private int itemOrder;
	private String itemKey;
	private User createdBy;
	private Date createdOn;
	
}
