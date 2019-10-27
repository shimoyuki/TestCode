package com.shizuku.mail.core.pop3.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Mail Message Headers.
 */
public class MailMessageHeaders {

    /**
     * The actual list of Headers, including placeholder entries.
     * Placeholder entries are Headers with a null value and
     * are never seen by clients of the InternetHeaders class.
     * Placeholder entries are used to keep track of the preferred
     * order of headers.  Headers are never actually removed from
     * the list, they're converted into placeholder entries.
     * New headers are added after existing headers of the same name
     * (or before in the case of <code>Received</code> and
     * <code>Return-Path</code> headers).  If no existing header
     * or placeholder for the header is found, new headers are
     * added after the special placeholder with the name ":".
     */
    protected List<MailMessageHeader> headers;

    /**
     * Create an empty MailMessageHeaders object.  Placeholder entries
     * are inserted to indicate the preferred order of headers.
     */
    public MailMessageHeaders() {
        headers = new ArrayList<MailMessageHeader>(40);
        String headerArray[] = getDefaultMailMessageHeaderNames();
        for (int i = 0; i < headerArray.length; i++) {
            headers.add(new MailMessageHeader(headerArray[i], null));
        }
    }

    /**
     * Read and parse the given RFC822 message stream till the 
     * blank line separating the header from the body. The input 
     * stream is left positioned at the start of the body. The 
     * header lines are stored internally. <p>
     *
     * For efficiency, wrap a BufferedInputStream around the actual
     * input stream and pass it as the parameter. <p>
     *
     * No placeholder entries are inserted; the original order of
     * the headers is preserved.
     *
     * @param br Buffered Reader
     */
    public MailMessageHeaders(BufferedReader br) throws MessageException {
        headers = new ArrayList<MailMessageHeader>(40);
        try {
            load(br);
        } catch (IOException ioe) {
            throw new MessageException();
        }
    }

    /**
     * 
     * Read and parse the given RFC822 message stream till the
     * blank line separating the header from the body. Store the
     * header lines inside this InternetHeaders object. The order
     * of header lines is preserved. <p>
     *
     * Note that the header lines are added into this InternetHeaders
     * object, so any existing headers in this object will not be
     * affected.  Headers are added to the end of the existing list
     * of headers, in order.
     *
     * @param br Buffered Reader
     * @throws com.shizuku.mail.Message.MessageException
     * @throws java.io.IOException
     */
    public void load(BufferedReader br) throws MessageException, IOException {
        // Read header lines until a blank line. It is valid
        // to have BodyParts with no header lines.
        String line;
        String prevline = null; // the previous header line, as a string
        // a buffer to accumulate the header in, when we know it's needed
        StringBuffer lineBuffer = new StringBuffer();

        do {
            line = br.readLine();
            if (line != null && (line.startsWith(" ") || line.startsWith("\t"))) {
                // continuation of header
                if (prevline != null) {
                    lineBuffer.append(prevline);
                    prevline = null;
                }
                lineBuffer.append("\r\n");
                lineBuffer.append(line);
            } else {
                // new header
                if (prevline != null) {
                    addHeaderLine(prevline);
                } else if (lineBuffer.length() > 0) {
                    // store previous header first
                    addHeaderLine(lineBuffer.toString());
                    lineBuffer.setLength(0);
                }
                prevline = line;
            }
        } while (line != null && line.length() > 0);
    }

