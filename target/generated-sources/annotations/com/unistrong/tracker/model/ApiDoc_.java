package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ApiDoc.class)
public abstract class ApiDoc_ extends module.orm.IdEntity_ {

	public static volatile SingularAttribute<ApiDoc, Long> modelId;
	public static volatile SingularAttribute<ApiDoc, String> desc;
	public static volatile SingularAttribute<ApiDoc, String> apiName;
	public static volatile SingularAttribute<ApiDoc, String> api;

}

