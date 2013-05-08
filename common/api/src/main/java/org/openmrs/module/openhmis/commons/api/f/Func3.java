package org.openmrs.module.openhmis.commons.api.f;

public interface Func3<TParm1, TParm2, TParm3, TResult> {
	TResult apply(TParm1 parameter1, TParm2 parameter2, TParm3 parameter3);
}
