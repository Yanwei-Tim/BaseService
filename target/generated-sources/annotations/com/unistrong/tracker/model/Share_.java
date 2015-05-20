package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Share.class)
public abstract class Share_ {

	public static volatile SingularAttribute<Share, Long> id;
	public static volatile SingularAttribute<Share, Long> expire;
	public static volatile SingularAttribute<Share, Long> userId;
	public static volatile SingularAttribute<Share, String> deviceSn;
	public static volatile SingularAttribute<Share, Integer> contentType;
	public static volatile SingularAttribute<Share, Long> publish;
	public static volatile SingularAttribute<Share, Long> act;
	public static volatile SingularAttribute<Share, String> locationTime;
	public static volatile SingularAttribute<Share, Long> end;
	public static volatile SingularAttribute<Share, String> mapType;
	public static volatile SingularAttribute<Share, Integer> privacyType;
	public static volatile SingularAttribute<Share, Long> begin;

}

