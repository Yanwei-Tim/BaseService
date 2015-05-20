/**
 * 
 */
package com.unistrong.tracker.handle.manual;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.springframework.stereotype.Component;

/**
 * @author fss
 * 
 */
@Component
public class ThreadSeperateDailyRollingFileAppender extends DailyRollingFileAppender {

	public ThreadSeperateDailyRollingFileAppender()
			throws IOException {
		// 改动只有这点：以线程名命名日志文件
		super(null, "logs" + File.separator + Thread.currentThread().getName() + ".log", "");
	}

}
