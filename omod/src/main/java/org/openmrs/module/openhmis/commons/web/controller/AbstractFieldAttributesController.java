package org.openmrs.module.openhmis.commons.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.Drug;
import org.openmrs.User;
import org.openmrs.Provider;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.UserService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.exception.PrivilegeException;
import org.openmrs.module.webservices.rest.SimpleObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract common field attributes functionality
 */
public abstract class AbstractFieldAttributesController {
	private static final Log LOG = LogFactory.getLog(AbstractFieldAttributesController.class);
	private ConceptService conceptService;
	private LocationService locationService;
	private UserService userService;
	private ProviderService providerService;
	private ProgramWorkflowService programWorkflowService;

	/**
	 * Retrieve name and id fields of given objects and return a list of simple objects.
	 * @param objects
	 * @param <T>
	 * @return
	 */
	protected <T extends OpenmrsObject> List<SimpleObject> populateObjects(Collection<T> objects) {
		List<SimpleObject> results = new ArrayList<SimpleObject>();
		try {
			for (T t : objects) {
				SimpleObject resultObject = new SimpleObject();
				if (t instanceof ConceptAnswer) {
					resultObject.put("id", String.valueOf(((ConceptAnswer)t).getAnswerConcept().getConceptId()));
					resultObject.put("name", ((ConceptAnswer)t).getAnswerConcept().getName().getName());
				} else if (t instanceof Location) {
					resultObject.put("id", String.valueOf(((Location)t).getLocationId()));
					resultObject.put("name", ((Location)t).getName());
				} else if (t instanceof Drug) {
					resultObject.put("id", String.valueOf(((Drug)t).getDrugId()));
					resultObject.put("name", ((Drug)t).getName());
				} else if (t instanceof User) {
					resultObject.put("id", String.valueOf(((User)t).getUserId()));
					resultObject.put("name", ((User)t).getUsername());
				} else if (t instanceof Provider) {
					resultObject.put("id", String.valueOf(((Provider)t).getProviderId()));
					resultObject.put("name", ((Provider)t).getName());
				}

				results.add(resultObject);
			}
		} catch (Exception ex) {
			LOG.error("error", ex);
		}
		return results;
	}

	/**
	 * Convert {@link SimpleObject} to JSON
	 * @param object
	 * @return
	 */
	protected String convertToJSON(SimpleObject object) {
		String jsonOutput;
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonOutput = mapper.writeValueAsString(object);
		} catch (Exception ex) {
			jsonOutput = ex.getMessage();
		}
		return jsonOutput;
	}

	/**
	 * Checks if the current user can access given privileges.
	 * @param privileges
	 * @return
	 */
	protected boolean hasPrivileges(String privileges) {
		try {
			return Context.hasPrivilege(privileges);
		} catch (PrivilegeException e) {
			LOG.error("error retrieving privilege ", e);
		}
		return false;
	}

	protected ConceptService getConceptService() {
		if (conceptService == null) {
			conceptService = Context.getConceptService();
		}
		return conceptService;
	}

	protected LocationService getLocationService() {
		if (locationService == null) {
			locationService = Context.getLocationService();
		}
		return locationService;
	}

	protected UserService getUserService() {
		if (userService == null) {
			userService = Context.getUserService();
		}
		return userService;
	}

	protected ProviderService getProviderService() {
		if (providerService == null) {
			providerService = Context.getProviderService();
		}
		return providerService;
	}

	protected ProgramWorkflowService getProgramWorkflowService() {
		if (programWorkflowService == null) {
			programWorkflowService = Context.getProgramWorkflowService();
		}
		return programWorkflowService;
	}
}
