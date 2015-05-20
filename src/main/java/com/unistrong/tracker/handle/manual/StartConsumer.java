package com.unistrong.tracker.handle.manual;

import module.util.AbsolutePath;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fyq
 */
public class StartConsumer {
	private static Logger logger = LoggerFactory.getLogger(StartConsumer.class);
	
	public static void main(String[] args) {
		try {
			String contextData = "contextData.xml";
			String contextMsg = "contextMsg.xml";
			String contextVersion = "contextVersion.xml";
			String[] contexts = new String[] { contextData, contextMsg, contextVersion};
			String pathPro = "com/unistrong/tracker/handle/manual/log4j.properties";
			PropertyConfigurator.configure(AbsolutePath.absolutePath(pathPro).toURL());
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					contexts);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					if (context != null) {
						context.close();
					}
				}
			});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}

