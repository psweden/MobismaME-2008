package MModel;

import MServer.Server;
import MControll.Main_Controll;
import MModel.ConnectTCPIP_Socket;
import MDataStore.DataBase_RMS;
import java.io.IOException;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStoreException;

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
public class Methods {

    private MControll.Main_Controll mainControll;
    private MModel.ConnectTCPIP_Socket tcpip;
    private MDataStore.DataBase_RMS rms;

    private String
            switchBoardNumber_PBX,
    lineAccess_PBX,
    countryCode_PBX,
    voiceMailOperator_PBX,
    voiceMailSwitchboard_PBX,
    extensionNumber_PBX,
    pinCodeNumber_PBX,
    pbx_type,
    precode_PBX,
    mexOnOffLG,

    callingVoicemail;

    public String
            getAbsentSEND,
    transferCall,
    getCallForwardInternSEND,
    getCallForwardExternSEND_1,
    getCallForwardExternRemoveSEND,
    getConnectPhoneCallSEND,
    logoffSpeficGroups,
    loginSpeficGroups;

    public Methods() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();
        this.switchBoardNumber_PBX = rms.switchBoardNumber_PBX;
        this.lineAccess_PBX = rms.lineAccess_PBX;
        this.countryCode_PBX = rms.countryCode_PBX;
        this.voiceMailOperator_PBX = rms.voiceMailOperator_PBX;
        this.voiceMailSwitchboard_PBX = rms.voiceMailSwitchboard_PBX;
        this.extensionNumber_PBX = rms.getExtensionNumber();
        this.pinCodeNumber_PBX = rms.getPinCodeNumber();
        this.pbx_type = rms.getPBXType();
        this.precode_PBX = rms.getPreEditCode();
        this.transferCall = rms.getTransferCall();

