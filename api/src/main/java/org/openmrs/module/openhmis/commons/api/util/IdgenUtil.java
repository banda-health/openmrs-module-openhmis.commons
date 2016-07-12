package org.openmrs.module.openhmis.commons.api.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;

/**
 * Utility class for generating identifiers using the Idgen module.
 */
public class IdgenUtil {
	private static final Log LOG = LogFactory.getLog(IdgenUtil.class);

	private IdgenUtil() {}

	/**
	 * Gets the {@link org.openmrs.module.idgen.IdentifierSource} with the id in the specified global property.
	 * @param propertyName The global property name.
	 * @return The IdentifierSource object.
	 */
	public static IdentifierSource getIdentifierSource(String propertyName) {
		if (StringUtils.isEmpty(propertyName)) {
			throw new IllegalArgumentException("The property name for the identifier source must be defined.");
		}

		AdministrationService administrationService = Context.getAdministrationService();
		IdentifierSourceService service = Context.getService(IdentifierSourceService.class);

		IdentifierSource source = null;
		String property = administrationService.getGlobalProperty(propertyName);
		Integer sourceId;
		try {
			sourceId = Integer.parseInt(property);

			source = service.getIdentifierSource(sourceId);
		} catch (Exception ex) {
			LOG.warn("Could not convert '" + property + "' into an integer.");
		}

		return source;
	}

	/**
	 * Generates a new identifier for the {@link org.openmrs.module.idgen.IdentifierSource} defined in the specified global
	 * property name.
	 * @param generatorSourcePropertyName The global property name.
	 * @return The new identifier.
	 */
	public static String generateId(String generatorSourcePropertyName) {
		IdentifierSource source = getIdentifierSource(generatorSourcePropertyName);

		return generateId(source);
	}

	/**
	 * Generates a new identifier for the specified {@link org.openmrs.module.idgen.IdentifierSource}.
	 * @param source The IdentifierSource object.
	 * @return The new identifier.
	 */
	public static String generateId(IdentifierSource source) {
		if (source == null) {
			throw new IllegalArgumentException("The identifier source to generate the new identifier from is required.");
		}

		IdentifierSourceService service = Context.getService(IdentifierSourceService.class);
		return service.generateIdentifier(source, "Generating stock operation number.");
	}
}
