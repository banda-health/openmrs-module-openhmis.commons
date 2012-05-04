package org.openhmis.common;

import javax.swing.event.EventListenerList;
import java.util.EventListener;

public class FireableEventListenerList extends EventListenerList {
	public <T extends EventListener> void fire(Class<T> cls, EventRaiser<T> eventRaiser) {
		T[] listeners = this.getListeners(cls);

		for (T listener : listeners) {
			eventRaiser.fire(listener);
		}
	}
}