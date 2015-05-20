package com.unistrong.tracker.handle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.unistrong.tracker.dao.ShortMessageDao;
import com.unistrong.tracker.model.Instruct;
import com.unistrong.tracker.model.ShortMessage;
import com.unistrong.tracker.service.InstructService;

@Component
public class ShortMessageHandle {

    @Resource
    private ShortMessageDao shortMessageDao;
    @Resource
    private InstructService instructService;

    public Map <String, Object> sentMessage (ShortMessage shortMessage) {

        Map <String, Object> result = new HashMap <String, Object> ();
        //TODO 存储短报文记录
        //instruct reply置0
        Instruct instruct = instructService.getByType (shortMessage.getSendHandsetId (), 2001);
        if (instruct == null) {
            instruct = new Instruct ();
            instruct.setDeviceSn (shortMessage.getSendHandsetId ());
            instruct.setType (2001);
            instruct.setId (shortMessage.getSendHandsetId ()+"-"+"2001");
//            result.put ("desc", shortMessage.getSendHandsetId ()+"设备的指令不存在,短报文未存储");
        }
            shortMessage.setSendDate (new Date ());
            shortMessage.setStatus (0);
            shortMessageDao.save (shortMessage);
            instruct.setReply (0);
            instructService.saveOrUpdate (instruct);
            result.put ("desc","已存储该短报文");
        return result;
    }

}
