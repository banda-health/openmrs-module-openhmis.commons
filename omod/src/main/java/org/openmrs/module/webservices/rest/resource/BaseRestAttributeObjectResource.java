package org.openmrs.module.webservices.rest.resource;

import org.openmrs.Concept;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttributeType;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;

// @formatter:off
/**
 * REST resource for {@link org.openmrs.OpenmrsObject}
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IAttribute}s.
 * @param <E> The customizable instance attribute class
 */
public abstract class BaseRestAttributeObjectResource<
			E extends IAttribute<?, TAttributeType> & OpenmrsObject,
			TAttributeType extends IAttributeType>
        extends BaseRestObjectResource<E> {
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

	@PropertyGetter("value")
	public Object getValue(E instance) {
		if (instance.getAttributeType().getFormat().contains("Concept")) {
			ConceptService service = Context.getService(ConceptService.class);
			Concept concept = service.getConcept(instance.getValue());

			return concept == null ? "" : concept.getDisplayString();
		} else {
			return instance.getHydratedValue();
		}
	}

	@PropertySetter("attributeType")
	public void setAttributeType(E instance, TAttributeType attributeType) {
		instance.setAttributeType(attributeType);
	}

	public Integer getAttributeOrder(E instance) {
		return instance.getAttributeType().getAttributeOrder();
	}
}
