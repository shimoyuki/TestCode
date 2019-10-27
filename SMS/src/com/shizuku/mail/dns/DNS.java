package com.shizuku.mail.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 * <pre>
 * Resolve an mx record from a dns server.  
 * The dnsjava library is used as the resolver this class is a simple wrapper
 * </pre>
 * 
 *  
 * 
 *
 * 
 * 
 */
public class DNS {

    /**
     * Creates an instance of Logger and initializes it. It is to write log for
     */
    private static Logger log = Logger.getLogger(DNS.class);

    /**
     * Given a domain will return all the mailservers used to send mail to the
     * given domain. Need to order the list by preference listed in the dns
     * server. This works by using the dnsjava library as the resolver.
     * 
     * @param domain
     *            The destnation server domain(or IP)
     * @return all the mailservers used to send mail to the given domain.
     */
    public static synchronized List<String> getMailServer(String domain) {

        List<String> servers = new ArrayList<String>();
        Record foundServers[];

        try {
            foundServers = new Lookup(domain, Type.MX).run();

            /**
             * Put the servers into the server list
             */
            if (foundServers != null) {
                for (int i = 0; i < foundServers.length; i++) {
                    MXRecord m = (MXRecord) foundServers[i];
                    /**
                     * Need to add in order of preference
                     */
                    servers.add(m.getTarget().toString());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return servers;
    }

    /**
     * Determines the IP address of a host, given the host's name.
     * 
     * <p>
     * The host name can either be a machine name, such as "
     * <code>java.sun.com</code>", or a textual representation of its IP
     * address. If a literal IP address is supplied, only the validity of the
     * address format is checked.
     * 
     * <p>
     * For <code>host</code> specified in literal IPv6 address, either the form
     * defined in RFC 2732 or the literal IPv6 address format defined in RFC
     * 2373 is accepted. IPv6 scoped addresses are also supported.
     * 
     * <p>
     * If the host is <tt>null</tt> then an <tt>InetAddress</tt> representing an
     * address of the loopback interface is returned. See <a
     * href="http://www.ietf.org/rfc/rfc3330.txt">RFC&nbsp;3330</a>
     * section&nbsp;2 and <a href="http://www.ietf.org/rfc/rfc2373.txt">
     * RFC&nbsp;2373</a> section&nbsp;2.5.3.
     * </p>
     * 
     * @param host
     *            the specified host, or <code>null</code>.
     * @return an IP address for the given host name.
     * @exception UnknownHostException
     *                if no IP address for the <code>host</code> could be found,
     *                or if a scope_id was specified for a global IPv6 address.
     * @exception SecurityException
     *                if a security manager exists and its checkConnect method
     *                doesn't allow the operation
     */
    public static InetAddress getByName(String host) throws UnknownHostException {
        if (host == null || host.trim().equals("")) {
            return null;
        }

        if (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }
        return InetAddress.getByName(host);
    }

    /**
     * 
     * @param host
     * @return
     * @throws org.xbill.DNS.TextParseException
     */
    public static String[] getHostIP(String host) throws TextParseException {
        Record record[] = new Lookup(host, Type.A).run();

        if (record == null) {
            return null;
        }
        String ip[] = new String[record.length];
        for (int i = 0; i < record.length; i++) {
            ip[i] = record[i].rdataToString();
        }
        return ip;
    }

    /**
     * 
     * @param host
     * @return
     * @throws org.xbill.DNS.TextParseException
     */
    public static String[] getMXHostIP(String host) throws TextParseException {
        List<String> result = new ArrayList<String>();
        List<String> list = getMailServer(host);
        for (int i = 0; i < list.size(); i++) {
            String hostIP[] = getHostIP(list.get(i).toString());
            if (hostIP != null) {
                for (int j = 0; j < hostIP.length; j++) {
                    result.add(hostIP[j]);
                }
            }
        }
        if (result.size() == 0) {
            return null;
        } else {
            String ip[] = new String[result.size()];
            result.toArray(ip);
            return ip;
        }
    }
}
