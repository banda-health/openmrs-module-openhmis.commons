package org.openmrs.module.openhmis.commons.uiframework;

import org.openmrs.util.OpenmrsClassLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract functionality for registering a UIFramework 2.x module. This class
 * will ONLY load modules if the UiFramework 2.x module is loaded. It replaces
 * use of openmrs annotations which do not check if UiFramework 2.x has been
 * loaded.
 */
public abstract class UiConfigurationFactory implements BeanFactoryPostProcessor {

	private static Log log = LogFactory.getLog(UiConfigurationFactory.class);

	/**
	 * Set the module id
	 */
	public abstract String getModuleId();

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		log.info("Start registering " + getModuleId() + " module");
		// checks if UiFramework's StandardModuleUiConfiguration class has been
		// loaded.
		Class cls = checkStandardModuleUiConfigurationClassExists();
		if (cls != null) {
			// get spring's bean definition builder
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(cls);

			// set the module Id
			builder.addPropertyValue("moduleId", getModuleId());

			// register bean
			((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(
					getModuleId() + "StandardModuleUiConfiguration", builder.getBeanDefinition());

			log.info(getModuleId() + " successfully registered!");

		} else {
			log.info(getModuleId() + " not registered!");
		}
	}

	@SuppressWarnings("rawtypes")
	private Class checkStandardModuleUiConfigurationClassExists() {
		try {
			return OpenmrsClassLoader.getInstance().loadClass("org.openmrs.ui.framework.StandardModuleUiConfiguration");
		} catch (ClassNotFoundException ex) {
			log.info("StandardModuleUiConfiguration class not found. UIFramework 2.x module not loaded.");
		}
		return null;
	}
}
