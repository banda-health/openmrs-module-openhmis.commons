package org.openmrs.module.openhmis.commons.api.f;

public interface Action2<TParm1, TParm2> {
	void apply(TParm1 parameter1, TParm2 parameter2);
}
