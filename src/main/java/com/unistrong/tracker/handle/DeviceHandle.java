package com.unistrong.tracker.handle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import module.util.Assert;
import module.util.DateUtil;
import module.util.EntityUtil;
import module.util.HttpUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.handle.i18n.MessageManager;
import com.unistrong.tracker.handle.util.InstructDb;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.FeeCheck;
import com.unistrong.tracker.model.HandSetToConductor;
import com.unistrong.tracker.model.Instruct;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.model.User;
import com.unistrong.tracker.model.UserDevice;
import com.unistrong.tracker.model.UserDeviceForbid;
import com.unistrong.tracker.model.UserGuarder;
import com.unistrong.tracker.model.UserJiLin;
import com.unistrong.tracker.service.AlarmService;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.GuarderService;
import com.unistrong.tracker.service.HandsetToConductorService;
import com.unistrong.tracker.service.InstructService;
import com.unistrong.tracker.service.PositionService;
import com.unistrong.tracker.service.SpotService;
import com.unistrong.tracker.service.UserDeviceForbidService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserJiLinService;
import com.unistrong.tracker.service.UserService;
import com.unistrong.tracker.service.cache.DeviceCache;
import com.unistrong.tracker.service.cache.PositionCache;
import com.unistrong.tracker.util.Logic;
import com.unistrong.tracker.util.UsConstants;

/**
 * @author fyq
 */
@Component
public class DeviceHandle {

    @Resource
    private DeviceService             deviceService;

    @Resource
    private UserService               userService;

    @Resource
    private UserJiLinService          userJiLinService;

    @Resource
    private GuarderService            guarderService;

    @Resource
    private UserDeviceService         userDeviceService;

    @Resource
    private UserDeviceForbidService   userDeviceForbidService;

    @Resource
    private InstructService           instructService;

    @Resource
    private PositionService           positionService;

    @Resource
    private PositionCache             positionCache;

    @Resource
    private DeviceCache               deviceCache;

    @Resource
    private SpotService               spotService;

    @Resource
    private AlarmService              alarmService;

    @Resource
    private HandsetToConductorService handsetToConductorService;

    // *****************************************
    private static Logger             logger = LoggerFactory.getLogger (DeviceHandle.class);