    /**
     * Add an RFC822 header line to the header store.
     * If the line starts with a space or tab (a continuation line),
     * add it to the last header line in the list.  Otherwise,
     * append the new header line to the list.  <p>
     *
     * Note that RFC822 headers can only contain US-ASCII characters
     *
     * @param	line	raw RFC822 header line
     */
    public void addHeaderLine(String line) {
        try {
            char c = line.charAt(0);
            if (c == ' ' || c == '\t') {
                int index = headers.size() - 1;
                MailMessageHeader h = (MailMessageHeader) headers.get(index);
                h.setLine(h.getLine() + "\r\n" + line);
                headers.set(index, h);
            } else {
                MailMessageHeader header = new MailMessageHeader(line);
                // Check Header
                if (isHeader(header.name))
                    headers.add(header);
            }
        } catch (StringIndexOutOfBoundsException e) {
            // line is empty, ignore it
            return;
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Return all the values for the specified header. The
     * values are String objects.  Returns <code>null</code>
     * if no headers with the specified name exist.
     *
     * @param	name 	header name
     * @return		array of header values, or null if none
     */
    public String[] getHeader(String name) {
        Iterator<MailMessageHeader> e = headers.iterator();
        List<String> v = new ArrayList<String>(); // accumulate return values

        while (e.hasNext()) {
            MailMessageHeader h = e.next();
            if (name.equalsIgnoreCase(h.getName()) && h.getLine() != null) {
                v.add(h.getValue());
            }
        }
        if (v.size() == 0) {
            return (null);
        }
        // convert List to an array for return
        String r[] = new String[v.size()];
        r = (String[]) v.toArray(r);
        return (r);
    }

    /**
     * Get all the headers for this header name, returned as a single
     * String, with headers separated by the delimiter. If the
     * delimiter is <code>null</code>, only the first header is 
     * returned.  Returns <code>null</code>
     * if no headers with the specified name exist.
     *
     * @param	name 		header name
     * @param   delimiter	delimiter
     * @return                  the value fields for all headers with
     *				this name, or null if none
     */
    public String getHeader(String name, String delimiter) {
        String s[] = getHeader(name);

        if (s == null) {
            return null;
        }

        if ((s.length == 1) || delimiter == null) {
            return s[0];
        }

        StringBuffer r = new StringBuffer(s[0]);
        for (int i = 1; i < s.length; i++) {
            r.append(delimiter);
            r.append(s[i]);
        }
        return r.toString();
    }

    /**
     * Change the first header line that matches name
     * to have value, adding a new header if no existing header
     * matches. Remove all matching headers but the first. <p>
     *
     * Note that RFC822 headers can only contain US-ASCII characters
     *
     * @param	name	header name
     * @param	value	header value
     */
    public void setHeader(String name, String value) {
        boolean found = false;

        for (int i = 0; i < headers.size(); i++) {
            MailMessageHeader h = (MailMessageHeader) headers.get(i);
            if (name.equalsIgnoreCase(h.getName())) {
                if (!found) {
                    int j;
                    if (h.getLine() != null && (j = h.getLine().indexOf(':')) >= 0) {
                        h.setLine(h.getLine().substring(0, j + 1) + " " + value);
                    } else {
                        h.setLine(name + ": " + value);
                    }
                    found = true;
                } else {
                    headers.remove(i);
                    i--; // have to look at i again
                }
            }
        }

        if (!found) {
            addHeader(name, value);
        }
    }

    /**
     * Add a header with the specified name and value to the header list. <p>
     *
     * The current implementation knows about the preferred order of most
     * well-known headers and will insert headers in that order.  In
     * addition, it knows that <code>Received</code> headers should be
     * inserted in reverse order (newest before oldest), and that they
     * should appear at the beginning of the headers, preceeded only by
     * a possible <code>Return-Path</code> header.  <p>
     *
     * Note that RFC822 headers can only contain US-ASCII characters.
     *
     * @param	name	header name
     * @param	value	header value
     */
    public void addHeader(String name, String value) {
        int pos = headers.size();
        boolean addReverse = name.equalsIgnoreCase("Received")
                             || name.equalsIgnoreCase("Return-Path");
        if (addReverse) {
            pos = 0;
        }
        for (int i = headers.size() - 1; i >= 0; i--) {
            MailMessageHeader h = (MailMessageHeader) headers.get(i);
            if (name.equalsIgnoreCase(h.getName())) {
                if (addReverse) {
                    pos = i;
                } else {
                    headers.add(i + 1, new MailMessageHeader(name, value));
                    return;
                }
            }
            // marker for default place to add new headers
            if (h.getName().equals(":")) {
                pos = i;
            }
        }
        headers.add(pos, new MailMessageHeader(name, value));
    }

    /**
     * Remove all header entries that match the given name
     * @param	name 	header name
     */
    public void removeHeader(String name) {
        for (int i = 0; i < headers.size(); i++) {
            MailMessageHeader h = (MailMessageHeader) headers.get(i);
            if (name.equalsIgnoreCase(h.getName())) {
                h.setLine(null);
                headers.remove(i);
            }
        }
    }

    /*
     * The enumeration object used to enumerate an
     * InternetHeaders object.  Can return
     * either a String or a Header object.
     */
    @SuppressWarnings("unchecked")
    static class HeaderMatchEnum implements Enumeration {

        private Iterator          e;          // enum object of headers List
        private String            names[];    // names to match, or not
        private boolean           match;      // return matching headers?
        private boolean           want_line;  // return header lines?
        private MailMessageHeader next_header; // the next header to be returned

        /*
         * Constructor.  Initialize the enumeration for the entire
         * List of headers, the set of headers, whether to return
         * matching or non-matching headers, and whether to return
         * header lines or Header objects.
         */
        HeaderMatchEnum(List v, String n[], boolean m, boolean l) {
            e = v.iterator();
            names = n;
            match = m;
            want_line = l;
            next_header = null;
        }

        /*
         * Any more elements in this enumeration?
         */
        public boolean hasMoreElements() {
            // if necessary, prefetch the next matching header,
            // and remember it.
            if (next_header == null) {
                next_header = nextMatch();
            }
            return next_header != null;
        }

        /*
         * Return the next element.
         */
        public Object nextElement() {
            if (next_header == null) {
                next_header = nextMatch();
            }

            if (next_header == null) {
                throw new NoSuchElementException("No more headers");
            }

            MailMessageHeader h = next_header;
            next_header = null;
            if (want_line) {
                return h.getLine();
            } else {
                return new Header(h.getName(), h.getValue());
            }
        }

        /*
         * Return the next Header object according to the match
         * criteria, or null if none left.
         */
        private MailMessageHeader nextMatch() {
            next: while (e.hasNext()) {
                MailMessageHeader h = (MailMessageHeader) e.next();

                // skip "place holder" headers
                if (h.getLine() == null) {
                    continue;
                }

                // if no names to match against, return appropriately
                if (names == null) {
                    return match ? null : h;
                }

                // check whether this header matches any of the names
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equalsIgnoreCase(h.getName())) {
                        if (match) {
                            return h;
                        } else // found a match, but we're
                        // looking for non-matches.
                        // try next header.
                        {
                            continue next;
                        }
                    }
                }
                // found no matches.  if that's what we wanted, return it.
                if (!match) {
                    return h;
                }
            }
            return null;
        }
    }

