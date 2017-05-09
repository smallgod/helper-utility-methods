/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities.dsmbridge;

import com.library.customexception.MyCustomException;
import com.library.datamodel.Constants.NamedConstants;
import com.library.datamodel.Constants.TaskType;
import com.library.datamodel.dsm_bridge.TbTerminal;
import com.library.hibernate.CustomHibernate;

/**
 *
 * @author smallgod
 */
public class TerminalAction {

    public void standby() {
//        final String strStandby = "<standby><date start=\"1970-01-01\" stop=\"2038-01-01\"><time start=\"00:00:00\" stop=\"23:59:59\"/></date></standby>";
//        try {
//            final MessageServer messageServer = new MessageServer();
//            if (this.selectIDs != null && this.selectIDs.split(",").length > 0) {
//                for (int i = 0; i < this.selectIDs.split(",").length; ++i) {
//                    (this.terminal = this.getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]))).setStandBySchedule(strStandby);
//                    if (this.getTerminalhandle().modify(this.terminal)) {
//                        messageServer.noticeAssignChanged(this.terminal.getDeviceId());
//                        LogHelp.userEvent(this.cstmId, this.userName, this.getText("terminalmonitor"), "[" + this.terminal.getDeviceName() + "]" + "StandBy");
//                    }
//                }
//            }
//        } catch (Exception ex) {
//        }
    }

    /**
     * Wakeup a terminal device
     * 
     * @param customHibernate
     * @param terminalDeviceId
     * @throws MyCustomException 
     */
    public void wakeup(CustomHibernate customHibernate, long terminalDeviceId) throws MyCustomException {

        String updateSql = "UPDATE tb_terminal SET STANDBY_SCHEDULE=''" + " WHERE CSTM_ID=" + NamedConstants.CUSTOMER_ID + " AND DEV_ID =" + terminalDeviceId;
        customHibernate.updateTerminalEntity(updateSql);

        final MessageServer messageServer = new MessageServer();

        messageServer.noticeAssignChanged(terminalDeviceId);
        //LogHelp.userEvent(this.cstmId, this.userName, this.getText("terminalmonitor"), "[" + this.terminal.getDeviceName() + "]" + "WakeUp");

    }

    public void reboot() {
//        try {
//            final MessageServer messageServer = new MessageServer();
//            if (this.selectIDs != null && this.selectIDs.split(",").length > 0) {
//                for (int i = 0; i < this.selectIDs.split(",").length; ++i) {
//                    messageServer.reboot(Long.parseLong(this.selectIDs.split(",")[i]));
//                    this.terminal = this.getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
//                    LogHelp.userEvent(this.cstmId, this.userName, this.getText("scheduled"), "[" + this.terminal.getDeviceName() + "]" + "Reboot");
//                }
//            }
//            JsonUtils.writeSuccess((ServletResponse) this.servletResponse);
//        } catch (Exception ex) {
//        }
    }

    public void clear() {
//        try {
//            final MessageServer messageServer = new MessageServer();
//            if (this.selectIDs != null && this.selectIDs.split(",").length > 0) {
//                for (int i = 0; i < this.selectIDs.split(",").length; ++i) {
//                    messageServer.clearContents(Long.parseLong(this.selectIDs.split(",")[i]));
//                    this.terminal = this.getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
//                    LogHelp.userEvent(this.cstmId, this.userName, this.getText("scheduled"), "[" + this.terminal.getDeviceName() + "]" + "Reboot");
//                }
//            }
//            JsonUtils.writeSuccess((ServletResponse) this.servletResponse);
//        } catch (Exception ex) {
//        }
    }

    public void screenshot() {
//        try {
//            final String[] idArray = (String[]) ((this.selectIDs != null) ? this.selectIDs.split(",") : null);
//            if (idArray != null && idArray.length > 0) {
//                final MessageServer messageServer = new MessageServer();
//                for (int i = 0; i < idArray.length; ++i) {
//                    final Terminal dev = this.getTerminalhandle().read(Long.parseLong(idArray[i]));
//                    try {
//                        final File baseDir = new File(Storage.getDeviceScreenDirctory(dev.getDeviceId()));
//                        if (baseDir.exists()) {
//                            final File[] children = baseDir.listFiles();
//                            if (children != null && children.length > 0) {
//                                File[] array;
//                                for (int length = (array = children).length, j = 0; j < length; ++j) {
//                                    final File f = array[j];
//                                    if (f.isFile()) {
//                                        f.delete();
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Exception ex2) {
//                    }
//                    messageServer.captureScreen(dev.getDeviceId());
//                    LogHelp.userEvent(this.cstmId, this.userName, this.getText("scheduled"), "[" + dev.getDeviceName() + "]" + "Screenshot");
//                }
//            }
//            JsonUtils.writeSuccess((ServletResponse) this.servletResponse);
//        } catch (Exception ex) {
//            JsonUtils.writeError((ServletResponse) this.servletResponse);
//            TerminalAction.log.error((Object) ("#screenshot#: " + ex.getMessage()), (Throwable) ex);
//        }
    }

}
