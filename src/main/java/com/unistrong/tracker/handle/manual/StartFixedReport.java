package com.unistrong.tracker.handle.manual;

import java.util.Date;

import module.util.DateUtil;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unistrong.tracker.handle.ReportSchedulerHandle;

/**
 * @author fyq
 */
public class StartFixedReport {

	public static void main(String[] args) {
		try {
			String contextData = "contextData.xml";
			String contextMsg = "contextCache.xml";
			String contextVersion = "contextVersion.xml";
			String[] contexts = new String[] { contextData, contextMsg, contextVersion };
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(contexts);
			
			ReportSchedulerHandle beanReport = (ReportSchedulerHandle) context
					.getBean("reportSchedulerHandle");
			
            for(int i=1;i<8;i++){
            	Date d= DateUtil.getYesterday(-1*i);
    			beanReport.fixed(d.getTime());
    			System.out.println(d.toLocaleString()+" over");
            }
			

			// -------------------------------------------------

			System.out.println("all over");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.exit(0);
	}

}
