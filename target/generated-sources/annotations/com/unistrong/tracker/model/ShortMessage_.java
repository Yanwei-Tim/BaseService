package com.unistrong.tracker.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShortMessage.class)
public abstract class ShortMessage_ {

	public static volatile SingularAttribute<ShortMessage, Date> sendDate;
	public static volatile SingularAttribute<ShortMessage, Long> id;
	public static volatile SingularAttribute<ShortMessage, Integer> status;
	public static volatile SingularAttribute<ShortMessage, String> sendHandsetId;
	public static volatile SingularAttribute<ShortMessage, String> sendContent;
	public static volatile SingularAttribute<ShortMessage, String> receiveHandsetId;

}

