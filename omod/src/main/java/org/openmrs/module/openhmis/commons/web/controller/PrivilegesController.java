package org.openmrs.module.openhmis.commons.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Checks required privileges
 */
@Controller(value = "privileges")
@RequestMapping("/module/openhmis/commons/privileges.page")
public class PrivilegesController {
	private static final Log LOG = LogFactory.getLog(PrivilegesController.class);

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public SimpleObject get(
	        @RequestParam(value = "privileges") String privileges) {
		SimpleObject results = new SimpleObject();
		if (StringUtils.isNotEmpty(privileges)) {
			try {
				results.put("hasPrivileges", Context.hasPrivilege(privileges));
			} catch (Exception ex) {
				LOG.error("Error checking privileges. ", ex);
			}
		}
		return results;
	}
}
