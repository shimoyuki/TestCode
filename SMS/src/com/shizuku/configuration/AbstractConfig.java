package com.shizuku.configuration;

import org.apache.log4j.Logger;

public abstract class AbstractConfig implements Config {

	protected String path;
	protected Logger log = Logger.getLogger(getClass());

	public AbstractConfig(String path) {
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
}
