package org.openmrs.module.plm;

public class PriorityPersistentListItem extends PersistentListItem {
	private int priority;
	private Integer order;

	PriorityPersistentListItem() {
	}

	public PriorityPersistentListItem(String key) {
		this(key, 0, null, null);
	}

	public PriorityPersistentListItem(String key, int priority, Integer order) {
		this(key, priority, order, null);
	}

	public PriorityPersistentListItem(String key, int priority, Integer order, Object item) {
		super(key, item);

		this.priority = priority;
		this.order = order;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
