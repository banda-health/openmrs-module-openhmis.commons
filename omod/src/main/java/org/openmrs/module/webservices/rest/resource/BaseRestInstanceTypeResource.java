package org.openmrs.module.webservices.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.openhmis.commons.api.entity.model.ICustomizableInstance;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttribute;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType;
import org.openmrs.module.openhmis.commons.api.entity.model.IInstanceType;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

/**
 * REST resource for {@link org.openmrs.module.openhmis.commons.api.entity.model.IInstanceAttributeType}s.
 * @param <E> The customizable instance attribute class
 * @param <TOwner> The owning model class
 * @param <TAttributeType> The attribute type class
 * @param <TAttribute> The model attribute class
 */
public abstract class BaseRestInstanceTypeResource<//
E extends IInstanceType<TAttributeType>, //
TOwner extends ICustomizableInstance<E, TAttribute>, //
TAttributeType extends IInstanceAttributeType<E>, //
TAttribute extends IInstanceAttribute<TOwner, TAttributeType>> //
        extends BaseRestMetadataResource<E> {
	
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
	
	@PropertySetter("attributeTypes")
	public void setAttributeTypes(E instance, List<TAttributeType> attributeTypes) {
		if (instance.getAttributeTypes() == null) {
			instance.setAttributeTypes(new ArrayList<TAttributeType>());
		}
		
		BaseRestDataResource.syncCollection(instance.getAttributeTypes(), attributeTypes);
		for (TAttributeType type : instance.getAttributeTypes()) {
			type.setOwner(instance);
		}
	}
}
