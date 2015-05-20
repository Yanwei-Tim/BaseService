package com.unistrong.tracker.entry.vo;

import java.lang.reflect.Method;

public class ReflectCmd {

	private int id;

	private Object object;

	private Method method;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
