package com.unistrong.tracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.DeviceDao;
import com.unistrong.tracker.dao.HandsetToUserDao;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.HandsetToUser;

@Service
public class HandsetToUserService {
    
    @Resource
    private HandsetToUserDao handsetToUserDao;
    
    @Resource
    private DeviceDao deviceDao;

    public void save (HandsetToUser handsetToUser) {

        handsetToUserDao.save (handsetToUser);
    }

    public List <Device> findHandsOfUser (long userId) {
        //TODO 自己有一个设备和用户的关系表？
        List <Device> devices = deviceDao.findAllByUser (userId, -1, -1);
        List <Device> handsets = new ArrayList <Device> ();
        for (Device device : devices) {
            if (device.getType () == 4) {//如果是手持机
                handsets.add (device);
            }
        }
        return handsets;
    }
}