        rms = null;

    }

    // =============== Aastra Ascotel ==========================================

    public void RefreshServerAastraPINcode() {

        Thread aas = new Thread() {

            public void run() {

                try {
                    String start_mexon_01 = "r," +
                                            switchBoardNumber_PBX + "," +
                                            extensionNumber_PBX +
                                            "*" + pinCodeNumber_PBX + "#" +
                                            lineAccess_PBX + ","
                                            + countryCode_PBX + ","
                                            + voiceMailOperator_PBX +
                                            ",";

                    MServer.Server server = new Server();
                    server.serverCMD(start_mexon_01);
                    server = null;

                    System.out.println("MexON >> " + start_mexon_01);

                } catch (Exception ex) {
                }

            }
        };
        aas.start();

    }


    //================ Aastra Business Phone ==================================

    // Aastra Business Phone
    public void removeAbsent() {

        Thread remove_Absent = new Thread() {


            public void run() {

                try {
                    String absentREMOVE = "h" + "," +
                                          switchBoardNumber_PBX +
                                          "," + "#23#" +
                                          ",";

                    MServer.Server server = new Server();
                    server.serverCMD(absentREMOVE);
                    server = null;

                    System.out.println("Absent REMOVE >> " +
                                       absentREMOVE);
                } catch (Exception ex) {
                }
            }
        };
        remove_Absent.start();

    }


    // Aastra Business Phone
    public void logInAllGroups() {

        Thread logInAllGroups = new Thread() {

            public void run() {

                try {
                    String logInAllGroups = "h" + "," +
                                            switchBoardNumber_PBX +
                                            "," + "*28**#" +
                                            ",";

                    MServer.Server server = new Server();
                    server.serverCMD(logInAllGroups);
                    server = null;
                    System.out.println(
                            "Login all group >> " +
                            logInAllGroups);
                } catch (Exception ex) {
                }

            }
        };
        logInAllGroups.start();
    }

    // Aastra Business Phone
    public void logOffAllGroups() {

        Thread logOffAllGroups = new Thread() {

            public void run() {

                try {
                    String logOffAllGroups = "h" + "," +
                                             switchBoardNumber_PBX +
                                             "," + "#28**#" +
                                             ",";

                    MServer.Server server = new Server();
                    server.serverCMD(logOffAllGroups);
                    server = null;

                    System.out.println(
                            "Logout all group >> " +
                            logOffAllGroups);
                } catch (Exception ex) {
                }
            }
        };
        logOffAllGroups.start();

    }

    // Aastra Business Phone
    public void logInSpecificGroups() {

        Thread logInSpecificGroups = new Thread() {

            public void run() {

                try {
                    String logInSpecificGroups = "h" + "," +
                                                 switchBoardNumber_PBX +
                                                 "," + "*28*" +
                                                 loginSpeficGroups + "#" + ",";

                    if (!loginSpeficGroups.equals("")) {

                        MServer.Server server = new Server();
                        server.serverCMD(logInSpecificGroups);
                        server = null;

                        System.out.println(
                                "Login group >> " +
                                logInSpecificGroups);
                    }

                } catch (Exception ex) {
                }
            }
        };
        logInSpecificGroups.start();

    }

    // Aastra Business Phone
    public void logOffSpecificGroups() {

        Thread logOutSpecificGroups = new Thread() {

            public void run() {

                try {
                    String logOutSpecificGroups = "h" + "," +
                                                  switchBoardNumber_PBX +
                                                  "," + "#28*" +
                                                  logoffSpeficGroups + "#" +
                                                  ",";

                    if (!logoffSpeficGroups.equals("")) {

                        MServer.Server server = new Server();
                        server.serverCMD(logOutSpecificGroups);
                        server = null;

                        System.out.println(
                                "Logout group >> " +
                                logOutSpecificGroups);
                    }

                } catch (Exception ex) {
                }
            }
        };
        logOutSpecificGroups.start();

    }

    // Aastra Business Phone
    public void callForwardToNumber() {

        Thread callForward_1 = new Thread() {

            public void run() {

                try {
                    String SetVoiceMail =
                            "h" + "," +
                            switchBoardNumber_PBX +
                            "," + "*21#" + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(SetVoiceMail);
                    server = null;

                    tcpip = null;

                    System.out.println(
                            "Fast Vidarekoppling >> " +
                            SetVoiceMail);
                } catch (Exception ex) {
                }

            }
        };
        callForward_1.start();
    }

    // Aastra Business Phone
    public void callForwardIntern() {

        Thread callForward_2 = new Thread() {

            public void run() {

                try {

                    String absentSEND = "h" + "," +
                                        switchBoardNumber_PBX +
                                        "," + "*21*" + getCallForwardInternSEND +
                                        "#" + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(absentSEND);
                    server = null;

                    System.out.println("Intern Vidarekoppling >> " + absentSEND);
                } catch (Exception ex) {
                }
            }
        };
        callForward_2.start();

    }

    // Aastra Business Phone
    public void callForwardExtern() {

        Thread callForward_3 = new Thread() {

            public void run() {

                try {

                    String absentSEND = "h" + "," +
                                        switchBoardNumber_PBX +
                                        "," + "*22*"
                                        + getCallForwardExternSEND_1 +
                                        "#" + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(absentSEND);
                    server = null;

                    System.out.println("Intern Vidarekoppling >> " + absentSEND);
                } catch (Exception ex) {
                }
            }
        };
        callForward_3.start();

    }

    // Aastra Business Phone
    public void callForwardRemoveExtern() {

        Thread callForward_3 = new Thread() {

            public void run() {

                try {

                    String absentSEND = "h" + "," +
                                        switchBoardNumber_PBX +
                                        "," + "#22#,";

                    MServer.Server server = new Server();
                    server.serverCMD(absentSEND);
                    server = null;

                    System.out.println("Intern Vidarekoppling >> " + absentSEND);
                } catch (Exception ex) {
                }
            }
        };
        callForward_3.start();

    }

    // Aastra Business Phone
    public void callForwardRemoveInternExtern() {

        Thread callForward_5 = new Thread() {

            public void run() {

                try {
                    String SetVoiceMail =
                            "h" + "," +
                            switchBoardNumber_PBX +
                            "," + "#21#" + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(SetVoiceMail);
                    server = null;

                    System.out.println(
                            "Ta bort intern/extern vidarekoppling >> " +
                            SetVoiceMail);
                } catch (Exception ex) {
                }

            }
        };
        callForward_5.start();
    }

    // Aastra Business Phone
    public void voiceMailSet() {

        Thread voiceMail_1 = new Thread() {

            public void run() {

                try {
                    String SetVoiceMail =
                            "a" + "," +
                            switchBoardNumber_PBX +
                            "," + "*21*" +
                            voiceMailSwitchboard_PBX + "#"
                            + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(SetVoiceMail);
                    server = null;

                    System.out.println(
                            "PBX VoiceMail Beställ >> " +
                            SetVoiceMail);
                } catch (Exception ex) {
                }

            }
        };
        voiceMail_1.start();
    }

    // Aastra Business Phone
    public void voiceMailRemove() {

        Thread voiceMail_2 = new Thread() {

            public void run() {

                try {
                    String RemoveVoiceMail = "h" + "," +
                                             switchBoardNumber_PBX +
                                             "," + "#21#" + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(RemoveVoiceMail);
                    server = null;

                    System.out.println(
                            "PBX VoiceMail Avbetställ >> " +
                            RemoveVoiceMail);
                    System.out.println(
                            "PBX VoiceMail Avbetställ resp >> " +
                            Main_Controll.response);
                } catch (Exception ex) {
                }

            }
        };
        voiceMail_2.start();
    }

    // Aastra Business Phone
    public void voiceMailListen() {

        Thread voiceMail_3 = new Thread() {

            public void run() {

                try {
                    String ListenVoiceMail = "a" + "," +
                                             switchBoardNumber_PBX +
                                             "," + "*59#" + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(ListenVoiceMail);
                    server = null;

                    System.out.println(
                            "PBX VoiceMail Lyssna >> " +
                            ListenVoiceMail);
                } catch (Exception ex) {
                }

            }
        };
        voiceMail_3.start();
    }

    // ==================== LG-Nortel ==========================================

    public void voiceMailListenRecordAdminLG(String s) {

        this.callingVoicemail = s;

        Thread voiceMailLG = new Thread() {

            public void run() {

                try {
                    String ListenVoiceMail = "a" + "," + callingVoicemail;

                    MServer.Server server = new Server();
                    server.serverCMD(ListenVoiceMail);
                    server = null;
                } catch (Exception ex) {
                }

            }
        };
        voiceMailLG.start();
    }

    public void voiceMailActive_deactiveLG(String s) {

        this.callingVoicemail = s;

        Thread voiceMailACLG = new Thread() {

            public void run() {

                try {
                    String ListenVoiceMail = "h" + "," + callingVoicemail;

                    MServer.Server server = new Server();
                    server.serverCMD(ListenVoiceMail);
                    server = null;
                } catch (Exception ex) {
                }

            }
        };
        voiceMailACLG.start();
    }

    public void LGMexOnOff(String s) {

        this.mexOnOffLG = s;

        Thread voiceMailACLG = new Thread() {

            public void run() {

                try {
                    String ListenVoiceMail = "h" + "," + mexOnOffLG;

                    MServer.Server server = new Server();
                    server.serverCMD(ListenVoiceMail);
                    server = null;
                } catch (Exception ex) {
                }

            }
        };
        voiceMailACLG.start();
    }



    // ==================== Panasonic metoder ==================================


    public void PanasonicSetAbsent() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex4) {
        } catch (RecordStoreNotOpenException ex4) {
        } catch (InvalidRecordIDException ex4) {
        } catch (RecordStoreException ex4) {
        }

        try {
            this.pbx_type = rms.getPBXType();
        } catch (RecordStoreNotOpenException ex5) {
        } catch (InvalidRecordIDException ex5) {
        } catch (RecordStoreException ex5) {
        }
        if (pbx_type.equals("3")) {

            Thread a_setAbsent = new Thread() {
                public void run() {
                    String set_Absent = "h," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX + getAbsentSEND + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(set_Absent);
                    server = null;

                    System.out.println(
                            "Sätt hänvisning panasonic AutoAccess >> " +
                            set_Absent);

                }
            };
            a_setAbsent.start();

        }
        if (pbx_type.equals("5")) {

            Thread a_setAbsent_pinCode = new Thread() {

                public void run() {
                    String set_Absent = "h," +
                                        switchBoardNumber_PBX + ",*47" +
                                        extensionNumber_PBX +
                                        pinCodeNumber_PBX +
                                        precode_PBX + getAbsentSEND + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(set_Absent);
                    server = null;

                    System.out.println("Sätt hänvisning panasonic PINCODE >> " +
                                       set_Absent);

                }
            };
            a_setAbsent_pinCode.start();

        }

        rms = null;

    }

    public void removePanasonicAbsent() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex4) {
        } catch (RecordStoreNotOpenException ex4) {
        } catch (InvalidRecordIDException ex4) {
        } catch (RecordStoreException ex4) {
        }

        try {
            this.pbx_type = rms.getPBXType();
        } catch (RecordStoreNotOpenException ex5) {
        } catch (InvalidRecordIDException ex5) {
        } catch (RecordStoreException ex5) {
        }
        if (pbx_type.equals("3")) {

            Thread a_absentRemove = new Thread() {
                public void run() {
                    String absent_remove = "h," +
                                           switchBoardNumber_PBX + "," +
                                           precode_PBX + "500" + "#,";

                    MServer.Server server = new Server();
                    server.serverCMD(absent_remove);
                    server = null;

                    System.out.println(
                            "Ta bort hänvisning panasonic AutoAccess >> " +
                            absent_remove);

                }
            };
            a_absentRemove.start();

        }
        if (pbx_type.equals("5")) {

            Thread a_absentRemove_pinCode = new Thread() {

                public void run() {
                    String absent_remove = "h," +
                                           switchBoardNumber_PBX + ",*47" +
                                           extensionNumber_PBX +
                                           pinCodeNumber_PBX +
                                           precode_PBX + "500" + "#,";

                    MServer.Server server = new Server();
                    server.serverCMD(absent_remove);
                    server = null;

                    System.out.println(
                            "Ta bort hänvisning panasonic PINCODE >> " +
                            absent_remove);

                }
            };
            a_absentRemove_pinCode.start();

        }

        rms = null;

    }

    public void RefreshServerPanasonicPINcode() {

        Thread Pan = new Thread() {

            public void run() {

                try {
                    String start_mexon_01 = "r," +
                                            switchBoardNumber_PBX + "," +
                                            "*47" + extensionNumber_PBX +
                                            pinCodeNumber_PBX +
                                            lineAccess_PBX + ","
                                            + countryCode_PBX + "," +
                                            voiceMailOperator_PBX + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(start_mexon_01);
                    server = null;

                    System.out.println("MexON >> " + start_mexon_01);

                } catch (Exception ex) {
                }

            }
        };
        Pan.start();

    }

    public void RefreshPINCODEServer() {

        Thread PINPanasonic = new Thread() {

            public void run() {

                if (lineAccess_PBX.equals("NONE")) {

                    lineAccess_PBX = "";

                }

                try {
                    String start_mexon_01 = "r," +
                                            switchBoardNumber_PBX + "," +
                                            "*47" + extensionNumber_PBX +
                                            pinCodeNumber_PBX +
                                            lineAccess_PBX + ","
                                            + countryCode_PBX + "," +
                                            voiceMailOperator_PBX + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(start_mexon_01);
                    server = null;

                    System.out.println("MexON Refresh AutoAccess>> " +
                                       start_mexon_01);

                } catch (Exception ex) {
                }

            }
        };
        PINPanasonic.start();

    }


    // ==================== Generella metoder ==================================

    public void Minimize() {

        Thread minimise = new Thread() {


            public void run() {

                try {
                    String minimize_mexoff = "m,";
                    MServer.Server server = new Server();
                    server.serverCMD(minimize_mexoff);
                    server = null;

                    System.out.println("Minimera >> " + minimize_mexoff);
                } catch (Exception ex) {
                }

            }
        };
        minimise.start();

    }

    public void sendAbsent() {

        Thread absentSEND = new Thread() {

            public void run() {

                try {
                    String absentSEND = "h" + "," +
                                        switchBoardNumber_PBX +
                                        "," + getAbsentSEND +
                                        ",";

                    MServer.Server server = new Server();
                    server.serverCMD(absentSEND);
                    server = null;

                    System.out.println("Absent SEND >> " + absentSEND);
                } catch (Exception ex) {
                }
            }
        };
        absentSEND.start();

    }

    public void RefreshServer() {

        Thread a = new Thread() {

            public void run() {

                if (lineAccess_PBX.equals("NONE")) {

                    lineAccess_PBX = "";

                }

                try {
                    String start_mexon_01 = "r," +
                                            switchBoardNumber_PBX + "," +
                                            lineAccess_PBX + ","
                                            + countryCode_PBX + "," +
                                            voiceMailOperator_PBX + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(start_mexon_01);
                    server = null;

                    System.out.println("MexON Refresh AutoAccess>> " +
                                       start_mexon_01);

                } catch (Exception ex) {
                }

            }
        };
        a.start();

    }

    public void ConnectPhoneCall() {

        Thread connectPhoneCall = new Thread() {

            public void run() {

                try {
                    String connectSend = "g" + "," + transferCall +
                                         getConnectPhoneCallSEND + ",";

                    MServer.Server server = new Server();
                    server.serverCMD(connectSend);
                    server = null;

                    System.out.println("Koppla samtal >> " + connectSend);
                } catch (Exception ex) {
                }
            }
        };

        connectPhoneCall.start();

    }


}
