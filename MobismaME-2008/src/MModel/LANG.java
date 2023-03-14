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

public  class LANG {

   //================== D E F A U L T - Språk =================================

    public  String

            settingsDefaultAbout_DEF,
            SettingsDefaultAccessPINcode_DEF,
            extensionDefaultAddNew_DEF,
            callForwardDefaultAllCalls_DEF,
            absentDefaultAtExt_DEF,
            settingsDefaultAutoAccess_DEF,
            genDefaultBack_DEF,
            absentDefaultBackAt_DEF,
            callForwardDefaultBusy_DEF,
            callForwardDefaultBusyNoAnswer_DEF,
            callDefaultCall_DEF,
            extensionDefaultCall_DEF,
            callForwardDefault_DEF,
            msgDefaultCallistIsEmpty_DEF,
            genDefaultCancel_DEF,
            alertDefaultCantAddAnymoreExt_DEF,
            alertDefaultCouldntAddChangesEmtpyField_DEF,
            alertDefaultCouldntAddRecord_DEF,
            alsertDefaultChangesSave_DEF,
            mgsDefaultContactListIsEmpty_DEF,
            alertDefaultCountryCodeError_DEF,
            settingsDefaultCountryCode_DEF,
            genDefaultDelete_DEF,
            genDefaultDeleteAll_DEF,
            dialledCallsDefault_DEF,
            callForwardDefaultDontDisturb_DEF,
            genDefaultEdit_DEF,
            settingsDefaultEditPBXAccess_DEF,
            absentDefaultEditPresence_DEF,
            voiceMailDefaultEditVoicemail_DEF,
            enterDefaultEngerCharacter_DEF,
            enterDefaultEnterExtension_DEF,
            enterDefaultEnterGroupNumber_DEF,
            enterDefaultEnterHHMM_DEF,
            enterDefaultEnterNumber_DEF,
            alertDefaultErrorChangeTo_DEF,
            alertDefaultError_DEF,
            enterDefaultEnterMMDD_DEF,
            exitDefaultExitTheProgramYesOrNo_DEF,
            genDefaultExit_DEF,
            settingsDefaultExtension_DEF,
            callExtensionDefaultExtensionWasAdded_DEF,
            callForwardDefaultExternCalls_DEF,
            absentDefaultGoneHome_DEF,
            groupsDefaultGroups_DEF,
            settingsDefaultHelp_DEF,
            absentDefaultInAMeeting_DEF,
            alertDefaultInfo_DEF,
            alertDefaultInstedOf_DEF,
            callForwardDefaultInternCalls_DEF,
            settingsDefaultLanguage_DEF,
            settingsDefaultLineAccess_DEF,
            groupsDefaultLoginAllGroups_DEF,
            groupsDefaultLoginSpecificGroup_DEF,
            groupsDefaultLogoutAllGroups_DEF,
            groupsDefaultLogoutSpecificGroup_DEF,
            alertDefaultMaxSize_DEF,
            genDefaultMinimise_DEF,
            callExtensionDefaultName_DEF,
            exitDefaultNo_DEF,
            callForwardDefaultNoAnswer_DEF,
            settingsDefaultOptions_DEF,
            absentDefaultOutUntil_DEF,
            absentDefaultPersonalAtt_DEF,
            settingsDefaultPINcode_DEF,
            settingsDefaultPreEditCode_DEF,
            callForwardDefaultRemove_DEF,
            absentDefaultRemovePresence_DEF,
            exitDefaultRestartProgram_DEF,
            alertDefaultSaveChanges_DEF,
            genDefaultSave_DEF,
            settingsDefaultSelectCountryCode_DEF,
            genDefaultSelect_DEF,
            genDefaultSend_DEF,
            absentDefaultSetPresence_DEF,
            settingsDefaultSettings_DEF,
            callExtensionDefaultSurname_DEF,
            settingsDefaultSwitchboardNumber_DEF,
            absentDefaultSystemAttOne_DEF,
            absentDeafaultSystemAttTwo_DEF,
            absentDeafaultWillReturnSoon_DEF,
            alertDefaultWrongInputTryAgain_DEF,
            voiceMailDefaultVoiceMail_DEF,
            genDefaultYes_DEF,

            accessPBXDefault_DEF,
            autoAccessDefault_DEF,
            accessViaPINCodeDefault_DEF,
            dialDefault_DEF,

            alertExitMEXOnMessage_DEF,
            AlertMessageExitText_DEF,
            alertMessageMEXOn_DEF,
            alertMessageMEXOff_DEF,
            alertMessageMexServerInfo_DEF,
            alertMessageMexAlreadyON_DEF,
            alertMessageMexAlreadyOFF_DEF,
            mainListAttributMexOn_DEF,
            mainListAttributMexOff_DEF,
            operatorVoicemail_DEF,

