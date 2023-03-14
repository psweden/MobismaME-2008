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

public class CONF {

    // - Inst�llningar ---

    public static final int MINIMISE = 1;   // 1 f�r Symbian 0 f�r alla andra
    public static final String MEX = "0"; // mex on '1' eller off '0'
    public static final String SWTBNR = "+46812200749"; // v�xelnummer
    public static final String LA = "0"; // Linjeaccess
    public static final String CCODE = "46"; // Landsnummer
    public static final String DBG = "0"; // Debug on eller off
    public static final String TESTURL =
            "http://www.mobisma.com/socketapi/mobismaSRV.php"; // Server testurl
    public static final String EXTURL =
            "http://www.mobisma.com:80/socketapi/mobilesock.php"; // url send logdata
    public static final String INTURL = "socket://127.0.0.1:8100"; // Inter socket url
    public static final String DEBUG = "0"; // Debug system. Om '1' k�r mot webbservern, f�r test.
    public static final String HGP = "197"; // 'Hangupkey' default
    public static final String VM = "222"; // Operat�r r�stbrevl�da
    public static final String PCODE = ""; // PINkod
    public static final String ANKN = "202"; // Anknytning
    public static final String LANG = "9"; // Spr�k
    public static final String PRECODE = "*7"; // Pre edit code
    public static final String PBXVM = "500"; // V�xelns r�stbrevl�dan
    public static final String DEMO = "0"; // Dema ja '1' eller nej '0'
    public static final String COMPANYNAME = "mobisma AB"; // F�retagsnamn
    public static final String NAME = "Peter Albertsson"; // Personnamn
    public static final String PRGNAME = "mobismaME"; // Personnamn
    public static final String IMEI2 = "00460881501594500"; // IMEI serienummer 00460101501594500 emulator,  356810032809537, 352921027118984
    public static final String DTMFP = ";postd="; // DTMF-tecken f�r att s�nda dtmf. ';postd=' eller '/p'.
    public static final String DL = "1"; // Default spr�k '1' inte engelska, '0' == engelska.
    public static final String PBRAND = "Sony Ericsson"; // Telefonm�rke
    public static final String PMODEL = "Satio"; // Telefonmodell
    public static final String PBXNAME = "Panasonic"; // V�xelnamn
    public static final String GSMNUMBER = "+46720018019"; // gsmnumber
    public static final String IMEIPROPERTY = "com.sonyericsson.imei"; // System.property f�r att ta fram IMEI nummer.
    public static final String JAD = "C:\\mobisma\\panasonicme.jad"; // Jad install
    public static final String DTMF1 = "p&LA#"; // DTMF mask
    public static final String IS = "3"; // Internsamtal length
    public static final int NATIVE = 0; // operativsystem. java 0, Symbian 1, RIM 2, win 3, ihpone 4, android 5.
    // V�xeltyp >> Panasonic '1', Aastra ascotel '2', Aastra BP '3', Aastra md110 '4', Aastra mxone '5', LG '6', Avaya '9', Siemens '10'.
    public static final String PBXID = "1";

    public static final String xhvtype = ""; // TV el. 8020


    public CONF() {


    }


}
