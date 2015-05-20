package com.unistrong.tracker.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.ShortMessage;
import com.unistrong.tracker.model.UserDevice;
import com.unistrong.tracker.service.DeviceService;
import com.unistrong.tracker.service.HandsetToConductorService;
import com.unistrong.tracker.service.HandsetToUserService;
import com.unistrong.tracker.service.ShortMessageService;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.UserService;

@Component
public class HandsetHandle implements ApplicationContextAware {

    private ApplicationContext        ctx;

    @Resource
    private HandsetToConductorService hToConductorService;
    @Resource
    private HandsetToUserService      hToUserService;
    @Resource
    private ShortMessageService       shortMessageService;
    @Resource
    private UserDeviceService         userDeviceService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private UserService userService;
    @Override
    public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {

        this.ctx = applicationContext;
    }

    public Map <String, String> buildHandset (UserDevice userDevice) {

        Map <String, String> result = new HashMap <String, String> ();
        if (deviceService.get (userDevice.getDeviceSn ()) == null) {
            result.put ("desc", "没有该设备");
            return result;
        }
        if (userService.get (userDevice.getUserId ()) == null) {
            result.put ("desc", "没有该用户");
            return result; 
        }
        if (userDeviceService.isBind (userDevice.getDeviceSn ())) {
            result.put ("desc", "该设备已绑定给其他用户,请从新选择设备");
            return result; 
        }
        else {
            userDeviceService.saveOrUpdate (userDevice);
            result.put ("desc", "设备绑定成功");
        }
        return result;
    }

    public List <Device> getHandsetOfUser (long userId) {
        
        List <Device> handsets = userDeviceService.findOneTypeByUserId (userId, 4);
        return handsets;
    }

    public List <ShortMessage> getShortMessageFromSend (String sendHandsetId) {

        List <ShortMessage> shortMessages = shortMessageService.getShortMessageFromSend (sendHandsetId);
        return shortMessages;
    }
}
