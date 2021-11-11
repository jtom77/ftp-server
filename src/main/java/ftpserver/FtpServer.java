package ftpserver;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FtpServer {


  org.apache.ftpserver.FtpServer createFtpServer() throws FtpException {
    FtpServerFactory serverFactory = new FtpServerFactory();
    ListenerFactory factory = new ListenerFactory();
    factory.setPort(2221);

    serverFactory.addListener("default", factory.createListener());

    final PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

    final UserManager userManager = userManagerFactory.createUserManager();
    BaseUser user = new BaseUser();
    user.setName("test");
    user.setPassword("test");
    user.setHomeDirectory("C:\\Users\\te\\ftp");
    List<Authority> authorities = new ArrayList<Authority>();
    authorities.add(new WritePermission());
    user.setAuthorities(authorities);
    userManager.save(user);
    serverFactory.setUserManager(userManager);

    org.apache.ftpserver.FtpServer server = serverFactory.createServer();
    return server;
  }

  org.apache.ftpserver.FtpServer createFtpsServer() throws FtpException {
    FtpServerFactory serverFactory = new FtpServerFactory();
    ListenerFactory factory = new ListenerFactory();

    factory.setPort(2221);

    SslConfigurationFactory ssl = new SslConfigurationFactory();

    ssl.setKeystoreFile(new File("C:\\Users\\te\\ftp\\serverkeystore.jks"));
    ssl.setKeystorePassword("javajava");

    factory.setSslConfiguration(ssl.createSslConfiguration());
    factory.setImplicitSsl(true);

    serverFactory.addListener("default", factory.createListener());
    final PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
    final UserManager userManager = userManagerFactory.createUserManager();
    BaseUser user = new BaseUser();
    user.setName("test");
    user.setPassword("test");
    user.setHomeDirectory("C:\\Users\\te\\ftp");
    List<Authority> authorities = new ArrayList<Authority>();
    authorities.add(new WritePermission());
    user.setAuthorities(authorities);
    userManager.save(user);
    serverFactory.setUserManager(userManager);

//    PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//    userManagerFactory.setFile(new File("myusers.properties"));
//    serverFactory.setUserManager(userManagerFactory.createUserManager());

    return serverFactory.createServer();
  }





  public static void main(String[] args) throws FtpException {

    FtpServer ftp = new FtpServer();
    ftp.createFtpServer().start();
//    Now, let’s make it possible for a client to use FTPS (FTP over SSL) for the default listener.

//    FtpServerFactory serverFactory = new FtpServerFactory();
//    ListenerFactory factory = new ListenerFactory();
//
//// set the port of the listener
//    factory.setPort(2221);
//
//// define SSL configuration
//    SslConfigurationFactory ssl = new SslConfigurationFactory();
//    ssl.setKeystoreFile(new File("src/test/resources/ftpserver.jks"));
//    ssl.setKeystorePassword("password");
//
//// set the SSL configuration for the listener
//    factory.setSslConfiguration(ssl.createSslConfiguration());
//    factory.setImplicitSsl(true);
//
//// replace the default listener
//    serverFactory.addListener("default", factory.createListener());
//    PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//    userManagerFactory.setFile(new File("myusers.properties"));
//    serverFactory.setUserManager(userManagerFactory.createUserManager());
//
//// start the server
//    FtpServer server = serverFactory.createServer();
//    server.start();
  }

}
