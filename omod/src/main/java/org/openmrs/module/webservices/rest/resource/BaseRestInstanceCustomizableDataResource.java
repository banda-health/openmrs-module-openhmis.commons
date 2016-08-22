package org.openmrs.module.webservices.rest.resource;

import org.openmrs.OpenmrsData;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceCustomizable;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType;

// @formatter:off
/**
 * REST resource for {@link org.openmrs.OpenmrsData}
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceCustomizable}s.
 * @param <E> The customizable instance model class
 * @param <TAttribute> The model attribute class
 */
public abstract class BaseRestInstanceCustomizableDataResource<
			E extends IInstanceCustomizable<TInstanceType, TAttribute> & OpenmrsData,
			TInstanceType extends IInstanceType<?>,
			TAttribute extends IInstanceAttribute<E, ?, ?>>
        extends BaseRestCustomizableDataResource<E, TAttribute> {
// @formatter:on
}
