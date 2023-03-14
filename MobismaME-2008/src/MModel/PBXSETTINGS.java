package MModel;

/**
 * <p>Title: Mobile Extension</p>
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

import MModel.Language;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import MDataStore.DataBase_RMS;
import java.io.IOException;
import javax.microedition.rms.RecordStoreException;

public  class PBXSETTINGS {

    /* PBX Settings */
    public String
    lineAccess_PBX,
    switchBoardNumber_PBX,
    countryCode_PBX,
    extensionNumber_PBX,
    HGP_PBX,
    pinCodeNumber_PBX,
    precode_PBX,
    voiceMailSwitchboard_PBX,
    voiceMailOperator_PBX,
    mexONOFF_PBX,
    checkStatus_PBX,
    dbg_PBX,
    demo_PBX,
    companyName_PBX,
    userName_PBX,
    countryName_PBX,
    iconNumber_PBX,
    lang_PBX,
    pbx_ID,
    prg_Name,
    CheckTwo,
    pbx_type,
    default_lang,
    eng_lang,
    absentStatus,
    device_brands,
    deveice_model,
    pbx_name,
    gsm_number,
    accessRB,
    accessKN,
    roomNR,
    emptyPrecense = "",
    setP_PBX,
    imei;




    public PBXSETTINGS() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {


     MDataStore.DataBase_RMS rms = new  DataBase_RMS();


             /* PBX Settings, attribut från RMS DB */
        this.lineAccess_PBX = rms.lineAccess_PBX;
        this.HGP_PBX = rms.HGP_PBX;
        this.precode_PBX = rms.precode_PBX;
        this.voiceMailSwitchboard_PBX = rms.voiceMailSwitchboard_PBX;
        this.switchBoardNumber_PBX = rms.switchBoardNumber_PBX;
        this.countryCode_PBX = rms.countryCode_PBX;
        this.extensionNumber_PBX = rms.extensionNumber_PBX;
        this.pinCodeNumber_PBX = rms.pinCodeNumber_PBX;
        this.voiceMailOperator_PBX = rms.voiceMailOperator_PBX;
        this.mexONOFF_PBX = rms.mexONOFF_PBX;
        this.checkStatus_PBX = rms.checkStatus_PBX;
        this.dbg_PBX = rms.dbg_PBX;
        this.demo_PBX = rms.demo_PBX;
        this.companyName_PBX = rms.companyName_PBX;
        this.userName_PBX = rms.userName_PBX;
        this.countryName_PBX = rms.countryName_PBX;
        this.iconNumber_PBX = rms.iconNumber_PBX;
        this.lang_PBX = rms.lang_PBX;
        this.CheckTwo = rms.CheckTwo;
        this.pbx_ID = rms.getPbxID();
        this.prg_Name = rms.getPrgName();
        this.pbx_type = rms.getPBXType();
        this.default_lang = rms.getDefaultLanguage();
        this.eng_lang = rms.getENGlang();
        this.absentStatus = rms.getAbsentStatus();
        this.device_brands = rms.getPhoneBrands();
        this.deveice_model = rms.getPhoneModel();
        this.pbx_name = rms.getPbxName();
        this.gsm_number = rms.getGSMNumber();
        this.accessRB = rms.getAccessRB();
        this.accessKN = rms.getAccessKN();
        this.roomNR = rms.getRoomNR();
        this.setP_PBX = rms.getDTMFsend();
        this.imei = rms.getIMEI();


     rms = null;

    }


}
