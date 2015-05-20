package com.unistrong.tracker.dao;

import java.util.ArrayList;
import java.util.List;

import module.orm.BaseSqlDao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unistrong.tracker.model.Spot;
import com.unistrong.tracker.util.DBUtils;
import com.unistrong.tracker.util.Helper;
import com.unistrong.tracker.util.TableUtils;

@Repository
@Scope("singleton")
public class SpotDao extends BaseSqlDao <Spot, Long> {

    private Logger log = LoggerFactory.getLogger (getClass ());

    private String tableName;

    @Autowired
    public SpotDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
        this.tableName = TableUtils.getName (Spot.class);
    }

    private String getTableName (String deviceSn) {

        String tableName = this.tableName;
        if (Helper.isDividedTable ()) {// 分表
            try {
                tableName += DBUtils.getSpotTableName (deviceSn);
            }
            catch (Exception e) {
                log.error (" divide table failed !!! ", e);
            }
        }
        return tableName;
    }

    @Override
    @Transactional
    public void saveOrUpdate (Spot en) {

        if (!Helper.isDividedTable ()) {
            super.saveOrUpdate (en);
        }
        else {
            try {
                StringBuilder sb = new StringBuilder ();
                sb.append (" INSERT INTO  ")
                        .append (this.getTableName (en.getDeviceSn ()))
                        .append (" ( f_device_sn , f_receive , f_direction , f_distance , f_info , f_lat ,")
                        .append (" f_lng , f_mode , f_speed , f_stayed, f_systime , f_acc , f_rfid , f_cell )")
                        .append (" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                        .append (" ON DUPLICATE KEY UPDATE ")
                        .append (" f_direction = ?, f_distance = ?, f_info = ?, f_lat = ?,")
                        .append (
                                " f_lng = ?, f_mode = ?, f_speed = ?, f_stayed= ?, f_systime = ?, f_acc = ?, f_rfid = ?, f_cell = ?");

                List <Object> ls = new ArrayList <Object> ();
                ls.add (en.getDeviceSn ());
                ls.add (en.getReceive ());
                ls.add (en.getDirection ());
                ls.add (en.getDistance ());
                ls.add (en.getInfo ());
                ls.add (en.getLat ());
                ls.add (en.getLng ());
                ls.add (en.getMode ());
                ls.add (en.getSpeed ());
                ls.add (en.getStayed ());
                ls.add (en.getSystime ());
                ls.add (en.getAccMode ());
                ls.add (en.getMode433 ());
                ls.add (en.getCell ());
                ls.add (en.getDirection ());
                ls.add (en.getDistance ());
                ls.add (en.getInfo ());
                ls.add (en.getLat ());
                ls.add (en.getLng ());
                ls.add (en.getMode ());
                ls.add (en.getSpeed ());
                ls.add (en.getStayed ());
                ls.add (en.getSystime ());
                ls.add (en.getAccMode ());
                ls.add (en.getMode433 ());
                ls.add (en.getCell ());

                save (sb.toString (), ls);

            }
            catch (Exception e) {
                log.error (" sql insert failed !!! ", e);
            }

        }
    }

    /**
     * 查询某时间段的轨迹(状态列表,点列表)
     */
    public List <Spot> getTrack (String deviceSn, Long begin, Long end) {

        StringBuilder sb = new StringBuilder ();
        sb.append ("SELECT * FROM ").append (this.getTableName (deviceSn))
                .append (" s WHERE s.f_device_sn=? and s.f_receive >=? and s.f_receive <=? ORDER BY s.f_receive DESC");

        List <Object> ls = new ArrayList <Object> ();
        ls.add (deviceSn);
        ls.add (begin);
        ls.add (end);

        return query (sb.toString (), ls);
    }

    public List <Spot> getTrackGps (String deviceSn, Long begin, Long end) {

        StringBuilder sb = new StringBuilder ();
        sb.append ("SELECT * FROM ")
                .append (this.getTableName (deviceSn))
                .append (
                        " s WHERE s.f_device_sn=? and s.f_receive >=? and s.f_receive <=? and s.f_mode=?  ORDER BY s.f_receive DESC");

        List <Object> ls = new ArrayList <Object> ();
        ls.add (deviceSn);
        ls.add (begin);
        ls.add (end);
        ls.add ("A");

        return query (sb.toString (), ls);
    }

    public Spot getSpot (String deviceSn, Long receive) {

        StringBuilder sb = new StringBuilder ();
        sb.append ("SELECT * FROM ").append (this.getTableName (deviceSn))
                .append (" s WHERE s.f_device_sn=? and s.f_receive =? ");

        return uniqueSql (sb.toString (), deviceSn, receive);
    }

    public Spot getSpot (String deviceSn, boolean isAsc) {

        StringBuilder sb = new StringBuilder ();

        sb.append (" SELECT s.f_receive FROM ").append (this.getTableName (deviceSn))
                .append (" s WHERE s.f_device_sn=? AND s.f_receive>0 ").append (" order by s.f_receive ");

        if (isAsc) {
            sb.append (" asc ");
        }
        else {
            sb.append (" desc ");
        }

        sb.append (" limit 1 ");

        return uniqueSql (sb.toString (), deviceSn);
    }

    public int deleteByDeviceSn (String deviceSn) {

        StringBuilder sb = new StringBuilder ();
        sb.append (" delete FROM ").append (this.getTableName (deviceSn)).append ("  WHERE f_device_sn=? ");

        return executeUpdateSql (sb.toString (), deviceSn);
    }

    public Spot getBeginSpot (String deviceSn) {

        StringBuilder sb = new StringBuilder ();
        sb.append ("SELECT * FROM ").append (this.getTableName (deviceSn))
                .append (" s WHERE s.f_device_sn=? order by f_receive asc limit 0,1");

        return uniqueSql (sb.toString (), deviceSn);
    }

}
