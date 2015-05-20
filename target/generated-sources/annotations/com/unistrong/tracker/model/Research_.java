package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Research.class)
public abstract class Research_ {

	public static volatile SingularAttribute<Research, String> topics;
	public static volatile SingularAttribute<Research, Long> time;
	public static volatile SingularAttribute<Research, Integer> status;
	public static volatile SingularAttribute<Research, Long> userId;

}

