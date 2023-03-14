package Mxhv;


import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import java.io.IOException;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.lcdui.Ticker;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Image;
import MDataStore.DataBase_RMS;
import javax.microedition.lcdui.List;
import MControll.Main_Controll;
import MModel.PBXSETTINGS;
import MModel.LANG;

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

public class M8020 {

    public Main_Controll main;
    public List absentList;
    public Ticker absentStatusTicker;
    public PBXSETTINGS pbxobj;
    public MModel.LANG langobj;


    public String dmd;

    public M8020() {// konstruktor


    }

    public List getAbsentList() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        pbxobj = new MModel.PBXSETTINGS();
        langobj = new MModel.LANG();
        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        try {

            pbxobj.absentStatus = rms.getAbsentStatus();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        pbxobj = null;

        absentList = new List("8020", Choice.IMPLICIT); // skapar en lista


        try {
            if (rms.getAbsentStatus().equals("0")) {
                absentList.setTicker(null);
                absentStatusTicker = new Ticker(" ");
                absentList.setTicker(absentStatusTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                absentList.setTicker(null);
                absentStatusTicker = new Ticker(rms.getAbsentStatus());
                absentList.setTicker(absentStatusTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        try {

            Image image1a = Image.createImage("/prg_icon/in.png");
            Image image2a = Image.createImage("/prg_icon/home.png");
            Image image3a = Image.createImage("/prg_icon/ute24.png");
            Image image4a = Image.createImage("/prg_icon/upptagen24.png");
            Image image5a = Image.createImage("/prg_icon/lunch24.png");
            Image image6a = Image.createImage("/prg_icon/private.png");
            Image image7a = Image.createImage("/prg_icon/sjuk24.png");
            Image image8a = Image.createImage("/prg_icon/semester24.png");
            Image image14a = Image.createImage("/prg_icon/samtalslista24.png");

            // Inne
            absentList.append("Inne", image1a);
            // Hemma
            absentList.append("Hemma", image2a);
            // Ute
            absentList.append("Ute", image3a);
            // Upptagen
            absentList.append("Upptagen", image4a);
            // Lunch
            absentList.append("Lunch", image5a);
            // Privat
            absentList.append("Privat", image6a);
            // Sjuk
            absentList.append("Sjuk", image7a);
            // Semester
            absentList.append("Semester", image8a);
            // Eget attribut
            absentList.append("", null);
            // Eget attribut
            absentList.append("", null);

            String absent_1 = rms.getEditAbsentName_1();
            String absent_2 = rms.getEditAbsentName_2();

            if (absent_1.equals("0")) {

                absentList.set(8, main.editAbsentName_1, image14a);

            } else if (!absent_1.equals("0")) {

                absentList.set(8, absent_1, image14a);

            }
            if (absent_2.equals("0")) {

                absentList.set(9, main.editAbsentName_2, image14a);

            } else if (!absent_2.equals("0")) {

                absentList.set(9, absent_2, image14a);

            }

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        } catch (RecordStoreNotOpenException ex) {
            /** @todo Handle this exception */
        } catch (InvalidRecordIDException ex) {
            /** @todo Handle this exception */
        } catch (RecordStoreException ex) {
            /** @todo Handle this exception */
        }

        rms = null;
        return absentList;
    }

}
