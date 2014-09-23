package org.openmrs.module.webservices.rest.resource;

import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseInstanceAttributeObject;
import org.openmrs.module.openhmis.commons.api.entity.model.ICustomizableInstance;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

/**
 * REST resource for {@link org.openmrs.OpenmrsObject}
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttribute}s.
 * @param <E> The customizable instance attribute class
 * @param <TOwner> The owning model class
 * @param <TInstanceType> The instance type class
 * @param <TAttributeType> The attribute type class
 */
public abstract class BaseRestInstanceAttributeObjectResource<//
E extends BaseInstanceAttributeObject<TOwner, TAttributeType>, //
TOwner extends ICustomizableInstance<TInstanceType, E>, //
TInstanceType extends IInstanceType<TAttributeType>, //
TAttributeType extends IInstanceAttributeType<TInstanceType>> //
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
	public Object getValue(E instance) {
		if (instance.getAttributeType().getFormat().contains("Concept")) {
			ConceptService service = Context.getService(ConceptService.class);
			Concept concept = service.getConcept(instance.getValue());
			
			return concept == null ? "" : concept.getDisplayString();
		} else {
			return instance.getValue();
		}
	}
	
	public Integer getAttributeOrder(E instance) {
		return instance.getAttributeType().getAttributeOrder();
	}
}
