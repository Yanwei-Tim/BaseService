package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserDevice.class)
public abstract class UserDevice_ extends module.orm.IdEntity_ {

	public static volatile SingularAttribute<UserDevice, Long> time;
	public static volatile SingularAttribute<UserDevice, Long> userId;
	public static volatile SingularAttribute<UserDevice, String> deviceSn;

}

