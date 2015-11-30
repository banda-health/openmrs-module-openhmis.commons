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
 * Abstract functionality for registering a UIFramework 2.x module.
 */
public abstract class UiConfigurationFactory implements BeanFactoryPostProcessor {

	private static Log log = LogFactory.getLog(UiConfigurationFactory.class);

	/**
	 * Set the module id
	 */
	public abstract String getModuleId();

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		log.info("Register " + getModuleId() + " module");
		try {
			// load UiFramework's StandardModuleUiConfiguration class
			Class cls = OpenmrsClassLoader.getInstance()
			        .loadClass("org.openmrs.ui.framework.StandardModuleUiConfiguration");

			// get spring's bean definition builder
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(cls);

			// set the module Id
			builder.addPropertyValue("moduleId", getModuleId());

			// register bean
			((DefaultListableBeanFactory)beanFactory).registerBeanDefinition(
			    getModuleId() + "StandardModuleUiConfiguration", builder.getBeanDefinition());
		} catch (ClassNotFoundException ex) {
			// StandardModuleUiConfiguration class not found!
			log.info("Unable to register " + getModuleId() + " UI 2.x module");
		}
	}
}
