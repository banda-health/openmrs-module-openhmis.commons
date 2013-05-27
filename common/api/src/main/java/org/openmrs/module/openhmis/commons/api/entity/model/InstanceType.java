package org.openmrs.module.openhmis.commons.api.entity.model;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;

public interface InstanceType<TInstanceType extends OpenmrsObject, AT extends InstanceAttributeType<TInstanceType>>
		extends OpenmrsMetadata {
	public AT getAttributeTypeInstance();
}
