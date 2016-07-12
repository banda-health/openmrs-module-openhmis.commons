package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

import java.util.ArrayList;
import java.util.List;

// @formatter:off
/**
 * REST resource for {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType}s.
 * @param <E> The customizable instance attribute class
 * @param <TAttributeType> The attribute type class
 */
public abstract class BaseRestInstanceTypeResource<
			E extends IInstanceType<TAttributeType>,
			TAttributeType extends IInstanceAttributeType<E>>
        extends BaseRestMetadataResource<E> {
// @formatter:on
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("attributeTypes");
		}

		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = super.getCreatableProperties();
		description.addProperty("attributeTypes");

		return description;
	}

	public void setAttributeTypesBase(E instance, List<TAttributeType> attributeTypes) {
		if (instance.getAttributeTypes() == null) {
			instance.setAttributeTypes(new ArrayList<TAttributeType>());
		}

		BaseRestDataResource.syncCollection(instance.getAttributeTypes(), attributeTypes);
		for (TAttributeType type : instance.getAttributeTypes()) {
			type.setOwner(instance);
		}
	}
}
