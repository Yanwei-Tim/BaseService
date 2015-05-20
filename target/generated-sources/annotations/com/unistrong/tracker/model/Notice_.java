package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notice.class)
public abstract class Notice_ extends module.orm.IdEntity_ {

	public static volatile SingularAttribute<Notice, String> content;
	public static volatile SingularAttribute<Notice, String> platform;
	public static volatile SingularAttribute<Notice, String> title;
	public static volatile SingularAttribute<Notice, String> condition;
	public static volatile SingularAttribute<Notice, Integer> frequency;
	public static volatile SingularAttribute<Notice, Long> onlinetime;
	public static volatile SingularAttribute<Notice, Integer> type;
	public static volatile SingularAttribute<Notice, Long> offlinetime;
	public static volatile SingularAttribute<Notice, String> url;

}

