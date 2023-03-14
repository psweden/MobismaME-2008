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

import javax.microedition.lcdui.List;
import MModel.CONF;
import Mxhv.M8020;
import Mxhv.TV;
import java.io.*;
import javax.microedition.rms.*;
import javax.microedition.rms.*;
import javax.microedition.rms.*;

public class Check_Xhv {


    public List hvList;
    public CONF conf;
    public M8020 c8020;
    public TV cTV;

    public Check_Xhv() {

        this.c8020 = c8020;
        this.cTV = cTV;

    }

    public List getxHvList(){

        this.conf = new CONF();

        if(conf.xhvtype.equals("8020")){

           this.c8020 = new M8020();

            try {
                return c8020.getAbsentList();
            } catch (IOException ex) {
                return null;
            } catch (RecordStoreNotOpenException ex) {
                return null;
            } catch (InvalidRecordIDException ex) {
                return null;
            } catch (RecordStoreException ex) {
                return null;
            }

        }
        if(conf.xhvtype.equals("TV")){

            this.cTV = new TV();

            try {
                return cTV.getAbsentList();
            } catch (IOException ex) {
                return null;
            } catch (RecordStoreNotOpenException ex) {
                return null;
            } catch (InvalidRecordIDException ex) {
                return null;
            } catch (RecordStoreException ex) {
                return null;
            }

        }

        return hvList;
    }

}
