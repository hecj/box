package com.boxamazing.admin.plugin;

public class Quartz {
	
	private String clazz;
	
	private String cron;
	
	private boolean enable;
	
	public Quartz() {
		
	}

	public Quartz(String clazz, String cron, boolean enable) {
		this.clazz = clazz;
		this.cron = cron;
		this.enable = enable;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	@Override
	public String toString() {
		return "[clazz:"+clazz+",cron:"+cron+",enable:"+enable+"]";
	}
	
}
