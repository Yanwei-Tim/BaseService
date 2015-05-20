package com.unistrong.tracker.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.ShortMessage;

import module.orm.BaseHBDao;

@Component
@Scope("singleton")
public class ShortMessageDao extends BaseHBDao <ShortMessage, Long> {
    
    private final String SELECT_SHORTMESSAGE_FROM_SEND = "from ShortMessage s where s.sendHandsetId = ?";
    @Autowired
    public ShortMessageDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

    public List <ShortMessage> getShortMessageFromSend (String sendHandsetId) {
        
        List <ShortMessage> shortMessages = list (SELECT_SHORTMESSAGE_FROM_SEND, -1, -1, sendHandsetId);
        return shortMessages;
    }
}
