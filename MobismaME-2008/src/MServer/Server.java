package MServer;

import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.*;
import MModel.CONF;
import javax.microedition.io.HttpConnection;

/**
 * <p>Title: Mobile Extesion</p>
 *
 * <p>Description: All PBX Include</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: mobisma ab</p>
 *
 * @author Peter Albertsson
 * @version 2.0
 */
public class Server {
   // private static String url = "socket://127.0.0.1:8100"; // Produrl
    private static String url; // Produrl
    private static String debugx;


    // private static String url =  "http://www.mobisma.com:80/socketapi/mobismaSRV.php"; //Testurl
    private static String response = " ";
    private static String request;
    //private static String url = "socket://127.0.0.1:8100";

    // Portnummer för TCP/IP connection
    // private static String url = "socket://127.0.0.1:8100";

    public static final int CONFDATA = 1;
    public static final int LOGDATA = 2;
    public static final int IMEIDATA = 3;
    public static final int LOGSIZE = 4;

    // private static String inturl = "socket://127.0.0.1:8100";
    // private static String ext_url =
    //         "http://www.mobisma.com:80/socketapi/mobilesock.php";
    private static String confdata;
    private static String logdata;
    private static int logfilesize;
    private static int icount;
    private static String logrequest;
    private static String imei;
        private static String dbgtext;

    public static boolean sendlogreq = false;



    public Server() {

        MModel.CONF conf = new CONF();
       this.debugx = conf.DEBUG;
       if(debugx.equals("1")){
           this.url = conf.TESTURL;
       } else {
        this.url = conf.INTURL;
       }
        conf = null;

    }

    public void serverCMD(String message) {
        System.out.println("Sätter serverreq till true (servern jobbar)");
                dbgtext = "";
                MControll.Main_Controll.serverreq = true;
        request = message;
        dbgtext = message;
                new Thread() {
            public void run() {
            System.out.println("Anropar serverCMDex");
             dbgtext = "serverCMDEx";
                         serverCMDEx(request);
            }
        }.start();
    }

    public void serverCMDEx(String request){
        try {
            // Ta bort vid inte test
           System.out.println("ServerCMDEx request: " + request);
            String xurl = " ";
            if(this.debugx.equals("1")){
               xurl = url + "?cmd=" + request + ""; // Test
            } else {
               xurl = url; // Prod
            }
            System.out.println("ServerCMDex url: : " + xurl);
            dbgtext = "Connector.open xurl: " + xurl;
                        StreamConnection conn = (StreamConnection) Connector.open(xurl);
                                dbgtext = "conn.openOutputStream xurl: " + xurl;
                                OutputStream out = conn.openOutputStream();
                 byte[] buf = request.getBytes();
                                 dbgtext = "out.write xurl: " + xurl;
                 out.write(buf, 0, buf.length);
                                 dbgtext = "out.flush xurl: " + xurl;
                 out.flush();
                 dbgtext = "out.close xurl: " + xurl;
                                 out.close();

                 byte[] data = new byte[256];
                                 dbgtext = "conn.openInputStream xurl: " + xurl;
                 InputStream in = conn.openInputStream();
                                 dbgtext = "in.read xurl: " + xurl;
                 int actualLength = in.read(data);
                                 dbgtext = "response new String";
                 String response = new String(data, 0, actualLength);
                 System.out.println("ServerCMDex response : " + response);
                                 dbgtext = "response: " + response;
                 MControll.Main_Controll.response = response;
                                 dbgtext = "in.close";
                 in.close();
                                 dbgtext = "conn.close";
                 conn.close();
             } catch (IOException ioe) {
                 ioe.printStackTrace();
                 System.out.println(ioe.getMessage());
                                 MControll.Main_Controll.response = dbgtext + " " + ioe.getMessage();
                 MControll.Main_Controll.serverreq = false;

             }
             System.out.println("ServerCMDex serverreq sätts till, false (servern klar)");
             MControll.Main_Controll.serverreq = false;

    }


    public void serverCMDInternal(String request){
        try {
            // Ta bort vid inte test
           System.out.println("ServerCMDEx request: " + request);
            String xurl = " ";
            if(this.debugx.equals("1")){
               xurl = url + "?cmd=" + request + ""; // Test
            } else {
               xurl = url; // Prod
            }
            System.out.println("ServerCMDex url: : " + xurl);
            StreamConnection conn = (StreamConnection) Connector.open(xurl);
                 OutputStream out = conn.openOutputStream();
                 byte[] buf = request.getBytes();
                 out.write(buf, 0, buf.length);
                 out.flush();
                 out.close();

                 byte[] data = new byte[256];
                 InputStream in = conn.openInputStream();
                 int actualLength = in.read(data);
                 String response = new String(data, 0, actualLength);
                 System.out.println("ServerCMDex response : " + response);
                 this.response = response;
                 in.close();
                 conn.close();
             } catch (IOException ioe) {
                 ioe.printStackTrace();
                 System.out.println(ioe.getMessage());
                 MControll.Main_Controll.serverreq = false;

             }
             System.out.println("ServerCMDex serverreq sätts till, false (servern klar)");
             MControll.Main_Controll.serverreq = false;

    }

    /* ==================== SERVER Debug ======================================== */

