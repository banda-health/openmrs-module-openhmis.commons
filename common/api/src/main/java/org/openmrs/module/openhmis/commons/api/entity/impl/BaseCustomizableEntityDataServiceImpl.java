package org.openmrs.module.openhmis.commons.api.entity.impl;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseCustomizableInstanceData;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseCustomizableEntityDataServiceImpl<E extends BaseCustomizableInstanceData>
		extends BaseEntityDataServiceImpl<E> {
	@Override
	@SuppressWarnings("unchecked")
	protected Collection<? extends OpenmrsObject> getRelatedObjects(E entity) {
		Collection<? extends OpenmrsObject> result = super.getRelatedObjects(entity);

		if (result == null) {
			result = new ArrayList<OpenmrsObject>();
		}

		Collection attributes = entity.getAttributes();
		if (attributes != null && attributes.size() > 0) {
			result.addAll(attributes);
		}

		return result;
	}
}

