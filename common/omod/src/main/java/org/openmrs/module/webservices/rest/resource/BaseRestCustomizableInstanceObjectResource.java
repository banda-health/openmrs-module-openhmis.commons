package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.model.*;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

import java.util.HashSet;
import java.util.List;

public abstract class BaseRestCustomizableInstanceObjectResource<
		E extends BaseCustomizableInstanceObject<TInstanceType, TAttribute>,
		TInstanceType extends IInstanceType<TAttributeType>,
		TAttributeType extends IInstanceAttributeType<TInstanceType>,
		TAttribute extends IInstanceAttribute<E, TAttributeType>>
		extends BaseRestObjectResource<E> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("instanceType", Representation.REF);

		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("attributes");

			if (rep instanceof FullRepresentation) {
				description.addProperty("auditInfo", findMethod("getAuditInfo"));
			}
		}

		return description;
	}

	@PropertySetter("attributes")
	public void setAttributes(E instance, List<TAttribute> attributes) {
		if (instance.getAttributes() == null) {
			instance.setAttributes(new HashSet<TAttribute>());
		}

		BaseRestDataResource.syncCollection(instance.getAttributes(), attributes);
		for (TAttribute attribute : instance.getAttributes()) {
			attribute.setOwner(instance);
		}
	}
}

