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
        // H�mtar det IMEI-nummer som �r satt i webb-servern.
        this.imeiWebb = rms.getIMEI();
        // H�mtar det Property som �r satt fr�n webb-servern.
        this.imei_prop = rms.getSystemIMEIProperty();
        rms = null;
    }

    /* ===== �vriga kontroll-metoder som b�r ligga i huvudklassen ========= */

   public void checkIMEI() throws IOException, RecordStoreNotOpenException,
           InvalidRecordIDException, RecordStoreException {

       MModel.SortClass sort = new SortClass();
       MDataStore.DataBase_RMS rms = new DataBase_RMS();

       // H�mta imei med property fr�n telefonen.
       String phoneEmei = System.getProperty(imei_prop);
       // Sortera ut tecken osv. bara siffror kvar.
       phoneEmei = sort.sortCharToDigits(phoneEmei);
       // Trimma str�ngarna.
       phoneEmei = phoneEmei.trim();
       imeiWebb = imeiWebb.trim();

       // Best�m l�ngden till '15' p� IMEI...
       phoneEmei = phoneEmei.substring(0, 15);
       imeiWebb = imeiWebb.substring(0, 15);

       // skriver ut f�r att j�mf�ra IMEI - str�ngarna
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
