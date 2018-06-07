package org.openmrs.module.openhmis.commons.api;

import java.io.File;

import org.openmrs.util.OpenmrsConstants;

public class TestUtil {

	public static String getVersionedFileIfExists(String datasetFileName) {
		String openMRSVersion = OpenmrsConstants.OPENMRS_VERSION_SHORT;
		int openMRSMajorVersionNumber = Integer.parseInt(openMRSVersion.split("\\.")[0]);
		int openMRSMinorVersionNumber = Integer.parseInt(openMRSVersion.split("\\.")[1]);

		File datasetFile = new File(datasetFileName);
		// try to load the file if its a straight up path to the file or
		// if its a classpath path to the file
		if (!datasetFile.exists()) {
			datasetFile = new File(TestUtil.class.getClassLoader().getResource(datasetFileName).getPath());
			// If it doesn't exist, we'll let the OpenMRS class throw the error
			if (!datasetFile.exists()) {
				return datasetFileName;
			}
		}

		// Split up the file path, file name, and file extension
		String datasetFileNameWithoutExtension = datasetFile.getName().substring(0, datasetFile.getName().lastIndexOf('.'));
		String datasetFileExtension = datasetFile.getName().substring(datasetFile.getName().lastIndexOf('.'));
		String datasetFilePath =
		        datasetFile.getPath().substring(0, datasetFile.getPath().lastIndexOf(datasetFileNameWithoutExtension));

		int majorVersionNumber = openMRSMajorVersionNumber;
		// Cycle through version numbers to check for each file
		for (int minorVersionNumber = openMRSMinorVersionNumber; minorVersionNumber >= 0; minorVersionNumber--) {
			String versionedDatasetFileName =
			        datasetFilePath + datasetFileNameWithoutExtension + "-" + majorVersionNumber + "." +
			                minorVersionNumber + datasetFileExtension;
			File versionedDatasetFile = new File(versionedDatasetFileName);
			if (versionedDatasetFile.exists()) {
				return versionedDatasetFileName;
			}

			// Assume the minor version is never higher than 30 (arbitrary assumption)
			if (minorVersionNumber == 0 && majorVersionNumber > 1) {
				minorVersionNumber = 30;
				majorVersionNumber--;
			}
		}

		return datasetFileName;
	}
}
