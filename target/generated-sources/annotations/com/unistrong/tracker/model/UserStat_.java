package com.unistrong.tracker.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserStat.class)
public abstract class UserStat_ {

	public static volatile SingularAttribute<UserStat, Date> fStatDate;
	public static volatile SingularAttribute<UserStat, Double> fMonthRate;
	public static volatile SingularAttribute<UserStat, Long> fServiceId;
	public static volatile SingularAttribute<UserStat, Long> fId;
	public static volatile SingularAttribute<UserStat, Double> fDayRate;
	public static volatile SingularAttribute<UserStat, Integer> fUserActive;
	public static volatile SingularAttribute<UserStat, Integer> fUserNew;
	public static volatile SingularAttribute<UserStat, Integer> fUserTotal;

}

