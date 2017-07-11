package org.openmrs.module.openhmis.commons.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.openmrs.util.OpenmrsUtil;

/**
 * Created by Kevin on 7/11/2017.
 */
public class TestUtil {

	public static final String TEST_DATASETS_PROPERTIES_FILE = "test-datasets.properties";

	/**
	 * Determines the name of the proper test dataset based on what version of OpenMRS we are testing against
	 */
	public String getTestDatasetFilename(String testDatasetName) throws Exception {

		InputStream propertiesFileStream = null;

		// try to load the file if its a straight up path to the file or
		// if its a classpath path to the file
		if (new File(TEST_DATASETS_PROPERTIES_FILE).exists()) {
			propertiesFileStream = new FileInputStream(TEST_DATASETS_PROPERTIES_FILE);
		} else {
			propertiesFileStream = getClass().getClassLoader().getResourceAsStream(TEST_DATASETS_PROPERTIES_FILE);
			if (propertiesFileStream == null) {
				throw new FileNotFoundException("Unable to find '" + TEST_DATASETS_PROPERTIES_FILE + "' in the classpath");
			}
		}

		Properties props = new Properties();

		OpenmrsUtil.loadProperties(props, propertiesFileStream);

		if (props.getProperty(testDatasetName) == null) {
			throw new Exception("Test dataset named " + testDatasetName + " not found in properties file");
		}

		return props.getProperty(testDatasetName);
	}
}
