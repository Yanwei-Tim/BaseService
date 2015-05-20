package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserToken.class)
public abstract class UserToken_ {

	public static volatile SingularAttribute<UserToken, Long> expire;
	public static volatile SingularAttribute<UserToken, String> token;
	public static volatile SingularAttribute<UserToken, Long> userId;

}

