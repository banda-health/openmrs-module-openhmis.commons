package org.openmrs.module.openhmis.commons.api.entity.impl;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseCustomizableInstanceObject;
import org.openmrs.module.openhmis.commons.api.entity.security.IObjectAuthorizationPrivileges;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseCustomizableObjectDataServiceImpl<E extends BaseCustomizableInstanceObject, P extends IObjectAuthorizationPrivileges>
		extends BaseObjectDataServiceImpl<E, P> {
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
