package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unistrong.tracker.handle.ShortMessageHandle;
import com.unistrong.tracker.model.ShortMessage;

@Controller
public class ShortMessageController {

    @Resource
    private ShortMessageHandle shortMessageHandle;

    /**
     * 短报文发送
     */
    @RequestMapping("/shortMessage.sendMessage.do")
    public @ResponseBody Map <String, Object> sendMessage (ShortMessage shortMessage) {

        Map <String, Object> rs = new HashMap <String, Object> ();
        Map <String, Object> result = shortMessageHandle.sentMessage (shortMessage);
        rs.putAll (result);
        rs.put ("ret", 1);
        return rs;
    }
}
