package com.shizuku.mail.core.pop3.message;

/**
 * The Header class stores a name/value pair to represent headers.
 */
public class Header {

    /**
     * The name of the header.
     */
    protected String name;
    /**
     * The value of the header.
     */
    protected String value;

    /**
     * Construct a Header object.
     *
     * @param name	name of the header
     * @param value	value of the header
     */
    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of this header.
     *
     * @return 		name of the header
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of this header.
     *
     * @return 		value of the header
     */
    public String getValue() {
        return value;
    }
}
