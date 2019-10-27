#
# Jpxx mail server(JMS) Version 1.00(BATA). 
#
# AUTHOR : John Lee (alias: Jun Li)
# EMAIL  : lijun@jpxx.org
# 

This is a lightweight (E)SMTP/POP3 mail server. 

<1> News in v1.0.0

1. After v0.03, JMS can be run with servlet. It is used when a separate servlet engine (or application server) such as Tomcat or Resin provides access to JMS.
2. Removes the useless GUI. It is used to run as a plugin.
3. Removes the support for MYSQL. Of course, you can implement org.jpxx.mail.user.MailUserHandler or extend org.jpxx.mail.user.AbstractMailUserHandler to support MYSQL. 
4. Reconstructs the architecture of JMS.

<2> Example

The is a example in test folder.

<3> Run JMS

MailServer.getSingletonInstance().startServer();

