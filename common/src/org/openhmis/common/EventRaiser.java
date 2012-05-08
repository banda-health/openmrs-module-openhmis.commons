package org.openhmis.common;

import java.util.EventListener;

public interface EventRaiser<T extends EventListener> {
	void fire(T listener);
}