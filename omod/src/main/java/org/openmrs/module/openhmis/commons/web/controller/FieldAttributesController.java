package org.openmrs.module.openhmis.commons.web.controller;

import org.openmrs.Concept;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.module.openhmis.commons.api.compatibility.PrivilegeConstantsCompatibility;
import org.openmrs.module.openhmis.commons.model.FieldAttributeType;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Get field attributes data.
 */
@Controller(value = "fieldAttributes")
@RequestMapping("/module/openhmis/common/fieldAttributes")
public class FieldAttributesController extends AbstractFieldAttributesController {

	@Autowired
	private PrivilegeConstantsCompatibility privilegeConstantsCompatibility;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestParam(value = "type") String type,
	        @RequestParam(value = "foreignKey", required = false) String foreignKey) {
		FieldAttributeType attributeType = FieldAttributeType.valueOf(type);
		String results;
		switch (attributeType) {
			case location:
				results = getLocations();
				break;
			case drug:
				results = getDrugs();
				break;
			case concept:
				results = getConcepts(foreignKey);
				break;
			case user:
				results = getUsers();
				break;
			case provider:
				results = getProviders();
				break;
			case programworkflow:
				results = getProgramWorkflows();
				break;
			default:
				results = "";
				break;
		}

		return results;
	}

	/**
	 * get locations
	 * @return
	 */
	private String getLocations() {
		SimpleObject results = new SimpleObject();
		if (hasPrivileges(privilegeConstantsCompatibility.GET_LOCATIONS)) {
			results.put("results", populateObjects(getLocationService().getAllLocations(false)));
		}
		return convertToJSON(results);
	}

	/**
	 * get drugs.
	 * @return
	 */
	private String getDrugs() {
		SimpleObject results = new SimpleObject();
		if (hasPrivileges(privilegeConstantsCompatibility.GET_CONCEPT_CLASSES)) {
			results.put("results", populateObjects(getConceptService().getAllDrugs()));
		}
		return convertToJSON(results);
	}

	/**
	 * get users
	 * @return
	 */
	private String getUsers() {
		SimpleObject results = new SimpleObject();
		if (hasPrivileges(privilegeConstantsCompatibility.GET_USERS)) {
			results.put("results", populateObjects(getUserService().getAllUsers()));
		}
		return convertToJSON(results);
	}

	/**
	 * get providers
	 * @return
	 */
	private String getProviders() {
		SimpleObject results = new SimpleObject();
		if (hasPrivileges(privilegeConstantsCompatibility.GET_PROVIDERS)) {
			results.put("results", populateObjects(getProviderService().getAllProviders()));
		}
		return convertToJSON(results);
	}

	/**
	 * get concepts given a foreign key
	 * @param foreignKey
	 * @return
	 */
	private String getConcepts(String foreignKey) {
		SimpleObject results = new SimpleObject();
		if (hasPrivileges(privilegeConstantsCompatibility.GET_CONCEPTS)) {
			Concept concept = getConceptService().getConcept(Integer.valueOf(foreignKey));
			if (concept != null) {
				results.put("results", populateObjects(concept.getAnswers()));
			}
			results.put("foreignKey", foreignKey);
		}
		return convertToJSON(results);
	}

	/**
	 * Retrieves a list of all program workflows.
	 * @return
	 */
	private String getProgramWorkflows() {
		SimpleObject results = new SimpleObject();
		try {
			List<String> programWorkflowObjects = new ArrayList<String>();
			List<ProgramWorkflow> programWorkflows = new ArrayList<ProgramWorkflow>();
			// retrieve all workflows from programs.
			for (Program program : getProgramWorkflowService().getAllPrograms()) {
				programWorkflows.addAll(program.getAllWorkflows());
			}
			// retrieve names of program workflows.
			for (ProgramWorkflow programWorkflow : programWorkflows) {
				programWorkflowObjects.add(programWorkflow.getConcept().getName().getName());
			}
			results.put("results", programWorkflowObjects);
		} catch (Exception ex) {
			results.put("error", ex.getMessage());
		}
		return convertToJSON(results);
	}
}
