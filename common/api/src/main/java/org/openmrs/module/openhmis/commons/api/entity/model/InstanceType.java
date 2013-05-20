package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.OpenmrsMetadata;

public interface InstanceType<TInstanceType extends InstanceType<TInstanceType>>
		extends OpenmrsMetadata {
	public InstanceAttributeType<TInstanceType> getAttributeTypeInstance();
}
