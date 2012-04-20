package org.openmrs.module.plm;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.plm.model.PersistentListItemModel;

import java.util.Date;

public class PersistentListItem {
	private Integer id;
	private String key;
	private User createdBy;
	private Date createdOn;

	PersistentListItem() {
	}

	public PersistentListItem(PersistentListItemModel model) {
		this(model.getItemId(), model.getItemKey(), model.getCreatedBy(), model.getCreatedOn());
	}

	public PersistentListItem(String key) {
		this(null, key, Context.getAuthenticatedUser(), new Date());
	}

	public PersistentListItem(String key, User createdBy) {
		this(null, key, createdBy, new Date());
	}

	public PersistentListItem(String key, User createdBy, Date createdOn) {
		this(null, key, createdBy, createdOn);
	}

	public PersistentListItem(Integer id, String key, User createdBy, Date createdOn) {
		this.id = id;
		this.key = key;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	void setCreatedOn(Date createdAt) {
		this.createdOn = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}

