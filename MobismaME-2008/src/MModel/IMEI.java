package MModel;

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

import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import MDataStore.DataBase_RMS;
import java.io.IOException;
import javax.microedition.rms.RecordStoreException;

public class IMEI {

    private String imeiWebb, imei_prop;

    public IMEI() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();
        // Hämtar det IMEI-nummer som är satt i webb-servern.
        this.imeiWebb = rms.getIMEI();
        // Hämtar det Property som är satt från webb-servern.
        this.imei_prop = rms.getSystemIMEIProperty();
        rms = null;
    }

    /* ===== Övriga kontroll-metoder som bör ligga i huvudklassen ========= */

   public void checkIMEI() throws IOException, RecordStoreNotOpenException,
           InvalidRecordIDException, RecordStoreException {

       MModel.SortClass sort = new SortClass();
       MDataStore.DataBase_RMS rms = new DataBase_RMS();

       // Hämta imei med property från telefonen.
       String phoneEmei = System.getProperty(imei_prop);
       // Sortera ut tecken osv. bara siffror kvar.
       phoneEmei = sort.sortCharToDigits(phoneEmei);
       // Trimma strängarna.
       phoneEmei = phoneEmei.trim();
       imeiWebb = imeiWebb.trim();

       // Bestäm längden till '15' på IMEI...
       phoneEmei = phoneEmei.substring(0, 15);
       imeiWebb = imeiWebb.substring(0, 15);

       // skriver ut för att jämföra IMEI - strängarna
       System.out.println("WEBB >>>>>>>>>>>>>>>>> " + imeiWebb);
       System.out.println("PHONE >>>>>>>>>>>>>>>> " + phoneEmei);

       // Skriver en if-sats.
        if(!phoneEmei.equals(imeiWebb)){

           rms.setTWO("2");
           rms.setImeiFalse("1");
       }

       sort = null;
       rms = null;
   }

}
