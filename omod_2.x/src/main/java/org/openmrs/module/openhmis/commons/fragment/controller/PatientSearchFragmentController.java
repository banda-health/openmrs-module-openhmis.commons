package org.openmrs.module.openhmis.commons.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * Patient search fragment logic
 */
public class PatientSearchFragmentController {

	/**
	 * Searches for a 'Visit Note' encounter from patient's last active visit.
	 * @param patientUuid
	 * @param patientService
	 * @param visitService
	 * @return JSON
	 */
	public SimpleObject getEncounter(@RequestParam("patientUuid") String patientUuid,
	        @SpringBean("patientService") PatientService patientService,
	        @SpringBean("visitService") VisitService visitService) {
		if (StringUtils.isNotEmpty(patientUuid)) {
			Patient patient = patientService.getPatientByUuid(patientUuid);
			if (patient != null) {
				List<Visit> activeVisits = visitService.getActiveVisitsByPatient(patient);
				if (activeVisits.size() > 0) {
					Set<Encounter> encounters = activeVisits.get(0).getEncounters();
					for (Encounter encounter : encounters) {
						if (encounter.getEncounterType().getName().equalsIgnoreCase("visit note")) {
							return SimpleObject.create("encounterId", encounter.getId());
						}
					}
				}
			}
		}

		return null;
	}
}
