package org.openmrs.module.openhmis.commons.api.entity;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.TestUtil;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class LazyRoleTest extends BaseModuleContextSensitiveTest {

	public static final String XML_DATASET_PATH = "org/openmrs/module/openhmis/commons/api/include/";

	public static final String XML_OPENHMIS_COMMONS_CORE_TEST_DATASET = "coreTest";

	private UserService userService;

	@Before
	public void before() throws Exception {
		executeDataSet(XML_DATASET_PATH + new TestUtil().getTestDatasetFilename(XML_OPENHMIS_COMMONS_CORE_TEST_DATASET));

		userService = Context.getUserService();
	}

	@Test
	public void selectAll_ShouldReturnAllRoles() throws Exception {
		List<Role> roles = userService.getAllRoles();

		Assert.assertEquals(8, roles.size());
	}
}
