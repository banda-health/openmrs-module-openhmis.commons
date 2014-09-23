package org.openmrs.module.webservices.rest.resource;

import java.util.HashSet;
import java.util.List;

import org.openmrs.module.openhmis.commons.api.entity.model.BaseCustomizableInstanceData;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

/**
 * REST resource for {@link org.openmrs.OpenmrsData}
 * {@link org.openmrs.module.openhmis.commons.api.entity.model.ICustomizableInstance}s.
 * @param <E> The customizable instance model class
 * @param <TInstanceType> The instance type class
 * @param <TAttributeType> The attribute type class
 * @param <TAttribute> The model attribute class
 */
public abstract class BaseRestCustomizableInstanceDataResource<//
E extends BaseCustomizableInstanceData<TInstanceType, TAttribute>, //
TInstanceType extends IInstanceType<TAttributeType>, //
TAttributeType extends IInstanceAttributeType<TInstanceType>, //
TAttribute extends IInstanceAttribute<E, TAttributeType>> //
        extends BaseRestDataResource<E> {
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		
		if (rep instanceof RefRepresentation) {
			description.addProperty("instanceType", Representation.REF);
		} else {
			description.addProperty("instanceType");
			description.addProperty("attributes");
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
