package org.openmrs.module.openhmis.commons.api.entity.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openmrs.BaseOpenmrsData;

/**
 * Base class for {@link org.openmrs.OpenmrsData} that need to support JSON serialization.
 */
public abstract class BaseSerializableOpenmrsData extends BaseOpenmrsData {
	public static final long serialVersionUID = 0L;

	@Override
	@JsonIgnore
	public Boolean getVoided() {
		return super.getVoided();
	}
}
