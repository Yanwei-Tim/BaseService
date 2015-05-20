package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.AlarmDao;
import com.unistrong.tracker.handle.manual.Config;
import com.unistrong.tracker.model.Alarm;
import com.unistrong.tracker.model.AlarmType;
import com.unistrong.tracker.service.cache.PositionCache;
import com.unistrong.tracker.util.Language;
import com.unistrong.tracker.util.LanguageUtil;

@Service
public class AlarmService {

	@Resource
	private Config config;

	@Resource
	private PositionCache positionCache;

	@Resource
	private AlarmDao alarmDao;

	public void updateToRead(Long alarmId, String deviceSn) {
		alarmDao.updateToRead(alarmId, deviceSn);
		positionCache.removeAlarmId(deviceSn, String.valueOf(alarmId));
	}

	public List<Alarm> listUnreadBefore3Day() {
		return alarmDao.listUnreadBefore3Day();
	}

	/**
	 * 未读告警数
	 */
	public int findCount(String deviceSn) {

		return positionCache.getAlarmCount(deviceSn);
	}

	/**
	 * 查询设备告警(时间段)
	 */
	public List<Alarm> findAllByUserAndDevice(String deviceSn, Long begin,
			Long end) {
		List<Alarm> list = alarmDao.listByTime(deviceSn, begin, end);
		Language lag = LanguageUtil.get();
		if (Language.en_US.equals(lag)) {
			for (Alarm a : list) {
				a.setInfo(AlarmType.getByNum(a.getType()).getEninfo());
				a.setAddr(a.getAddrEn());
			}
		}

		return list;
	}

	public int findTypeByDevice(String deviceSn, int type, Long begin, Long end) {
		return alarmDao.findTypeCount(deviceSn, type, begin, end);
	}

	/**
	 * 拔出统计
	 */
	public Long pullOut(String deviceSn, Long begin, Long end) {
		return alarmDao.getCountByTime(deviceSn, begin, end,
				AlarmType.EPD.getNum());
	}

	/**
	 * 超速统计
	 */
	public Long overSpeedTime(String deviceSn, Long begin, Long end) {
		return alarmDao.getCountByTime(deviceSn, begin, end,
				AlarmType.SPDHI.getNum());
	}
	
	/**
	 * 出围栏告警统计
	 */
	public Long outFenceTime(String deviceSn, Long begin, Long end) {
		return alarmDao.getCountByTime(deviceSn, begin, end, AlarmType.BNDOUT.getNum());
	}

	/**
	 * 删除报警
	 */
	public void delete(String deviceSn) {
		alarmDao.delete(deviceSn);
	}

}
