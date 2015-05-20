package com.unistrong.tracker.entry.context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import module.util.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.unistrong.tracker.entry.vo.ReflectCmd;
import com.unistrong.tracker.model.Protocol;

@Component
public class Maping implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(Maping.class);

	private Map<Integer, ReflectCmd> mapped = new HashMap<Integer, ReflectCmd>();

	private ApplicationContext ctx;

	@PostConstruct
	public void init() throws Exception {
		for (Integer key : Strategy.strategyMap.keySet()) {
			String reflect = Strategy.strategyMap.get(key);
			String[] beanMethod = reflect.split("\\.");
			String beanName = beanMethod[0];
			String methodName = beanMethod[1];
			Object object = ctx.getBean(beanName);
			Method method = object.getClass().getMethod(methodName,
					new Class[] { Map.class, Protocol.class });
			ReflectCmd cmdMaping = new ReflectCmd();
			cmdMaping.setId(key);
			cmdMaping.setObject(object);
			cmdMaping.setMethod(method);
			mapped.put(key, cmdMaping);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> cmdDo(Map<String, Object> cmd, Integer cmdInt, Protocol protocol) {
		ReflectCmd reflectCmd = mapped.get(cmdInt);
		Assert.notNull(reflectCmd, "cmd暂不支持:" + cmdInt);
		Method method = reflectCmd.getMethod();
		Map<String, Object> rs = null;
		try {
			rs = (Map<String, Object>) method.invoke(reflectCmd.getObject(), cmd, protocol);
		} catch (IllegalArgumentException ex) {
			logger.error("error:", ex);
		} catch (IllegalAccessException ex) {
			logger.error("error:", ex);
		} catch (InvocationTargetException ex) {
			Throwable target = ex.getTargetException();
			if (target instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) target;
			} else
				throw new RuntimeException(target);
		}
		return rs;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
