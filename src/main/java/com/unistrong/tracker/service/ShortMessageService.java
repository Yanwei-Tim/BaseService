package com.unistrong.tracker.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unistrong.tracker.dao.ShortMessageDao;
import com.unistrong.tracker.model.ShortMessage;

@Service
public class ShortMessageService {

    @Resource
    private ShortMessageDao shortMessageDao;

    public List <ShortMessage> getShortMessageFromSend (String sendHandsetId) {

       return shortMessageDao.getShortMessageFromSend(sendHandsetId);
    }
}
