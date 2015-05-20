package com.unistrong.tracker.dao;

import module.cache.operate.Delete;
import module.cache.operate.GetAndSet;
import module.orm.BaseHBDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.model.UserToken;

@Component
@Scope("singleton")
public class UserTokenDao extends BaseHBDao<UserToken, Long> {

    @Autowired
    public UserTokenDao (@Qualifier("sessionFactory") SessionFactory session) {

        super ();
        this.setSessionFactory (session);
    }

	@Delete(prefix = "UserToken")
	public void saveOrUpdate(UserToken userToken) {
		super.saveOrUpdate(userToken);
	}

	@GetAndSet(prefix = "UserToken", expiration = 60 * 60 * 10)
	public UserToken get(Long id) {
		return super.get(id);
	}

	public UserTokenDao() {
		super(UserToken.class, "id");
	}

}
