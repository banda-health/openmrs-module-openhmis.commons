package org.openmrs.module.webservices.rest.resource;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceCustomizable;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

// @formatter:off
/**
 * REST resource for {@link org.openmrs.OpenmrsObject}
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceCustomizable}s.
 * @param <E> The customizable instance model class
 * @param <TAttribute> The model attribute class
 */
public abstract class BaseRestInstanceCustomizableObjectResource<
			E extends IInstanceCustomizable<TInstanceType, TAttribute> & OpenmrsObject,
			TInstanceType extends IInstanceType<?>,
			TAttribute extends IInstanceAttribute<E, ?, ?>>
        extends BaseRestCustomizableObjectResource<E, TAttribute> {
// @formatter:on
}
