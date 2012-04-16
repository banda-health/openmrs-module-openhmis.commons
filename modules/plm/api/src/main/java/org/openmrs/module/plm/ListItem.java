package org.openmrs.module.plm;

public class ListItem {
	private String key;
	private Object item;

	ListItem() {
	}

	public ListItem(String key) {
		this(key, null);
	}

	public ListItem(String key, Object item) {
		this.key = key;
		this.item = item;
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
}

