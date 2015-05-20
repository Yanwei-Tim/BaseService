package com.unistrong.tracker.scheduler;

import java.util.Date;

import module.util.DateUtil;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.ReportSchedulerHandle;

/**
 * @author fyq
 */
@Component
public class ReportScheduler extends QuartzJobBean {
	
	private ReportSchedulerHandle reportSchedulerHandle;
	
	private int daysAgo=3;//统计3天的 以免漏掉补传的数据
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
        for(int i=1;i<=daysAgo;i++){
        	Date d= DateUtil.getYesterday(-1*i);
        	reportSchedulerHandle.fixed(d.getTime());
        }
		
	}

	public void setReportSchedulerHandle(ReportSchedulerHandle reportSchedulerHandle) {
		this.reportSchedulerHandle = reportSchedulerHandle;
	}
	
}
