package org.openmrs.module.plm;

public class ListItem {
	private String key;
	private Object item;
	private Integer order;

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

	Integer getOrder() {
		return order;
	}

	void setOrder(Integer order) {
		this.order = order;
	}
}

