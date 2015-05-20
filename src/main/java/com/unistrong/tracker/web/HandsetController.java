package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unistrong.tracker.handle.HandsetHandle;
import com.unistrong.tracker.model.Device;
import com.unistrong.tracker.model.HandsetToUser;
import com.unistrong.tracker.model.ShortMessage;
import com.unistrong.tracker.model.UserDevice;

/**
 * 
 * @author wyr
 *
 */
@Controller
public class HandsetController {

    @Resource
    private HandsetHandle handsetHandle;

    /**
     * 绑定手持机到用户
     */
    @RequestMapping("/handset.buildHandset.do")
    @ResponseBody public Map <String, Object> buildHandset (UserDevice userDevice) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        rs.put ("ret", 1);
        Map <String, String> result = handsetHandle.buildHandset (userDevice);
        rs.putAll (result);
        return rs;
    }

    /**
     * 获取该用户下的手持机
     */
    @RequestMapping("/handset.getHandsetOfUser.do")
    public Map <String, Object> getHandsetOfUser (long userId) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        rs.put ("ret", 1);
        List <Device> handsets = handsetHandle.getHandsetOfUser (userId);
        rs.put ("handsets", handsets);
        return rs;
    }
   
    /**
     * 查询手持机对应的短报文信息
     */
    @RequestMapping("/handset.getShortMessageOfHandset.do")
    public Map <String, Object> getShortMessageOfHandset (String sendHandsetId ) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        rs.put ("ret", 1);
        List <ShortMessage> shortMessages = handsetHandle.getShortMessageFromSend (sendHandsetId);
        rs.put ("shortMessages", shortMessages);
        return rs;
    }
    
    
}
