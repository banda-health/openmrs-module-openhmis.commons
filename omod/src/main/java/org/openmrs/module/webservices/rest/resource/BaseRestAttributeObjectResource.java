package org.openmrs.module.webservices.rest.resource;

import org.openmrs.Concept;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IAttributeType;
import org.openmrs.module.webservices.rest.util.ReflectionUtil;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ConversionException;

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
			description.addProperty("order", ReflectionUtil.findMethod(getClass(), "getAttributeOrder"));
		}

		return description;
	}

	@Override
	public void setProperty(Object instance, String propertyName, Object value) throws ConversionException {
		// Since the order property is from the attribute type and isn't modifyable, don't do anything if it's being set
		if (propertyName.equals("order")) {
			return;
		}
		super.setProperty(instance, propertyName, value);
	}

	protected Object baseGetValue(E instance) {
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
