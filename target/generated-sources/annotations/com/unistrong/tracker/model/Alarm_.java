package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Alarm.class)
public abstract class Alarm_ extends module.orm.IdEntity_ {

	public static volatile SingularAttribute<Alarm, Long> systime;
	public static volatile SingularAttribute<Alarm, Integer> indoor;
	public static volatile SingularAttribute<Alarm, Double> speed;
	public static volatile SingularAttribute<Alarm, String> extra;
	public static volatile SingularAttribute<Alarm, String> cell;
	public static volatile SingularAttribute<Alarm, Integer> lock;
	public static volatile SingularAttribute<Alarm, String> deviceSn;
	public static volatile SingularAttribute<Alarm, Double> lng;
	public static volatile SingularAttribute<Alarm, String> addr;
	public static volatile SingularAttribute<Alarm, Integer> type;
	public static volatile SingularAttribute<Alarm, String> info;
	public static volatile SingularAttribute<Alarm, String> system;
	public static volatile SingularAttribute<Alarm, Long> time;
	public static volatile SingularAttribute<Alarm, Double> px;
	public static volatile SingularAttribute<Alarm, Double> py;
	public static volatile SingularAttribute<Alarm, Integer> mode433;
	public static volatile SingularAttribute<Alarm, Integer> accMode;
	public static volatile SingularAttribute<Alarm, String> name;
	public static volatile SingularAttribute<Alarm, Integer> battery;
	public static volatile SingularAttribute<Alarm, Integer> read;
	public static volatile SingularAttribute<Alarm, Double> lat;
	public static volatile SingularAttribute<Alarm, String> addrEn;

}

