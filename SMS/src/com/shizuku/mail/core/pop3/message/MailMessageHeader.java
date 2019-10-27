package com.shizuku.mail.core.pop3.message;

/**
 * An individual message header.<br>
 *
 * An MailMessageHeader object with a null value is used as a placeholder
 * for headers of that name, to preserve the order of headers.
 * A placeholder MailMessageHeader object with a name of ":" marks
 * the location in the list of headers where new headers are
 * added by default.
 */
public class MailMessageHeader extends Header {

    /**
     * Note that the value field from the superclass
     * isn't used in this class.  We extract the value
     * from the line field as needed.  We store the line
     * rather than just the value to ensure that we can
     * get back the exact original line, with the original
     * whitespace, etc.
     */
    private String line;

    /**
     * Constructor that takes a line and splits out
     * the header name.
     */
    public MailMessageHeader(String line) {
        super("", "");
        int i = line.indexOf(':');
        if (i < 0) {
            // should never happen
            name = line.trim();
        } else {
            name = line.substring(0, i).trim();
        }
        this.line = line;
    }

    /**
     * Constructor that takes a header name and value.
     */
    public MailMessageHeader(String n, String v) {
        super(n, "");
        if (v != null) {
            this.line = n + ": " + v;
        } else {
            this.line = null;
        }
    }

    /**
     * Return the "value" part of the header line.
     */
    @Override
    public String getValue() {
        int i = line.indexOf(':');
        if (i < 0) {
            return line;
        }
        // skip whitespace after ':'
        int j;
        for (j = i + 1; j < line.length(); j++) {
            char c = line.charAt(j);
            if (!(c == ' ' || c == '\t' || c == '\r' || c == '\n')) {
                break;
            }
        }
        return line.substring(j);
    }

    /**
     * Get the line
     * @return the line
     */
    public String getLine() {
        return this.line;
    }

    /**
     * Set the line
     * @param line
     */
    public void setLine(String line) {
        this.line = line;
    }
}