      private String URLEncode(String s) {

          StringBuffer sbuf = new StringBuffer();
          int ch;
          for (int i = 0; i < s.length(); i++) {
              ch = s.charAt(i);
              switch (ch) {
              case ' ': {
                  sbuf.append("+");
                  break;
              }
              case '!': {
                  sbuf.append("%21");
                  break;
              }
              case '*': {
                  sbuf.append("%2A");
                  break;
              }
              case '\'': {
                  sbuf.append("%27");
                  break;
              }
              case '(': {
                  sbuf.append("%28");
                  break;
              }
              case ')': {
                  sbuf.append("%29");
                  break;
              }
              case ';': {
                  sbuf.append("%3B");
                  break;
              }
              case ':': {
                  sbuf.append("%3A");
                  break;
              }
              case '@': {
                  sbuf.append("%40");
                  break;
              }
              case '&': {
                  sbuf.append("%26");
                  break;
              }
              case '=': {
                  sbuf.append("%3D");
                  break;
              }
              case '+': {
                  sbuf.append("%2B");
                  break;
              }
              case '$': {
                  sbuf.append("%24");
                  break;
              }
              case ',': {
                  sbuf.append("%2C");
                  break;
              }
              case '/': {
                  sbuf.append("%2F");
                  break;
              }
              case '?': {
                  sbuf.append("%3F");
                  break;
              }
              case '%': {
                  sbuf.append("%25");
                  break;
              }
              case '#': {
                  sbuf.append("%23");
                  break;
              }
              case '[': {
                  sbuf.append("%5B");
                  break;
              }
              case ']': {
                  sbuf.append("%5D");
                  break;
              }
              default:
                  sbuf.append((char) ch);
              }
          }
          return sbuf.toString();
      }

      public void sendLogdata() {
          System.out.println("Send logdata: ");
          logdata = "";
          System.out.println("Hämtar IMEI: ");
          sendMessageInt("k,IMEI,", IMEIDATA);
          System.out.println("Hämtar Confdata: ");
          sendMessageInt("i,", CONFDATA);
          System.out.println("Hämtar Logsize: ");
          sendMessageInt("u", LOGSIZE);
          System.out.println("Hämtar Logdata: ");
          int bufsize = 256;
          for (icount = 0; icount < logfilesize; icount = icount + bufsize) {
              logrequest = "j," + icount + ",";
              System.out.println("logrequest: " + logrequest);
              sendMessageInt(logrequest, LOGDATA);
          }
          System.out.println("Call SendlogdataExt");
          sendLogdataExt();
          sendlogreq = false;
      }

      public void getLogdata() {
           MControll.Main_Controll.serverreq = true;
          System.out.println("Send logdata: ");
          logdata = "";
          System.out.println("Hämtar IMEI: ");
          sendMessageInt("k,IMEI,", IMEIDATA);
          System.out.println("Hämtar Confdata: ");
          sendMessageInt("i,", CONFDATA);
          System.out.println("Hämtar Logsize: ");
          sendMessageInt("u", LOGSIZE);
          System.out.println("Hämtar Logdata: ");
          int bufsize = 256;
          for (icount = 0; icount < logfilesize; icount = icount + bufsize) {
              logrequest = "j," + icount + ",";
              System.out.println("logrequest: " + logrequest);
              sendMessageInt(logrequest, LOGDATA);
          }
          //System.out.println("Call SendlogdataExt");
          MControll.Main_Controll.response = "** CONFDATA *** " + confdata + "\n*** LOGDATA***" + logdata;
                  //sendLogdataExt();
          sendlogreq = false;
           MControll.Main_Controll.serverreq = false;
      }

      //  public void sendRequestInt(String message, int what) {
      // this.request = message;
      // this.requestwhat = what;
      // new Thread() {
      //     public void run() {
      //sendMessageInt(request, requestwhat);
      //     }
      // }.start();
      // }



      public void sendMessageInt(String message, int what) {
          //    try {
          System.out.println("SendMessageInt: message: " + message + " what: " +
                             what);

          serverCMDInternal(message);
          switch (what) {
          case CONFDATA:
              confdata = this.response;
              System.out.println("confdata: " + confdata);
              break;
          case LOGDATA:
              logdata = logdata + this.response;
              System.out.println("logdata: " + logdata);
              break;
          case IMEIDATA:
              imei = this.response;
              System.out.println("imei: " + imei);
              break;
          case LOGSIZE:
              logfilesize = Integer.parseInt(this.response);
              System.out.println("logfilesize: " + logfilesize);
              break;
          }

          //  } catch (IOException ioe) {
          //      ioe.printStackTrace();
          //  }
      }

      public void sendLogdataExt() {
          try {
              System.out.println("SendlogdataExt");
              MModel.CONF conf = new CONF();
              String ext_url = conf.EXTURL;
              conf = null;
              System.out.println("ext_url: " + ext_url);

              HttpConnection conn = (HttpConnection)
                                    Connector.open(ext_url);
              String submitstring = "imei=" + URLEncode(imei) + "&confdata=" +
                                    URLEncode(confdata) + "&logdata=" +
                                    URLEncode(logdata) + "&logfilesize=" +
                                    logfilesize + "&icount=" + icount +
                                    "&Submit=Submit";
              System.out.println("submitstring: " + submitstring);
              byte[] data = submitstring.getBytes();

              conn.setRequestMethod(HttpConnection.POST);
              conn.setRequestProperty("User-Agent",
                                      "Profile/MIDP-1.0 Configuration/CLDC-1.0");
              conn.setRequestProperty("Content-Language", "en-US");
              conn.setRequestProperty("Content-Type",
                                      "application/x-www-form-urlencoded");
              OutputStream os = conn.openOutputStream();
              os.write(data);
              os.close();

              byte[] data2 = new byte[2048];
              InputStream in = conn.openInputStream();
              int actualLength = in.read(data2);
              String response = new String(data2, 0, actualLength);
              MControll.Main_Controll.response = response;

              // setAlertMEXONOFF(response);
              in.close();
              conn.close();
              MControll.Main_Controll.serverreq = false;

          } catch (IOException ioe) {
              MControll.Main_Controll.serverreq = false;
              ioe.printStackTrace();
          }
    }

}
