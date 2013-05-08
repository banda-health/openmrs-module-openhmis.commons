package org.openmrs.module.openhmis.commons.api.f;

public interface Func2<TParm1, TParm2, TResult> {
	TResult apply(TParm1 parameter1, TParm2 parameter2);
}

