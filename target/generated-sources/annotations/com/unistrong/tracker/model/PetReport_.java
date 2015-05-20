package com.unistrong.tracker.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PetReport.class)
public abstract class PetReport_ {

	public static volatile SingularAttribute<PetReport, String> id;
	public static volatile SingularAttribute<PetReport, Long> time;
	public static volatile SingularAttribute<PetReport, String> deviceSn;
	public static volatile SingularAttribute<PetReport, Double> slow;
	public static volatile SingularAttribute<PetReport, Double> rest;
	public static volatile SingularAttribute<PetReport, Double> fast;
	public static volatile SingularAttribute<PetReport, Integer> energy;

}

