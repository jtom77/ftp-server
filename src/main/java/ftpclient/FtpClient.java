package ftpclient;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FtpClient {

  public static void main(String[] args) {
    FTPClient client = new FTPClient();
    FTPClientConfig config = new FTPClientConfig();

    client.configure(config );
    boolean error = false;
    try {
      int reply;
      String server = "localhost";
      client.connect(server, 2221);
      System.out.println("Connected to " + server + ".");
      System.out.print(client.getReplyString());

      reply = client.getReplyCode();

      client.login("test", "test");

      if(!FTPReply.isPositiveCompletion(reply)) {
        client.disconnect();
        System.err.println("FTP server refused connection.");
        System.exit(1);
      }

      File firstLocalFile = new File("C:\\Users\\te\\big.png");
      String firstRemoteFile = "big.png";
      InputStream inputStream = new FileInputStream(firstLocalFile);

      System.out.println("Start uploading first file");
      boolean done = client.storeFile(firstRemoteFile, inputStream);
      inputStream.close();
      if (done) {
        System.out.println("The first file is uploaded successfully.");
      }

      System.out.println(client.getReplyString());

      client.logout();
    } catch(IOException e) {
      error = true;
      e.printStackTrace();
    } finally {
      if(client.isConnected()) {
        try {
          client.disconnect();
        } catch(IOException ioe) {
          // do nothing
        }
      }
      System.exit(error ? 1 : 0);
    }
  }

}
