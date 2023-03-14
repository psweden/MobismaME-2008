package MControll;

/**
 * <p>Title: Mobile Extension</p>
 *
 * <p>Description:(JAVA) Panasonic PBX Include</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: mobisma ab</p>
 *
 * @author Peter Albertsson
 * @version 2.0
 */
/*Import av java-paket*/
/* DataStore */
import java.io.*;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.*;

/* Model */
import MModel.Date_Time;
import MView.*;
import MDataStore.DataBase_RMS;
import MModel.CONF;
import MModel.CONF_settings;
import MModel.SortClass;
import MModel.InternNumber;
import MModel.IMEI;
import MModel.PBXSETTINGS;
import MModel.LANG;
import Mxhv.*;
import Mwmaserver.*;

/* Klassen Main_Controll startar här. */
public class Main_Controll extends MIDlet implements ItemStateListener,
        CommandListener,

        Runnable {
    /*
     - Konfigueringsfilen CONF och Language --- initierar programmets
         - Språk, Ikoner och annan information.
     */
    private MModel.CONF conf;
    private MModel.CONF_settings conf_S;
    private MModel.Language language;
    private MModel.Date_Time dateTime;
    private MDataStore.DataBase_RMS rms;
    private MView.AboutUs aboutUs;
    private MView.HelpInfo helpInfo;
    private MView.ServerNumber serverNumber;
    private ConnectServer wma_server;

    public ChoiceGroup calledButtons = new ChoiceGroup("", Choice.EXCLUSIVE);
    public ChoiceGroup internCallButtons = new ChoiceGroup("", Choice.EXCLUSIVE);
    private ChoiceGroup editInternButtons = new ChoiceGroup("",
            Choice.EXCLUSIVE);
    private int defaultIndex, editInternButtonIndex, internDefaultIndex;

    public int IDInternNumber;
    private boolean isInitialized;
    private boolean splashIsShown;
    public static String response;
    public static boolean serverreq = false;

    public Mxhv.Check_Xhv xhv;
    public PBXSETTINGS pbxobj;
    public LANG langobj;
    public ChoiceGroup optionsGroup = new ChoiceGroup("", ChoiceGroup.EXCLUSIVE);

    // Public strängar.
    public String
    checkAlert,
    ViewDateString,
    editNEWAbsent,
    editHHTTMMTT = "",
    mexOnOff,
    absentChooseOneTwo,
    allCallForwardRename = "",
    externCallForwardRename = "",
    internCallForwardRename = "",
    absentSystemName = "",
    sendSMSStatus,
    setSMSStatusString,
    setAbsentStatusTime,
    tv_set_hhmm_mmdd,
    setTVCFPrefix,
    tvStatusPrefix;

    /* Ny hänvisning */
    public String

            Presence_This_String,
    Call_This_Number,
    editAbsentName_1,
    editAbsentName_2,
    editAbsentName_3,
    editAbsentDTMF_1,
    editAbsentDTMF_2,
    editAbsentDTMF_3,
    setAbsentNAMEString,
    edit_HHMM_TTMM_1,
    edit_HHMM_TTMM_2,
    edit_HHMM_TTMM_3;

    private Ticker absentStatusTicker, mainStatusTicker, callForwardTicker,
    allCallForwardTicker, externCallForwardTicker, internCallForwardTicker;

    private String
            /*Tid och Datum*/
            setYear_30DAY, setDay_30DAY, setMounth_30DAY, setMounthName_30DAY,
    setDay_TODAY, setMounth_TODAY, setYear_TODAY, setMounthNameToday;

    /* Listor/Menyer i prg */
    public List

            linePrefix_List, pbx_List_type,
    pbx_List, mainList, language_List, absentList, absentEditList,
    groupList, tv_CallForwardList,

    /* Callforward list */
    callForwardList, allCallForwardList,
    externCallForwardList, internCallForwardList;

    /* Övriga settings som Display, Alert, Commands och Form osv. */
    private Display display;

    public Alert alertEditSettings, alertRestarting,
    alertExit, alertON, alertOFF, alertMexAlreadyONOFF,
    alertDebugONOFF, alertSendOKNOK, alertSENDDebug, alertLogOutDebug,
    alertExpernceLicense, alertSMS;

    private Form
            // AutoAccess settings.
            AutoAccessSettingsForm,
    AutoAccessSettingsNOPrefixForm,

    // PIN-Code settings
    pinCodeSettingsForm,

    // Ring samtal
    callPhoneForm,

    // Intern samtal
    internCallPhoneForm, callInternEditForm,
    callRenameForm, callRenameEditForm,

    // Pre-edit code
    preEditForm,

    // Totalview hänvisning
    tvForm, tv_CallForwardForm, tv_StatusForm,
    tvEditForm,

    // Hänvisningsformer.

    atExtForm, // - Finns på anknytning
    backAtForm, // - Åter klockan
    outForm, // - Åter den


    // Editera ny hänvisning
    editAbsentForm, // - Lägg till ny hänvisning

    // Grupp former.
    loginGroupForm, logoffGroupForm,

    // PBX Röstbrevlåda form.
    voiceEditForm_PBX,

    // Ange landsnummer form.
    countryForm,

    // Vidarekoppling
    allCallForwardForm, externCallForwardForm, internCallForwardForm;

    private Command
            // AutoAccess kommandon.
            AutoAccessBackCommand, AutoAccessCancelCommand,
    AutoAccessSaveCommand,
    AutoAccessSaveNOPrefixCommand, AutoAccessBackNOPrefixCommand,
    AutoAccessCancelNOPrefixCommand,

    // PIN-Code kommandon
    pinCodeSaveCommand, pinCodeBackCommand, pinCodeCancelCommand,

    // Pre-edit Code kommandon
    preEditSaveCommand, preEditBackCommand, preEditCancelCommand,

    // Ring, uppringda samtal kommando
    callCommand, callBackCommand,

    // Intern samtal
    internCallCommand, internCallBackCommand,

    internCallEditRenameCommand, internCallEditBackCommand,
    internCallEditCancelCommand,

    internCallEditSaveCommand, internCallRenameBackCommand,

    internCallEditRenameBackCommand, internCallEditRenameCancelCommand,
    internCallEditRenameSaveCommand,

    // Vidarekoppla kommando
    allCallForwardSendCommand, allCallForwardBackCommand,
    externCallForwardSendCommand, externCallForwardBackCommand,
    internCallForwardSendCommand, internCallForwardBackCommand,

    backCallForwardCommand, backAllCallForwardCommand,
    backExternCallForwardCommand, backInternCallForwardCommand,

    // Röstbrevlåda PBX, kommandon
    voiceEditSaveCommand_PBX, voiceEditBackcommand_PBX,
    voiceEditCancelCommand_PBX,

    // Country kommandon
    countryBackCommand, countryCancelCommand, countrySaveCommand,

    // MainList kommandon
    mainListEditCommand, mainListaboutMobismaCommand, mainListExitCommand,

    // Totalview kommandon
    tvSendCommand, tvBackCommand, tvFormSendCallForwardCommand,
            tvFormBackCallForwardCommand,
    tvStatusBackCommand, tvStatusSendCommand,
    tvEditBackCommand, tvEditSaveCommand, tvEditCancelCommand,
    tv_BackCallForwardCommand,


    // Hänvisning kommandon
    BackCommandAbsentList, // - kommando till huvudmenyn för hänvisning.

    atExtBackCommand, atExtSendCommand, // - Finns på anknytning
    backAtBackCommand, backAtSendCommand, // - Tillbaka klockan
    outBackCommand, outSendCommand, // - Åter den

    // - Ta bort hänvisning se absentList plats 6.

    // Editera ny hänvisning (Form) kommandon
    editAbsentBackCommand, editAbsentSaveCommand,
    editAbsentCancelCommand,

    // Editera ny hänvisning (List) kommandon
    editAbsentListBackCommand,
    editAbsentListCancelCommand,

    // Grupp kommandon
    groupBackCommand, loginGroupSendCommand,
    loginGroupBackCommand, logoffGroupSendCommand,
    logoffGroupBackCommand,

    // Fristående kommandon.
    GraphicsBackCommand, goGraphicsBackCommand,
    GraphicsAboutCommand, GraphicsHelpCommand,

    // pbx_List kommando.
    pbx_ListCancelCommand,

    // pbx_List_type kommandon.
    pbx_List_typeBackCommand, pbx_List_typeCancelCommand,

    // language_List kommando
    languageListBackCommand,

    // Alert-Exit 'confirm' Ja eller Nej
    confirmExitYESCommand, confirmExitNOCommand,
    confirmOnYESCommand, confirmOnCancelCommand,
    confirmOffYESCommand, confirmOffCancelCommand,
    confirmSENDSMSYESCommand, confirmSENDSMSNOCommand,


    // Alert-Experince Licens
    licensYESCommand,

    // kommando för trådarna RUN.
    thCmd,

    // Kommando för vidarekoppling


    // linePrefix_List
    linePrefixBackCommand;

    public TextField
            // Ring, uppringda samtal textfield
            callTextField,
    internCallTextField,
    internCallEditNameTextField, internCallEditExtensionTextField,
    internCallEditRenameNameTextField, internCallEditRenameExtensionTextField,

    // Pre-edit Code textfält
    preEditTextField,

    // Totalview
    tvMessageTextField, tvTimeDateTextField,
    tvFormMessageTextField, tvFormNumberTextField,
    tvStatusTextField, tvStatusDateTextField,
    tvEditNameTextField, tvEditPrefixTextField,

    // HänvisningsTextField.
    atExtTextField, // - Finns på anknytning
    backAtTextField, // - Tillbaka klockan
    outTextField, // - Åter den

    newAbsent_1TextField, // - Ny hänvisning
    newAbsent_2TextField, // - Ny hänvisning
    newAbsent_3TextField, // - Ny hänvisning

    // Editera ny hänvisning
    editAbsentName_TextField, // - Lägg till namn för ny hänvisning

    // AutoAccess textfält tillhör AutoAccessSettingsForm
    AutoAccessLineAccessTextField, AutoAccessSwitchBoardTextField,
    AutoAccessGSMnumberTextField,
    AutoAccessNOPrefixSwitchBoardTextField, AutoAccessNOPrefixGSMnumberTextField,

    // PIN-Coide textfält tillhör pinCodeSettingsForm
    pinCodeLineAccessTextField, pinCodeSwitchBoardNumberTextField,
    pinCodeExtensionNumberTextField, pinCodePinCodeNumberTextField,
    pinCodeGSMnumberTextField,



    // PBX'ns textfält för röstbrevlåda tillhör voiceEditForm_PBX
    voiceMailPBXTextField_PBX,

    // Textfältet tillhör countryForm
    countryTextField,

    // Textfältet tillhör loginGroupForm
    loginGroupTextField,

    // Textfältet tillhör logoffGroupForm
    logoffGroupTextField,

    // Vidarekoppling
    allCallForwardTextField, externCallForwardTextField,
    internCallForwardTextField;

    /* Konstruktorn startar här. */

    public Main_Controll() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        //------------ kontrollerar om CONF.java är satt -----------------------
        MModel.CONF_settings conf_S = new CONF_settings();
        conf_S.setConfSettings();
        conf_S = null;

        langobj = new MModel.LANG();
        pbxobj = new MModel.PBXSETTINGS();

        // --- SplashScreen
        display = Display.getDisplay(this);

        /*RMS DB objekt hämtar in egenskaperna här*/
        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        // Ny hänvisning
        this.editAbsentName_1 = rms.getEditAbsentName_1();
        this.editAbsentName_2 = rms.getEditAbsentName_2();
        this.editAbsentName_3 = rms.getEditAbsentName_3();
        this.editAbsentDTMF_1 = rms.getEditAbsentDTMF_1();
        this.editAbsentDTMF_2 = rms.getEditAbsentDTMF_2();
        this.editAbsentDTMF_3 = rms.getEditAbsentDTMF_3();
        this.edit_HHMM_TTMM_1 = rms.getHHMMTTMM_1();
        this.edit_HHMM_TTMM_2 = rms.getHHMMTTMM_2();
        this.edit_HHMM_TTMM_3 = rms.getHHMMTTMM_3();

        /*Datum och Tid objekt hämtar in egenskaperna här*/
        MModel.Date_Time dateTime = new Date_Time();

        /*Settings för metoden ControllDateTime() */
        this.setDay_30DAY = rms.getDay_30_DAY();
        this.setMounth_30DAY = rms.getMounth_30_DAY();
        this.setMounthName_30DAY = dateTime.setMounth(setMounth_30DAY);
        this.setYear_30DAY = rms.getYear_30_DAY();

        this.setDay_TODAY = rms.getDay_TODAY();
        this.setMounth_TODAY = rms.getMounth_TODAY();
        this.setMounthNameToday = rms.getMounth_TODAY();
        this.setMounthNameToday = dateTime.setMounth(setMounth_TODAY);
        this.setYear_TODAY = rms.getYear_TODAY();

        // linePrefix_List linePrefixBackCommand

        linePrefix_List = new List(langobj.settingsDefaultLineAccess_DEF,
                                   Choice.IMPLICIT);

        linePrefix_List.append(langobj.genDefaultYes_DEF, null);
        linePrefix_List.append(langobj.exitDefaultNo_DEF, null);

        linePrefixBackCommand = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);

        linePrefix_List.addCommand(linePrefixBackCommand);
        linePrefix_List.setCommandListener(this);

        /* ================== RING SAMTAL ============================ */

        callPhoneForm = new Form(langobj.callDefaultCall_DEF);
        callTextField = new TextField(langobj.enterDefaultEnterNumber_DEF, "",
                                      32,
                                      TextField.PHONENUMBER);

        callCommand = new Command(langobj.dialDefault_DEF, Command.OK, 1);
        callBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK,
                                      2);
        callPhoneForm.addCommand(callCommand);
        callPhoneForm.addCommand(callBackCommand);
        // callPhoneForm.addCommand(callEditRenameCommand);
        callPhoneForm.setCommandListener(this);
        callPhoneForm.setItemStateListener(this);

        // --- Intern samtal.

        internCallPhoneForm = new Form(langobj.extensionDefaultCall_DEF);
        internCallTextField = new TextField(langobj.enterDefaultEnterNumber_DEF,
                                            "", 5,
                                            TextField.PHONENUMBER);

        internCallCommand = new Command(langobj.dialDefault_DEF, Command.OK, 1);
        internCallBackCommand = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);
        internCallEditRenameCommand = new Command(langobj.genDefaultEdit_DEF,
                                                  Command.OK, 3);
        internCallPhoneForm.addCommand(internCallCommand);
        internCallPhoneForm.addCommand(internCallBackCommand);
        internCallPhoneForm.addCommand(internCallEditRenameCommand);
        internCallPhoneForm.setCommandListener(this);
        internCallPhoneForm.setItemStateListener(this);

        // --- Edit callEditForm

        callInternEditForm = new Form(langobj.extensionDefaultCall_DEF);

        internCallEditNameTextField = new TextField(
                langobj.callExtensionDefaultName_DEF,
                "", 30, TextField.SENSITIVE);
        internCallEditExtensionTextField = new TextField(
                langobj.settingsDefaultExtension_DEF, "", 5,
                TextField.PHONENUMBER);

        internCallEditSaveCommand = new Command(langobj.genDefaultSave_DEF,
                                                Command.OK,
                                                1);
        internCallEditBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                Command.BACK,
                                                2);
        internCallEditCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                                  Command.CANCEL, 3);

        callInternEditForm.addCommand(internCallEditSaveCommand);
        callInternEditForm.addCommand(internCallEditBackCommand);
        callInternEditForm.addCommand(internCallEditCancelCommand);
        callInternEditForm.setCommandListener(this);

        // --- Rename connectRenameForm

        callRenameForm = new Form(langobj.extensionDefaultCall_DEF);

        internCallRenameBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                  Command.BACK,
                                                  2);

        callRenameForm.addCommand(internCallRenameBackCommand);
        callRenameForm.setCommandListener(this);
        callRenameForm.setItemStateListener(this);

        // --- Edit connectRenameEditForm

        callRenameEditForm = new Form(langobj.extensionDefaultCall_DEF);

        internCallEditRenameNameTextField = new TextField(
                langobj.callExtensionDefaultName_DEF, "", 30,
                TextField.SENSITIVE);
        internCallEditRenameExtensionTextField = new TextField(
                langobj.settingsDefaultExtension_DEF, "", 5,
                TextField.PHONENUMBER);

        internCallEditRenameSaveCommand = new Command(langobj.
                genDefaultSave_DEF,
                Command.OK, 1);
        internCallEditRenameBackCommand = new Command(langobj.
                genDefaultBack_DEF,
                Command.BACK, 2);
        internCallEditRenameCancelCommand = new Command(langobj.
                genDefaultCancel_DEF,
                Command.CANCEL, 3);

        callRenameEditForm.addCommand(internCallEditRenameSaveCommand);
        callRenameEditForm.addCommand(internCallEditRenameBackCommand);
        callRenameEditForm.addCommand(internCallEditRenameCancelCommand);
        callRenameEditForm.setCommandListener(this);

        /* ================== HÄNVISNING ================================ */

        //--- AbsentList, lista för hänvisning i prg.

        //---- Finns på anknytning

        atExtForm = new Form(langobj.absentDefaultAtExt_DEF);
        atExtTextField = new TextField(langobj.enterDefaultEnterExtension_DEF,
                                       "", 4,
                                       TextField.NUMERIC);

        atExtSendCommand = new Command(langobj.genDefaultSend_DEF, Command.OK,
                                       1);
        atExtBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK,
                                       2);
        atExtForm.addCommand(atExtSendCommand);
        atExtForm.addCommand(atExtBackCommand);
        atExtForm.setCommandListener(this);

        //--- Åter Klockan

        backAtForm = new Form(langobj.absentDefaultBackAt_DEF);
        backAtTextField = new TextField(langobj.enterDefaultEnterHHMM_DEF, "",
                                        4,
                                        TextField.NUMERIC);

        backAtSendCommand = new Command(langobj.genDefaultSend_DEF, Command.OK,
                                        1);
        backAtBackCommand = new Command(langobj.genDefaultBack_DEF,
                                        Command.BACK, 2);
        backAtForm.addCommand(backAtSendCommand);
        backAtForm.addCommand(backAtBackCommand);
        backAtForm.setCommandListener(this);

        //--- Ute

        outForm = new Form(langobj.absentDefaultOutUntil_DEF);
        outTextField = new TextField(langobj.enterDefaultEnterMMDD_DEF, "", 4,
                                     TextField.NUMERIC);

        outSendCommand = new Command(langobj.genDefaultSend_DEF, Command.OK, 1);
        outBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK,
                                     2);
        outForm.addCommand(outSendCommand);
        outForm.addCommand(outBackCommand);
        outForm.setCommandListener(this);

        /* =================== GRUPPER ================================= */

        //--- GroupList, lista för att välja svarsgrupper i PBX'en.

        groupList = new List("", Choice.IMPLICIT); // skapar en lista
        groupList.setTitle(langobj.groupsDefaultGroups_DEF);

        groupBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK,
                                       1);

        try {

            Image imageGroup = Image.createImage("/prg_icon/konf24.png");

            groupList.append(langobj.groupsDefaultLoginAllGroups_DEF,
                             imageGroup);
            groupList.append(langobj.groupsDefaultLogoutAllGroups_DEF,
                             imageGroup);
            groupList.append(langobj.groupsDefaultLoginSpecificGroup_DEF,
                             imageGroup);
            groupList.append(langobj.groupsDefaultLogoutSpecificGroup_DEF,
                             imageGroup);

            groupList.addCommand(groupBackCommand);
            groupList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //--- logoutGroup

        logoffGroupForm = new Form(langobj.groupsDefaultLogoutSpecificGroup_DEF);
        logoffGroupTextField = new TextField(langobj.
                                             enterDefaultEnterNumber_DEF, "", 5,
                                             TextField.NUMERIC);

        logoffGroupSendCommand = new Command(langobj.genDefaultSend_DEF,
                                             Command.OK, 1);
        logoffGroupBackCommand = new Command(langobj.genDefaultBack_DEF,
                                             Command.BACK,
                                             2);

        logoffGroupForm.addCommand(logoffGroupSendCommand);
        logoffGroupForm.addCommand(logoffGroupBackCommand);
        logoffGroupForm.setCommandListener(this);

        //--- loginGroup

        loginGroupForm = new Form(langobj.groupsDefaultLoginSpecificGroup_DEF);
        loginGroupTextField = new TextField(langobj.enterDefaultEnterNumber_DEF,
                                            "", 5, TextField.NUMERIC);

        loginGroupSendCommand = new Command(langobj.genDefaultSend_DEF,
                                            Command.OK, 1);
        loginGroupBackCommand = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);

        loginGroupForm.addCommand(loginGroupSendCommand);
        loginGroupForm.addCommand(loginGroupBackCommand);
        loginGroupForm.setCommandListener(this);

        /* =================== STATUS ==================================== */

        tv_StatusForm = new Form("");

        tvStatusTextField = new TextField(langobj.userName_DEF + ":", "", 16,
                                          TextField.ANY);
        tvStatusDateTextField = new TextField(langobj.enterDefaultEnterMMDD_DEF + ":", "", 4,
                                              TextField.NUMERIC);

        tvStatusSendCommand = new Command(langobj.genDefaultSend_DEF,
                                          Command.OK, 1);
        tvStatusBackCommand = new Command(langobj.genDefaultBack_DEF,
                                          Command.BACK, 2);

        tv_StatusForm.addCommand(tvStatusSendCommand);
        tv_StatusForm.addCommand(tvStatusBackCommand);
        tv_StatusForm.setCommandListener(this);


        /* =================== VIDAREKOPPLA ============================== */

        // --- Totalview

        tv_CallForwardForm = new Form("");

        tvFormMessageTextField = new TextField(langobj.message_DEF + ":", "", 16,
                                               TextField.ANY);
        tvFormNumberTextField = new TextField(langobj.
                                              enterDefaultEnterNumber_DEF, "",
                                              32, TextField.PHONENUMBER);

        tvFormSendCallForwardCommand = new Command(langobj.genDefaultSend_DEF,
                Command.OK, 1);
        tvFormBackCallForwardCommand = new Command(langobj.genDefaultBack_DEF,
                Command.BACK, 2);

        tv_CallForwardForm.addCommand(tvFormSendCallForwardCommand);
        tv_CallForwardForm.addCommand(tvFormBackCallForwardCommand);
        tv_CallForwardForm.setCommandListener(this);


        // --- Alla samtal
        allCallForwardForm = new Form("");
        allCallForwardTextField = new TextField(langobj.
                                                enterDefaultEnterNumber_DEF, "",
                                                32, TextField.PHONENUMBER);

        allCallForwardSendCommand = new Command(langobj.genDefaultSend_DEF,
                                                Command.OK,
                                                1);
        allCallForwardBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                Command.BACK, 2);

        allCallForwardForm.addCommand(allCallForwardSendCommand);
        allCallForwardForm.addCommand(allCallForwardBackCommand);
        allCallForwardForm.setCommandListener(this);

        // --- Externa samtal
        externCallForwardForm = new Form("");
        externCallForwardTextField = new TextField(langobj.
                enterDefaultEnterNumber_DEF,
                "", 32, TextField.PHONENUMBER);

        externCallForwardSendCommand = new Command(langobj.genDefaultSend_DEF,
                Command.OK, 1);
        externCallForwardBackCommand = new Command(langobj.genDefaultBack_DEF,
                Command.BACK, 2);

        externCallForwardForm.addCommand(externCallForwardSendCommand);
        externCallForwardForm.addCommand(externCallForwardBackCommand);
        externCallForwardForm.setCommandListener(this);

        // --- Interna samtal
        internCallForwardForm = new Form("");
        internCallForwardTextField = new TextField(langobj.
                enterDefaultEnterNumber_DEF,
                "", 32, TextField.PHONENUMBER);

        internCallForwardSendCommand = new Command(langobj.genDefaultSend_DEF,
                Command.OK, 1);
        internCallForwardBackCommand = new Command(langobj.genDefaultBack_DEF,
                Command.BACK, 2);

        internCallForwardForm.addCommand(internCallForwardSendCommand);
        internCallForwardForm.addCommand(internCallForwardBackCommand);
        internCallForwardForm.setCommandListener(this);

        /* =================== RÖSTBREVLÅDOR ============================= */

        //--- Röstbrevlåda PBX, vxl'ens egna voicemail.

        voiceEditForm_PBX = new Form(langobj.voiceMailDefaultEditVoicemail_DEF);
        voiceMailPBXTextField_PBX = new TextField(langobj.
                                                  enterDefaultEnterNumber_DEF,
                                                  "", 4, TextField.NUMERIC);

        voiceEditSaveCommand_PBX = new Command(langobj.genDefaultSave_DEF,
                                               Command.OK,
                                               1);
        voiceEditCancelCommand_PBX = new Command(langobj.genDefaultCancel_DEF,
                                                 Command.CANCEL, 2);
        voiceEditBackcommand_PBX = new Command(langobj.genDefaultBack_DEF,
                                               Command.BACK,
                                               3);

        voiceEditForm_PBX.addCommand(voiceEditSaveCommand_PBX);
        voiceEditForm_PBX.addCommand(voiceEditBackcommand_PBX);
        voiceEditForm_PBX.addCommand(voiceEditCancelCommand_PBX);
        voiceEditForm_PBX.setCommandListener(this);

        /* ================== REDIGERA/EDIT ============================ */

        //--- pbx_List, editerar olika 'settings' i prg. som vxlnr osv...

        pbx_List = new List(langobj.settingsDefaultEditPBXAccess_DEF,
                            Choice.IMPLICIT);

        pbx_List.append(langobj.accessPBXDefault_DEF, null);
        pbx_List.append(langobj.voiceMailDefaultEditVoicemail_DEF, null);
        pbx_List.append(langobj.absentDefaultEditPresence_DEF, null);
        pbx_List.append(langobj.settingsDefaultPreEditCode_DEF, null);
        pbx_List.append(langobj.settingsDefaultLanguage_DEF, null);

        pbx_ListCancelCommand = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);

        pbx_List.addCommand(pbx_ListCancelCommand);
        pbx_List.setCommandListener(this);

        //-------- pbx_List_type, editerar olika settings för PBX-typer

        pbx_List_type = new List(langobj.settingsDefaultEditPBXAccess_DEF,
                                 Choice.IMPLICIT);
        pbx_List_type.append(langobj.autoAccessDefault_DEF, null);
        pbx_List_type.append(langobj.accessViaPINCodeDefault_DEF, null);

        pbx_List_typeBackCommand = new Command(langobj.genDefaultBack_DEF,
                                               Command.BACK,
                                               1);
        pbx_List_typeCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                                 Command.CANCEL, 2);

        pbx_List_type.addCommand(pbx_List_typeBackCommand);
        pbx_List_type.addCommand(pbx_List_typeCancelCommand);
        pbx_List_type.setCommandListener(this);

        //--- language_List, editerar att byta språk i programmet.

        language_List = new List(langobj.settingsDefaultLanguage_DEF,
                                 Choice.IMPLICIT);
        try {
            String iconPath = "/prg_icon/" + rms.getIconNumber() + ".png";

            System.out.println("ICONPATH >> " + iconPath);

            Image imageEng = Image.createImage("/prg_icon/2.png");
            Image imageIcon = Image.createImage(iconPath);

            language_List.append("English", imageEng);
            language_List.append(this.pbxobj.countryName_PBX, imageIcon);

            languageListBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                  Command.BACK, 2);

            language_List.addCommand(languageListBackCommand);
            language_List.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- PRE-EDIT-FORM ------------------------------------------

        preEditForm = new Form(langobj.settingsDefaultPreEditCode_DEF);
        preEditTextField = new TextField(langobj.enterDefaultEngerCharacter_DEF,
                                         "", 2,
                                         TextField.PHONENUMBER);

        preEditSaveCommand = new Command(langobj.genDefaultSave_DEF, Command.OK,
                                         1);
        preEditBackCommand = new Command(langobj.genDefaultBack_DEF,
                                         Command.BACK, 2);
        preEditCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                           Command.CANCEL,
                                           3);

        preEditForm.addCommand(preEditSaveCommand);
        preEditForm.addCommand(preEditBackCommand);
        preEditForm.addCommand(preEditCancelCommand);
        preEditForm.setCommandListener(this);

        // --- pinCodeSettingsForm, editerar PIN-Code på PBX'en.

        pinCodeSettingsForm = new Form(langobj.accessViaPINCodeDefault_DEF);

        pinCodeLineAccessTextField = new TextField(langobj.settingsDefaultLineAccess_DEF, "", 32, TextField.NUMERIC);

        pinCodeSwitchBoardNumberTextField = new TextField(langobj.settingsDefaultSwitchboardNumber_DEF, "", 32, TextField.PHONENUMBER);

        pinCodeExtensionNumberTextField = new TextField(langobj.settingsDefaultExtension_DEF, "", 32, TextField.PHONENUMBER);

        pinCodePinCodeNumberTextField = new TextField(langobj.settingsDefaultPINcode_DEF, "", 32, TextField.NUMERIC);

        pinCodeGSMnumberTextField = new TextField(langobj.GSMmodemLG_DEF, "", 32, TextField.PHONENUMBER);

        pinCodeSaveCommand = new Command(langobj.genDefaultSave_DEF, Command.OK, 1);
        pinCodeBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK, 2);
        pinCodeCancelCommand = new Command(langobj.genDefaultCancel_DEF, Command.CANCEL, 3);

        pinCodeSettingsForm.addCommand(pinCodeSaveCommand);
        pinCodeSettingsForm.addCommand(pinCodeBackCommand);
        pinCodeSettingsForm.addCommand(pinCodeCancelCommand);
        pinCodeSettingsForm.setCommandListener(this);

        //--- AutoAccessSettingsForm, editerar autologin på PBX'en.

        AutoAccessSettingsForm = new Form(langobj.autoAccessDefault_DEF);

        AutoAccessLineAccessTextField = new TextField(langobj.settingsDefaultLineAccess_DEF, "", 32, TextField.NUMERIC);

        AutoAccessSwitchBoardTextField = new TextField(langobj.settingsDefaultSwitchboardNumber_DEF, "", 32, TextField.PHONENUMBER);

        AutoAccessGSMnumberTextField = new TextField(langobj.GSMmodemLG_DEF, "", 32, TextField.PHONENUMBER);

        AutoAccessBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK, 2);
        AutoAccessCancelCommand = new Command(langobj.genDefaultCancel_DEF, Command.CANCEL, 3);
        AutoAccessSaveCommand = new Command(langobj.genDefaultSave_DEF, Command.OK, 1);

        AutoAccessSettingsForm.addCommand(AutoAccessBackCommand);
        AutoAccessSettingsForm.addCommand(AutoAccessCancelCommand);
        AutoAccessSettingsForm.addCommand(AutoAccessSaveCommand);
        AutoAccessSettingsForm.setCommandListener(this);

        //--- AutoAccessSettingsNOPrefixForm, editerar autologin på PBX'en.

        AutoAccessSettingsNOPrefixForm = new Form(langobj.autoAccessDefault_DEF);

        AutoAccessNOPrefixSwitchBoardTextField = new TextField(langobj.settingsDefaultSwitchboardNumber_DEF, "", 32, TextField.PHONENUMBER);
        AutoAccessNOPrefixGSMnumberTextField = new TextField(langobj.GSMmodemLG_DEF, "", 32, TextField.PHONENUMBER);

        AutoAccessBackNOPrefixCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK, 2);
        AutoAccessCancelNOPrefixCommand = new Command(langobj.genDefaultCancel_DEF, Command.CANCEL, 3);
        AutoAccessSaveNOPrefixCommand = new Command(langobj.genDefaultSave_DEF, Command.OK, 1);

        AutoAccessSettingsNOPrefixForm.addCommand(AutoAccessBackNOPrefixCommand);
        AutoAccessSettingsNOPrefixForm.addCommand(AutoAccessCancelNOPrefixCommand);
        AutoAccessSettingsNOPrefixForm.addCommand(AutoAccessSaveNOPrefixCommand);
        AutoAccessSettingsNOPrefixForm.setCommandListener(this);


        // --- countryForm, editera och byt önskat landsnummer i prg.

        countryForm = new Form(langobj.settingsDefaultCountryCode_DEF);

        countryTextField = new TextField(langobj.
                                         settingsDefaultSelectCountryCode_DEF,
                                         "",
                                         4,
                                         TextField.NUMERIC);

        countryBackCommand = new Command(langobj.genDefaultBack_DEF,
                                         Command.BACK,
                                         2);
        countryCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                           Command.CANCEL,
                                           3);
        countrySaveCommand = new Command(langobj.genDefaultSave_DEF, Command.OK,
                                         1);

        countryForm.addCommand(countryBackCommand);
        countryForm.addCommand(countryCancelCommand);
        countryForm.addCommand(countrySaveCommand);
        countryForm.setCommandListener(this);

        // --- editAbsentForm, editera, DTFM-sträng, namn osv.

        editAbsentForm = new Form(langobj.absentDefaultEditPresence_DEF);

        editAbsentName_TextField = new TextField(langobj.
                                                 callExtensionDefaultName_DEF,
                                                 "", 32,
                                                 TextField.INITIAL_CAPS_WORD);

        editAbsentSaveCommand = new Command(langobj.genDefaultSave_DEF,
                                            Command.OK, 1);
        editAbsentBackCommand = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);
        editAbsentCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                              Command.CANCEL, 3);

        editAbsentForm.addCommand(editAbsentBackCommand);
        editAbsentForm.addCommand(editAbsentCancelCommand);
        editAbsentForm.addCommand(editAbsentSaveCommand);
        editAbsentForm.setCommandListener(this);

        /* ================== ALERT's ================================== */

        // --- Exit alert. Visas då prg avslutas.
        try {
            Image alertExitImage = Image.createImage("/prg_icon/exit2_64.png");
            alertExit = new Alert(langobj.genDefaultExit_DEF,
                                  langobj.exitDefaultExitTheProgramYesOrNo_DEF,
                                  alertExitImage, AlertType.CONFIRMATION);

            alertExit.setTimeout(Alert.FOREVER);

            confirmExitYESCommand = new Command(langobj.genDefaultYes_DEF,
                                                Command.EXIT, 1);
            confirmExitNOCommand = new Command(langobj.exitDefaultNo_DEF,
                                               Command.OK, 2);

            alertExit.addCommand(confirmExitYESCommand);
            alertExit.addCommand(confirmExitNOCommand);
            alertExit.setCommandListener(this);
        } catch (IOException ex5) {
        }

        // --- alertON, Visas då prg 'Mex on' är aktiv.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertON = new Alert(langobj.mainListAttributMexOn_DEF, "",
                                alertInfo,
                                AlertType.INFO);
            alertON.setString(langobj.mainListAttributMexOn_DEF + "?");
            alertON.setTimeout(Alert.FOREVER);

            confirmOnYESCommand = new Command(langobj.genDefaultYes_DEF,
                                              Command.OK, 1);
            confirmOnCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                                 Command.CANCEL, 2);

            alertON.addCommand(confirmOnYESCommand);
            alertON.addCommand(confirmOnCancelCommand);
            alertON.setCommandListener(this);

        } catch (IOException ex11) {
        }

        //--- alertOFF, Visas då prg 'Mex off' är aktiv.

        try {

            Image alertMexOff = Image.createImage("/prg_icon/info.png");
            alertOFF = new Alert(langobj.mainListAttributMexOff_DEF, "",
                                 alertMexOff,
                                 AlertType.INFO);
            alertOFF.setString(langobj.mainListAttributMexOff_DEF + "?");
            alertOFF.setTimeout(Alert.FOREVER);

            confirmOffYESCommand = new Command(langobj.genDefaultYes_DEF,
                                               Command.OK, 1);
            confirmOffCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                                  Command.CANCEL, 2);

            alertOFF.addCommand(confirmOffYESCommand);
            alertOFF.addCommand(confirmOffCancelCommand);
            alertOFF.setCommandListener(this);

        } catch (IOException ex11) {
        }

        //--- alertEditSettings, Visas då något i prg sparas om.

        Image alertEditSettingImage = Image.createImage(
                "/prg_icon/save.png");
        alertEditSettings = new Alert(langobj.alertDefaultSaveChanges_DEF,
                                      langobj.alsertDefaultChangesSave_DEF,
                                      alertEditSettingImage,
                                      AlertType.CONFIRMATION);

        alertEditSettings.setTimeout(2000);

        //--- alertDebugONOFF, Visas då prg 'Debug' är ON el. OFF

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertDebugONOFF = new Alert(langobj.alertMessageMexServerInfo_DEF,
                                        "",
                                        alertInfo,
                                        AlertType.INFO);
            alertDebugONOFF.setTimeout(1000);

        } catch (IOException ex11) {
        }

        //--- alertSendOKNOK, Visas då prg 'Sänder till webservern' är Ok el fel.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertSendOKNOK = new Alert(langobj.alertMessageMexServerInfo_DEF,
                                       "",
                                       alertInfo,
                                       AlertType.INFO);
            alertSendOKNOK.setTimeout(Alert.FOREVER);

        } catch (IOException ex11) {
        }

        //--- alertSENDDebug, Visas då Debug-logg skickas.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertSENDDebug = new Alert(langobj.alertMessageMexServerInfo_DEF,
                                       "Sending Log!\nWait for response.",
                                       alertInfo, AlertType.INFO);
            alertSENDDebug.setTimeout(1500);

        } catch (IOException ex11) {
        }

        //--- alertLogOutDebug, Visas då Debug-logg skickas.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertLogOutDebug = new Alert(langobj.alertMessageMexServerInfo_DEF,
                                         "Logout!", alertInfo, AlertType.INFO);
            alertLogOutDebug.setTimeout(1000);

        } catch (IOException ex11) {
        }

        //--- alertMexAlreadyONOFF, Visas då prg 'Mex on/off' redan är aktiv.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertMexAlreadyONOFF = new Alert(langobj.
                                             alertMessageMexServerInfo_DEF, "",
                                             alertInfo,
                                             AlertType.INFO);
            alertMexAlreadyONOFF.setTimeout(Alert.FOREVER);

        } catch (IOException ex11) {
        }

        //--- alertRestarting, Visad då prg måste startas om, tex språkbyte.


        Image alertRestartImage = Image.createImage("/prg_icon/restart.png");
        alertRestarting = new Alert(langobj.alertDefaultSaveChanges_DEF,
                                    langobj.exitDefaultRestartProgram_DEF,
                                    alertRestartImage,
                                    AlertType.CONFIRMATION);

        alertRestarting.setTimeout(Alert.FOREVER);

        /* ================== FRISTÅENDE KOMMANDON ========================= */

        GraphicsAboutCommand = new Command(langobj.version_DEF, Command.HELP, 3);
        goGraphicsBackCommand = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);
        GraphicsHelpCommand = new Command(langobj.settingsDefaultHelp_DEF,
                                          Command.HELP,
                                          3);

        /* ================== LICENS KONTROLL ============================= */

        // Om licensen är en demo-licens '1' kontrollera datumet.
        if (pbxobj.demo_PBX.equals("1")) {
            ControllDateTime();
        } else if (!pbxobj.demo_PBX.equals("1")) {

            this.ViewDateString = "Enterprise License";

        }

        /* =========== kontroll av IMEI nummer =============== */

        MModel.IMEI imei = new IMEI();
