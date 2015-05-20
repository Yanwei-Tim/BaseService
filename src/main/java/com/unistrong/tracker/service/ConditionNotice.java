package com.unistrong.tracker.service;

import com.unistrong.tracker.model.Notice;
import com.unistrong.tracker.util.NoticeResponse;

public interface ConditionNotice {
	
	boolean check(Notice notice,Long userId);
	
}
