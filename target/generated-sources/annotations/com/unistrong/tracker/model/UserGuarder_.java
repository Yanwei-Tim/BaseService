package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserGuarder.class)
public abstract class UserGuarder_ extends module.orm.IdEntity_ {

	public static volatile SingularAttribute<UserGuarder, String> phone;
	public static volatile SingularAttribute<UserGuarder, String> address;
	public static volatile SingularAttribute<UserGuarder, String> company;
	public static volatile SingularAttribute<UserGuarder, String> guarderName;

}