            absentTimeOfReturn_DEF,
            absentDateOfReturn_DEF,
            absentLunch_DEF,
            absentMeeting_DEF,
            absentVacation_DEF,
            absentIllness_DEF,

            callForwardTransfer_DEF,
            callForwardPermForward_DEF,
            callForwardInterForward_DEF,
            callForwardExternForward_DEF,
            callForwardCancelExtern_DEF,
            callForwardCancelPermIntern_DEF,

            transferBack_DEF,

            textYourNumber_DEF,
            textNewNumber_DEF,

            voiceMailActivate_DEF,
            voiceMailDeActivate_DEF,
            voiceMailListen_DEF,

            version_DEF,
            system_DEF,

            programExitON_DEF,
            programExitOFF_DEF,
            programConference_DEF,

            absentLunchLG_DEF,
            absentVacationLG_DEF,
            absentBackAtLG_DEF,
            absentDateBackLG_DEF,
            absentOutLG_DEF,
            absentBusyLG_DEF,
            absentTravelLG_DEF,
            absentVABLG_DEF,
            absentTALKEDLG_DEF,

            absentCallLG_DEF,
            absentExtLG_DEF,
            GSMmodemLG_DEF,
            isON_DEF,
            isOFF_DEF,
            mex_DEF,
            listenVoicemail_DEF,
            recordVoicemail_DEF,
            adminVoicemail_DEF,
            activeVoicemail_DEF,
            deactiveVoicemail_DEF,
            accessCode_DEF,
            number_DEF,

            userName_DEF,
            message_DEF,
            inside_DEF,
            private_DEF,
            statusToday_DEF,
            statusTodayTomorrow_DEF,
            statusDate_DEF,
            statusContact_DEF,
            tv_lunch_DEF,
            hhmm_DEF,
            mmdd_DEF;