    /**
     * Return all the headers as an Enumeration of
     * {@link com.shizuku.mail.Message.Header} objects.
     *
     * @return	Header objects	
     */
    @SuppressWarnings("unchecked")
    public Enumeration getAllHeaders() {
        return (new HeaderMatchEnum(headers, null, false, false));
    }

    /**
     * Return all matching {@link com.shizuku.mail.Message.Header} objects.
     *
     * @return	matching Header objects	
     */
    @SuppressWarnings("unchecked")
    public Enumeration getMatchingHeaders(String[] names) {
        return (new HeaderMatchEnum(headers, names, true, false));
    }

    /**
     * Return all non-matching {@link com.shizuku.mail.Message.Header} objects.
     *
     * @return	non-matching Header objects	
     */
    @SuppressWarnings("unchecked")
    public Enumeration getNonMatchingHeaders(String[] names) {
        return (new HeaderMatchEnum(headers, names, false, false));
    }

    /**
     * Return all the header lines as an Enumeration of Strings.
     */
    public Enumeration<String> getAllHeaderLines() {
        return (getNonMatchingHeaderLines(null));
    }

    /**
     * Return all matching header lines as an Enumeration of Strings.
     */
    @SuppressWarnings("unchecked")
    public Enumeration getMatchingHeaderLines(String[] names) {
        return (new HeaderMatchEnum(headers, names, true, true));
    }

    /**
     * Return all non-matching header lines
     */
    @SuppressWarnings("unchecked")
    public Enumeration<String> getNonMatchingHeaderLines(String[] names) {
        return (new HeaderMatchEnum(headers, names, false, true));
    }

    /**
     * Check current message line is a mail header or not
     * @param name header name
     * @return is header return true, else return false.
     */
    public boolean isHeader(String name) {
        String headerArray[] = getDefaultMailMessageHeaderNames();
        for (int i = 0; i < headerArray.length; i++) {
            if (headerArray[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return default headers
     */
    public static String[] getDefaultMailMessageHeaderNames() {
        String headerNames[] = new String[30];
        headerNames[0] = "Return-Path";
        headerNames[1] = "Received";
        headerNames[2] = "Resent-Date";
        headerNames[3] = "Resent-From";
        headerNames[4] = "Resent-Sender";
        headerNames[5] = "Resent-To";
        headerNames[6] = "Resent-Cc";
        headerNames[7] = "Resent-Bcc";
        headerNames[8] = "Resent-Message-Id";
        headerNames[9] = "Date";
        headerNames[10] = "From";
        headerNames[11] = "Sender";
        headerNames[12] = "Reply-To";
        headerNames[13] = "To";
        headerNames[14] = "Cc";
        headerNames[15] = "Bcc";
        headerNames[16] = "Message-Id";
        headerNames[17] = "In-Reply-To";
        headerNames[18] = "References";
        headerNames[19] = "Subject";
        headerNames[20] = "Comments";
        headerNames[21] = "Keywords";
        headerNames[22] = "Errors-To";
        headerNames[23] = "MIME-Version";
        headerNames[24] = "Content-Type";
        headerNames[25] = "Content-Transfer-Encoding";
        headerNames[26] = "Content-MD5";
        headerNames[27] = ":";
        headerNames[28] = "Content-Length";
        headerNames[29] = "Status";

        return headerNames;
    }
}
