package Mxhv;

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

public class TV {

    public Main_Controll main;
    public List absentList;
    public Ticker absentStatusTicker;
    public PBXSETTINGS pbxobj;
    public MModel.LANG langobj;


    public String dmd;

    public TV() {// konstruktor


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

        absentList = new List("Totalview", Choice.IMPLICIT); // skapar en lista

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
            Image image2a = Image.createImage("/prg_icon/upptagen24.png");
            Image image3a = Image.createImage("/prg_icon/lunch24.png");
            Image image4a = Image.createImage("/prg_icon/private.png");
            Image image5a = Image.createImage("/prg_icon/sjuk24.png");
            Image image6a = Image.createImage("/prg_icon/semester24.png");
            Image image8a = Image.createImage("/prg_icon/user.png");
            Image image7a = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image9a = Image.createImage("/prg_icon/samtalslista24.png");

            // Inne
            absentList.append(langobj.inside_DEF, image1a);
            // Upptagen
            absentList.append(langobj.callForwardDefaultBusy_DEF, image2a);
            // Lunch
            absentList.append(langobj.tv_lunch_DEF, image3a);
            // Privat
            absentList.append(langobj.private_DEF, image4a);
            // Sjuk
            absentList.append(langobj.absentIllness_DEF, image5a);
            // Semester
            absentList.append(langobj.absentVacation_DEF, image6a);
            // Vidarekoppla
            absentList.append(langobj.callForwardDefault_DEF, image7a);
            // Status
            absentList.append(langobj.statusToday_DEF, image8a);
            // Status
            absentList.append(langobj.statusTodayTomorrow_DEF, image8a);
            // Status
            absentList.append(langobj.statusDate_DEF, image8a);
            // Status
            absentList.append(langobj.statusContact_DEF, image8a);
            // Eget attribut
          //  absentList.append(langobj.genDefaultEdit_DEF, null);
            // Eget attribut
         //   absentList.append(langobj.genDefaultEdit_DEF, null);
/*
            String absent_1 = rms.getEditAbsentName_1();
            String absent_2 = rms.getEditAbsentName_2();

            if (absent_1.equals("0")) {

                absentList.set(11, main.editAbsentName_1, image9a);

            } else if (!absent_1.equals("0")) {

                absentList.set(11, absent_1, image9a);

            }
            if (absent_2.equals("0")) {

                absentList.set(12, main.editAbsentName_2, image9a);

            } else if (!absent_2.equals("0")) {

                absentList.set(12, absent_2, image9a);

            }*/

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        } //catch (RecordStoreNotOpenException ex) {
            /** @todo Handle this exception */
        //} catch (InvalidRecordIDException ex) {
            /** @todo Handle this exception */
        //} catch (RecordStoreException ex) {
            /** @todo Handle this exception */
        //}

        rms = null;
        return absentList;
    }

}
