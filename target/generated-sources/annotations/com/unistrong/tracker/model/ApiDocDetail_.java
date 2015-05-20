package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ApiDocDetail.class)
public abstract class ApiDocDetail_ extends module.orm.IdEntity_ {

	public static volatile SingularAttribute<ApiDocDetail, String> desc;
	public static volatile SingularAttribute<ApiDocDetail, String> json;
	public static volatile SingularAttribute<ApiDocDetail, String> name;
	public static volatile SingularAttribute<ApiDocDetail, Integer> docId;
	public static volatile SingularAttribute<ApiDocDetail, String> type;
	public static volatile SingularAttribute<ApiDocDetail, Integer> returnType;
	public static volatile SingularAttribute<ApiDocDetail, String> must;

}

