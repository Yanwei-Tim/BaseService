package com.unistrong.tracker.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeviceStat.class)
public abstract class DeviceStat_ {

	public static volatile SingularAttribute<DeviceStat, Date> fStatDate;
	public static volatile SingularAttribute<DeviceStat, Integer> fTotalDevice;
	public static volatile SingularAttribute<DeviceStat, Integer> fInstallDevice;
	public static volatile SingularAttribute<DeviceStat, Double> fNewInstallRate;
	public static volatile SingularAttribute<DeviceStat, Integer> fInstallTotalDevice;
	public static volatile SingularAttribute<DeviceStat, Long> fServiceId;
	public static volatile SingularAttribute<DeviceStat, Long> fId;
	public static volatile SingularAttribute<DeviceStat, Double> fInstallRate;

}