    public Map <String, Object> editDelDevice (String userIdStr, String sn, String name, String icon, String car,
            String phone, String flowStr, String batteryStr, String speedThreshold, String height, String weight,
            String gender, String age, String tick, String sos, String speedingSwitch, String fenceSwitch,
            String moveSwitch, String operate, String targetIdStr, String appName, String birthStr, String dogFigure,
            String dogBreed, String contact, String sms_alarm, String ptype, String serviceIdStr) {

        HttpUtil.validateNull (new String [] {
                "user_id", "device_sn"
        }, new String [] {
                userIdStr, sn
        });
        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });

        HttpUtil.validateLong (new String [] {
            "ptype"
        }, new String [] {
            ptype
        });

        HttpUtil.validateIntScope (operate, 1, 5);
        Float flow = HttpUtil.getFloat (flowStr, null);
        Integer battery = HttpUtil.getInt (batteryStr, null);
        Float heightNum = HttpUtil.getFloat (height, null);
        Float weightNum = HttpUtil.getFloat (weight, null);
        Integer genderNum = HttpUtil.getInt (gender, null);
        // Integer ageNum = HttpUtil.getInt(age, null);
        Integer tickNum = HttpUtil.getInt (tick, null);
        Integer speedThresholdNum = HttpUtil.getInt (speedThreshold, null);
        Integer speedingSwitchNum = HttpUtil.getInt (speedingSwitch, null);
        Integer fenceSwitchNum = HttpUtil.getInt (fenceSwitch, null);
        Integer moveSwitchNum = HttpUtil.getInt (moveSwitch, null);
        Long birth = HttpUtil.getLong (birthStr, 0L);
        Long userId = HttpUtil.getLong (userIdStr);
        Integer enableSmsAlarm = HttpUtil.getInt (sms_alarm, null);

        Integer personType = HttpUtil.getInt (ptype, 1);

        Map <String, Object> rs = new HashMap <String, Object> ();
        User entity = userService.get (userId);

        if (entity != null && entity.getType () != null && entity.getType () == 1) {
            if (targetIdStr == "") {
                targetIdStr = userIdStr;
            }
            HttpUtil.validateNull (new String [] {
                "target_id"
            }, new String [] {
                targetIdStr
            });
            Long targetId = HttpUtil.getLong (targetIdStr);
            Assert.isTrue (userService.isAuthorized (userId, targetId, appName),
                    UsConstants.getI18nMessage (UsConstants.USER_DENIED));
            userId = targetId;
            userIdStr = targetIdStr;
        }

        sn = sn.trim ();
        if ("3".equals (operate)) {// 3更新设备
            Device form = new Device ();
            form.setSn (sn);
            form.setName (name);
            form.setPhone (phone);
            form.setCar (car);
            form.setHeight (heightNum);
            form.setWeight (weightNum);
            form.setGender (genderNum);
            Calendar now = Calendar.getInstance ();
            Calendar birthday = Calendar.getInstance ();

            if (birth > 0) {
                birthday.setTimeInMillis (birth);
            }

            int month_diff = DateUtil.getMonthDiff (now, birthday);

            if (month_diff < 0) {
                month_diff = 0;
            }

            int ageYear = month_diff / 12;
            int ageMonth = month_diff % 12;

            form.setAge (ageYear);
            form.setMonth (ageMonth);

            String sage = ageYear + "岁";
            if (ageMonth > 0) {
                sage += ageMonth + "个月";
            }

            form.setSage (sage);

            form.setIcon (icon);
            form.setBirth (birth);
            form.setDogFigure (dogFigure);
            form.setDogBreed (dogBreed);
            // 指令
            form.setBattery (battery);
            form.setFlow (flow);
            form.setSpeedThreshold (speedThresholdNum);
            form.setTick (tickNum);
            form.setSosNum (InstructDb.arrayDb (sos));
            form.setSpeedingSwitch (speedingSwitchNum);
            form.setFenceSwitch (fenceSwitchNum);
            form.setMoveSwitch (moveSwitchNum);
            form.setContact (InstructDb.arrayDb (contact));
            form.setEnableSmsAlarm (enableSmsAlarm);
            //TODO 先删下
            //			form.setPersonType(personType);

            rs.putAll (update (form, userId, operate));
        }
        else {

            HttpUtil.validateNull (new String [] {
                "service_id"
            }, new String [] {
                serviceIdStr
            });
            HttpUtil.validateLong (new String [] {
                "service_id"
            }, new String [] {
                serviceIdStr
            });

            long serviceId = HttpUtil.getLong (serviceIdStr);

            User user = userService.findByUserAndServiceId (userId, serviceId);
            Device device = deviceService.findBySnAndServiceUser (sn, serviceId);

            Assert.notNull (device, MessageManager.DEVICE_NOT_IMPORT ());
            Assert.notNull (user, MessageManager.DEVICE_NOT_IMPORT ());

            if (device.getStatus () == 2) {
                rs.put ("ret", 2);
                rs.put ("desc", MessageManager.DEVICE_FORBID ());
                return rs;
            }

            if ("1".equals (operate)) {// 1绑定
                rs.putAll (bind (sn, userId));
            }
            else if ("2".equals (operate)) {// 2 解绑
                rs.putAll (unbind (sn, userId));
            }
            else if ("4".equals (operate)) {// 4初始化
                rs.putAll (restoreDevice (sn, userIdStr));
            }
            else if ("5".equals (operate)) {// 5 重启
                rs.putAll (rebootDevice (sn));
            }
        }
        rs.put ("operate", operate);
        return rs;
    }

    public Map <String, Object> operateDevice (String serviceIdStr, String userIdStr, String sn, String operate,
            String targetIdStr, String appName) {

        HttpUtil.validateNull (new String [] {
                "service_id", "user_id", "device_sn"
        }, new String [] {
                serviceIdStr, userIdStr, sn
        });
        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        HttpUtil.validateLong (new String [] {
            "service_id"
        }, new String [] {
            serviceIdStr
        });
        HttpUtil.validateIntScope (operate, 1, 5);

        Long userId = HttpUtil.getLong (userIdStr);

        long serviceId = HttpUtil.getLong (serviceIdStr);

        Map <String, Object> rs = new HashMap <String, Object> ();

        User user = userService.findByUserAndServiceId (userId, serviceId);
        Device device = deviceService.findBySnAndServiceUser (sn, serviceId);

        Assert.notNull (device, MessageManager.DEVICE_NOT_IMPORT ());
        Assert.notNull (user, MessageManager.DEVICE_NOT_IMPORT ());

        if (user.getType () != null && user.getType () == 1) {
            HttpUtil.validateNull (new String [] {
                "target_id"
            }, new String [] {
                targetIdStr
            });
            Long targetId = HttpUtil.getLong (targetIdStr);
            Assert.isTrue (userService.isAuthorized (userId, targetId, appName),
                    UsConstants.getI18nMessage (UsConstants.USER_DENIED));
            userId = targetId;
            userIdStr = targetIdStr;
        }

        sn = sn.trim ();

        if (device.getStatus () == 2) {
            rs.put ("ret", 2);
            rs.put ("desc", MessageManager.DEVICE_FORBID ());
            return rs;
        }

        if ("1".equals (operate)) {// 1绑定
            rs.putAll (bind (sn, userId));
        }
        else if ("2".equals (operate)) {// 2 解绑
            rs.putAll (unbind (sn, userId));
        }
        else if ("4".equals (operate)) {// 4初始化
            rs.putAll (restoreDevice (sn, userIdStr));
        }
        else if ("5".equals (operate)) {// 5 重启
            rs.putAll (rebootDevice (sn));
        }

        rs.put ("operate", operate);

        return rs;
    }

    public Map <String, Object> updateDevice (String userIdStr, String sn, String name, String icon, String car,
            String phone, String flowStr, String batteryStr, String speedThreshold, String height, String weight,
            String gender, String age, String tick, String sos, String speedingSwitch, String fenceSwitch,
            String moveSwitch, String targetIdStr, String appName, String birthStr, String dogFigure, String dogBreed,
            String contact, String sms_alarm) {

        HttpUtil.validateNull (new String [] {
                "user_id", "device_sn"
        }, new String [] {
                userIdStr, sn
        });
        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        Float flow = HttpUtil.getFloat (flowStr, null);
        Integer battery = HttpUtil.getInt (batteryStr, null);
        Float heightNum = HttpUtil.getFloat (height, null);
        Float weightNum = HttpUtil.getFloat (weight, null);
        Integer genderNum = HttpUtil.getInt (gender, null);
        Integer tickNum = HttpUtil.getInt (tick, null);
        Integer speedThresholdNum = HttpUtil.getInt (speedThreshold, null);
        Integer speedingSwitchNum = HttpUtil.getInt (speedingSwitch, null);
        Integer fenceSwitchNum = HttpUtil.getInt (fenceSwitch, null);
        Integer moveSwitchNum = HttpUtil.getInt (moveSwitch, null);
        Long birth = HttpUtil.getLong (birthStr, 0L);
        Long userId = HttpUtil.getLong (userIdStr);
        Integer enableSmsAlarm = HttpUtil.getInt (sms_alarm, null);

        Map <String, Object> rs = new HashMap <String, Object> ();

        User entity = userService.get (userId);

        if (entity != null & entity.getType () != null && entity.getType () == 1) {
            HttpUtil.validateNull (new String [] {
                "target_id"
            }, new String [] {
                targetIdStr
            });
            Long targetId = HttpUtil.getLong (targetIdStr);
            Assert.isTrue (userService.isAuthorized (userId, targetId, appName),
                    UsConstants.getI18nMessage (UsConstants.USER_DENIED));
            userId = targetId;
            userIdStr = targetIdStr;
        }

        sn = sn.trim ();

        Device form = new Device ();
        form.setSn (sn);
        form.setName (name);
        form.setPhone (phone);
        form.setCar (car);
        form.setHeight (heightNum);
        form.setWeight (weightNum);
        form.setGender (genderNum);
        Calendar now = Calendar.getInstance ();
        Calendar birthday = Calendar.getInstance ();

        if (birth > 0) {
            birthday.setTimeInMillis (birth);
        }

        int month_diff = DateUtil.getMonthDiff (now, birthday);

        if (month_diff < 0) {
            month_diff = 0;
        }

        int ageYear = month_diff / 12;
        int ageMonth = month_diff % 12;

        form.setAge (ageYear);
        form.setMonth (ageMonth);

        String sage = ageYear + "岁";
        if (ageMonth > 0) {
            sage += ageMonth + "个月";
        }

        form.setSage (sage);

        form.setIcon (icon);
        form.setBirth (birth);
        form.setDogFigure (dogFigure);
        form.setDogBreed (dogBreed);
        // 指令
        form.setBattery (battery);
        form.setFlow (flow);
        form.setSpeedThreshold (speedThresholdNum);
        form.setTick (tickNum);
        form.setSosNum (InstructDb.arrayDb (sos));
        form.setSpeedingSwitch (speedingSwitchNum);
        form.setFenceSwitch (fenceSwitchNum);
        form.setMoveSwitch (moveSwitchNum);
        form.setContact (contact);
        form.setEnableSmsAlarm (enableSmsAlarm);
        rs.putAll (update (form, userId, "3"));

        return rs;
    }

    /**
     * 更新设备
     */
    private Map <String, Object> update (Device form, Long userId, String operate) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        String sn = form.getSn ();
        rs.put ("sn", sn);
        Device entity = deviceService.findBySnAndUser (sn, userId);
        Assert.notNull (entity, UsConstants.getI18nMessage (UsConstants.USER_NOTDEVICE));

        if (null != form.getTick ()
                || (null != form.getSosNum () && !form.getSosNum ().equals ("") && entity.getType () == 3)
                || null != form.getSpeedingSwitch () || null != form.getSpeedThreshold ()
                || null != form.getFenceSwitch () || null != form.getMoveSwitch ()) {// 含指令

            Instruct instruct = null;

            if (null != form.getTick ()) {// 上传间隔
                instruct = InstructDb.tick (form, entity);
            }
            else if (null != form.getSosNum () && !"".equals (form.getSosNum ()) && entity.getType () == 3) {// sos号码
                instruct = InstructDb.sos (form, entity);
            }
            else if (null != form.getSpeedingSwitch ()) {// 超速开关
                int max = 80;
                if (null != entity.getSpeedThreshold () && 0 != entity.getSpeedThreshold ()) {
                    max = entity.getSpeedThreshold ();
                }
                form.setSpeedThreshold (max);

                instruct = InstructDb.speed (form, entity);
            }
            else if (null != form.getSpeedThreshold ()) {// 超速阀值
                form.setSpeedingSwitch (1);
                instruct = InstructDb.speed (form, entity);
            }
            else if (null != form.getFenceSwitch ()) {// 围栏开关
                instruct = InstructDb.fenceSwitch (form, entity);
            }
            else if (null != form.getMoveSwitch ()) {// 移动报警开关
                instruct = InstructDb.moveSwitch (form, entity);
            }

            instructService.saveOrUpdate (instruct);
        }

        BeanUtils.copyProperties (form, entity, EntityUtil.nullField (Device.class, form));
        deviceService.update (entity);
        // TODO:通过心跳告诉客户端设备有更新
        Position position = positionService.get (sn);
        position.setStamp (new Date ().getTime ());
        positionService.set (position);
        rs.put (Logic.DESC, MessageManager.UPDATE_SUCCESS ());
        return rs;
    }

    /**
     * 设备重启指令
     */
    private Map <String, Object> rebootDevice (String deviceSn) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        HttpUtil.validateNull (new String [] {
            "device_sn"
        }, new String [] {
            deviceSn
        });
        instructService.saveOrUpdate (InstructDb.reboot (deviceSn));
        rs.put (Logic.DESC, MessageManager.RESTART_SUCCESS ());
        return rs;
    }

    /**
     * 恢复出厂设置指令
     */
    private Map <String, Object> restoreDevice (String deviceSn, String userIdStr) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        HttpUtil.validateNull (new String [] {
                "user_id", "device_sn"
        }, new String [] {
                userIdStr, deviceSn
        });
        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        Long userId = HttpUtil.getLong (userIdStr);
        Device entity = deviceService.findBySnAndUser (deviceSn, userId);
        Assert.notNull (entity, "用户无此设备");

        Device form = new Device ();
        form.setSn (deviceSn);

        // 上传间隔
        form.setTick (30);
        instructService.saveOrUpdate (InstructDb.tick (form, entity));

        // 超速-开
        form.setSpeedingSwitch (1);
        form.setSpeedThreshold (120);
        instructService.saveOrUpdate (InstructDb.speed (form, entity));

        // 围栏-关
        form.setFenceSwitch (2);
        instructService.saveOrUpdate (InstructDb.fenceSwitch (form, entity));

        // 移动-关
        form.setMoveSwitch (2);
        instructService.saveOrUpdate (InstructDb.moveSwitch (form, entity));

        BeanUtils.copyProperties (form, entity, EntityUtil.nullField (Device.class, form));
        deviceService.saveOrUpdate (entity);
        Position position = positionService.get (deviceSn);
        position.setStamp (new Date ().getTime ());
        positionService.set (position);

        rs.put (Logic.DESC, MessageManager.RESET_SUCCESS ());
        return rs;
    }

    /**
     * 绑定-只增加用户与设备关系
     */
    private Map <String, Object> bind (String sn, Long userId) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        if (sn.length () == 9)
            sn = "354188" + sn;// 前三千设备在包装盒上少打印了
        Device device = deviceService.get (sn);
        Assert.notNull (device, MessageManager.DEVICE_NOT_IMPORT ());
        String appName = device.getAppName ();
        User user = userService.get (userId);
        if (appName == null)
            appName = "M2616_BD";
        if (user.getAppName () == null)
            user.setAppName ("M2616_BD");
        Assert.isTrue (appName.equals (user.getAppName ()), UsConstants.getI18nMessage (UsConstants.USER_DENIED));
        if (userDeviceService.isBind (sn)) {// 有无被其他人绑定
            Assert.isNull (userDeviceService.getBySnAndUser (sn, userId), MessageManager.REPEAT_BIND ());
            rs.put ("ret", "3");
            rs.put ("desc", MessageManager.BIND_EXIST_DEVICE_SUCCESS ());
        }
        else {
            Long begin = new Date ().getTime ();
            Long end = DateUtil.getYesterday (365).getTime ();
            device.setBegin (begin);
            device.setEnd (end);
            rs.put ("desc", MessageManager.BIND_NEW_DEVICE_SUCCESS ());
        }
        userDeviceService.saveOrUpdate (new UserDevice (userId, sn, Calendar.getInstance ().getTimeInMillis ()));
        // 应老曹要求，每次绑定一个新设备都需要往capcare监管账户绑定一次 xubin modify 2014-12-25 begin
        int type = device.getType ();
        String usrIdStr = "";
        if (type == 1) {
            usrIdStr = "17860,17920,17923";
        }
        else if (type == 2) {
            usrIdStr = "17859,17919,17923";
        }
        else if (type == 3) {
            usrIdStr = "17858,17921,17923";
        }
        else {
            usrIdStr = "17923";
        }
        String str [] = usrIdStr.split (",");
        for (int i = 0; i < str.length; i++) {
            String usrId = str [i];
            userId = Long.parseLong (usrId);
            UserDevice userDevice = userDeviceService.getBySnAndUser (sn, userId);
            if (userDevice == null) {
                userDeviceService
                        .saveOrUpdate (new UserDevice (userId, sn, Calendar.getInstance ().getTimeInMillis ()));
            }
        }
        // 应老曹要求，每次绑定一个新设备都需要往capcare监管账户绑定一次 xubin modify 2014-12-25 end
        rs.put ("device", device);
        rs.put ("device_sn", sn);
        return rs;
    }

    /**
     * 解绑-只删除用户与设备关系
     */
    private Map <String, Object> unbind (String sn, Long userId) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        Device device = deviceService.findBySnAndUser (sn, userId);
        int type = device.getType ();
        Assert.notNull (device, UsConstants.getI18nMessage (UsConstants.USER_NOTDEVICE));
        userDeviceService.deleteByDeviceAndUser (sn, userId);
        // 应老曹要求，每次绑定一个新设备都需要往capcare监管账户绑定一次 xubin modify 2014-12-29 begin0
        List <Long> list = userDeviceService.findUserIdBySn (sn);
        if (list.size () == 0) {
            String usrIdStr = "";
            if (type == 1) {
                usrIdStr = "17860,17920,17923";
            }
            else if (type == 2) {
                usrIdStr = "17859,17919,17923";
            }
            else if (type == 3) {
                usrIdStr = "17858,17921,17923";
            }
            else {
                usrIdStr = "17923";
            }
            String str [] = usrIdStr.split (",");
            for (int i = 0; i < str.length; i++) {
                String usrId = str [i];
                userId = Long.parseLong (usrId);
                userDeviceService.deleteByDeviceAndUser (sn, userId);
            }
        }
        // 应老曹要求，每次绑定一个新设备都需要往capcare监管账户绑定一次 xubin modify 2014-12-29 end
        rs.put (Logic.DESC, MessageManager.UNBIND_DEVICE_SUCCESS ());
        return rs;
    }

    /**
     * 查询设备
     */
    public Device get (String userIdStr, String sn) {

        HttpUtil.validateNull (new String [] {
                "user_id", "sn"
        }, new String [] {
                userIdStr, sn
        });
        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        Long userId = HttpUtil.getLong (userIdStr);
        Device device = deviceService.findBySnAndUser (sn, userId);
        if (device != null) {
            device.setPosition (positionService.get (device.getSn ()));
        }
        return device;
    }

    public FeeCheck getFeeCheck (String deviceSn) {

        HttpUtil.validateNull (new String [] {
            "deviceSn"
        }, new String [] {
            deviceSn
        });
        List <FeeCheck> feeCheckList = deviceService.getFeeCheck (deviceSn);
        FeeCheck feeCheck = new FeeCheck ();
        // StringBuffer sb = new StringBuffer();
        if (feeCheckList.size () == 0) {
            feeCheck.setContent ("如需开通此服务，请正确填写设备电话信息后，联系客服开通010-56731866。");
        }
        else {
            feeCheck = feeCheckList.get (0);
            // for (int i = 0; i < feeCheckList.size(); i++) {
            // String content = feeCheckList.get(i).getContent();
            // sb.append(content);
            // }
            // feeCheck.setContent(sb.toString());
        }
        return feeCheck;
    }

    /**
     * 查询设备列表
     */
    public Map <String, Object> list (String userIdStr, String targetIdStr, String appName, String pageNumberStr,
            String pageSizeStr) {

        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        Map <String, Object> rs = new HashMap <String, Object> ();
        Long userId = HttpUtil.getLong (userIdStr);
        int pageNumber = HttpUtil.getInt (pageNumberStr, -1);
        int pageSize = HttpUtil.getInt (pageSizeStr, -1);
        User entity = userService.get (userId);
        List <Device> devices = new ArrayList <Device> ();

        if (entity != null && entity.getType () != null && entity.getType () == 1) {
            if (targetIdStr != null) {
                HttpUtil.validateNull (new String [] {
                    "target_id"
                }, new String [] {
                    targetIdStr
                });
                Long targetId = HttpUtil.getLong (targetIdStr);// 针对企业用户user下级的id
                Assert.isTrue (userService.isAuthorized (userId, targetId, appName),
                        UsConstants.getI18nMessage (UsConstants.USER_DENIED));
                devices = deviceService.findAllByUser (targetId, pageNumber, pageSize);
            }
            else {
                List <User> userList = userService.getUserDown (userId, appName);

                Set <String> allSns = new HashSet <String> ();

                for (User user : userList) {
                    List <String> sns = deviceService.findAllSnByUser (user.getId (), -1, -1);
                    allSns.addAll (sns);
                }

                StringBuilder sns = new StringBuilder ();

                for (String sn : allSns) {
                    sns.append ("'");
                    sns.append (sn).append ("',");
                }
                if (sns.length () > 0) {
                    String asns = sns.substring (0, sns.length () - 1).toString ();

                    devices = deviceService.findAllDeviceBySns (asns, -1, -1);
                }
            }
        }
        else {
            devices = deviceService.findAllByUser (userId, pageNumber, pageSize);
        }

        // if (appName != null && !appName.equals("") &&
        // !appName.equals("M2616_BD")) {
        // if (targetIdStr != null) {
        // HttpUtil.validateLong(new String[] { "target_id" }, new String[] {
        // targetIdStr });
        // Long targetId = HttpUtil.getLong(targetIdStr);
        // Assert.isTrue(userService.isAuthorized(userId, targetId, appName),
        // UsConstants.USER_DENIED);
        // userId = targetId;
        // }
        // List<User> userList = userService.getUserDown(userId, appName);
        //
        // devices.clear();
        // Set<String> snSet = new HashSet<String>();
        // for (User user : userList) {
        // List<Device> temp = deviceService.findAllByUser(user.getId(),
        // pageNumber, pageSize);
        // if (temp != null && temp.size() > 0) {
        // for(Device device : temp) {
        // if(!snSet.contains(device.getSn())) {
        // snSet.add(device.getSn());
        // devices.add(device);
        // }
        // }
        // }
        // }
        // }
        // List<UserJiLin> userList = new ArrayList<UserJiLin>();
        Long begin = 1420041600000l;// 默认开通时间 2015-01-01
        for (Device device : devices) {
            List <UserGuarder> guarderList = new ArrayList <UserGuarder> ();
            device.setPosition (positionService.get (device.getSn ()));
            UserJiLin user = new UserJiLin ();
            device.setUserJiLin (user);// 使用人信息
            user = userJiLinService.getUser (device.getSn ());
            if (user != null) {
                device.setUserJiLin (user);
                String guarderIds = user.getGuarder ();
                if (guarderIds != null) {
                    String str [] = guarderIds.split (",");
                    for (int i = 0; i < str.length; i++) {
                        UserGuarder guarder = guarderService.getGuarder (Long.parseLong (str [i]));
                        if (guarder != null) {
                            guarder.setSn (device.getSn ());
                            guarderList.add (guarder);// 监护人信息
                        }
                        else {
                            UserGuarder guarder1 = new UserGuarder ();
                            guarder1.setSn (device.getSn ());
                            guarder1.setAddress ("");
                            guarder1.setCompany ("");
                            guarder1.setGuarderName ("");
                            guarder1.setId (0l);
                            guarder1.setPhone ("");
                            guarderList.add (guarder1);// 监护人信息
                        }
                    }
                }
                device.setUserGuarder (guarderList);
            }
            else {
                device.setUserJiLin (new UserJiLin ());
                device.setUserGuarder (new ArrayList <UserGuarder> ());
            }
            Spot spot = spotService.getBeginSpot (device.getSn ());
            if (spot != null) {
                begin = spot.getReceive ();
            }
            device.setBeginTime (begin.toString ());
        }

        rs.put ("devices", devices);
        return rs;
    }

    /**
     * 查询属于某用户的设备列表
     */
    public Map <String, Object> findByServiceUser (String userIdStr, String sn, String operTypeStr,
            String pageNumberStr, String pageSizeStr) {

        Map <String, Object> rs = new HashMap <String, Object> ();

        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        Long serviceId = HttpUtil.getLong (userIdStr);

        int pageNumber = HttpUtil.getInt (pageNumberStr, -1);
        int pageSize = HttpUtil.getInt (pageSizeStr, -1);

        int operType = HttpUtil.getInt (operTypeStr, 1);// 默认是模糊查询

        List <Device> devices = deviceService.findByServiceUser (serviceId, sn, operType, pageNumber, pageSize);

        rs.put ("devices", devices);
        return rs;
    }

    /**
     * 查看设备信息
     */
    public Map <String, Object> lookup (String userIdStr, String sn) {

        Map <String, Object> rs = new HashMap <String, Object> ();

        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });

        Long userId = HttpUtil.getLong (userIdStr);

        // imei、上传时间、是否已被绑定、是否已上传有效位置点、最后一次上传位置点时间、第一次上传位置点时间

        // 是否已被绑定
        boolean isbind = userDeviceService.isBind (sn);

        // 第一次上传位置点时间
        Spot fist_spot = spotService.getSpot (sn, true);
        Long first_point = 0L;
        if (fist_spot != null && fist_spot.getReceive () != null) {
            first_point = fist_spot.getReceive ();
        }

        // 最后一次上传位置点时间
        Spot last_spot = spotService.getSpot (sn, false);
        Long last_point = 0L;
        if (last_spot != null && last_spot.getReceive () != null) {
            last_point = last_spot.getReceive ();
        }

        // 是否已上传有效位置点
        boolean has_point = false;
        if (first_point > 0) {
            has_point = true;
        }

        rs.put ("imei", sn);
        rs.put ("isbind", isbind);
        rs.put ("has_point", has_point);
        rs.put ("first_point", DateUtil.longStr (first_point, "yyyy-MM-dd HH:mm:ss"));
        rs.put ("last_point", DateUtil.longStr (last_point, "yyyy-MM-dd HH:mm:ss"));
        return rs;
    }

    public Map <String, Object> operateBackendDevice (String userIdStr, String sn, String operate) {

        HttpUtil.validateNull (new String [] {
                "user_id", "device_sn"
        }, new String [] {
                userIdStr, sn
        });
        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });
        HttpUtil.validateIntScope (operate, 1, 3);
        Long serviceId = HttpUtil.getLong (userIdStr);
        Map <String, Object> rs = new HashMap <String, Object> ();

        sn = sn.trim ();

        int oper = Integer.parseInt (operate);

        if (Device.ENABLE == oper) {// 启用
            rs.putAll (enable (sn, serviceId));
        }
        else if (Device.DISABLE == oper) {// 禁用
            rs.putAll (disable (sn, serviceId));
        }
        else if (Device.DELETE == oper) {// 删除
            rs.putAll (delete (sn, serviceId));
        }

        rs.put ("operate", operate);

        return rs;
    }

    /**
     * 删除设备
     */
    public Map <String, Object> delete (String sn, Long serviceId) {

        Map <String, Object> rs = new HashMap <String, Object> ();

        Device device = deviceService.findBySnAndServiceUser (sn, serviceId);

        Assert.notNull (device, UsConstants.getI18nMessage (UsConstants.USER_NOTDEVICE));

        // if (device == null) {
        // return null;
        // }

        try {
            // 是否已被绑定
            boolean isbind = userDeviceService.isBind (sn);

            // if (isConfirm) {

            // 删除轨迹
            //TODO 报错先注掉
            //		    spotService.deleteByDeviceSn(sn);
            // 删除报警
            alarmService.delete (sn);
            // 删除指令
            instructService.delete (sn);
            // 解除绑定，删除设备
            deviceService.delete (device);
            // 删除禁用表中设备
            userDeviceForbidService.deleteByDevice (sn);

            //TODO 短报文是否删除
            //			handsetToConductorService.deleteByHandSetSn(sn);

        }
        catch (Exception e) {
            e.printStackTrace ();
        }

        // TODO 删除缓存
        deviceCache.removeDevice (sn);
        positionCache.removePosition (sn);
        positionCache.removeInstruct (sn);

        rs.put ("user_id", serviceId);
        rs.put ("sn", sn);
        rs.put (Logic.DESC, MessageManager.DELETE_SUCCESS ());
        return rs;
        // }
    }

    /**
     * 禁用设备
     */
    public Map <String, Object> disable (String sn, Long serviceId) {

        Map <String, Object> rs = new HashMap <String, Object> ();

        Device device = deviceService.findBySnAndServiceUser (sn, serviceId);

        device.setStatus (Device.DISABLE);// 禁用
        deviceService.update (device);

        List <UserDevice> uds = userDeviceService.findBySn (sn);

        if (uds != null && uds.size () > 0) {

            for (UserDevice ud : uds) {
                if (ud == null)
                    continue;
                UserDeviceForbid udf = new UserDeviceForbid ();
                udf.setDeviceSn (ud.getDeviceSn ());
                udf.setTime (ud.getTime ());
                udf.setUserId (ud.getUserId ());
                userDeviceForbidService.saveOrUpdate (udf);
            }

            userDeviceService.deleteByDevice (sn);
        }

        rs.put ("user_id", serviceId);
        rs.put ("sn", sn);
        rs.put (Logic.DESC, MessageManager.UPDATE_SUCCESS ());
        return rs;
    }

    /**
     * 启用设备
     */
    public Map <String, Object> enable (String sn, Long serviceId) {

        Map <String, Object> rs = new HashMap <String, Object> ();

        Device device = deviceService.findBySnAndServiceUser (sn, serviceId);

        device.setStatus (Device.ENABLE);// 启用
        deviceService.update (device);

        List <UserDeviceForbid> udfs = userDeviceForbidService.findBySn (sn);

        if (udfs != null && udfs.size () > 0) {
            for (UserDeviceForbid udf : udfs) {
                if (udf == null)
                    continue;
                UserDevice ud = new UserDevice ();
                ud.setDeviceSn (udf.getDeviceSn ());
                ud.setTime (udf.getTime ());
                ud.setUserId (udf.getUserId ());
                userDeviceService.saveOrUpdate (ud);
            }
            userDeviceForbidService.deleteByDevice (sn);
        }

        rs.put ("user_id", serviceId);
        rs.put ("sn", sn);
        rs.put (Logic.DESC, MessageManager.UPDATE_SUCCESS ());
        return rs;
    }

    public Map <String, Object> addDevice (String userIdStr, String sn, String phone, String isvirtual, String type,
            String protocol, String hardware, String conductorSn) {

        HttpUtil.validateNull (new String [] {
                "user_id", "device_sn", "phone", "virtual", "type", "protocol"
        }, new String [] {
                userIdStr, sn, phone, isvirtual, type, protocol
        });

        HttpUtil.validateLong (new String [] {
            "user_id"
        }, new String [] {
            userIdStr
        });

        Long serviceId = HttpUtil.getLong (userIdStr);

        sn = sn.trim ();

        Device dev = deviceService.get (sn);
        Assert.isNull (dev, UsConstants.getI18nMessage (UsConstants.DEVICE_REGISTERED));

        //		if (dev == null) {
        //			Map<String, Object> rs = new HashMap<String, Object>();
        //			rs.put(Logic.DESC,UsConstants.getI18nMessage(UsConstants.DEVICE_REGISTERED));
        //			return rs;
        //		}

        Device device = new Device ();

        device.setServiceId (serviceId);// 服务用户id
        device.setSn (sn);// sn号
        device.setName (sn);// 设备名称
        device.setProtocol (HttpUtil.getInt (protocol));// 协议(obd,808,m2616)
        device.setType (HttpUtil.getInt (type));// 设备类型（车人宠物）
        device.setPhone (phone);// sim卡号
        device.setIsvirtual (HttpUtil.getInt (isvirtual));
        device.setStatus (1);// 默认启用

        String app_name = userService.getAppName (serviceId);
        device.setAppName (app_name);

        device.setStamp (new Date ().getTime ());// 修改时间，初始是当前时间
        device.setTime (new Date ().getTime ());// 设备添加时间

        if (type.equals ("1")) {// 为车添加默认值

            if (!StringUtils.isBlank (hardware)) {
                device.setHardware (hardware);
            }
            device.setFenceSwitch (2);
            device.setMoveSwitch (2);
            device.setSpeedingSwitch (2);
            device.setTick (30);
            device.setSpeedThreshold (120);
            device.setCar ("");
            device.setIcon ("1.png");
        }
        else if (type.equals ("2")) {// 宠物
            device.setTick (240);
            device.setFenceSwitch (2);
            device.setWeight (0.0f);
            device.setBirth (0l);
            device.setAge (0);
            device.setHeight (0.0f);
            device.setIcon ("2.png");
        }
        else if (type.equals ("3")) {// 人
            device.setSpeedingSwitch (2);
            device.setTick (60);
            device.setFenceSwitch (2);
            device.setSosNum ("");
            device.setSpeedThreshold (30);
            device.setIcon ("3.png");
        }
        else if (type.equals ("4")) {//手持机
            device.setSpeedingSwitch (2);
            device.setTick (60);
            device.setFenceSwitch (2);
            device.setSosNum ("");
            device.setSpeedThreshold (30);
            device.setIcon ("3.png");
            //TODO conductorSn放到设备指挥机表
            try {
                HandSetToConductor hToConductor = new HandSetToConductor ();
                hToConductor.setConductorId (conductorSn);
                hToConductor.setDeviceSn (device.getSn ());
                handsetToConductorService.save (hToConductor);
            }
            catch (Exception e) {
                logger.info ("", e);
                e.printStackTrace ();
            }

        }

        deviceService.saveOrUpdate (device);

        Map <String, Object> rs = new HashMap <String, Object> ();
        rs.put (Logic.DESC, MessageManager.ADD_SUCCESS ());
        return rs;
    }

    public Long getBegin (String sn) {

        HttpUtil.validateNull (new String [] {
            "sn"
        }, new String [] {
            sn
        });
        Spot spot = spotService.getBeginSpot (sn);
        Long begin = 1420041600000l;// 默认开通时间 2015-01-01
        if (spot != null) {
            begin = spot.getReceive ();
        }
        return begin;
    }

}
