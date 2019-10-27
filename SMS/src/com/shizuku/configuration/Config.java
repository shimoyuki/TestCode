package com.shizuku.configuration;

/**
 * 定义配置操作的接口
 */
public interface Config {

	/**
	 * Returns the value to which the specified key is mapped.
	 * 
	 * @param key
	 *            a specific key
	 * @return the value to which the key is mapped; <code>null</code> if the
	 *         key is not mapped to any value.
	 */
	public String get(String key);

	/**
	 * 
	 * Maps the specified <code>key</code> to the specified <code>value</code>.
	 * Neither the key nor the value can be <code>null</code>.
	 * 
	 * @param key
	 *            a specific key
	 * @param value
	 * @return the previous value of the specified key
	 */
	public String put(String key, String value);

	/**
	 * Removes the key (and its corresponding value).
	 * 
	 * @param key
	 *            a specific key
	 * @return true if delete successfully.
	 */
	public boolean remove(String key);

	/**
	 * Returns true if this key is mapped.
	 * 
	 * @param key
	 *            a specific key
	 * @return true if the key is existing, otherwise return false.
	 */
	public boolean find(String key);
}
