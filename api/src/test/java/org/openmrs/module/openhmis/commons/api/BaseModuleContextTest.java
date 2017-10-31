/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.commons.api;

import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.sql.Connection;

/**
 * Base class for OpenHMIS tests
 */
public abstract class BaseModuleContextTest extends BaseModuleContextSensitiveTest {
	@Override
	public void executeDataSet(String datasetFilename) throws Exception {
		Connection conn = super.getConnection();
		try {
			conn.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE").execute();

			super.executeDataSet(datasetFilename);
		} finally {
			conn.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE").execute();
		}
	}
}
