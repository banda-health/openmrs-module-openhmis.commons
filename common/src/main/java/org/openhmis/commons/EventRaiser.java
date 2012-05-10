package org.openhmis.commons;

import java.util.EventListener;

/**
 * Represents types that can fire an event.
 * @param <T> The event listener type.s
 */
public interface EventRaiser<T extends EventListener> {
	void fire(T listener);
}