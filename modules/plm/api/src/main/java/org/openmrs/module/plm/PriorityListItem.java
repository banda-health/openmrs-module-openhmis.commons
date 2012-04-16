package org.openmrs.module.plm;

public class PriorityListItem extends ListItem {
	private int priority;

	PriorityListItem() {
	}

	public PriorityListItem(String key) {
		this(key, 0, null);
	}

	public PriorityListItem(String key, int priority) {
		this(key, priority, null);
	}

	public PriorityListItem(String key, int priority, Object item) {
		super(key, item);

		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
