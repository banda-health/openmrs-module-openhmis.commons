package org.openmrs.module.webservices.rest.resource.search;

import org.apache.commons.lang.StringUtils;
import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.IObjectDataService;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;

/**
 * Provides helper methods for search handlers.
 */
public abstract class BaseSearchHandler implements SearchHandler {
	/**
	 * Gets an optional entity by uuid.
	 * @param service The entity service.
	 * @param uuid The entity uuid.
	 * @param <T> The entity class.
	 * @return The entity object or {@code null} if not defined or found.
	 */
	protected <T extends OpenmrsObject> T getOptionalEntityByUuid(IObjectDataService<T> service, String uuid) {
		T entity = null;
		if (!StringUtils.isEmpty(uuid)) {
			entity = service.getByUuid(uuid);
		}

		return entity;
	}
}
