package com.example.demo.controllers.redis;

public class Operacion {

	private String operacion;
	
	private String key;
	
	private Object value;

	public Operacion() {}

	public Operacion(String operacion, String key, Object value) {
		
		this.operacion=operacion;
		this.key=key;
		this.value=value;
		
	}
	
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
