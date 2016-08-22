package org.openmrs.module.webservices.rest.resource;

import org.openmrs.Concept;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttributeType;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

// @formatter:off
/**
 * REST resource for {@link org.openmrs.OpenmrsMetadata}
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IAttribute}s.
 * @param <E> The customizable instance attribute class
 */
public abstract class BaseRestAttributeMetadataResource<
			E extends IAttribute<?, TAttributeType> & OpenmrsMetadata,
			TAttributeType extends IAttributeType>
        extends BaseRestMetadataResource<E> {
// @formatter:on
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

	protected Object baseGetPropertyValue(E instance) {
		if (instance.getAttributeType().getFormat().contains("Concept")) {
			ConceptService service = Context.getService(ConceptService.class);
			Concept concept = service.getConcept(instance.getValue());

			return concept == null ? "" : concept.getDisplayString();
		} else {
			return instance.getHydratedValue();
		}
	}

	protected void baseSetAttributeType(E instance, TAttributeType attributeType) {
		instance.setAttributeType(attributeType);
	}

	public Integer getAttributeOrder(E instance) {
		return instance.getAttributeType().getAttributeOrder();
	}
}
