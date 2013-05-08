package org.openmrs.module.openhmis.commons.api.f;

/**
 * Represents a function that can be executed, returning an object.
 * @param <TResult> The class of the return object.
 */
public interface Func<TResult> {
	TResult apply();
}