    public LANG() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {


     MDataStore.DataBase_RMS rms = new  DataBase_RMS();
     String lang = rms.lang_PBX;
     rms = null;
            /* ================== SPRÅK =============================  */

        // --- Default språk, Engelska. Alltid andraspråk i prg.

        if (lang.equals("2")) { // English >> Default

            settingsDefaultAbout_DEF = MModel.Language.settingsDefaultAbout_1;
            SettingsDefaultAccessPINcode_DEF = MModel.Language.
                                               SettingsDefaultAccessPINcode_1;
            extensionDefaultAddNew_DEF = MModel.Language.extensionDefaultAddNew_1;
           callForwardDefaultAllCalls_DEF = MModel.Language.
                                             callForwardDefaultAllCalls_1;
            absentDefaultAtExt_DEF = MModel.Language.absentDefaultAtExt_1;
            settingsDefaultAutoAccess_DEF = MModel.Language.
                                            settingsDefaultAutoAccess_1;
            genDefaultBack_DEF = MModel.Language.genDefaultBack_1;
            absentDefaultBackAt_DEF = MModel.Language.absentDefaultBackAt_1;
            callForwardDefaultBusy_DEF = MModel.Language.callForwardDefaultBusy_1;
            callForwardDefaultBusyNoAnswer_DEF =
                    MModel.Language.callForwardDefaultBusyNoAnswer_1;
           callDefaultCall_DEF = MModel.Language.callDefaultCall_1;
            extensionDefaultCall_DEF = MModel.Language.extensionDefaultCall_1;
            callForwardDefault_DEF = MModel.Language.callForwardDefault_1;
            msgDefaultCallistIsEmpty_DEF = MModel.Language.msgDefaultCallistIsEmpty_1;
            genDefaultCancel_DEF = MModel.Language.genDefaultCancel_1;
            alertDefaultCantAddAnymoreExt_DEF =
                    MModel.Language.alertDefaultCantAddAnymoreExt_1;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    MModel.Language.alertDefaultCouldntAddChangesEmtpyField_1;
            alertDefaultCouldntAddRecord_DEF = MModel.Language.
                                               alertDefaultCouldntAddRecord_1;
            alsertDefaultChangesSave_DEF = MModel.Language.alertDefaultChangesSave_1;
            mgsDefaultContactListIsEmpty_DEF = MModel.Language.
                                               mgsDefaultContactListIsEmpty_1;
            alertDefaultCountryCodeError_DEF = MModel.Language.
                                               alertDefaultCountryCodeError_1;
            settingsDefaultCountryCode_DEF = MModel.Language.
                                             settingsDefaultCountryCode_1;
            genDefaultDelete_DEF = MModel.Language.genDefaultDelete_1;
            genDefaultDeleteAll_DEF = MModel.Language.genDefaultDeleteAll_1;
            dialledCallsDefault_DEF = MModel.Language.dialledCallsDefault_1;
           callForwardDefaultDontDisturb_DEF =
                    MModel.Language.callForwardDefaultDontDisturb_1;
            genDefaultEdit_DEF = MModel.Language.genDefaultEdit_1;
            settingsDefaultEditPBXAccess_DEF = MModel.Language.
                                               settingsDefaultEditPBXAccess_1;
            absentDefaultEditPresence_DEF = MModel.Language.
                                            absentDefaultEditPresence_1;
            voiceMailDefaultEditVoicemail_DEF =
                    MModel.Language.voiceMailDefaultEditVoicemail_1;
            enterDefaultEngerCharacter_DEF = MModel.Language.
                                             enterDefaultEngerCharacter_1;
           enterDefaultEnterExtension_DEF = MModel.Language.
                                             enterDefaultEnterExtension_1;
           enterDefaultEnterGroupNumber_DEF = MModel.Language.
                                               enterDefaultEnterGroupNumber_1;
            enterDefaultEnterHHMM_DEF = MModel.Language.enterDefaultEnterHHMM_1;
            enterDefaultEnterNumber_DEF = MModel.Language.enterDefaultEnterNumber_1;
            alertDefaultErrorChangeTo_DEF = MModel.Language.
                                            alertDefaultErrorChangeTo_1;
            alertDefaultError_DEF = MModel.Language.alertDefaultError_1;
            enterDefaultEnterMMDD_DEF = MModel.Language.enterDefaultEnterMMDD_1;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    MModel.Language.exitDefaultExitTheProgramYesOrNo_1;
            genDefaultExit_DEF = MModel.Language.genDefaultExit_1;
           settingsDefaultExtension_DEF = MModel.Language.settingsDefaultExtension_1;
            callExtensionDefaultExtensionWasAdded_DEF =
                    MModel.Language.callExtensionDefaultExtensionWasAdded_1;
           callForwardDefaultExternCalls_DEF =
                    MModel.Language.callForwardDefaultExternCalls_1;
            absentDefaultGoneHome_DEF = MModel.Language.absentDefaultGoneHome_1;
            groupsDefaultGroups_DEF = MModel.Language.groupsDefaultGroups_1;
            settingsDefaultHelp_DEF = MModel.Language.settingsDefaultHelp_1;
            absentDefaultInAMeeting_DEF = MModel.Language.absentDefaultInAMeeting_1;
            alertDefaultInfo_DEF = MModel.Language.alertDefaultInfo_1;
            alertDefaultInstedOf_DEF = MModel.Language.alertDefaultInstedOf_1;
            callForwardDefaultInternCalls_DEF =
                    MModel.Language.callForwardDefaultInternCalls_1;
           settingsDefaultLanguage_DEF = MModel.Language.settingsDefaultLanguage_1;
           settingsDefaultLineAccess_DEF = MModel.Language.
                                            settingsDefaultLineAccess_1;
            groupsDefaultLoginAllGroups_DEF = MModel.Language.
                                              groupsDefaultLoginAllGroups_1;
           groupsDefaultLoginSpecificGroup_DEF =
                    MModel.Language.groupsDefaultLoginSpecificGroup_1;
           groupsDefaultLogoutAllGroups_DEF = MModel.Language.
                                               groupsDefaultLogoutAllGroups_1;
            groupsDefaultLogoutSpecificGroup_DEF =
                    MModel.Language.groupsDefaultLogoutSpecificGroup_1;
            alertDefaultMaxSize_DEF = MModel.Language.alertDefaultMaxSize_1;
            genDefaultMinimise_DEF = MModel.Language.genDefaultMinimise_1;
            callExtensionDefaultName_DEF = MModel.Language.callExtensionDefaultName_1;
            exitDefaultNo_DEF = MModel.Language.exitDefaultNo_1;
            callForwardDefaultNoAnswer_DEF = MModel.Language.
                                             callForwardDefaultNoAnswer_1;
            settingsDefaultOptions_DEF = MModel.Language.settingsDefaultOptions_1;
            absentDefaultOutUntil_DEF = MModel.Language.absentDefaultOutUntil_1;

            settingsDefaultPINcode_DEF = MModel.Language.settingsDefaultPINcode_1;
            settingsDefaultPreEditCode_DEF = MModel.Language.
                                             settingsDefaultPreEditCode_1;
            callForwardDefaultRemove_DEF = MModel.Language.callForwardDefaultRemove_1;
            absentDefaultRemovePresence_DEF = MModel.Language.
                                              absentDefaultRemovePresence_1;
            exitDefaultRestartProgram_DEF = MModel.Language.
                                            exitDefaultRestartProgram_1;
            alertDefaultSaveChanges_DEF = MModel.Language.alertDefaultSaveChanges_1;
            genDefaultSave_DEF = MModel.Language.genDefaultSave_1;
            settingsDefaultSelectCountryCode_DEF =
                    MModel.Language.settingsDefaultSelectCountryCode_1;
            genDefaultSelect_DEF = MModel.Language.genDefaultSelect_1;
            genDefaultSend_DEF = MModel.Language.genDefaultSend_1;
            absentDefaultSetPresence_DEF = MModel.Language.absentDefaultSetPresence_1;
            settingsDefaultSettings_DEF = MModel.Language.settingsDefaultSettings_1;
           callExtensionDefaultSurname_DEF = MModel.Language.
                                              callExtensionDefaultSurname_1;
            settingsDefaultSwitchboardNumber_DEF =
                    MModel.Language.settingsDefaultSwitchboardNumber_1;

            absentDefaultSystemAttOne_DEF = MModel.Language.
                                            absentDefaultSystemAttOne_1;
           absentDeafaultSystemAttTwo_DEF = MModel.Language.
                                             absentDeafaultSystemAttTwo_1;
           absentDefaultPersonalAtt_DEF = MModel.Language.absentDefaultPersonalAtt_1;

            absentDeafaultWillReturnSoon_DEF = MModel.Language.
                                               absentDeafaultWillReturnSoon_1;
            alertDefaultWrongInputTryAgain_DEF =
                    MModel.Language.alertDefaultWrongInputTryAgain_1;
            voiceMailDefaultVoiceMail_DEF = MModel.Language.
                                            voiceMailDefaultVoiceMail_1;
            genDefaultYes_DEF = MModel.Language.genDefaultYes_1;

            accessPBXDefault_DEF = MModel.Language.accessPBXDefault_1;
            autoAccessDefault_DEF = MModel.Language.autoAccessDefault_1;
            accessViaPINCodeDefault_DEF = MModel.Language.accessViaPINCodeDefault_1;
            dialDefault_DEF = MModel.Language.dialDefault_1;

            alertExitMEXOnMessage_DEF = MModel.Language.alertExitMEXOnMessage_1;
            alertMessageMEXOn_DEF = MModel.Language.alertMessageMEXOn_1;
            alertMessageMEXOff_DEF = MModel.Language.alertMessageMEXOff_1;
            alertMessageMexServerInfo_DEF = MModel.Language.
                                            alertMessageMexServerInfo_1;
            alertMessageMexAlreadyON_DEF = MModel.Language.alertMessageMexAlreadyON_1;
            alertMessageMexAlreadyOFF_DEF = MModel.Language.
                                            alertMessageMexAlreadyOFF_1;
            mainListAttributMexOn_DEF = MModel.Language.mainListAttributMexOn_1;
            mainListAttributMexOff_DEF = MModel.Language.mainListAttributMexOff_1;
            operatorVoicemail_DEF = MModel.Language.operatorVoicemail_1;

            AlertMessageExitText_DEF = MModel.Language.AlertMessageExitText_1;

            absentTimeOfReturn_DEF = MModel.Language.absentTimeOfReturn_1;
            absentDateOfReturn_DEF = MModel.Language.absentDateOfReturn_1;
            absentLunch_DEF = MModel.Language.absentLunch_1;
            absentMeeting_DEF = MModel.Language.absentMeeting_1;
            absentVacation_DEF = MModel.Language.absentVacation_1;
            absentIllness_DEF = MModel.Language.absentIllness_1;

            callForwardTransfer_DEF = MModel.Language.callForwardTransfer_1;
            callForwardPermForward_DEF = MModel.Language.callForwardPermForward_1;
            callForwardInterForward_DEF = MModel.Language.callForwardInterForward_1;
            callForwardExternForward_DEF = MModel.Language.callForwardExternForward_1;
            callForwardCancelExtern_DEF = MModel.Language.callForwardCancelExtern_1;
            callForwardCancelPermIntern_DEF = MModel.Language.
                                              callForwardCancelPermIntern_1;

            textYourNumber_DEF = MModel.Language.textYourNumber_1;
            textNewNumber_DEF = MModel.Language.textNewNumber_1;

            voiceMailActivate_DEF = MModel.Language.voiceMailActivate_1;
            voiceMailDeActivate_DEF = MModel.Language.voiceMailDeActivate_1;
            voiceMailListen_DEF = MModel.Language.voiceMailListen_1;

            version_DEF = MModel.Language.version_1;
            system_DEF = MModel.Language.system_1;

            programExitON_DEF = MModel.Language.programExitON_1;
            programExitOFF_DEF = MModel.Language.programExitOFF_1;

            transferBack_DEF = MModel.Language.transferBack_1;

            textYourNumber_DEF = MModel.Language.textYourNumber_1;
            textNewNumber_DEF = MModel.Language.textNewNumber_1;
            voiceMailActivate_DEF = MModel.Language.voiceMailActivate_1;
            voiceMailDeActivate_DEF = MModel.Language.voiceMailDeActivate_1;
            voiceMailListen_DEF = MModel.Language.voiceMailListen_1;
            version_DEF = MModel.Language.version_1;
            system_DEF = MModel.Language.system_1;

            programExitON_DEF = MModel.Language.programExitON_1;
            programExitOFF_DEF = MModel.Language.programExitOFF_1;
            programConference_DEF = MModel.Language.programConference_1;

            absentLunchLG_DEF = MModel.Language.absentLunchLG_1;
            absentVacationLG_DEF = MModel.Language.absentVacationLG_1;
            absentBackAtLG_DEF = MModel.Language.absentBackAtLG_1;
            absentDateBackLG_DEF = MModel.Language.absentDateBackLG_1;
            absentOutLG_DEF = MModel.Language.absentOutLG_1;
            absentBusyLG_DEF = MModel.Language.absentBusyLG_1;
            absentTravelLG_DEF = MModel.Language.absentTravelLG_1;
            absentVABLG_DEF = MModel.Language.absentVABLG_1;
            absentTALKEDLG_DEF = MModel.Language.absentTALKEDLG_1;

            absentCallLG_DEF = MModel.Language.absentCallLG_1;
            absentExtLG_DEF = MModel.Language.absentExtLG_1;
            GSMmodemLG_DEF = MModel.Language.GSMmodemLG_1;
            isON_DEF = MModel.Language.isON_1;
            isOFF_DEF = MModel.Language.isOFF_1;
            mex_DEF = MModel.Language.mex_1;
            listenVoicemail_DEF = MModel.Language.listenVoicemail_1;
            recordVoicemail_DEF = MModel.Language.recordVoicemail_1;
            adminVoicemail_DEF = MModel.Language.adminVoicemail_1;
            activeVoicemail_DEF = MModel.Language.activeVoicemail_1;
            deactiveVoicemail_DEF = MModel.Language.deactiveVoicemail_1;
            accessCode_DEF = MModel.Language.accessCode_1;
            number_DEF = MModel.Language.number_1;

            userName_DEF = MModel.Language.userName_1;
            message_DEF = MModel.Language.message_1;
            inside_DEF = MModel.Language.inside_1;
            private_DEF = MModel.Language.private_1;
            statusToday_DEF = MModel.Language.statusToday_1;
            statusTodayTomorrow_DEF = MModel.Language.statusTodayTomorrow_1;
            statusDate_DEF = MModel.Language.statusDate_1;
            statusContact_DEF = MModel.Language.statusContact_1;
            tv_lunch_DEF = MModel.Language.tv_lunch_1;
            hhmm_DEF = MModel.Language.hhmm_1;
            mmdd_DEF = MModel.Language.mmdd_1;



        }

        // --- Övriga språk beroende på vilket nummer som är satt i DB.

        /* Danish, Dutch, Finnish, French, German,
           Norwegian, Italian, Spanish, Swedish */
        else {

            settingsDefaultAbout_DEF = MModel.Language.settingsDefaultAbout_2;
            SettingsDefaultAccessPINcode_DEF = MModel.Language.
                                               SettingsDefaultAccessPINcode_2;
            extensionDefaultAddNew_DEF = MModel.Language.extensionDefaultAddNew_2;
            callForwardDefaultAllCalls_DEF = MModel.Language.
                                             callForwardDefaultAllCalls_2;
            absentDefaultAtExt_DEF = MModel.Language.absentDefaultAtExt_2;
            settingsDefaultAutoAccess_DEF = MModel.Language.
                                            settingsDefaultAutoAccess_2;
            genDefaultBack_DEF = MModel.Language.genDefaultBack_2;
            absentDefaultBackAt_DEF = MModel.Language.absentDefaultBackAt_2;
            callForwardDefaultBusy_DEF = MModel.Language.callForwardDefaultBusy_2;
            callForwardDefaultBusyNoAnswer_DEF =
                    MModel.Language.callForwardDefaultBusyNoAnswer_2;
            callDefaultCall_DEF = MModel.Language.callDefaultCall_2;
            extensionDefaultCall_DEF = MModel.Language.extensionDefaultCall_2;
            callForwardDefault_DEF = MModel.Language.callForwardDefault_2;
            msgDefaultCallistIsEmpty_DEF = MModel.Language.msgDefaultCallistIsEmpty_2;
            genDefaultCancel_DEF = MModel.Language.genDefaultCancel_2;
            alertDefaultCantAddAnymoreExt_DEF =
                    MModel.Language.alertDefaultCantAddAnymoreExt_2;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    MModel.Language.alertDefaultCouldntAddChangesEmtpyField_2;
            alertDefaultCouldntAddRecord_DEF = MModel.Language.
                                               alertDefaultCouldntAddRecord_2;
            alsertDefaultChangesSave_DEF = MModel.Language.alertDefaultChangesSave_2;
            mgsDefaultContactListIsEmpty_DEF = MModel.Language.
                                               mgsDefaultContactListIsEmpty_2;
            alertDefaultCountryCodeError_DEF = MModel.Language.
                                               alertDefaultCountryCodeError_2;
            settingsDefaultCountryCode_DEF = MModel.Language.
                                             settingsDefaultCountryCode_2;
            genDefaultDelete_DEF = MModel.Language.genDefaultDelete_2;
            genDefaultDeleteAll_DEF = MModel.Language.genDefaultDeleteAll_2;
            dialledCallsDefault_DEF = MModel.Language.dialledCallsDefault_2;
            callForwardDefaultDontDisturb_DEF =
                    MModel.Language.callForwardDefaultDontDisturb_2;
            genDefaultEdit_DEF = MModel.Language.genDefaultEdit_2;
            settingsDefaultEditPBXAccess_DEF = MModel.Language.
                                               settingsDefaultEditPBXAccess_2;
            absentDefaultEditPresence_DEF = MModel.Language.
                                            absentDefaultEditPresence_2;
            voiceMailDefaultEditVoicemail_DEF =
                    MModel.Language.voiceMailDefaultEditVoicemail_2;
            enterDefaultEngerCharacter_DEF = MModel.Language.
                                             enterDefaultEngerCharacter_2;
            enterDefaultEnterExtension_DEF = MModel.Language.
                                             enterDefaultEnterExtension_2;
            enterDefaultEnterGroupNumber_DEF = MModel.Language.
                                               enterDefaultEnterGroupNumber_2;
            enterDefaultEnterHHMM_DEF = MModel.Language.enterDefaultEnterHHMM_2;
            enterDefaultEnterNumber_DEF = MModel.Language.enterDefaultEnterNumber_2;
            alertDefaultErrorChangeTo_DEF = MModel.Language.
                                            alertDefaultErrorChangeTo_2;
            alertDefaultError_DEF = MModel.Language.alertDefaultError_2;
            enterDefaultEnterMMDD_DEF = MModel.Language.enterDefaultEnterMMDD_2;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    MModel.Language.exitDefaultExitTheProgramYesOrNo_2;
            genDefaultExit_DEF = MModel.Language.genDefaultExit_2;
            settingsDefaultExtension_DEF = MModel.Language.settingsDefaultExtension_2;
            callExtensionDefaultExtensionWasAdded_DEF =
                    MModel.Language.callExtensionDefaultExtensionWasAdded_2;
            callForwardDefaultExternCalls_DEF =
                    MModel.Language.callForwardDefaultExternCalls_2;
            absentDefaultGoneHome_DEF = MModel.Language.absentDefaultGoneHome_2;
            groupsDefaultGroups_DEF = MModel.Language.groupsDefaultGroups_2;
            settingsDefaultHelp_DEF = MModel.Language.settingsDefaultHelp_2;
            absentDefaultInAMeeting_DEF = MModel.Language.absentDefaultInAMeeting_2;
            alertDefaultInfo_DEF = MModel.Language.alertDefaultInfo_2;
            alertDefaultInstedOf_DEF = MModel.Language.alertDefaultInstedOf_2;
            callForwardDefaultInternCalls_DEF =
                    MModel.Language.callForwardDefaultInternCalls_2;
            settingsDefaultLanguage_DEF = MModel.Language.settingsDefaultLanguage_2;
            settingsDefaultLineAccess_DEF = MModel.Language.
                                            settingsDefaultLineAccess_2;
            groupsDefaultLoginAllGroups_DEF = MModel.Language.
                                              groupsDefaultLoginAllGroups_2;
            groupsDefaultLoginSpecificGroup_DEF =
                    MModel.Language.groupsDefaultLoginSpecificGroup_2;
            groupsDefaultLogoutAllGroups_DEF = MModel.Language.
                                               groupsDefaultLogoutAllGroups_2;
            groupsDefaultLogoutSpecificGroup_DEF =
                    MModel.Language.groupsDefaultLogoutSpecificGroup_2;
            alertDefaultMaxSize_DEF = MModel.Language.alertDefaultMaxSize_2;
            genDefaultMinimise_DEF = MModel.Language.genDefaultMinimise_2;
            callExtensionDefaultName_DEF = MModel.Language.callExtensionDefaultName_2;
            exitDefaultNo_DEF = MModel.Language.exitDefaultNo_2;
            callForwardDefaultNoAnswer_DEF = MModel.Language.
                                             callForwardDefaultNoAnswer_2;
            settingsDefaultOptions_DEF = MModel.Language.settingsDefaultOptions_2;
            absentDefaultOutUntil_DEF = MModel.Language.absentDefaultOutUntil_2;
            settingsDefaultPINcode_DEF = MModel.Language.settingsDefaultPINcode_2;
            settingsDefaultPreEditCode_DEF = MModel.Language.
                                             settingsDefaultPreEditCode_2;
            callForwardDefaultRemove_DEF = MModel.Language.callForwardDefaultRemove_2;
            absentDefaultRemovePresence_DEF = MModel.Language.
                                              absentDefaultRemovePresence_2;
            exitDefaultRestartProgram_DEF = MModel.Language.
                                            exitDefaultRestartProgram_2;
            alertDefaultSaveChanges_DEF = MModel.Language.alertDefaultSaveChanges_2;
            genDefaultSave_DEF = MModel.Language.genDefaultSave_2;
            settingsDefaultSelectCountryCode_DEF =
                    MModel.Language.settingsDefaultSelectCountryCode_2;
            genDefaultSelect_DEF = MModel.Language.genDefaultSelect_2;
            genDefaultSend_DEF = MModel.Language.genDefaultSend_2;
            absentDefaultSetPresence_DEF = MModel.Language.absentDefaultSetPresence_2;
            settingsDefaultSettings_DEF = MModel.Language.settingsDefaultSettings_2;
            callExtensionDefaultSurname_DEF = MModel.Language.
                                              callExtensionDefaultSurname_2;
            settingsDefaultSwitchboardNumber_DEF =
                    MModel.Language.settingsDefaultSwitchboardNumber_2;

            absentDefaultSystemAttOne_DEF = MModel.Language.
                                            absentDefaultSystemAttOne_2;
            absentDeafaultSystemAttTwo_DEF = MModel.Language.
                                             absentDeafaultSystemAttTwo_2;
            absentDefaultPersonalAtt_DEF = MModel.Language.absentDefaultPersonalAtt_2;

            absentDeafaultWillReturnSoon_DEF = MModel.Language.
                                               absentDeafaultWillReturnSoon_2;
            alertDefaultWrongInputTryAgain_DEF =
                    MModel.Language.alertDefaultWrongInputTryAgain_2;
            voiceMailDefaultVoiceMail_DEF = MModel.Language.
                                            voiceMailDefaultVoiceMail_2;
            genDefaultYes_DEF = MModel.Language.genDefaultYes_2;

            accessPBXDefault_DEF = MModel.Language.accessPBXDefault_2;
            autoAccessDefault_DEF = MModel.Language.autoAccessDefault_2;
            accessViaPINCodeDefault_DEF = MModel.Language.accessViaPINCodeDefault_2;
            dialDefault_DEF = MModel.Language.dialDefault_2;

            alertExitMEXOnMessage_DEF = MModel.Language.alertExitMEXOnMessage_2;
            alertMessageMEXOn_DEF = MModel.Language.alertMessageMEXOn_2;
            alertMessageMEXOff_DEF = MModel.Language.alertMessageMEXOff_2;
            alertMessageMexServerInfo_DEF = MModel.Language.
                                            alertMessageMexServerInfo_2;
            alertMessageMexAlreadyON_DEF = MModel.Language.alertMessageMexAlreadyON_2;
            alertMessageMexAlreadyOFF_DEF = MModel.Language.
                                            alertMessageMexAlreadyOFF_2;
            mainListAttributMexOn_DEF = MModel.Language.mainListAttributMexOn_2;
            mainListAttributMexOff_DEF = MModel.Language.mainListAttributMexOff_2;
            operatorVoicemail_DEF = MModel.Language.operatorVoicemail_2;

            AlertMessageExitText_DEF = MModel.Language.AlertMessageExitText_2;

            absentTimeOfReturn_DEF = MModel.Language.absentTimeOfReturn_2;
            absentDateOfReturn_DEF = MModel.Language.absentDateOfReturn_2;
            absentLunch_DEF = MModel.Language.absentLunch_2;
            absentMeeting_DEF = MModel.Language.absentMeeting_2;
            absentVacation_DEF = MModel.Language.absentVacation_2;
            absentIllness_DEF = MModel.Language.absentIllness_2;

            callForwardTransfer_DEF = MModel.Language.callForwardTransfer_2;
            callForwardPermForward_DEF = MModel.Language.callForwardPermForward_2;
            callForwardInterForward_DEF = MModel.Language.callForwardInterForward_2;
            callForwardExternForward_DEF = MModel.Language.callForwardExternForward_2;
            callForwardCancelExtern_DEF = MModel.Language.callForwardCancelExtern_2;
            callForwardCancelPermIntern_DEF = MModel.Language.
                                              callForwardCancelPermIntern_2;

            textYourNumber_DEF = MModel.Language.textYourNumber_2;
            textNewNumber_DEF = MModel.Language.textNewNumber_2;

            voiceMailActivate_DEF = MModel.Language.voiceMailActivate_2;
            voiceMailDeActivate_DEF = MModel.Language.voiceMailDeActivate_2;
            voiceMailListen_DEF = MModel.Language.voiceMailListen_2;

            version_DEF = MModel.Language.version_2;
            system_DEF = MModel.Language.system_2;

            programExitON_DEF = MModel.Language.programExitON_2;
            programExitOFF_DEF = MModel.Language.programExitOFF_2;

            transferBack_DEF = MModel.Language.transferBack_2;

            textYourNumber_DEF = MModel.Language.textYourNumber_2;
            textNewNumber_DEF = MModel.Language.textNewNumber_2;
            voiceMailActivate_DEF = MModel.Language.voiceMailActivate_2;
            voiceMailDeActivate_DEF = MModel.Language.voiceMailDeActivate_2;
            voiceMailListen_DEF = MModel.Language.voiceMailListen_2;
            version_DEF = MModel.Language.version_2;
            system_DEF = MModel.Language.system_2;

            programExitON_DEF = MModel.Language.programExitON_2;
            programExitOFF_DEF = MModel.Language.programExitOFF_2;
            programConference_DEF = MModel.Language.programConference_2;

            absentLunchLG_DEF = MModel.Language.absentLunchLG_2;
            absentVacationLG_DEF = MModel.Language.absentVacationLG_2;
            absentBackAtLG_DEF = MModel.Language.absentBackAtLG_2;
            absentDateBackLG_DEF = MModel.Language.absentDateBackLG_2;
            absentOutLG_DEF = MModel.Language.absentOutLG_2;
            absentBusyLG_DEF = MModel.Language.absentBusyLG_2;
            absentTravelLG_DEF = MModel.Language.absentTravelLG_2;
            absentVABLG_DEF = MModel.Language.absentVABLG_2;
            absentTALKEDLG_DEF = MModel.Language.absentTALKEDLG_2;

            absentCallLG_DEF = MModel.Language.absentCallLG_2;
            absentExtLG_DEF = MModel.Language.absentExtLG_2;
            GSMmodemLG_DEF = MModel.Language.GSMmodemLG_2;
            isON_DEF = MModel.Language.isON_2;
            isOFF_DEF = MModel.Language.isOFF_2;
            mex_DEF = MModel.Language.mex_2;
            listenVoicemail_DEF = MModel.Language.listenVoicemail_2;
            recordVoicemail_DEF = MModel.Language.recordVoicemail_2;
            adminVoicemail_DEF = MModel.Language.adminVoicemail_2;
            activeVoicemail_DEF = MModel.Language.activeVoicemail_2;
            deactiveVoicemail_DEF = MModel.Language.deactiveVoicemail_2;
            accessCode_DEF = MModel.Language.accessCode_2;
            number_DEF = MModel.Language.number_2;

            userName_DEF = MModel.Language.userName_2;
            message_DEF = MModel.Language.message_2;
            inside_DEF = MModel.Language.inside_2;
            private_DEF = MModel.Language.private_2;
            statusToday_DEF = MModel.Language.statusToday_2;
            statusTodayTomorrow_DEF = MModel.Language.statusTodayTomorrow_2;
            statusDate_DEF = MModel.Language.statusDate_2;
            statusContact_DEF = MModel.Language.statusContact_2;
            tv_lunch_DEF = MModel.Language.tv_lunch_2;
            hhmm_DEF = MModel.Language.hhmm_2;
            mmdd_DEF = MModel.Language.mmdd_2;


        }

    }


}