//        imei.checkIMEI();
        imei = null;

        dateTime = null;
        rms = null;

    } // Konstruktorn slutar här.


    /* ================== FORM settings ==================================== */

    public Alert getAlertExpernceLisence() {

        // --- 31-dagars licens har gått ut.

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            Image alertExitImage = Image.createImage("/prg_icon/exit2_64.png");

            if (rms.getTWO().equals("2") && !rms.getImeiFalse().equals("1")) {

                alertExpernceLicense = new Alert("License expired",
                                                 "Your license has expired\n" +
                                                 "The program will shut down. " +
                                                 "\nPlease contact your PBX dealer."
                                                 + "\n\nwww.mobisma.com",

                                                 alertExitImage,
                                                 AlertType.CONFIRMATION);

                alertExpernceLicense.setTimeout(Alert.FOREVER);
                licensYESCommand = new Command("Ok", Command.EXIT, 1);

                alertExpernceLicense.addCommand(licensYESCommand);
                alertExpernceLicense.setCommandListener(this);

            } else if (rms.getTWO().equals("2") &&
                       rms.getImeiFalse().equals("1")) {

                alertExpernceLicense = new Alert("License expired",
                                                 "The IMEI is incorrect!\n" +
                                                 "The program will shut down. " +
                                                 "\nPlease contact your PBX dealer."
                                                 + "\n\nwww.mobisma.com",

                                                 alertExitImage,
                                                 AlertType.CONFIRMATION);

                alertExpernceLicense.setTimeout(Alert.FOREVER);
                licensYESCommand = new Command("Ok", Command.EXIT, 1);

                alertExpernceLicense.addCommand(licensYESCommand);
                alertExpernceLicense.setCommandListener(this);

            }

        } catch (IOException ex5) {
        } catch (RecordStoreNotOpenException ex) {
            /** @todo Handle this exception */
        } catch (InvalidRecordIDException ex) {
            /** @todo Handle this exception */
        } catch (RecordStoreException ex) {
            /** @todo Handle this exception */
        }

        rms = null;

        return alertExpernceLicense;
    }


    public Alert getAlertExit() {

        return alertExit;
    }


    // --- Edit uppringda nummer
    public Form getCallEditForm() {

        callInternEditForm.deleteAll();
        internCallEditNameTextField.setString("");
        callInternEditForm.append(internCallEditNameTextField);
        internCallEditExtensionTextField.setString("");
        callInternEditForm.append(internCallEditExtensionTextField);
        return callInternEditForm;
    }

    // --- Editra 'listan' uppringda nummer
    public Form getCallRenameEditForm() {

        callRenameEditForm.deleteAll();
        callRenameEditForm.append(internCallEditRenameNameTextField);
        callRenameEditForm.append(internCallEditRenameExtensionTextField);
        return callRenameEditForm;
    }

    // --- Ring samtal
    public Form getCallPhoneForm() {

        callPhoneForm.deleteAll();
        callPhoneForm.append(callTextField);

        try {
            callPhoneForm.append(getCalledButton());
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }

        return callPhoneForm;
    }

    public Form getInternCallPhoneForm() {

        internCallPhoneForm.deleteAll();
        internCallPhoneForm.append(internCallTextField);

        try {
            internCallPhoneForm.append(getInternCallButton());
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }

        return internCallPhoneForm;
    }


    public Form getRenameForm() {

        callRenameForm.deleteAll();

        try {
            callRenameForm.append(getEditInternButton());
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }

        return callRenameForm;
    }

    // --- Hänvisning forms.

    public Form getAtExtForm() { // - Tillfälligt ute

        atExtForm.deleteAll();
        atExtForm.append(atExtTextField);

        return atExtForm;
    }

    public Form getBackATForm() { // - Sammanträde

        backAtForm.deleteAll();
        backAtForm.append(backAtTextField);

        return backAtForm;
    }

    public Form getOutForm() { // - Tjänsteresa

        outForm.deleteAll();
        outForm.append(outTextField);

        return outForm;
    }

    public Form getEditAbsentForm(String s) {

        editAbsentForm.deleteAll();

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        if (s.equals("1")) {

            try {
                this.absentSystemName = rms.getEditAbsentName_1();
            } catch (RecordStoreNotOpenException ex) {
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreException ex) {
            }

        } else if (s.equals("2")) {

            try {
                this.absentSystemName = rms.getEditAbsentName_2();
            } catch (RecordStoreNotOpenException ex) {
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreException ex) {
            }

        } else if (s.equals("3")) {

            try {
                this.absentSystemName = rms.getEditAbsentName_3();
            } catch (RecordStoreNotOpenException ex) {
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreException ex) {
            }

        }
        editAbsentName_TextField.setString(absentSystemName);
        editAbsentForm.append(editAbsentName_TextField);

        rms = null;
        return editAbsentForm;
    }


    // --- Sätter värden och kan ändra tecknet *7
    public Form getPreEditForm() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        preEditForm.deleteAll();

        String setPreEditCode = this.pbxobj.precode_PBX;
        preEditTextField.setString(setPreEditCode);
        preEditForm.append(preEditTextField);

        return preEditForm;
    }

    // --- Sätter värden i pinCodeSettingsForm
    public Form getPINCodeSettingsForm() {

        pinCodeSettingsForm.deleteAll();

        if (this.pbxobj.lineAccess_PBX.equals("NONE")) {

            this.pbxobj.lineAccess_PBX = "9";
        }

        pinCodeLineAccessTextField.setString(pbxobj.lineAccess_PBX);
        pinCodeSettingsForm.append(pinCodeLineAccessTextField);
        pinCodeSwitchBoardNumberTextField.setString(pbxobj.
                switchBoardNumber_PBX);
        pinCodeSettingsForm.append(pinCodeSwitchBoardNumberTextField);
        pinCodeExtensionNumberTextField.setString(pbxobj.extensionNumber_PBX);
        pinCodeSettingsForm.append(pinCodeExtensionNumberTextField);
        pinCodePinCodeNumberTextField.setString(pbxobj.pinCodeNumber_PBX);
        pinCodeSettingsForm.append(pinCodePinCodeNumberTextField);

        if (!conf.xhvtype.equals("")) { // om det är extrahänvisning.

            pinCodeGSMnumberTextField.setString(pbxobj.gsm_number);
            pinCodeSettingsForm.append(pinCodeGSMnumberTextField);
        }


        return pinCodeSettingsForm;
    }

    // --- Sätter värden i AutoAccessSettingsNOPrefixForm.
    public Form getAutoAccessNOPrefixForm() {

        AutoAccessSettingsNOPrefixForm.deleteAll();

        AutoAccessNOPrefixSwitchBoardTextField.setString(pbxobj.
                switchBoardNumber_PBX);
        AutoAccessSettingsNOPrefixForm.append(
                AutoAccessNOPrefixSwitchBoardTextField);

        if (!conf.xhvtype.equals("")) { // om det är extrahänvisning.

            AutoAccessNOPrefixGSMnumberTextField.setString(pbxobj.gsm_number);
            AutoAccessSettingsNOPrefixForm.append(AutoAccessNOPrefixGSMnumberTextField);
        }


        return AutoAccessSettingsNOPrefixForm;
    }


    // --- Sätter värden i AutoAccessSettingsForm.
    public Form getAutoAccessSettingForm() {

        AutoAccessSettingsForm.deleteAll();

        if (this.pbxobj.lineAccess_PBX.equals("NONE")) {

            this.pbxobj.lineAccess_PBX = "9";
        }

        AutoAccessLineAccessTextField.setString(pbxobj.lineAccess_PBX);
        AutoAccessSettingsForm.append(AutoAccessLineAccessTextField);

        AutoAccessSwitchBoardTextField.setString(pbxobj.switchBoardNumber_PBX);
        AutoAccessSettingsForm.append(AutoAccessSwitchBoardTextField);

        if (!conf.xhvtype.equals("")) { // om det är extrahänvisning.

            AutoAccessGSMnumberTextField.setString(pbxobj.gsm_number);
            AutoAccessSettingsForm.append(AutoAccessGSMnumberTextField);
        }


        return AutoAccessSettingsForm;

    }

    // --- Sätter värden i countryForm.
    public Form getCountryForm() {

        countryForm.deleteAll();
        try {
            countryTextField.setString(this.pbxobj.countryCode_PBX);
        } catch (Exception ex) {
        }
        countryForm.append(countryTextField);

        return countryForm;
    }

    // --- Sätter värden i voiceEditForm_PBX.
    public Form getPBXVoiceEditForm() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        voiceEditForm_PBX.deleteAll();
        voiceMailPBXTextField_PBX.setString(pbxobj.voiceMailSwitchboard_PBX);
        voiceEditForm_PBX.append(voiceMailPBXTextField_PBX);

        return voiceEditForm_PBX;
    }

    // --- Sätter och visar logoffGroupForm.
    public Form getLogoffGroupForm() {

        logoffGroupForm.deleteAll();
        logoffGroupForm.append(logoffGroupTextField);

        return logoffGroupForm;
    }

    // --- Sätter och visar loginGroupForm.
    public Form getLoginGroupForm() {

        loginGroupForm.deleteAll();
        loginGroupForm.append(loginGroupTextField);

        return loginGroupForm;
    }

    public Form getAllCallForwardForm() {

        allCallForwardForm.deleteAll();

        if (allCallForwardRename.equals("1")) {

            allCallForwardForm.setTitle(langobj.callForwardDefaultAllCalls_DEF);

        } else if (allCallForwardRename.equals("2")) {

            allCallForwardForm.setTitle(langobj.callForwardDefaultBusy_DEF);

        } else if (allCallForwardRename.equals("3")) {

            allCallForwardForm.setTitle(langobj.callForwardDefaultNoAnswer_DEF);

        } else if (allCallForwardRename.equals("4")) {

            allCallForwardForm.setTitle(langobj.
                                        callForwardDefaultBusyNoAnswer_DEF);

        }

        allCallForwardForm.append(allCallForwardTextField);

        return allCallForwardForm;
    }

    public Form getExternCallForwardForm() {

        externCallForwardForm.deleteAll();

        if (externCallForwardRename.equals("1")) {

            externCallForwardForm.setTitle(langobj.
                                           callForwardDefaultAllCalls_DEF);

        } else if (externCallForwardRename.equals("2")) {

            externCallForwardForm.setTitle(langobj.callForwardDefaultBusy_DEF);

        } else if (externCallForwardRename.equals("3")) {

            externCallForwardForm.setTitle(langobj.
                                           callForwardDefaultNoAnswer_DEF);

        } else if (externCallForwardRename.equals("4")) {

            externCallForwardForm.setTitle(langobj.
                                           callForwardDefaultBusyNoAnswer_DEF);

        }

        externCallForwardForm.append(externCallForwardTextField);

        return externCallForwardForm;
    }

    public Form getInternCallForwardForm() {

        internCallForwardForm.deleteAll();

        if (internCallForwardRename.equals("1")) {

            internCallForwardForm.setTitle(langobj.
                                           callForwardDefaultAllCalls_DEF);

        } else if (internCallForwardRename.equals("2")) {

            internCallForwardForm.setTitle(langobj.callForwardDefaultBusy_DEF);

        } else if (internCallForwardRename.equals("3")) {

            internCallForwardForm.setTitle(langobj.
                                           callForwardDefaultNoAnswer_DEF);

        } else if (internCallForwardRename.equals("4")) {

            internCallForwardForm.setTitle(langobj.
                                           callForwardDefaultBusyNoAnswer_DEF);

        }

        internCallForwardForm.append(internCallForwardTextField);

        return internCallForwardForm;
    }

    /* ================== LIST settings ==================================== */

    public List getCallForwardList() {

        // --- CallforwardList

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        callForwardList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                callForwardList.setTicker(null);
                callForwardTicker = new Ticker(" ");
                callForwardList.setTicker(callForwardTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                callForwardList.setTicker(null);
                callForwardTicker = new Ticker(rms.getAbsentStatus());
                callForwardList.setTicker(callForwardTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        backCallForwardCommand = new Command(langobj.genDefaultBack_DEF,
                                             Command.BACK,
                                             2);
        try {
            Image imageCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            callForwardList.append(langobj.callForwardDefaultAllCalls_DEF,
                                   imageCallForward);
            callForwardList.append(langobj.callForwardDefaultExternCalls_DEF,
                                   imageCallForward);
            callForwardList.append(langobj.callForwardDefaultInternCalls_DEF,
                                   imageCallForward);

            callForwardList.addCommand(backCallForwardCommand);
            callForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }
        rms = null;
        return callForwardList;
    }

    public List getAllCallForwardList() {

        // --- AllCallforwardList

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        allCallForwardList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                allCallForwardList.setTicker(null);
                allCallForwardTicker = new Ticker(" ");
                allCallForwardList.setTicker(allCallForwardTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                allCallForwardList.setTicker(null);
                allCallForwardTicker = new Ticker(rms.getAbsentStatus());
                allCallForwardList.setTicker(allCallForwardTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        backAllCallForwardCommand = new Command(langobj.genDefaultBack_DEF,
                                                Command.BACK, 2);

        try {
            Image imageAllCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            allCallForwardList.append(langobj.callForwardDefaultRemove_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(langobj.callForwardDefaultDontDisturb_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(langobj.callForwardDefaultAllCalls_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(langobj.callForwardDefaultBusy_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(langobj.callForwardDefaultNoAnswer_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(langobj.
                                      callForwardDefaultBusyNoAnswer_DEF,
                                      imageAllCallForward);

            allCallForwardList.addCommand(backAllCallForwardCommand);
            allCallForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }
        rms = null;
        return allCallForwardList;
    }

    public List getExternCallForwardList() {

        // --- ExternCallforwardList

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        externCallForwardList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                externCallForwardList.setTicker(null);
                externCallForwardTicker = new Ticker(" ");
                externCallForwardList.setTicker(externCallForwardTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                externCallForwardList.setTicker(null);
                externCallForwardTicker = new Ticker(rms.getAbsentStatus());
                externCallForwardList.setTicker(externCallForwardTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        backExternCallForwardCommand = new Command(langobj.genDefaultBack_DEF,
                Command.BACK, 2);

        try {
            Image imageExternCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            externCallForwardList.append(langobj.callForwardDefaultRemove_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(langobj.
                                         callForwardDefaultDontDisturb_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(langobj.callForwardDefaultAllCalls_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(langobj.callForwardDefaultBusy_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(langobj.callForwardDefaultNoAnswer_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(langobj.
                                         callForwardDefaultBusyNoAnswer_DEF,
                                         imageExternCallForward);

            externCallForwardList.addCommand(backExternCallForwardCommand);
            externCallForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }
        rms = null;
        return externCallForwardList;
    }

    public List getInternCallForwardList() {

        // --- InternCallforwardList

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        internCallForwardList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                internCallForwardList.setTicker(null);
                internCallForwardTicker = new Ticker(" ");
                internCallForwardList.setTicker(internCallForwardTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                internCallForwardList.setTicker(null);
                internCallForwardTicker = new Ticker(rms.getAbsentStatus());
                internCallForwardList.setTicker(internCallForwardTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        backInternCallForwardCommand = new Command(langobj.genDefaultBack_DEF,
                Command.BACK, 2);

        try {
            Image imageInternCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            internCallForwardList.append(langobj.callForwardDefaultRemove_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(langobj.
                                         callForwardDefaultDontDisturb_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(langobj.callForwardDefaultAllCalls_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(langobj.callForwardDefaultBusy_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(langobj.callForwardDefaultNoAnswer_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(langobj.
                                         callForwardDefaultBusyNoAnswer_DEF,
                                         imageInternCallForward);

            internCallForwardList.addCommand(backInternCallForwardCommand);
            internCallForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }
        rms = null;
        return internCallForwardList;
    }

    public List getAbsentEditList() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        // --- absentEditList, välj att editera attribut 1 eller 2.

        absentEditList = new List(langobj.absentDefaultEditPresence_DEF,
                                  Choice.IMPLICIT);
        absentEditList.append("", null);
        absentEditList.append("", null);
        absentEditList.append("", null);

        editAbsentListBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                Command.BACK, 1);
        editAbsentListCancelCommand = new Command(langobj.genDefaultCancel_DEF,
                                                  Command.CANCEL, 2);

        absentEditList.addCommand(editAbsentListBackCommand);
        absentEditList.addCommand(editAbsentListCancelCommand);
        absentEditList.setCommandListener(this);

        String absent_1 = null;
        try {
            absent_1 = rms.getEditAbsentName_1();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        String absent_2 = null;
        try {
            absent_2 = rms.getEditAbsentName_2();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        String absent_3 = null;
        try {
            absent_3 = rms.getEditAbsentName_3();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        absentEditList.set(0, "1: " + absent_1, null);
        absentEditList.set(1, "2: " + absent_2, null);
        absentEditList.set(2, "3: " + absent_3, null);

        rms = null;
        return absentEditList;

    }

    public List getAbsentList() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        try {
            this.pbxobj.absentStatus = rms.getAbsentStatus();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        absentList = new List("", Choice.IMPLICIT); // skapar en lista

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

        BackCommandAbsentList = new Command(langobj.genDefaultBack_DEF,
                                            Command.BACK, 2);

        try {

            Image image1c = Image.createImage(
                    "/prg_icon/taborthanvisning24.png");
            Image image2c = Image.createImage("/prg_icon/ute24.png");
            Image image3c = Image.createImage("/prg_icon/upptagen24.png");
            Image image4c = Image.createImage("/prg_icon/systemphone24.png");
            Image image5c = Image.createImage("/prg_icon/tillbakakl24.png");
            Image image6c = Image.createImage("/prg_icon/tillbakaden24.png");
            Image image7c = Image.createImage("/prg_icon/konference24.png");
            Image image8c = Image.createImage("/prg_icon/samtalslista24.png");

            // - Ta bort hänvisning
            absentList.append(langobj.absentDefaultRemovePresence_DEF, image1c);
            // - Strax tillbaka
            absentList.append(langobj.absentDeafaultWillReturnSoon_DEF, image2c);
            // - Gått för dagen
            absentList.append(langobj.absentDefaultGoneHome_DEF, image3c);
            // - Finns på anknytning
            absentList.append(langobj.absentDefaultAtExt_DEF, image4c);
            // - Åter klockan
            absentList.append(langobj.absentDefaultBackAt_DEF, image5c);
            // - Åter den
            absentList.append(langobj.absentDefaultOutUntil_DEF, image6c);
            // - På möte
            absentList.append(langobj.absentDefaultInAMeeting_DEF, image7c);
            // - System attribut 1
            absentList.append("", null);
            // - System attribut 2
            absentList.append("", null);
            // - Personligt attriut
            absentList.append("", null);

            absentList.addCommand(BackCommandAbsentList);
            absentList.setCommandListener(this);

            String absent_1 = rms.getEditAbsentName_1();
            String absent_2 = rms.getEditAbsentName_2();
            String absent_3 = rms.getEditAbsentName_3();

            if (absent_1.equals("0")) {

                absentList.set(7, editAbsentName_1, image8c); // mex on

            } else if (!absent_1.equals("0")) {

                absentList.set(7, absent_1, image8c); // mex on

            }
            if (absent_2.equals("0")) {

                absentList.set(8, editAbsentName_2, image8c); // mex on

            } else if (!absent_2.equals("0")) {

                absentList.set(8, absent_2, image8c); // mex on

            }
            if (absent_3.equals("0")) {

                absentList.set(9, editAbsentName_3, image8c); // mex off

            } else if (!absent_3.equals("0")) {

                absentList.set(9, absent_3, image8c); // mex off

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


    public List getMainList() {

        /* ================== HUVUDMENY/LISTA ============================ */

         if (conf.xhvtype.equals("")) { // om inte extrahänvisning.

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        mainList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                mainList.setTicker(null);
                mainStatusTicker = new Ticker(" ");
                mainList.setTicker(mainStatusTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                mainList.setTicker(null);
                mainStatusTicker = new Ticker(rms.getAbsentStatus());
                mainList.setTicker(mainStatusTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        // Huvudlistan, förstamenyn visas när prg startat.
        mainListEditCommand = new Command(langobj.genDefaultEdit_DEF,
                                          Command.OK, 1);
        mainListaboutMobismaCommand = new Command(langobj.
                                                  settingsDefaultAbout_DEF,
                                                  Command.OK, 2);
        mainListExitCommand = new Command(langobj.genDefaultExit_DEF,
                                          Command.OK, 3);

        try {

            Image image1a = Image.createImage("/prg_icon/ring24.png");
            Image image3a = Image.createImage("/prg_icon/systemphone24.png");
            Image image4a = Image.createImage("/prg_icon/hanvisa24.png");
            Image image5a = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image6a = Image.createImage("/prg_icon/rostbrevlada24.png");
            Image image7a = Image.createImage("/prg_icon/konf24.png");
            Image image8a = Image.createImage("/prg_icon/minimera24.png");

            mainList.append(langobj.callDefaultCall_DEF, image1a);
            mainList.append(langobj.extensionDefaultCall_DEF, image3a);
            mainList.append(langobj.absentDefaultSetPresence_DEF, image4a);
            mainList.append(langobj.callForwardDefault_DEF, image5a);
            mainList.append(langobj.voiceMailDefaultVoiceMail_DEF, image6a);
            mainList.append(langobj.groupsDefaultGroups_DEF, image7a);
            mainList.append(langobj.genDefaultMinimise_DEF, image8a);

            mainList.addCommand(mainListExitCommand);
            mainList.addCommand(mainListEditCommand);
            mainList.addCommand(mainListaboutMobismaCommand);
            mainList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        rms = null;
        return mainList;

    }

    else if (!conf.xhvtype.equals("")) { // Om det är extrahänvisning

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        mainList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                mainList.setTicker(null);
                mainStatusTicker = new Ticker(" ");
                mainList.setTicker(mainStatusTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                mainList.setTicker(null);
                mainStatusTicker = new Ticker(rms.getAbsentStatus());
                mainList.setTicker(mainStatusTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        // Huvudlistan, förstamenyn visas när prg startat.
        mainListEditCommand = new Command(langobj.genDefaultEdit_DEF,
                                          Command.OK, 1);
        mainListaboutMobismaCommand = new Command(langobj.
                                                  settingsDefaultAbout_DEF,
                                                  Command.OK, 2);
        mainListExitCommand = new Command(langobj.genDefaultExit_DEF,
                                          Command.OK, 3);

        try {

            Image image1a = Image.createImage("/prg_icon/ring24.png");
            Image image3a = Image.createImage("/prg_icon/systemphone24.png");
            Image image4a = Image.createImage("/prg_icon/hanvisa24.png");
            Image image6a = Image.createImage("/prg_icon/rostbrevlada24.png");
            Image image7a = Image.createImage("/prg_icon/konf24.png");
            Image image8a = Image.createImage("/prg_icon/minimera24.png");
            Image logTV = Image.createImage("/prg_icon/TV.png");
            Image log8020 = Image.createImage("/prg_icon/8020.png");

            mainList.append(langobj.callDefaultCall_DEF, image1a);
            mainList.append(langobj.extensionDefaultCall_DEF, image3a);
            // - Hänvisa
               if (!conf.xhvtype.equals("")) {

                   if (conf.xhvtype.equals("TV")) {
                       mainList.append(langobj.absentDefaultSetPresence_DEF, logTV);
                   } else if (conf.xhvtype.equals("8020")) {
                       mainList.append(langobj.absentDefaultSetPresence_DEF,
                                       log8020);
                   }

               } else {
                   mainList.append(langobj.absentDefaultSetPresence_DEF, image4a);
               }

            mainList.append(langobj.voiceMailDefaultVoiceMail_DEF, image6a);
            mainList.append(langobj.groupsDefaultGroups_DEF, image7a);
            mainList.append(langobj.genDefaultMinimise_DEF, image8a);

            mainList.addCommand(mainListExitCommand);
            mainList.addCommand(mainListEditCommand);
            mainList.addCommand(mainListaboutMobismaCommand);
            mainList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        rms = null;
        return mainList;

    }

    return null;
    }
    // --- Sätter och visar listan
    public List getLanguageList() {

        return language_List;
    }

    // --- Sätter och visar listan
    public List getSettingsList() {

        return pbx_List;
    }


    /* ================== ITEM ChoiceGroup ==================================== */

    public ChoiceGroup getCalledButton() throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        calledButtons.deleteAll();

        calledButtons.append(rms.getInternName(76), null);
        calledButtons.append(rms.getInternName(77), null);
        calledButtons.append(rms.getInternName(78), null);
        calledButtons.append(rms.getInternName(79), null);
        calledButtons.append(rms.getInternName(80), null);
        calledButtons.append(rms.getInternName(81), null);
        calledButtons.append(rms.getInternName(82), null);
        calledButtons.append(rms.getInternName(83), null);
        calledButtons.append(rms.getInternName(84), null);
        calledButtons.append(rms.getInternName(85), null);
        calledButtons.append(rms.getInternName(86), null);
        calledButtons.append(rms.getInternName(87), null);
        calledButtons.append(rms.getInternName(88), null);
        calledButtons.append(rms.getInternName(89), null);
        calledButtons.append(rms.getInternName(90), null);
        calledButtons.append(rms.getInternName(91), null);
        calledButtons.append(rms.getInternName(92), null);
        calledButtons.append(rms.getInternName(93), null);
        calledButtons.append(rms.getInternName(94), null);
        calledButtons.append(rms.getInternName(95), null);
        calledButtons.append(rms.getInternName(96), null);
        calledButtons.append(rms.getInternName(97), null);
        calledButtons.append(rms.getInternName(98), null);
        calledButtons.append(rms.getInternName(99), null);

        defaultIndex = calledButtons.append(rms.getInternName(100), null);
        calledButtons.setSelectedIndex(defaultIndex, true);
        calledButtons.setLabel(langobj.dialledCallsDefault_DEF);

        String s1 = rms.getInternName(76);
        String s2 = rms.getInternName(77);
        String s3 = rms.getInternName(78);
        String s4 = rms.getInternName(79);
        String s5 = rms.getInternName(80);

        String s6 = rms.getInternName(81);
        String s7 = rms.getInternName(82);
        String s8 = rms.getInternName(83);
        String s9 = rms.getInternName(84);
        String s10 = rms.getInternName(85);

        String s11 = rms.getInternName(86);
        String s12 = rms.getInternName(87);
        String s13 = rms.getInternName(88);
        String s14 = rms.getInternName(89);
        String s15 = rms.getInternName(90);

        String s16 = rms.getInternName(91);
        String s17 = rms.getInternName(92);
        String s18 = rms.getInternName(93);
        String s19 = rms.getInternName(94);
        String s20 = rms.getInternName(95);

        String s21 = rms.getInternName(96);
        String s22 = rms.getInternName(97);
        String s23 = rms.getInternName(98);
        String s24 = rms.getInternName(99);
        String s25 = rms.getInternName(100);

        calledButtons.set(0, s1, null);
        calledButtons.set(1, s2, null);
        calledButtons.set(2, s3, null);
        calledButtons.set(3, s4, null);
        calledButtons.set(4, s5, null);

        calledButtons.set(5, s6, null);
        calledButtons.set(6, s7, null);
        calledButtons.set(7, s8, null);
        calledButtons.set(8, s9, null);
        calledButtons.set(9, s10, null);

        calledButtons.set(10, s11, null);
        calledButtons.set(11, s12, null);
        calledButtons.set(12, s13, null);
        calledButtons.set(13, s14, null);
        calledButtons.set(14, s15, null);

        calledButtons.set(15, s16, null);
        calledButtons.set(16, s17, null);
        calledButtons.set(17, s18, null);
        calledButtons.set(18, s19, null);
        calledButtons.set(19, s20, null);

        calledButtons.set(20, s21, null);
        calledButtons.set(21, s22, null);
        calledButtons.set(22, s23, null);
        calledButtons.set(23, s24, null);
        calledButtons.set(24, s25, null);

        rms = null;
        return calledButtons;
    }

    public ChoiceGroup getInternCallButton() throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        MModel.InternNumber intern = new InternNumber();

        if (pbxobj.lang_PBX.equals("2")) {

            String s = language.extensionDefaultAddNew_1;
            intern.setInternButtonName(s);

        }

        else if (this.pbxobj.lang_PBX.equals("0") ||
                 this.pbxobj.lang_PBX.equals("1")
                 || this.pbxobj.lang_PBX.equals("3") ||
                 this.pbxobj.lang_PBX.equals("4")
                 || this.pbxobj.lang_PBX.equals("5") ||
                 this.pbxobj.lang_PBX.equals("6")
                 || this.pbxobj.lang_PBX.equals("7") ||
                 this.pbxobj.lang_PBX.equals("8")
                 || this.pbxobj.lang_PBX.equals("9")) {

            String s = language.extensionDefaultAddNew_2;
            intern.setInternButtonName(s);

        }

        internCallButtons.deleteAll();

        internCallButtons.append(rms.getInternName(51), null);
        internCallButtons.append(rms.getInternName(52), null);
        internCallButtons.append(rms.getInternName(53), null);
        internCallButtons.append(rms.getInternName(54), null);
        internCallButtons.append(rms.getInternName(55), null);
        internCallButtons.append(rms.getInternName(56), null);
        internCallButtons.append(rms.getInternName(57), null);
        internCallButtons.append(rms.getInternName(58), null);
        internCallButtons.append(rms.getInternName(59), null);
        internCallButtons.append(rms.getInternName(60), null);
        internCallButtons.append(rms.getInternName(61), null);
        internCallButtons.append(rms.getInternName(62), null);
        internCallButtons.append(rms.getInternName(63), null);
        internCallButtons.append(rms.getInternName(64), null);
        internCallButtons.append(rms.getInternName(65), null);
        internCallButtons.append(rms.getInternName(66), null);
        internCallButtons.append(rms.getInternName(67), null);
        internCallButtons.append(rms.getInternName(68), null);
        internCallButtons.append(rms.getInternName(69), null);
        internCallButtons.append(rms.getInternName(70), null);
        internCallButtons.append(rms.getInternName(71), null);
        internCallButtons.append(rms.getInternName(72), null);
        internCallButtons.append(rms.getInternName(73), null);
        internCallButtons.append(rms.getInternName(74), null);

        internDefaultIndex = internCallButtons.append(rms.getInternName(75), null);
        internCallButtons.setSelectedIndex(internDefaultIndex, true);
        internCallButtons.setLabel(langobj.extensionDefaultCall_DEF);

        String s1 = rms.getInternName(51);
        String s2 = rms.getInternName(52);
        String s3 = rms.getInternName(53);
        String s4 = rms.getInternName(54);
        String s5 = rms.getInternName(55);

        String s6 = rms.getInternName(56);
        String s7 = rms.getInternName(57);
        String s8 = rms.getInternName(58);
        String s9 = rms.getInternName(59);
        String s10 = rms.getInternName(60);

        String s11 = rms.getInternName(61);
        String s12 = rms.getInternName(62);
        String s13 = rms.getInternName(63);
        String s14 = rms.getInternName(64);
        String s15 = rms.getInternName(65);

        String s16 = rms.getInternName(66);
        String s17 = rms.getInternName(67);
        String s18 = rms.getInternName(68);
        String s19 = rms.getInternName(69);
        String s20 = rms.getInternName(70);

        String s21 = rms.getInternName(71);
        String s22 = rms.getInternName(72);
        String s23 = rms.getInternName(73);
        String s24 = rms.getInternName(74);
        String s25 = rms.getInternName(75);

        internCallButtons.set(0, s1, null);
        internCallButtons.set(1, s2, null);
        internCallButtons.set(2, s3, null);
        internCallButtons.set(3, s4, null);
        internCallButtons.set(4, s5, null);

        internCallButtons.set(5, s6, null);
        internCallButtons.set(6, s7, null);
        internCallButtons.set(7, s8, null);
        internCallButtons.set(8, s9, null);
        internCallButtons.set(9, s10, null);

        internCallButtons.set(10, s11, null);
        internCallButtons.set(11, s12, null);
        internCallButtons.set(12, s13, null);
        internCallButtons.set(13, s14, null);
        internCallButtons.set(14, s15, null);

        internCallButtons.set(15, s16, null);
        internCallButtons.set(16, s17, null);
        internCallButtons.set(17, s18, null);
        internCallButtons.set(18, s19, null);
        internCallButtons.set(19, s20, null);

        internCallButtons.set(20, s21, null);
        internCallButtons.set(21, s22, null);
        internCallButtons.set(22, s23, null);
        internCallButtons.set(23, s24, null);
        internCallButtons.set(24, s25, null);

        intern = null;
        rms = null;
        return internCallButtons;

    }

    public ChoiceGroup getEditInternButton() throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String s = this.langobj.extensionDefaultAddNew_DEF;

        editInternButtons.deleteAll();

        editInternButtons.append(rms.getInternName(51), null);
        editInternButtons.append(rms.getInternName(52), null);
        editInternButtons.append(rms.getInternName(53), null);
        editInternButtons.append(rms.getInternName(54), null);
        editInternButtons.append(rms.getInternName(55), null);
        editInternButtons.append(rms.getInternName(56), null);
        editInternButtons.append(rms.getInternName(57), null);
        editInternButtons.append(rms.getInternName(58), null);
        editInternButtons.append(rms.getInternName(59), null);
        editInternButtons.append(rms.getInternName(60), null);
        editInternButtons.append(rms.getInternName(61), null);
        editInternButtons.append(rms.getInternName(62), null);
        editInternButtons.append(rms.getInternName(63), null);
        editInternButtons.append(rms.getInternName(64), null);
        editInternButtons.append(rms.getInternName(65), null);
        editInternButtons.append(rms.getInternName(66), null);
        editInternButtons.append(rms.getInternName(67), null);
        editInternButtons.append(rms.getInternName(68), null);
        editInternButtons.append(rms.getInternName(69), null);
        editInternButtons.append(rms.getInternName(70), null);
        editInternButtons.append(rms.getInternName(71), null);
        editInternButtons.append(rms.getInternName(72), null);
        editInternButtons.append(rms.getInternName(73), null);
        editInternButtons.append(rms.getInternName(74), null);

        editInternButtonIndex = editInternButtons.append(rms.getInternName(75), null);
        editInternButtons.setSelectedIndex(editInternButtonIndex, true);
        editInternButtons.setLabel(langobj.genDefaultEdit_DEF);

        String s1 = rms.getInternName(51);
        String s2 = rms.getInternName(52);
        String s3 = rms.getInternName(53);
        String s4 = rms.getInternName(54);
        String s5 = rms.getInternName(55);

        String s6 = rms.getInternName(56);
        String s7 = rms.getInternName(57);
        String s8 = rms.getInternName(58);
        String s9 = rms.getInternName(59);
        String s10 = rms.getInternName(60);

        String s11 = rms.getInternName(61);
        String s12 = rms.getInternName(62);
        String s13 = rms.getInternName(63);
        String s14 = rms.getInternName(64);
        String s15 = rms.getInternName(65);

        String s16 = rms.getInternName(66);
        String s17 = rms.getInternName(67);
        String s18 = rms.getInternName(68);
        String s19 = rms.getInternName(69);
        String s20 = rms.getInternName(70);

        String s21 = rms.getInternName(71);
        String s22 = rms.getInternName(72);
        String s23 = rms.getInternName(73);
        String s24 = rms.getInternName(74);
        String s25 = rms.getInternName(75);

        editInternButtons.set(0, s1, null);
        editInternButtons.set(1, s2, null);
        editInternButtons.set(2, s3, null);
        editInternButtons.set(3, s4, null);
        editInternButtons.set(4, s5, null);

        editInternButtons.set(5, s6, null);
        editInternButtons.set(6, s7, null);
        editInternButtons.set(7, s8, null);
        editInternButtons.set(8, s9, null);
        editInternButtons.set(9, s10, null);

        editInternButtons.set(10, s11, null);
        editInternButtons.set(11, s12, null);
        editInternButtons.set(12, s13, null);
        editInternButtons.set(13, s14, null);
        editInternButtons.set(14, s15, null);

        editInternButtons.set(15, s16, null);
        editInternButtons.set(16, s17, null);
        editInternButtons.set(17, s18, null);
        editInternButtons.set(18, s19, null);
        editInternButtons.set(19, s20, null);

        editInternButtons.set(20, s21, null);
        editInternButtons.set(21, s22, null);
        editInternButtons.set(22, s23, null);
        editInternButtons.set(23, s24, null);
        editInternButtons.set(24, s25, null);

        rms = null;
        return editInternButtons;
    }


    public void itemStateChanged(Item item) {

        MModel.InternNumber intern = new InternNumber();
        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        if (item == calledButtons) {

            // ID int-värde av valt nummer 0 - 24 av id-nummren
            int ID = calledButtons.getSelectedIndex();

            // Sätter rätt id för hämta rätt uppringt nummer, tex plats 0 = 76 i DB.
            String idNumber = null;
            try {
                idNumber = intern.getPerson(ID);
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            System.out.println("Returvärde person >> " + idNumber);

            // Om ID platsen != '0' så ring samtal
            if (!idNumber.equals("0")) {

                /* Plockar ut index, datum och tid, sorterar bort.
                 Använder sedan siffrorna getPersonIDs för att ringa ett samtal */
                String getPersonIDs = calledButtons.getString(calledButtons.
                        getSelectedIndex());
                String calledNumber = new String(getPersonIDs);
                MModel.SortClass sort = new SortClass();
                calledNumber = sort.sortCharToDigits(calledNumber);

                int index = 0;
                index = calledNumber.indexOf('\n');

                System.out.println("PLATS substring >>>>>>>>>>>>> " + index);

                String callNumber = calledNumber.substring(0, index);
                callNumber = sort.checkCallNumber(callNumber);

                try {
                    if (rms.getPBXType().equals("3")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + callNumber;

                    } else if (rms.getPBXType().equals("5")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + "*47"
                                                + pbxobj.extensionNumber_PBX +
                                                pbxobj.pinCodeNumber_PBX +
                                                callNumber;

                    }
                } catch (RecordStoreNotOpenException ex2) {
                } catch (InvalidRecordIDException ex2) {
                } catch (RecordStoreException ex2) {
                }

                platformRequest();

                System.out.println("INDEX ID >> " + ID);
                System.out.println("PersonID  >> " + getPersonIDs);
                System.out.println("Samtalslista (Ringer till)  >> " +
                                   callNumber);

                sort = null;
            }

        } else if (item == internCallButtons) {

            // ID int-värde av valt nummer 0 - 24 av id-nummren
            int ID = internCallButtons.getSelectedIndex();

            // Sätter rätt id för hämta rätt uppringt nummer, tex plats 0 = 76 i DB.
            String idNumber = null;
            try {
                idNumber = intern.getInternPerson(ID);
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            System.out.println("Returvärde person >> " + idNumber);

            // Om ID platsen == '0' så editera.
            if (idNumber.equals("0") || idNumber.equals(null) ||
                idNumber.equals(language.extensionDefaultAddNew_1) ||
                idNumber.equals(language.extensionDefaultAddNew_2)) {

                Display.getDisplay(this).setCurrent(getCallEditForm());
                ID = intern.getInternID(ID);
                this.IDInternNumber = ID;

            }

            // Om ID platsen != '0' så ring samtal
            else if (!idNumber.equals("0")) {

                /* Plockar ut index, datum och tid, sorterar bort.
                 Använder sedan siffrorna getPersonIDs för att ringa ett samtal */
                String getPersonIDs = internCallButtons.getString(
                        internCallButtons.getSelectedIndex());
                String callInternNumber = new String(getPersonIDs);
                MModel.SortClass sort = new SortClass();
                callInternNumber = sort.sortCharToDigits(callInternNumber);

                try {
                    if (rms.getPBXType().equals("3")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX +
                                                callInternNumber;

                    } else if (rms.getPBXType().equals("5")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + "*47"
                                                + pbxobj.extensionNumber_PBX +
                                                pbxobj.pinCodeNumber_PBX +
                                                callInternNumber;

                    }
                } catch (RecordStoreNotOpenException ex2) {
                } catch (InvalidRecordIDException ex2) {
                } catch (RecordStoreException ex2) {
                }

                platformRequest();

                System.out.println("INDEX ID >> " + ID);
                System.out.println("PersonID  >> " + getPersonIDs);
                System.out.println(
                        "Samtalslista (Ringer till Internnummer)  >> " +
                        callInternNumber);

                sort = null;
            }

        } else if (item == editInternButtons) {

            // ID int-värde av valt nummer 0 - 19 av id-nummren
            int ID = editInternButtons.getSelectedIndex();

            // Sätter rätt id för hämta rätt anknytning, tex plats 0 = 51 i DB.
            String person = null;
            try {
                person = intern.getInternPerson(ID);
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            System.out.println("Returvärde person >> " + person);
            MModel.SortClass sort = new SortClass();
            String name = sort.sortDigitsToCharacter(person);
            String number = sort.sortCharToDigits(person);
            sort = null;

            internCallEditRenameNameTextField.setString(name.trim());
            internCallEditRenameExtensionTextField.setString(number.trim());

            // Om ID platsen == '0' i DB öppna edit-formen | editera
            Display.getDisplay(this).setCurrent(getCallRenameEditForm());
            ID = intern.getInternID(ID);
            this.IDInternNumber = ID;

        }
        rms = null;
        intern = null;
    }

    /* ===== Huvudklassens tre metoder, main osv. ========================== */

    public void startApp() {

        startSplash();

    }


    public void pauseApp() {

    }

    public void destroyApp(boolean unconditional) {

    }

    // --- Sätter java.lang.string
    public String toString(String b) {

        String s = b;

        return s;
    }

    // --- Ring metod.
    public void platformRequest() {

        Thread platForm = new Thread() {

            public void run() {

                try {

                    platformRequest("tel:" + Call_This_Number);

                    Call_This_Number = "";

                } catch (Exception ex) {
                }

            }
        };
        platForm.start();

    }

    public void platformRequestSetPresence() {

        Thread platFormPresence = new Thread() {

            public void run() {

                try {

                    platformRequest("tel:" + Presence_This_String);

                    Presence_This_String = "";

                } catch (Exception ex) {
                }

            }
        };
        platFormPresence.start();

    }


    /* ===== List-Kommandon =========================================== */

    public void commandAction(Command c, Displayable d) {
        Thread th = new Thread(this);
        thCmd = c;
        th.start();
        // Om det 'ÄR' tilläggs hänvisning ta bort vidarekoppla från huvudmeny
        if (!conf.xhvtype.equals("")) {
            if (d.equals(mainList)) {
                if (c == List.SELECT_COMMAND) {
                    if (d.equals(mainList)) {
                        switch (((List) d).getSelectedIndex()) {

                        case 0: // Ring, (uppringda nummer)

                            Display.getDisplay(this).setCurrent(
                                    getCallPhoneForm());

                            break;

                        case 1: // Ring anknytning

                            Display.getDisplay(this).setCurrent(
                                    getInternCallPhoneForm());

                            break;

                        case 2: // Hänvisa

                            this.xhv = new Check_Xhv();
                                    this.absentList = this.xhv.getxHvList();
                                    BackCommandAbsentList = new Command(langobj.genDefaultBack_DEF, Command.BACK, 2);
                                    absentList.addCommand(BackCommandAbsentList);
                                    absentList.setCommandListener(this);
                                    xhv = null;
                                    Display.getDisplay(this).setCurrent(this.absentList);


                            break;

                        case 3: // Röstbrevlådan

                            DataBase_RMS rms = null;
                            try {
                                rms = new DataBase_RMS();
                            } catch (IOException ex1) {
                            } catch (RecordStoreNotOpenException ex1) {
                            } catch (InvalidRecordIDException ex1) {
                            } catch (RecordStoreException ex1) {
                            }

                            try {
                                if (rms.getPBXType().equals("3")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX +
                                            this.pbxobj.
                                            voiceMailSwitchboard_PBX + "p" +
                                            "#6*" + pbxobj.extensionNumber_PBX;

                                } else if (rms.getPBXType().equals("5")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX + "*47" +
                                            pbxobj.extensionNumber_PBX +
                                            pbxobj.pinCodeNumber_PBX +
                                            this.pbxobj.
                                            voiceMailSwitchboard_PBX +
                                            "p" + "#6*" +
                                            pbxobj.extensionNumber_PBX;

                                }
                            } catch (RecordStoreNotOpenException ex2) {
                            } catch (InvalidRecordIDException ex2) {
                            } catch (RecordStoreException ex2) {
                            }

                            platformRequestSetPresence();

                            rms = null;

                            break;

                        case 4: // Grupper

                            Display.getDisplay(this).setCurrent(groupList);

                            break;

                        case 5: // Minimera
                            Display.getDisplay(this).setCurrent(null);
                            break;

                        }
                    }

                }

            }
            // // Om det 'INTE ÄR' tilläggs hänvisning, använd 'orginal huvudmeny'
        } else if (conf.xhvtype.equals("")) {

            if (d.equals(mainList)) {
                if (c == List.SELECT_COMMAND) {
                    if (d.equals(mainList)) {
                        switch (((List) d).getSelectedIndex()) {

                        case 0: // Ring, (uppringda nummer)

                            Display.getDisplay(this).setCurrent(
                                    getCallPhoneForm());

                            break;

                        case 1: // Ring anknytning

                            Display.getDisplay(this).setCurrent(
                                    getInternCallPhoneForm());

                            break;

                        case 2: // Hänvisa

                            Display.getDisplay(this).setCurrent(getAbsentList());

                            break;

                        case 3: // Vidarekoppla

                            Display.getDisplay(this).setCurrent(
                                    getCallForwardList());

                            break;

                        case 4: // Röstbrevlådan

                            DataBase_RMS rms = null;
                            try {
                                rms = new DataBase_RMS();
                            } catch (IOException ex1) {
                            } catch (RecordStoreNotOpenException ex1) {
                            } catch (InvalidRecordIDException ex1) {
                            } catch (RecordStoreException ex1) {
                            }

                            try {
                                if (rms.getPBXType().equals("3")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX +
                                            this.pbxobj.
                                            voiceMailSwitchboard_PBX + "p" +
                                            "#6*" + pbxobj.extensionNumber_PBX;

                                } else if (rms.getPBXType().equals("5")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX + "*47" +
                                            pbxobj.extensionNumber_PBX +
                                            pbxobj.pinCodeNumber_PBX +
                                            this.pbxobj.
                                            voiceMailSwitchboard_PBX +
                                            "p" + "#6*" +
                                            pbxobj.extensionNumber_PBX;

                                }
                            } catch (RecordStoreNotOpenException ex2) {
                            } catch (InvalidRecordIDException ex2) {
                            } catch (RecordStoreException ex2) {
                            }

                            platformRequestSetPresence();

                            rms = null;

                            break;

                        case 5: // Grupper

                            Display.getDisplay(this).setCurrent(groupList);

                            break;

                        case 6: // Minimera
                            Display.getDisplay(this).setCurrent(null);
                            break;

                        }
                    }

                }

            }

        }

        // Vidarekoppla med TV **********************************
        if (d.equals(tv_CallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(tv_CallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // alla samtal

                        Display.getDisplay(this).setCurrent(
                                getTvCallForwardForm());
                        sendSMSStatus = "in";
                        setTVCFPrefix = "a";
                        String allCF_TV = langobj.callForwardDefault_DEF + " " + langobj.callForwardDefaultAllCalls_DEF;
                        this.setSMSStatusString = allCF_TV;

                        break;

                    case 1: // extern samtal

                        Display.getDisplay(this).setCurrent(
                                getTvCallForwardForm());
                        sendSMSStatus = "in";
                        setTVCFPrefix = "e";
                        String exCF_TV = langobj.callForwardDefault_DEF + " " + langobj.callForwardDefaultExternCalls_DEF;
                        this.setSMSStatusString = exCF_TV;

                        break;

                    case 2: // internsamtal

                        Display.getDisplay(this).setCurrent(
                                getTvCallForwardForm());
                        sendSMSStatus = "in";
                        setTVCFPrefix = "i";
                        String inCF_TV = langobj.callForwardDefault_DEF + " " + langobj.callForwardDefaultInternCalls_DEF;
                        this.setSMSStatusString = inCF_TV;

                        break;

                    case 3: // ta bort vidarekoppling

                        Thread t_remove = new Thread() {


                            public void run() {

                                String tvRemoveCF = "in::n:";
                                sendSMSStatus = tvRemoveCF;
                                setSMSDisplay();

                            }
                        };
                        t_remove.start();

                                String remove = langobj.callForwardDefaultRemove_DEF + " " + langobj.callForwardDefault_DEF;
                                setSMSStatusString = remove;
                                setAbsentStatusString(this.setSMSStatusString);

                                // Skapar nytt databasobjekt
                                DataBase_RMS rms = null;
                                try {
                                    rms = new DataBase_RMS();
                                } catch (IOException ex7) {
                                } catch (RecordStoreNotOpenException ex7) {
                                } catch (InvalidRecordIDException ex7) {
                                } catch (RecordStoreException ex7) {
                                }

                                // Sparar senast satta statuset i databasen.
                                rms.setAbsentStatus(setAbsentStatusTime);
                                rms = null;


                        break;

                    }
                }

            }

        }

        // Vidarekoppling *****************************************

        if (d.equals(callForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(callForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardList());

                        break;

                    case 1:

                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());

                        break;

                    case 2:

                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());

                        break;

                    }
                }

            }

        }

        if (d.equals(allCallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(allCallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        rms.setAbsentStatus("0");

                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "100";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "100";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardList());
                        platformRequestSetPresence();

                        rms = null;
                        break;

                    case 1:

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }

                        String setPresenceName = langobj.callForwardDefault_DEF +
                                                 " " +
                                                 langobj.
                                                 callForwardDefaultAllCalls_DEF +
                                                 " " +
                                                 langobj.
                                callForwardDefaultDontDisturb_DEF + " ";
                        setAbsentStatusStringOrginal(setPresenceName);

                        try {

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "101";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "101";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardList());
                        platformRequestSetPresence();

                        rms = null;
                        break;

                    case 2:
                        this.allCallForwardRename = "1";
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardForm());

                        break;

                    case 3:
                        this.allCallForwardRename = "2";
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardForm());

                        break;

                    case 4:
                        this.allCallForwardRename = "3";
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardForm());

                        break;

                    case 5:
                        this.allCallForwardRename = "4";
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(externCallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(externCallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex1) {
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }
                        try {
                            rms.setAbsentStatus("0");

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "110";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "110";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());
                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 1:

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultExternCalls_DEF +
                                    " " +
                                    langobj.
                                    callForwardDefaultDontDisturb_DEF + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "111";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "111";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());
                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 2:

                        this.externCallForwardRename = "1";
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardForm());

                        break;

                    case 3:

                        this.externCallForwardRename = "2";
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardForm());

                        break;

                    case 4:

                        this.externCallForwardRename = "3";
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardForm());

                        break;

                    case 5:

                        this.externCallForwardRename = "4";
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(internCallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(internCallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex1) {
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }
                        try {
                            rms.setAbsentStatus("0");

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "120";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "120";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());
                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 1:

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }

                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultInternCalls_DEF +
                                    " " +
                                    langobj.
                                    callForwardDefaultDontDisturb_DEF + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "121";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "121";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());

                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 2:

                        this.internCallForwardRename = "1";
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardForm());

                        break;

                    case 3:

                        this.internCallForwardRename = "2";
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardForm());

                        break;

                    case 4:

                        this.internCallForwardRename = "3";
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardForm());

                        break;

                    case 5:

                        this.internCallForwardRename = "4";
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardForm());

                        break;

                    }
                }

            }

        }

        // Hänvisning *********************************************
        if (!conf.xhvtype.equals("")) { // Om det ÄR tilläggs hänvisning
            if (conf.xhvtype.equals("TV")) { // Använd Totalview som hänvisning

                    if (d.equals(absentList)) {
                        if (c == List.SELECT_COMMAND) {
                            if (d.equals(absentList)) {
                                switch (((List) d).getSelectedIndex()) {

                                case 0: // - Inne

                                    Thread t_in = new Thread() {


                                        public void run() {

                                            String in = "in::n:";
                                            sendSMSStatus = in;
                                            setSMSDisplay();
                                        }
                                    };
                                    t_in.start();

                                    String inne = langobj.inside_DEF;
                                    setSMSStatusString = inne;
                                    setAbsentStatusString(this.setSMSStatusString);

                                    // Skapar nytt databasobjekt
                                    DataBase_RMS rms = null;
                                    try {
                                        rms = new DataBase_RMS();
                                    } catch (IOException ex7) {
                                    } catch (RecordStoreNotOpenException ex7) {
                                    } catch (InvalidRecordIDException ex7) {
                                    } catch (RecordStoreException ex7) {
                                    }

                                    // Sparar senast satta statuset i databasen.
                                    rms.setAbsentStatus(setAbsentStatusTime);
                                    rms = null;

                                    break;

                                case 1: // - Upptagen
                                    this.tv_set_hhmm_mmdd = "1";
                                    Display.getDisplay(this).setCurrent(getTVForm());
                                    sendSMSStatus = "bu";
                                    String busy = langobj.callForwardDefaultBusy_DEF;
                                    this.setSMSStatusString = busy;

                                    break;

                                case 2: // - Lunch
                                    this.tv_set_hhmm_mmdd = "1";
                                    Display.getDisplay(this).setCurrent(getTVForm());
                                    sendSMSStatus = "Lu";
                                    String lunch = langobj.tv_lunch_DEF;
                                    this.setSMSStatusString = lunch;

                                    break;

                                case 3: // - Privat
                                    this.tv_set_hhmm_mmdd = "1";
                                    Display.getDisplay(this).setCurrent(getTVForm());
                                    sendSMSStatus = "pr";
                                    String privat = langobj.private_DEF;
                                    this.setSMSStatusString = privat;

                                    break;

                                case 4: // - Sjuk

                                    Thread t_sick = new Thread() {


                                        public void run() {

                                            String sick = "Si::" + "a" +
                                                    pbxobj.voiceMailSwitchboard_PBX +
                                                    ":";
                                            sendSMSStatus = sick;
                                            setSMSDisplay();
                                        }
                                    };
                                    t_sick.start();

                                    String sjuk = langobj.absentIllness_DEF;
                                    setSMSStatusString = sjuk;
                                    setAbsentStatusString(this.setSMSStatusString);

                                    // Skapar nytt databasobjekt

                                    rms = null;
                                    try {
                                        rms = new DataBase_RMS();
                                    } catch (IOException ex5) {
                                    } catch (RecordStoreNotOpenException ex5) {
                                    } catch (InvalidRecordIDException ex5) {
                                    } catch (RecordStoreException ex5) {
                                    }

                                    // Sparar senast satta statuset i databasen.
                                    rms.setAbsentStatus(setAbsentStatusTime);
                                    rms = null;


                                    break;

                                case 5: // - Semester
                                    this.tv_set_hhmm_mmdd = "2";
                                    Display.getDisplay(this).setCurrent(getTVForm());
                                    sendSMSStatus = "hol";
                                    String semester = langobj.absentVacation_DEF;
                                    this.setSMSStatusString = semester;

                                    break;

                                case 6: // - Vidarekoppla, Undermeny

                                    Display.getDisplay(this).setCurrent(
                                            getTVCallForwardList());

                                    break;

                                case 7: // - Status Idag

                                    Display.getDisplay(this).setCurrent(
                                            getTvStatusForm());
                                    sendSMSStatus = "u:";
                                    tvStatusPrefix = "";
                                    String todayStatus = langobj.statusToday_DEF;
                                    this.setSMSStatusString = todayStatus;

                                    break;

                                case 8: // - Status Idag/Imorgon

                                    Display.getDisplay(this).setCurrent(
                                            getTvStatusForm());
                                    sendSMSStatus = "u:";
                                    tvStatusPrefix = "+";
                                    String tomorrowStatus = langobj.statusTodayTomorrow_DEF;
                                    this.setSMSStatusString = tomorrowStatus;

                                    break;

                                case 9: // - Status Datum

                                    Display.getDisplay(this).setCurrent(
                                            getTvStatusForm());
                                    sendSMSStatus = "u:";
                                    tvStatusPrefix = "";
                                    String dateStatus = langobj.statusDate_DEF;
                                    this.setSMSStatusString = dateStatus;

                                    break;

                                case 10: // - Status Kontakt

                                    Display.getDisplay(this).setCurrent(
                                            getTvStatusForm());
                                    sendSMSStatus = "u:";
                                    tvStatusPrefix = "?";
                                    String contactStatus = langobj.statusContact_DEF;
                                    this.setSMSStatusString = contactStatus;

                                    break;

                                }
                            }

                        }

                    }

                }


        }
        else if (conf.xhvtype.equals("8020")) { // Använd 8020 som hänvisning

                if (d.equals(absentList)) {
                    if (c == List.SELECT_COMMAND) {
                        if (d.equals(absentList)) {
                            switch (((List) d).getSelectedIndex()) {

                            case 0: //
                                break;

                            case 1: //

                                break;

                            case 2: //

                                break;

                            case 3: //

                                break;

                            case 4: //

                                break;

                            case 5: //

                                break;

                            case 6: //

                                break;

                            case 7: //

                                break;

                            case 8: //

                                break;

                            case 9: //

                                break;

                            }
                        }

                    }

                }

            } else { // PBX's ORGINAL hänvisning

            if (d.equals(absentList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(absentList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Remove Presence

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex1) {
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }
                        rms.setAbsentStatus("0");
                        Display.getDisplay(this).setCurrent(getAbsentList());
                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "500#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "500#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;
                        break;

                    case 1: // Will return soon

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }
                        setAbsentStatusStringOrginal(langobj.
                                              absentDeafaultWillReturnSoon_DEF);
                        Display.getDisplay(this).setCurrent(getAbsentList());
                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "501#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "501#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 2: // Gone Home

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }
                        setAbsentStatusStringOrginal(langobj.absentDefaultGoneHome_DEF);
                        Display.getDisplay(this).setCurrent(getAbsentList());
                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "502#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "502#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 3: // At Ext.

                        Display.getDisplay(this).setCurrent(getAtExtForm());

                        break;

                    case 4: // Back At

                        Display.getDisplay(this).setCurrent(getBackATForm());

                        break;

                    case 5: // Out Until

                        Display.getDisplay(this).setCurrent(getOutForm());

                        break;

                    case 6: // In a Meeting

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }
                        setAbsentStatusStringOrginal(langobj.
                                              absentDefaultInAMeeting_DEF);
                        Display.getDisplay(this).setCurrent(getAbsentList());
                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "506#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "506#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 7: // Systemattribut 1, ny hänvisning 'rename'

                        int p = absentList.getSelectedIndex();
                        System.out.println("getselected index >> " + p);

                        if (p == 7 && this.editAbsentDTMF_1.equals("0")) {
                            String s1 = "1";
                            Display.getDisplay(this).setCurrent(
                                    getEditAbsentForm(s1));
                            this.editNEWAbsent = "1";

                        }

                        else {

                            rms = null;
                            try {
                                rms = new MDataStore.DataBase_RMS();
                            } catch (InvalidRecordIDException ex4) {
                            } catch (RecordStoreNotOpenException ex4) {
                            } catch (RecordStoreException ex4) {
                            } catch (IOException ex4) {
                            }
                            String absentName_1 = null;
                            try {
                                absentName_1 = rms.getEditAbsentName_1();
                            } catch (RecordStoreNotOpenException ex6) {
                            } catch (InvalidRecordIDException ex6) {
                            } catch (RecordStoreException ex6) {
                            }

                            setAbsentStatusStringOrginal(absentName_1);
                            Display.getDisplay(this).setCurrent(getAbsentList());

                            try {
                                if (rms.getPBXType().equals("3")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX +
                                            this.pbxobj.precode_PBX +
                                            "507#";

                                } else if (rms.getPBXType().equals("5")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX + "*47"
                                            + pbxobj.extensionNumber_PBX +
                                            pbxobj.pinCodeNumber_PBX +
                                            this.pbxobj.precode_PBX + "507#";

                                }
                            } catch (RecordStoreNotOpenException ex2) {
                            } catch (InvalidRecordIDException ex2) {
                            } catch (RecordStoreException ex2) {
                            }

                            platformRequestSetPresence();

                            rms = null;

                        }

                        break;

                    case 8: // Systemattribut 2, ny hänvisning 'rename'

                        int pp = absentList.getSelectedIndex();
                        System.out.println("getselected index >> " + pp);

                        if (pp == 8 && this.editAbsentDTMF_2.equals("0")) {
                            String s2 = "2";
                            Display.getDisplay(this).setCurrent(
                                    getEditAbsentForm(s2));
                            this.editNEWAbsent = "2";
                        }

                        else {

                            rms = null;
                            try {
                                rms = new MDataStore.DataBase_RMS();
                            } catch (InvalidRecordIDException ex4) {
                            } catch (RecordStoreNotOpenException ex4) {
                            } catch (RecordStoreException ex4) {
                            } catch (IOException ex4) {
                            }
                            String absentName_2 = null;
                            try {
                                absentName_2 = rms.getEditAbsentName_2();
                            } catch (RecordStoreNotOpenException ex6) {
                            } catch (InvalidRecordIDException ex6) {
                            } catch (RecordStoreException ex6) {
                            }

                            setAbsentStatusStringOrginal(absentName_2);
                            Display.getDisplay(this).setCurrent(getAbsentList());

                            try {
                                if (rms.getPBXType().equals("3")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX +
                                            this.pbxobj.precode_PBX +
                                            "508#";

                                } else if (rms.getPBXType().equals("5")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX + "*47"
                                            + pbxobj.extensionNumber_PBX +
                                            pbxobj.pinCodeNumber_PBX +
                                            this.pbxobj.precode_PBX + "508#";

                                }
                            } catch (RecordStoreNotOpenException ex2) {
                            } catch (InvalidRecordIDException ex2) {
                            } catch (RecordStoreException ex2) {
                            }

                            platformRequestSetPresence();

                            rms = null;

                        }

                        break;

                    case 9: // Personligt, ny hänvisning 'rename'

                        int ppp = absentList.getSelectedIndex();
                        System.out.println("getselected index >> " + ppp);

                        if (ppp == 9 && this.editAbsentDTMF_3.equals("0")) {
                            String s3 = "3";
                            Display.getDisplay(this).setCurrent(
                                    getEditAbsentForm(s3));
                            this.editNEWAbsent = "3";
                        }

                        else {

                            rms = null;
                            try {
                                rms = new MDataStore.DataBase_RMS();
                            } catch (InvalidRecordIDException ex4) {
                            } catch (RecordStoreNotOpenException ex4) {
                            } catch (RecordStoreException ex4) {
                            } catch (IOException ex4) {
                            }
                            String absentName_3 = null;
                            try {
                                absentName_3 = rms.getEditAbsentName_3();
                            } catch (RecordStoreNotOpenException ex6) {
                            } catch (InvalidRecordIDException ex6) {
                            } catch (RecordStoreException ex6) {
                            }

                            setAbsentStatusStringOrginal(absentName_3);
                            Display.getDisplay(this).setCurrent(getAbsentList());

                            try {
                                if (rms.getPBXType().equals("3")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX +
                                            this.pbxobj.precode_PBX +
                                            "509#";

                                } else if (rms.getPBXType().equals("5")) {

                                    this.Presence_This_String =
                                            pbxobj.switchBoardNumber_PBX +
                                            pbxobj.setP_PBX + "*47"
                                            + pbxobj.extensionNumber_PBX +
                                            pbxobj.pinCodeNumber_PBX +
                                            this.pbxobj.precode_PBX + "509#";

                                }
                            } catch (RecordStoreNotOpenException ex2) {
                            } catch (InvalidRecordIDException ex2) {
                            } catch (RecordStoreException ex2) {
                            }

                            platformRequestSetPresence();

                            rms = null;

                        }

                        break;

                    }
                }

            }

        }


        }

        if (d.equals(absentEditList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(absentEditList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0:

                        String s1 = "1";
                        Display.getDisplay(this).setCurrent(getEditAbsentForm(
                                s1));
                        this.editNEWAbsent = "1";
                        System.out.println("Seeeeeeeeeeeeeeeeee >>>>> " +
                                           editNEWAbsent);

                        break;

                    case 1:

                        String s2 = "2";
                        Display.getDisplay(this).setCurrent(getEditAbsentForm(
                                s2));
                        this.editNEWAbsent = "2";
                        System.out.println("Seeeeeeeeeeeeeeeeee >>>>> " +
                                           editNEWAbsent);

                        break;

                    case 2:

                        String s3 = "3";
                        Display.getDisplay(this).setCurrent(getEditAbsentForm(
                                s3));
                        this.editNEWAbsent = "3";
                        System.out.println("Seeeeeeeeeeeeeeeeee >>>>> " +
                                           editNEWAbsent);

                        break;

                    }
                }

            }

        }

        if (d.equals(pbx_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(pbx_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Access PBX

                        Display.getDisplay(this).setCurrent(pbx_List_type);

                        break;

                    case 1: // Redigera röstbrevlådan

                        try {
                            Display.getDisplay(this).setCurrent(
                                    getPBXVoiceEditForm());
                        } catch (InvalidRecordIDException ex13) {
                        } catch (RecordStoreNotOpenException ex13) {
                        } catch (RecordStoreException ex13) {
                        }

                        break
                                ;

                    case 2: // Redigera attribut

                        Display.getDisplay(this).setCurrent(getAbsentEditList());

                        break;

                    case 3: // Redigera tecken

                        try {
                            Display.getDisplay(this).setCurrent(getPreEditForm());
                        } catch (InvalidRecordIDException ex3) {
                        } catch (RecordStoreNotOpenException ex3) {
                        } catch (RecordStoreException ex3) {
                        }

                        break
                                ;

                    case 4: // Språk

                        Display.getDisplay(this).setCurrent(getLanguageList());

                        break;

                    }
                }

            }

        }
        if (d.equals(pbx_List_type)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(pbx_List_type)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        Display.getDisplay(this).setCurrent(linePrefix_List);

                        break;

                    case 1:

                        Display.getDisplay(this).setCurrent(
                                getPINCodeSettingsForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(linePrefix_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(linePrefix_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // AutoAccess med linjeprefix

                        Display.getDisplay(this).setCurrent(
                                getAutoAccessSettingForm());

                        break;

                    case 1: // AutoAccess utan linjeprefix

                        Display.getDisplay(this).setCurrent(
                                getAutoAccessNOPrefixForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(groupList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(groupList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0:

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex1) {
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "361*";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "361*";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 1:

                        rms = null;
                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }

                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "360*";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "360*";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                        break;

                    case 2: // inlogg grupp

                        System.out.println("Inlogg grupp");
                        Display.getDisplay(this).setCurrent(getLoginGroupForm());

                        break;

                    case 3: // urlogg grupp

                        System.out.println("Urlogg grupp");
                        Display.getDisplay(this).setCurrent(getLogoffGroupForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(language_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(language_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // English >> Default
                        this.pbxobj.lang_PBX = "2";
                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 1: // Språk_2
                        this.pbxobj.lang_PBX = pbxobj.iconNumber_PBX;
                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    }
                }

            }

        }

    }

    /* ===== Knapp-Kommandon =========================================== */

    public void run() {
        try {
            if (thCmd.getCommandType() == Command.EXIT) {
                notifyDestroyed();

            }

            /* ------- Send - Commands --------------------- */

            else if (thCmd == licensYESCommand) {

                notifyDestroyed();

            }

            else if (thCmd == tvStatusSendCommand) {

                String inputUser = tvStatusTextField.getString().trim();

                if (!inputUser.equals("")) {

                    if (absentList.getSelectedIndex() == 9) {

                        String setDate = tvStatusDateTextField.getString().trim();

                        inputUser = inputUser + setDate;

                    }

                    String SENDmessage = sendSMSStatus + inputUser +
                                         tvStatusPrefix;

                    sendSMSStatus = SENDmessage;
                    setSMSDisplay();
                    // setSms(this.sendSMSStatus);
                    setAbsentStatusString(this.setSMSStatusString);

                    // Skapar nytt databasobjekt
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    // Sparar senast satta statuset i databasen.
                    rms.setAbsentStatus(setAbsentStatusTime);
                    rms = null;

                    tvStatusTextField.setString("");

                } else {

                    Display.getDisplay(this).setCurrent(getTvStatusForm());
                }
            }

            else if (thCmd == tvFormSendCallForwardCommand) {

                String setNumber = tvFormNumberTextField.getString();

                if (!setNumber.equals("")) {

                    String setMessage = tvFormMessageTextField.getString();

                    String SENDmessage = sendSMSStatus + "::" + setTVCFPrefix +
                                         setNumber + ":" + setMessage;

                    sendSMSStatus = SENDmessage;
                    setSMSDisplay();
                    // setSms(this.sendSMSStatus);
                    setAbsentStatusString(this.setSMSStatusString);

                    // Skapar nytt databasobjekt
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    // Sparar senast satta statuset i databasen.
                    rms.setAbsentStatus(setAbsentStatusTime);
                    rms = null;

                    tvFormNumberTextField.setString("");
                    tvFormMessageTextField.setString("");

                } else {

                    Display.getDisplay(this).setCurrent(getTvCallForwardForm());
                    tvFormMessageTextField.setString("");
                }

            } else if (thCmd == tvSendCommand) {

                String setMinute = tvTimeDateTextField.getString();
                String setDate = setMinute;
                setMinute = getMinutes(setMinute);

                if (!setMinute.equals("")) {

                    Display.getDisplay(this).setCurrent(getMainList());

                    String setmMinuteDay = "";
                    String setMessage = tvMessageTextField.getString();

                    if (sendSMSStatus.equals("hol")) {

                        MModel.Date_Time date = new Date_Time();

                        String setDateMonth = setDate.substring(0, 2);
                        setDateMonth = date.setBackMounthDigit(setDateMonth);

                        String setDateDay = setDate.substring(2, 4);
                        setDateDay = date.setBackDay(setDateDay);

                        // Deklarerar nya variabler med INT-värden.
                        Integer month = new Integer(0);
                        Integer day = new Integer(0);

                        // Konverterar 'strängar' till INT-värden.
                        int in_month = month.parseInt(setDateMonth);
                        int in_day = day.parseInt(setDateDay);

                        int result = date.getDayOFYear(in_month, in_day);
                        date = null;

                        // konvertera int till string...
                        String sendDate = Integer.toString(result);
                        setMinute = sendDate;
                        setmMinuteDay = "d";

                    } else {
                        setmMinuteDay = "m";
                    }

                    String SENDmessage = sendSMSStatus + ":" + setMinute +
                                         setmMinuteDay + ":" + "a" +
                                         pbxobj.voiceMailSwitchboard_PBX + ":" +
                                         setMessage;

                    sendSMSStatus = SENDmessage;
                    setSMSDisplay();
                    // setSms(this.sendSMSStatus);
                    setAbsentStatusString(this.setSMSStatusString);

                    // Skapar nytt databasobjekt
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    // Sparar senast satta statuset i databasen.
                    rms.setAbsentStatus(setAbsentStatusTime);
                    rms = null;

                    tvTimeDateTextField.setString("");
                    tvMessageTextField.setString("");

                } else {

                    Display.getDisplay(this).setCurrent(getTVForm());
                }

            } else if (thCmd == confirmSENDSMSYESCommand) {

                if (!conf.xhvtype.equals("")) {

                    Display.getDisplay(this).setCurrent(getMainList());
                    this.xhv = new Check_Xhv();
                    this.absentList = this.xhv.getxHvList();
                    BackCommandAbsentList = new Command(langobj.
                            genDefaultBack_DEF, Command.BACK, 2);
                    absentList.addCommand(BackCommandAbsentList);
                    absentList.setCommandListener(this);
                    xhv = null;

                    setSms(this.sendSMSStatus);
                    setAbsentStatusString(this.setSMSStatusString);
                    // Skapar nytt databasobjekt
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    // Sparar senast satta statuset i databasen.
                    rms.setAbsentStatus(setAbsentStatusTime);
                    rms = null;

                }
                if (conf.xhvtype.equals("")) {

                    Display.getDisplay(this).setCurrent(getMainList());

                    setSms(this.sendSMSStatus);
                    setAbsentStatusStringOrginal(this.setSMSStatusString);
                    // Skapar nytt databasobjekt
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    // Sparar senast satta statuset i databasen.
                    rms.setAbsentStatus(setAbsentStatusTime);
                    rms = null;

                }

            }

            else if (thCmd == confirmSENDSMSNOCommand
                     || thCmd == tvBackCommand
                     || thCmd == tv_BackCallForwardCommand
                     || thCmd == tvFormBackCallForwardCommand
                     || thCmd == tvStatusBackCommand
                     || thCmd == tvEditBackCommand) {

                if (!conf.xhvtype.equals("")) {

                    this.xhv = new Check_Xhv();
                    this.absentList = this.xhv.getxHvList();
                    BackCommandAbsentList = new Command(langobj.
                            genDefaultBack_DEF,
                            Command.BACK, 2);
                    absentList.addCommand(BackCommandAbsentList);
                    absentList.setCommandListener(this);
                    xhv = null;
                    Display.getDisplay(this).setCurrent(this.absentList);

                } else {

                    Display.getDisplay(this).setCurrent(getAbsentList());
                }

            }


            else if (thCmd == atExtSendCommand
                     || thCmd == backAtSendCommand
                     || thCmd == outSendCommand) {

                String absentNUM = "";
                String getFromTextField = "";
                String absentSEND = "";

                if (thCmd == atExtSendCommand) {
                    absentNUM = "4";
                    getFromTextField = atExtTextField.getString();
                    atExtTextField.setString("");
                } else if (thCmd == backAtSendCommand) {
                    absentNUM = "5";
                    getFromTextField = backAtTextField.getString();
                    backAtTextField.setString("");
                } else if (thCmd == outSendCommand) {
                    absentNUM = "6";
                    getFromTextField = outTextField.getString();
                    outTextField.setString("");
                }

                if (!getFromTextField.equals("")) {

                    if (absentNUM.equals("4")) {

                        absentSEND = this.pbxobj.precode_PBX + "503" +
                                     getFromTextField + "#";

                        String extension = langobj.absentDefaultAtExt_DEF + " " +
                                           getFromTextField;
                        setAbsentStatusStringOrginal(extension);
                        Display.getDisplay(this).setCurrent(getAbsentList());

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex1) {
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + absentSEND;

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        absentSEND;

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                    }

                    getAtExtForm();

                }
                if (!getFromTextField.equals("")) {

                    if (absentNUM.equals("5")) {

                        absentSEND = this.pbxobj.precode_PBX + "504" +
                                     getFromTextField + "#";

                        String backAtTime = langobj.absentDefaultBackAt_DEF +
                                            " " +
                                            getFromTextField;
                        setAbsentStatusStringOrginal(backAtTime);
                        Display.getDisplay(this).setCurrent(getAbsentList());

                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }

                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + absentSEND;

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        absentSEND;

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                    }

                    getBackATForm();

                }
                if (!getFromTextField.equals("")) {

                    if (absentNUM.equals("6")) {

                        absentSEND = this.pbxobj.precode_PBX + "505" +
                                     getFromTextField + "#";

                        String dateBack = langobj.absentDefaultOutUntil_DEF +
                                          " " +
                                          getFromTextField;
                        setAbsentStatusStringOrginal(dateBack);
                        Display.getDisplay(this).setCurrent(getAbsentList());

                        try {
                            rms = new MDataStore.DataBase_RMS();
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (RecordStoreException ex4) {
                        } catch (IOException ex4) {
                        }

                        try {
                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + absentSEND;

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        absentSEND;

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }

                        platformRequestSetPresence();

                        rms = null;

                    }

                    getOutForm();

                }

            }

            /* ------- Send - Commands --------------------- */

            // Ring intern-samtal.
            else if (thCmd == internCallCommand) {

                MDataStore.DataBase_RMS rms = new DataBase_RMS();

                if (!internCallTextField.getString().equals("")) {

                    String internCall = internCallTextField.getString();

                    if (rms.getPBXType().equals("3")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + internCall;

                    } else if (rms.getPBXType().equals("5")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + "*47"
                                                + pbxobj.extensionNumber_PBX +
                                                pbxobj.pinCodeNumber_PBX +
                                                internCall;

                    }

                    platformRequest();

                    internCallTextField.setString("");
                }

                rms = null;
                getInternCallPhoneForm();
            }

            // Ring ett samtal
            else if (thCmd == callCommand) {
                MModel.SortClass sort = new SortClass();
                MDataStore.DataBase_RMS rms = new DataBase_RMS();

                if (!callTextField.getString().equals("")) {

                    String call = callTextField.getString();

                    String setCallString = sort.checkCallNumber(call);

                    if (rms.getPBXType().equals("3")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + setCallString;

                    } else if (rms.getPBXType().equals("5")) {

                        this.Call_This_Number = pbxobj.switchBoardNumber_PBX +
                                                pbxobj.setP_PBX + "*47"
                                                + pbxobj.extensionNumber_PBX +
                                                pbxobj.pinCodeNumber_PBX +
                                                setCallString;

                    }

                    platformRequest();

                    callTextField.setString("");

                }
                rms = null;
                sort = null;
                getCallPhoneForm();
            }

            else if (thCmd == allCallForwardSendCommand) {

                MModel.SortClass sort = new SortClass();

                DataBase_RMS rms = null;
                try {
                    rms = new DataBase_RMS();
                } catch (IOException ex1) {
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

                String allCallForward = allCallForwardTextField.getString();
                allCallForward = sort.checkCallNumber(allCallForward);

                if (!allCallForwardTextField.getString().equals("")) {

                    if (allCallForwardRename.equals("1")) {
                        try {

                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " " +
                                    langobj.callForwardDefaultAllCalls_DEF +
                                               ": " +
                                               allCallForwardTextField.getString() +
                                               " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "102" +
                                        allCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "102" +
                                        allCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(this.
                                getAllCallForwardList());
                        platformRequestSetPresence();
                        allCallForwardTextField.setString("");

                    } else if (allCallForwardRename.equals("2")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF + " "
                                    + langobj.callForwardDefaultAllCalls_DEF +
                                    ": " + langobj.callForwardDefaultBusy_DEF +
                                    ": " +
                                    allCallForwardTextField.getString() +
                                    " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "103" +
                                        allCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "103" +
                                        allCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardList());
                        platformRequestSetPresence();
                        allCallForwardTextField.setString("");

                    } else if (allCallForwardRename.equals("3")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultAllCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultNoAnswer_DEF +
                                    ": " +
                                    allCallForwardTextField.getString() +
                                    " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "104" +
                                        allCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "104" +
                                        allCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardList());
                        platformRequestSetPresence();
                        allCallForwardTextField.setString("");

                    } else if (allCallForwardRename.equals("4")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultAllCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultBusyNoAnswer_DEF +
                                    ": " +
                                    allCallForwardTextField.getString() +
                                    " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "105" +
                                        allCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "105" +
                                        allCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getAllCallForwardList());
                        platformRequestSetPresence();
                        allCallForwardTextField.setString("");
                    }

                }
                rms = null;
                sort = null;
                getAllCallForwardForm();

            } else if (thCmd == externCallForwardSendCommand) {

                MModel.SortClass sort = new SortClass();

                DataBase_RMS rms = null;
                try {
                    rms = new DataBase_RMS();
                } catch (IOException ex1) {
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

                String externCallForward = externCallForwardTextField.getString();
                externCallForward = sort.checkCallNumber(externCallForward);

                if (!externCallForwardTextField.getString().equals("")) {

                    if (externCallForwardRename.equals("1")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultExternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultAllCalls_DEF +
                                    ": " +
                                    externCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "112" +
                                        externCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "112" +
                                        externCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());
                        platformRequestSetPresence();
                        externCallForwardTextField.setString("");

                    } else if (externCallForwardRename.equals("2")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultExternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultBusy_DEF +
                                    ": " +
                                    externCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "113" +
                                        externCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "113" +
                                        externCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());
                        platformRequestSetPresence();
                        externCallForwardTextField.setString("");

                    } else if (externCallForwardRename.equals("3")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultExternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultNoAnswer_DEF +
                                    ": " +
                                    externCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "114" +
                                        externCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "114" +
                                        externCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());
                        platformRequestSetPresence();
                        externCallForwardTextField.setString("");

                    } else if (externCallForwardRename.equals("4")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultExternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultBusyNoAnswer_DEF +
                                    ": " +
                                    externCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "115" +
                                        externCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "115" +
                                        externCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getExternCallForwardList());
                        platformRequestSetPresence();
                        externCallForwardTextField.setString("");

                    }

                }
                rms = null;
                sort = null;
                getExternCallForwardForm();

            } else if (thCmd == internCallForwardSendCommand) {

                MModel.SortClass sort = new SortClass();

                DataBase_RMS rms = null;
                try {
                    rms = new DataBase_RMS();
                } catch (IOException ex1) {
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

                String internCallForward = internCallForwardTextField.getString();
                internCallForward = sort.checkCallNumber(internCallForward);

                if (!internCallForwardTextField.getString().equals("")) {

                    if (internCallForwardRename.equals("1")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultInternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultAllCalls_DEF +
                                    ": " +
                                    internCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "122" +
                                        internCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "122" +
                                        internCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());
                        platformRequestSetPresence();
                        internCallForwardTextField.setString("");

                    } else if (internCallForwardRename.equals("2")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultInternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultBusy_DEF +
                                    ": " +
                                    internCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "123" +
                                        internCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "123" +
                                        internCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());
                        platformRequestSetPresence();
                        internCallForwardTextField.setString("");

                    } else if (internCallForwardRename.equals("3")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultInternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultNoAnswer_DEF +
                                    ": " +
                                    internCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "124" +
                                        internCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "124" +
                                        internCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());
                        platformRequestSetPresence();
                        internCallForwardTextField.setString("");

                    } else if (internCallForwardRename.equals("4")) {
                        try {
                            String setPresenceName = langobj.
                                    callForwardDefault_DEF +
                                    " "
                                    +
                                    langobj.callForwardDefaultInternCalls_DEF +
                                    ": " +
                                    langobj.callForwardDefaultBusyNoAnswer_DEF +
                                    ": " +
                                    internCallForwardTextField.
                                    getString() + " ";
                            setAbsentStatusStringOrginal(setPresenceName);

                            if (rms.getPBXType().equals("3")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX +
                                        this.pbxobj.precode_PBX + "125" +
                                        internCallForward + "#";

                            } else if (rms.getPBXType().equals("5")) {

                                this.Presence_This_String =
                                        pbxobj.switchBoardNumber_PBX +
                                        pbxobj.setP_PBX + "*47"
                                        + pbxobj.extensionNumber_PBX +
                                        pbxobj.pinCodeNumber_PBX +
                                        this.pbxobj.precode_PBX + "125" +
                                        internCallForward + "#";

                            }
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(
                                getInternCallForwardList());
                        platformRequestSetPresence();
                        internCallForwardTextField.setString("");

                    }

                }
                rms = null;
                sort = null;
                getInternCallForwardForm();

            }

            // Login send kommando
            else if (thCmd == loginGroupSendCommand) {

                MDataStore.DataBase_RMS rms = new DataBase_RMS();
                String groupLogin = loginGroupTextField.getString();

                if (!loginGroupTextField.getString().equals("")) {

                    try {
                        if (rms.getPBXType().equals("3")) {

                            this.Presence_This_String =
                                    pbxobj.switchBoardNumber_PBX +
                                    pbxobj.setP_PBX + this.pbxobj.precode_PBX +
                                    "361" +
                                    groupLogin;

                        } else if (rms.getPBXType().equals("5")) {

                            this.Presence_This_String =
                                    pbxobj.switchBoardNumber_PBX +
                                    pbxobj.setP_PBX + "*47"
                                    + pbxobj.extensionNumber_PBX +
                                    pbxobj.pinCodeNumber_PBX +
                                    this.pbxobj.precode_PBX + "361" +
                                    groupLogin;

                        }
                    } catch (RecordStoreNotOpenException ex2) {
                    } catch (InvalidRecordIDException ex2) {
                    } catch (RecordStoreException ex2) {
                    }
                    platformRequestSetPresence();
                    loginGroupTextField.setString("");

                }

                rms = null;
                getLoginGroupForm();

            }
            // Logoff send kommando
            else if (thCmd == logoffGroupSendCommand) {

                MDataStore.DataBase_RMS rms = new DataBase_RMS();

                String groupLogoff = logoffGroupTextField.getString();

                if (!logoffGroupTextField.getString().equals("")) {

                    try {
                        if (rms.getPBXType().equals("3")) {

                            this.Presence_This_String =
                                    pbxobj.switchBoardNumber_PBX +
                                    pbxobj.setP_PBX + this.pbxobj.precode_PBX +
                                    "360" +
                                    groupLogoff;

                        } else if (rms.getPBXType().equals("5")) {

                            this.Presence_This_String =
                                    pbxobj.switchBoardNumber_PBX +
                                    pbxobj.setP_PBX + "*47"
                                    + pbxobj.extensionNumber_PBX +
                                    pbxobj.pinCodeNumber_PBX +
                                    this.pbxobj.precode_PBX + "360" +
                                    groupLogoff;

                        }
                    } catch (RecordStoreNotOpenException ex2) {
                    } catch (InvalidRecordIDException ex2) {
                    } catch (RecordStoreException ex2) {
                    }

                    platformRequestSetPresence();
                    logoffGroupTextField.setString("");

                }
                rms = null;
                getLogoffGroupForm();

            }

            /* ------- Koppla samtal kommandon ------------- */

            else if (thCmd == internCallEditRenameCommand) {

                Display.getDisplay(this).setCurrent(getRenameForm());

            }

            /* ------- Save - Commands --------------------- */

            else if (thCmd == internCallEditRenameSaveCommand) {

                if (!internCallEditRenameNameTextField.getString().equals("")) {

                    if (!internCallEditRenameExtensionTextField.getString().
                        equals(
                                "")) {

                        Display.getDisplay(this).setCurrent(this.
                                alertEditSettings,
                                getMainList());

                        MDataStore.DataBase_RMS rms = new DataBase_RMS();

                        String a = internCallEditRenameNameTextField.getString();
                        String b = ": ";
                        String c = internCallEditRenameExtensionTextField.
                                   getString();

                        String person = a + b + c;
                        int p = this.IDInternNumber;
                        rms.setInternName(person, p);

                        System.out.println(rms.getInternName(p));
                        System.out.println("this.IDInternNumber >> " +
                                           this.IDInternNumber);

                        rms.setDataStore();
                        rms.upDateDataStore();
                        rms = null;

                    }
                }

                getCallRenameEditForm();

            }

            // Sparar värden för Intern-nummer

            else if (thCmd == internCallEditSaveCommand) {

                if (!internCallEditNameTextField.getString().equals("")) {

                    if (!internCallEditExtensionTextField.getString().equals("")) {

                        Display.getDisplay(this).setCurrent(this.
                                alertEditSettings,
                                getMainList());

                        MDataStore.DataBase_RMS rms = new DataBase_RMS();

                        String a = internCallEditNameTextField.getString();
                        String b = ": ";
                        String c = internCallEditExtensionTextField.getString();

                        String person = a + b + c;
                        int p = this.IDInternNumber;
                        rms.setInternName(person, p);

                        System.out.println(rms.getInternName(p));
                        System.out.println("this.IDInternNumber >> " +
                                           this.IDInternNumber);

                        rms.setDataStore();
                        rms.upDateDataStore();
                        rms = null;

                    }
                }

                getCallEditForm();

            }

            // Sparar nya värden för editera dynamiska attribut. (hänvisning)
            else if (thCmd == editAbsentSaveCommand) {

                MDataStore.DataBase_RMS rms = new DataBase_RMS();

                if (editNEWAbsent.equals("1")) {

                    String name_1 = editAbsentName_TextField.getString();
                    String dtmf_1 = "507#";

                    if (name_1.equals("") || dtmf_1.equals("") ||
                        name_1.equals("") && dtmf_1.equals("")) {
                        String s1 = "1";
                        Display.getDisplay(this).setCurrent(getEditAbsentForm(
                                s1));

                    } else if (!name_1.equals("") && !dtmf_1.equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings,
                                getMainList());

                        rms.setRename_1("1");
                        rms.setEditAbsentName_1(name_1);
                        rms.setEditAbsentDTMF_1(dtmf_1);
                        this.editAbsentDTMF_1 = dtmf_1;

                        if (editHHTTMMTT.equals("1")) {

                            rms.setHHMMTTMM_1("1");

                        } else if (editHHTTMMTT.equals("2")) {

                            rms.setHHMMTTMM_1("2");

                        }

                    }

                    editAbsentName_TextField.setString("");

                }

                if (editNEWAbsent.equals("2")) {

                    String name_2 = editAbsentName_TextField.getString();
                    String dtmf_2 = "508#";

                    if (name_2.equals("") || dtmf_2.equals("") ||
                        name_2.equals("") && dtmf_2.equals("")) {
                        String s2 = "2";
                        Display.getDisplay(this).setCurrent(getEditAbsentForm(
                                s2));

                    } else if (!name_2.equals("") && !dtmf_2.equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings,
                                getMainList());

                        rms.setRename_2("1");
                        rms.setEditAbsentName_2(name_2);
                        rms.setEditAbsentDTMF_2(dtmf_2);
                        this.editAbsentDTMF_2 = dtmf_2;

                        if (editHHTTMMTT.equals("1")) {

                            rms.setHHMMTTMM_2("1");

                        } else if (editHHTTMMTT.equals("2")) {

                            rms.setHHMMTTMM_2("2");

                        }

                    }

                    editAbsentName_TextField.setString("");

                }

                if (editNEWAbsent.equals("3")) {

                    String name_3 = editAbsentName_TextField.getString();
                    String dtmf_3 = "509#";

                    if (name_3.equals("") || dtmf_3.equals("") ||
                        name_3.equals("") && dtmf_3.equals("")) {
                        String s3 = "3";
                        Display.getDisplay(this).setCurrent(getEditAbsentForm(
                                s3));

                    } else if (!name_3.equals("") && !dtmf_3.equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings,
                                getMainList());

                        rms.setRename_3("1");
                        rms.setEditAbsentName_3(name_3);
                        rms.setEditAbsentDTMF_3(dtmf_3);
                        this.editAbsentDTMF_3 = dtmf_3;

                        if (editHHTTMMTT.equals("1")) {

                            rms.setHHMMTTMM_3("1");

                        } else if (editHHTTMMTT.equals("2")) {

                            rms.setHHMMTTMM_3("2");

                        }

                    }

                    editAbsentName_TextField.setString("");

                }

                rms = null;
            }

            // Sparar nya värden för autoaccessNOPrefix
            else if (thCmd == AutoAccessSaveNOPrefixCommand) {

                if (!AutoAccessNOPrefixSwitchBoardTextField.getString().equals(
                        "")) {

                    Display.getDisplay(this).setCurrent(alertEditSettings, getMainList());
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();

                    pbxobj.lineAccess_PBX = "NONE";
                    pbxobj.switchBoardNumber_PBX = AutoAccessNOPrefixSwitchBoardTextField.getString();
                    pbxobj.gsm_number = AutoAccessNOPrefixGSMnumberTextField.getString();

                    if(pbxobj.gsm_number.equals("")){

                            pbxobj.gsm_number = "0";
                        }

                    rms.setLineAccess(pbxobj.lineAccess_PBX);
                    rms.setSwitchBoardNumber(pbxobj.switchBoardNumber_PBX);
                    rms.setDataStore();
                    rms.upDateDataStore();
                    pbxobj.lineAccess_PBX = rms.getAccessNumber();
                    rms.setGSMNumber(pbxobj.gsm_number);
                    rms.setAccessTypeTo("3");

                    rms = null;

                }

                getAutoAccessNOPrefixForm();

            }
            // Sparar nya värden för autoaccess
            else if (thCmd == AutoAccessSaveCommand) {

                if (!AutoAccessLineAccessTextField.getString().equals("")) {

                    if (!AutoAccessSwitchBoardTextField.getString().equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings, getMainList());
                       MDataStore.DataBase_RMS rms = new DataBase_RMS();

                       pbxobj.lineAccess_PBX = AutoAccessLineAccessTextField.getString();
                       pbxobj.switchBoardNumber_PBX = AutoAccessSwitchBoardTextField.getString();
                       pbxobj.gsm_number = AutoAccessGSMnumberTextField.getString();

                       if(pbxobj.gsm_number.equals("")){

                           pbxobj.gsm_number = "0";
                       }

                       rms.setLineAccess(pbxobj.lineAccess_PBX);
                       rms.setSwitchBoardNumber(pbxobj.switchBoardNumber_PBX);
                       rms.setGSMNumber(pbxobj.gsm_number);
                       rms.setAccessTypeTo("3");

                       rms.setDataStore();
                       rms.upDateDataStore();
                       rms = null;

                    }

                }

                getAutoAccessSettingForm();

            }
            // Sparar nya värden för PIN-Code
            else if (thCmd == pinCodeSaveCommand) {

                if (!pinCodeLineAccessTextField.getString().equals("")) {

                    if (!pinCodeSwitchBoardNumberTextField.getString().equals(
                            "")) {

                        if (!pinCodeExtensionNumberTextField.getString().equals(
                                "")) {

                            if (!pinCodePinCodeNumberTextField.getString().
                                equals("")) {

                                Display.getDisplay(this).setCurrent(alertEditSettings, getMainList());
                                MDataStore.DataBase_RMS rms = new DataBase_RMS();

                                pbxobj.lineAccess_PBX = pinCodeLineAccessTextField.getString();
                                pbxobj.switchBoardNumber_PBX = pinCodeSwitchBoardNumberTextField.getString();
                                pbxobj.extensionNumber_PBX = pinCodeExtensionNumberTextField.getString();
                                pbxobj.pinCodeNumber_PBX = pinCodePinCodeNumberTextField.getString();
                                pbxobj.gsm_number = pinCodeGSMnumberTextField.getString();

                                if (pbxobj.gsm_number.equals("")) {

                                    pbxobj.gsm_number = "0";
                                }

                                rms.setLineAccess(pbxobj.lineAccess_PBX);
                                rms.setSwitchBoardNumber(pbxobj.switchBoardNumber_PBX);
                                rms.setExtensionNumber(pbxobj.extensionNumber_PBX);
                                rms.setPINCode(pbxobj.pinCodeNumber_PBX);
                                rms.setGSMNumber(pbxobj.gsm_number);
                                rms.setAccessTypeTo("5");

                                rms.setDataStore();
                                rms.upDateDataStore();
                                rms = null;

                            }
                        }
                    }
                }

                getPINCodeSettingsForm();

            }

            // Sparar nya värden för PRE-edit code
            else if (thCmd == preEditSaveCommand) {

                if (!preEditTextField.getString().equals("")) {

                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            getMainList());
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    this.pbxobj.precode_PBX = preEditTextField.getString();

                    rms.setPreCode(pbxobj.precode_PBX);
                    rms.setDataStore();
                    rms.upDateDataStore();
                    rms = null;

                }

                getPreEditForm();

            }

            // Sparar värden för Språk.
            else if (thCmd == countrySaveCommand) {

                if (!countryTextField.getString().equals("")) {

                    Display.getDisplay(this).setCurrent(alertRestarting,
                            getMainList());

                    MDataStore.DataBase_RMS rms = new DataBase_RMS();

                    rms.setLanguage(this.pbxobj.lang_PBX);
                    pbxobj.countryCode_PBX = countryTextField.getString();
                    rms.setCountryCode(pbxobj.countryCode_PBX);

                    String rename_1 = rms.getRename_1();
                    String rename_2 = rms.getRename_2();
                    String rename_3 = rms.getRename_3();

                    if (rename_1.equals("0") && rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_1(language.
                                                absentDefaultSystemAttOne_1);

                    }
                    if (rename_2.equals("0") && rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_2(language.
                                                absentDeafaultSystemAttTwo_1);

                    }
                    if (rename_3.equals("0") && rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_3(language.
                                                absentDefaultPersonalAtt_1);

                    }

                    if (rename_1.equals("0") && !rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_1(language.
                                                absentDefaultSystemAttOne_2);

                    }
                    if (rename_2.equals("0") && !rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_2(language.
                                                absentDeafaultSystemAttTwo_2);

                    }
                    if (rename_3.equals("0") && !rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_3(language.
                                                absentDefaultPersonalAtt_2);

                    }

                    rms.setAbsentStatus("0");

                    rms = null;
                }
                getCountryForm();
            }

            // Sparar värden för voicemail pbx.
            else if (thCmd == voiceEditSaveCommand_PBX) {

                if (!voiceMailPBXTextField_PBX.getString().equals("")) {

                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            getMainList());
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    pbxobj.voiceMailSwitchboard_PBX = voiceMailPBXTextField_PBX.
                            getString();
                    rms.setVoiceMailSwitchBoard(pbxobj.voiceMailSwitchboard_PBX);
                    rms = null;

                }

                getPBXVoiceEditForm();

            }

            /* ------- View - Commands Graphics --------------------- */

            else if (thCmd == mainListEditCommand) {

                Display.getDisplay(this).setCurrent(getSettingsList());

            } else if (thCmd == GraphicsAboutCommand) {

                GraphicsBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                  Command.OK, 2);
                String sendName = pbxobj.prg_Name + " " + langobj.version_DEF;
                Displayable k = new AboutUs(sendName);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(GraphicsBackCommand);
                k.setCommandListener(this);

            } else if (thCmd == GraphicsHelpCommand) {

                GraphicsBackCommand = new Command(langobj.genDefaultBack_DEF,
                                                  Command.OK, 2);
                String sendName = pbxobj.prg_Name + " " + langobj.version_DEF;
                Displayable k = new HelpInfo(sendName);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(GraphicsBackCommand);
                k.setCommandListener(this);

            } else if (thCmd == GraphicsBackCommand) {

                String sendName = pbxobj.prg_Name + " " + langobj.version_DEF;
                Displayable k = new ServerNumber(sendName, ViewDateString,
                                                 pbxobj.device_brands,
                                                 pbxobj.deveice_model,
                                                 pbxobj.pbx_name);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goGraphicsBackCommand);
                k.addCommand(GraphicsAboutCommand);
                k.addCommand(GraphicsHelpCommand);
                k.setCommandListener(this);

            } else if (thCmd == mainListaboutMobismaCommand) {

                String sendName = pbxobj.prg_Name + " " + langobj.version_DEF;
                Displayable k = new ServerNumber(sendName, ViewDateString,
                                                 pbxobj.device_brands,
                                                 pbxobj.deveice_model,
                                                 pbxobj.pbx_name);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goGraphicsBackCommand);
                k.addCommand(GraphicsAboutCommand);
                k.addCommand(GraphicsHelpCommand);
                k.setCommandListener(this);

            }

            /* ------- Back - Commands --------------------- */

            else if (thCmd == allCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(getAllCallForwardList());
            }

            else if (thCmd == externCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(getExternCallForwardList());
            }

            else if (thCmd == internCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(getInternCallForwardList());
            }

            else if (thCmd == backAllCallForwardCommand
                     || thCmd == backExternCallForwardCommand
                     || thCmd == backInternCallForwardCommand) {

                Display.getDisplay(this).setCurrent(getCallForwardList());
            }

            else if (thCmd == editAbsentBackCommand) {

                Display.getDisplay(this).setCurrent(getAbsentEditList());
            }

            else if (thCmd == internCallEditRenameBackCommand) {

                Display.getDisplay(this).setCurrent(getRenameForm());

            } else if (thCmd == mainListExitCommand) {

                Display.getDisplay(this).setCurrent(getAlertExit());

            }

            else if (thCmd == internCallEditBackCommand
                     || thCmd == internCallRenameBackCommand
                     || thCmd == internCallEditRenameCancelCommand) {

                Display.getDisplay(this).setCurrent(getInternCallPhoneForm());

            }

            else if (thCmd == groupBackCommand
                     || thCmd == confirmExitNOCommand
                     || thCmd == voiceEditCancelCommand_PBX
                     || thCmd == pbx_ListCancelCommand
                     || thCmd == BackCommandAbsentList
                     || thCmd == AutoAccessCancelCommand
                     || thCmd == AutoAccessCancelNOPrefixCommand
                     || thCmd == editAbsentListCancelCommand
                     || thCmd == editAbsentCancelCommand
                     || thCmd == goGraphicsBackCommand
                     || thCmd == callBackCommand
                     || thCmd == internCallEditCancelCommand
                     || thCmd == confirmOnCancelCommand
                     || thCmd == confirmOffCancelCommand
                     || thCmd == pbx_List_typeCancelCommand
                     || thCmd == pinCodeCancelCommand
                     || thCmd == preEditCancelCommand
                     || thCmd == backCallForwardCommand
                     || thCmd == internCallBackCommand) {

                Display.getDisplay(this).setCurrent(getMainList());

            } else if (thCmd == linePrefixBackCommand
                       || thCmd == voiceEditBackcommand_PBX
                       || thCmd == editAbsentListBackCommand
                       || thCmd == languageListBackCommand
                       || thCmd == countryCancelCommand
                       || thCmd == pbx_List_typeBackCommand
                       || thCmd == preEditBackCommand) {

                Display.getDisplay(this).setCurrent(pbx_List);

            } else if (thCmd == pinCodeBackCommand
                       || thCmd == AutoAccessBackCommand
                       || thCmd == AutoAccessBackNOPrefixCommand) {

                Display.getDisplay(this).setCurrent(pbx_List_type);

            } else if (thCmd == countryBackCommand) {

                Display.getDisplay(this).setCurrent(language_List);

            } else if (thCmd == loginGroupBackCommand
                       || thCmd == logoffGroupBackCommand) {

                Display.getDisplay(this).setCurrent(groupList);

            } else if (thCmd == atExtBackCommand
                       || thCmd == backAtBackCommand
                       || thCmd == outBackCommand) {

                Display.getDisplay(this).setCurrent(getAbsentList());
            }

            /* ------- Exit - Commands --------------------- */

            else if (thCmd == confirmExitYESCommand) {

                notifyDestroyed();

            }

        } catch (Exception ex) {
        }
    }

    /* **************************************************************************************************** */
    /* ===== Övriga kontroll-metoder som bör ligga i huvudklassen ========= */

    public void setAbsentStatusString(String s) {

        String presenceName = s;

        Date_Time date = null;
        try {
            date = new Date_Time();
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (RecordStoreException ex1) {
        } catch (IOException ex1) {
        }

        String minut = date.getAbsentMinute();
        String hour = date.getAbsentHour();
        int intYear = date.getYear();
        String year = Integer.toString(intYear);
        int intMounth = date.getMonth();
        String mounth = Integer.toString(intMounth);
        mounth = date.setMounthDigit(mounth);
        int intDay = date.getDay();
        String day = Integer.toString(intDay);
        day = date.setDay(day);

        setAbsentStatusTime = presenceName + ":" + " " + hour + "." +
                              minut + " " + year + "." + mounth + "." +
                              day;
        System.out.println("Står i STATUSFÄLT: >> " + setAbsentStatusTime);
        // Sparar senast satta statuset i databasen.
        // rms.setAbsentStatus(setAbsentStatusTime);
        // Visar status som är satt i alerten.
        alertSMS.setString(setAbsentStatusTime);

        date = null;
    }
    public void setAbsentStatusStringOrginal(String s) {

            String presenceName = s;

            DataBase_RMS rms = null;
            try {
                rms = new DataBase_RMS();
            } catch (IOException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreException ex) {
            }
            Date_Time date = null;
            try {
                date = new Date_Time();
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            String minut = date.getAbsentMinute();
            String hour = date.getAbsentHour();
            int intYear = date.getYear();
            String year = Integer.toString(intYear);
            int intMounth = date.getMonth();
            String mounth = Integer.toString(intMounth);
            mounth = date.setMounthDigit(mounth);
            int intDay = date.getDay();
            String day = Integer.toString(intDay);
            day = date.setDay(day);

            String setAbsentStatusTime = presenceName + ":" + " " + hour + "." +
                                         minut + " " + year + "." + mounth + "." +
                                         day;
            System.out.println("Set STATUS >> " + setAbsentStatusTime);

            rms.setAbsentStatus(setAbsentStatusTime);

            date = null;
            rms = null;
    }

    // --- metoden kontrollerar datum för demo-licenser
    public void ControllDateTime() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        MModel.Date_Time dateTime = new Date_Time();

        String s1 = setDay_30DAY; // Dag (30 dagar framåt i tiden)
        String s2 = setMounth_30DAY; // Månad (30 dagar framåt i tiden)
        String s3 = setYear_30DAY; // År (30 dagar framåt i tiden)
        String s4 = setYear_TODAY; // År (dagens datum)
        String s5 = setMounth_TODAY; // Månad (dagens datum)
        String s6 = setDay_TODAY; // Dag (dagens datum)

        dateTime.controllDate(s1, s2, s3, s4, s5, s6);
        this.ViewDateString = setDay_30DAY + " " + setMounthName_30DAY + " " +
                              setYear_30DAY;
        String licensStart = setDay_TODAY + " " + setMounthNameToday + " " +
                             setYear_TODAY;

        System.out.println("Licensen Startdatum >> " + licensStart);
        System.out.println("Licensen gäller till >> " + ViewDateString);
        System.out.println("VÄRDET PLATS getTWO(); >> " + pbxobj.CheckTwo);
        dateTime = null;
    }

    // ***************** Splash-Screen ********************

    // --- Metoder
    public void startSplash() {

        try {

            if (!splashIsShown) {
                String sendName = pbxobj.prg_Name + " " + langobj.version_DEF;
                Displayable k = new SplashCanvas(sendName, ViewDateString);
                display.setCurrent(k);

            }

            doTimeConsumingInit();

            if (true) {
                // Game loop
            }

        } catch (Exception ex) {
        }

    }


    private void doTimeConsumingInit() {
        // Just mimic some lengthy initialization for 10 secs
        long endTime = System.currentTimeMillis() + 3000;
        while (System.currentTimeMillis() < endTime) {}

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            if (rms.getTWO().equals("2")) {

                Display.getDisplay(this).setCurrent(getAlertExpernceLisence());

            }
            if (!rms.getTWO().equals("2")) {
                isInitialized = true;
                Display.getDisplay(this).setCurrent(getMainList());
            }
            // starta wma_servern för att skicka sms.
            if (wma_server == null) {
                wma_server = wma_server.getInstance(this.display);

                if (wma_server != null) {
                    String sendNumber = this.pbxobj.gsm_number;
                    wma_server.createServerConnection(sendNumber);
                    wma_server.getSMSC();
                }
            }


        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        rms = null;

    }


    // --- Två egna klasser
    public class SplashScreen implements Runnable {
        private SplashCanvas splashCanvas;

        public void run() {
            String sendName = pbxobj.prg_Name + " " + langobj.version_DEF;
            splashCanvas = new SplashCanvas(sendName, ViewDateString);
            display.setCurrent(splashCanvas);
            splashCanvas.repaint();
            splashCanvas.serviceRepaints();
            while (!isInitialized) {
                try {
                    Thread.yield();
                } catch (Exception e) {}
            }

        }

    }


    public class SplashCanvas extends Canvas {

        private String prg_Name, viewDateString;

        // Tar emot värden från huvudclassen i konstruktorn.
        public SplashCanvas(String name, String ViewDateString) {

            this.viewDateString = ViewDateString;
            this.prg_Name = name;

        }

        protected void paint(Graphics g) {
            int width = getWidth();
            int height = getHeight();

            // Create an Image the same size as the
            // Canvas.
            Image image = Image.createImage(width, height);
            Graphics imageGraphics = image.getGraphics();

            // Fill the background of the image black
            imageGraphics.setColor(0x000000);
            imageGraphics.fillRect(0, 0, width, height);

            // Draw a pattern of lines
            int count = 10;
            int yIncrement = height / count;
            int xIncrement = width / count;
            for (int i = 0, x = xIncrement, y = 0; i < count; i++) {
                imageGraphics.setColor(0xC0 + ((128 + 10 * i) << 8) +
                                       ((128 + 10 * i) << 16));
                imageGraphics.drawLine(0, y, x, height);
                y += yIncrement;
                x += xIncrement;
            }

            // Add some text
            imageGraphics.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 0,
                                               Font.SIZE_SMALL));
            imageGraphics.setColor(0xffff00);
            imageGraphics.drawString(prg_Name, width / 2, 15,
                                     Graphics.TOP | Graphics.HCENTER);

            try {
                Image image1 = Image.createImage("/mobisma_icon/mexa.png");
                imageGraphics.drawImage(image1, width / 2, 50,
                                        Graphics.TOP | Graphics.HCENTER);
            } catch (IOException ex) {
            }

            imageGraphics.drawString(viewDateString, width / 2, 100,
                                     Graphics.TOP | Graphics.HCENTER);

            imageGraphics.setColor(0xffffff);
            imageGraphics.drawString("mobisma AB", width / 2, 120,
                                     Graphics.TOP | Graphics.HCENTER);
            imageGraphics.drawString("All Rights Reserved © | 2008", width / 2,
                                     140, Graphics.TOP | Graphics.HCENTER);

            // Copy the Image to the screen
            g.drawImage(image, 0, 0, Graphics.TOP | Graphics.LEFT);

            splashIsShown = true;
        }


    }

    // Sätt sms Alert fönster skicka ja || nej
    public void setSMSDisplay() {

        Display.getDisplay(this).setCurrent(getSMSConfirmAlert());

    }
    public Form getTVForm() {

            // --- Totalview Hänvisning

            tvForm = new Form("");

            if(tv_set_hhmm_mmdd.equals("1")){

                tvTimeDateTextField = new TextField(langobj.hhmm_DEF + ":", "", 4, TextField.NUMERIC);
                tvMessageTextField = new TextField(langobj.message_DEF + ":", "", 16, TextField.ANY);

                tvForm.append(tvTimeDateTextField);
                tvForm.append(tvMessageTextField);


            } else if(tv_set_hhmm_mmdd.equals("2")){

                tvTimeDateTextField = new TextField(langobj.mmdd_DEF + ":", "", 4, TextField.NUMERIC);
                tvMessageTextField = new TextField(langobj.message_DEF + ":", "", 16, TextField.ANY);

                tvForm.append(tvTimeDateTextField);
                tvForm.append(tvMessageTextField);


            }

            tvSendCommand = new Command(langobj.genDefaultSend_DEF, Command.OK, 1);
            tvBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK, 2);
            tvForm.addCommand(tvSendCommand);
            tvForm.addCommand(tvBackCommand);
            tvForm.setCommandListener(this);

            return tvForm;
    }

    public List getTVCallForwardList() {

        try {
            tv_CallForwardList = new List(langobj.callForwardDefault_DEF, Choice.IMPLICIT);

            Image image1CF = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image2CF = Image.createImage("/prg_icon/mex24red.png");

            tv_CallForwardList.append(langobj.callForwardDefaultAllCalls_DEF, image1CF);
            tv_CallForwardList.append(langobj.callForwardDefaultExternCalls_DEF, image1CF);
            tv_CallForwardList.append(langobj.callForwardDefaultInternCalls_DEF, image1CF);
            tv_CallForwardList.append(langobj.callForwardDefaultRemove_DEF, image2CF);

            tv_BackCallForwardCommand = new Command(langobj.genDefaultBack_DEF,
                    Command.BACK, 1);

            tv_CallForwardList.addCommand(tv_BackCallForwardCommand);
            tv_CallForwardList.setCommandListener(this);
        } catch (IOException ex) {
        }

        return tv_CallForwardList;
    }
    public Form getTvStatusForm() {

            tv_StatusForm.deleteAll();
            tv_StatusForm.append(tvStatusTextField);

            if (absentList.getSelectedIndex() == 9) {

                tv_StatusForm.append(tvStatusDateTextField);

            }
            return tv_StatusForm;
    }
    public Alert getSMSConfirmAlert() {

         Image alertSMSImage = null;
         try {
             alertSMSImage = Image.createImage("/prg_icon/info.png");
         } catch (IOException ex) {
         }
         alertSMS = new Alert("", "", alertSMSImage, AlertType.CONFIRMATION);
         alertSMS.setTitle(langobj.absentDefaultSetPresence_DEF);
         setAbsentStatusString(setSMSStatusString);
         alertSMS.setTimeout(Alert.FOREVER);

         confirmSENDSMSYESCommand = new Command(langobj.genDefaultYes_DEF,
                                                Command.OK, 1);
         confirmSENDSMSNOCommand = new Command(langobj.exitDefaultNo_DEF,
                                               Command.CANCEL, 2);

         alertSMS.addCommand(confirmSENDSMSYESCommand);
         alertSMS.addCommand(confirmSENDSMSNOCommand);
         alertSMS.setCommandListener(this);

         return alertSMS;
     }

    public Form getTvCallForwardForm() {

        tv_CallForwardForm.deleteAll();
        tv_CallForwardForm.append(tvFormNumberTextField);
        tv_CallForwardForm.append(tvFormMessageTextField);

        return tv_CallForwardForm;
    }

    // Skickar status till wma-servern.
    public void setSms(String setStatus) {

        String sendStatus = setStatus;
        String sendNumber = this.pbxobj.gsm_number;
        wma_server.sendTextMessage(sendStatus, sendNumber);

        System.out.println("Sänder in status >> " + sendStatus);

    }

    public String getMinutes(String s) throws InvalidRecordIDException,
                RecordStoreNotOpenException, RecordStoreException, IOException {

            // inskickat värde/tid
            String convertTime = s;
            String hourSub = convertTime.substring(0, 2);
            String minuteSub = convertTime.substring(2, 4);

            MModel.Date_Time date = new Date_Time();

            // - hämta nutid timme
            int hour = date.getHour();
            int minute = date.getMinute();
            // Deklarerar nya variabler med INT-värden.
            Integer intHour = new Integer(0);
            Integer intMinute = new Integer(0);

            // Konverterar 'strängar' till INT-värden.
            int INT_inputHour = intHour.parseInt(hourSub);
            int INT_inputMinute = intMinute.parseInt(minuteSub);

            // input värdet HH minus servern timmar
            int sendHour = INT_inputHour - hour;
            sendHour = sendHour * 60;
            int sendMinute = INT_inputMinute - minute;

            int subTotal = sendHour + sendMinute;
            String readyMinute = Integer.toString(subTotal);

            date = null;
            return readyMinute;
    }

    public Form getTvEditAbsentForm(){

         tvEditForm = new Form(langobj.absentDefaultEditPresence_DEF);
         tvEditForm.deleteAll();

         TextField tvEditNameTextField = new TextField(langobj.callExtensionDefaultName_DEF + ":", "", 20, TextField.ANY);
         tvEditNameTextField.setLayout(Item.LAYOUT_NEWLINE_AFTER);
         tvEditForm.append(tvEditNameTextField);

         Spacer tomrummet = new Spacer(10, 5);
         tomrummet.setLayout(Item.LAYOUT_NEWLINE_AFTER);
         tvEditForm.append(tomrummet);

         TextField tvEditPrefixTextField = new TextField(langobj.enterDefaultEngerCharacter_DEF + ":", "", 4, TextField.ANY);
         tvEditPrefixTextField.setLayout(Item.LAYOUT_NEWLINE_AFTER);
         tvEditForm.append(tvEditPrefixTextField);

         optionsGroup.setLayout(Item.LAYOUT_NEWLINE_AFTER);
         optionsGroup.append(langobj.enterDefaultEnterMMDD_DEF, null);
         optionsGroup.append(langobj.enterDefaultEnterHHMM_DEF, null);
         tvEditForm.append(optionsGroup);

         tvEditSaveCommand = new Command(langobj.genDefaultSave_DEF, Command.OK, 1);
         tvEditCancelCommand = new Command(langobj.genDefaultCancel_DEF, Command.CANCEL, 2);
         tvEditBackCommand = new Command(langobj.genDefaultBack_DEF, Command.BACK, 3);

         tvEditForm.addCommand(tvEditSaveCommand);
         tvEditForm.addCommand(tvEditCancelCommand);
         tvEditForm.addCommand(tvEditBackCommand);
         tvEditForm.setCommandListener(this);

        return tvEditForm;

     }



}
