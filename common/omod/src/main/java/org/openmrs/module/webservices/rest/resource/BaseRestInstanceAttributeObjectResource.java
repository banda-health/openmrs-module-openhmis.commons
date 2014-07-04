package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.model.*;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

public abstract class BaseRestInstanceAttributeObjectResource<
		E extends BaseInstanceAttributeObject<TOwner, TAttributeType>,
		TOwner extends ICustomizableInstance<TInstanceType, E>,
		TInstanceType extends IInstanceType<TAttributeType>,
		TAttributeType extends IInstanceAttributeType<TInstanceType>>
		extends BaseRestObjectResource<E> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("value");
			description.addProperty("attributeType", Representation.REF);
			description.addProperty("order", findMethod("getAttributeOrder"));
		}

		return description;
	}

	@PropertyGetter("value")
	public Object getPropertyValue(E instance) {
		return instance.getHydratedValue();
	}

	public Integer getAttributeOrder(E instance) {
		return instance.getAttributeType().getAttributeOrder();
	}
}

