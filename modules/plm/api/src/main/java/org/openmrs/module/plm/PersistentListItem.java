package org.openmrs.module.plm;

import java.util.Date;

public class PersistentListItem {
	private String key;
	private Object item;
	private Date createdAt;

	PersistentListItem() {
	}

	public PersistentListItem(String key) {
		this(key, null);
	}

	public PersistentListItem(String key, Object item) {
		this.key = key;
		this.item = item;

		createdAt = new Date();
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}

