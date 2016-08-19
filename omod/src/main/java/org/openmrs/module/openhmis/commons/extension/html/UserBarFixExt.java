package org.openmrs.module.openhmis.commons.extension.html;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.openhmis.commons.web.ModuleWebConstants;
import org.openmrs.module.web.extension.HeaderIncludeExt;

/**
 * Header extension to add css file that resolves user bar display issue.
 */
public class UserBarFixExt extends HeaderIncludeExt {
	@Override
	public List<String> getHeaderFiles() {
		List<String> files = new ArrayList<String>(1);
		files.add(ModuleWebConstants.OPENHMIS_STYLE_URL);

		return files;
	}

}
