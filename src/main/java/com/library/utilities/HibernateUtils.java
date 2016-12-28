/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.datamodel.dsm_bridge.TbCustomer;
import com.library.datamodel.dsm_bridge.TbFile;
import com.library.datamodel.dsm_bridge.TbFileId;
import com.library.hibernate.CustomHibernate;
import com.library.utilities.dsmbridge.IDCreator;
import static com.library.utilities.GeneralUtils.convertListToSet;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class contains all the helper methods when dealing with Hibernate
 *
 * @author smallgod
 */
public class HibernateUtils {

    private static final LoggerUtil LOG = new LoggerUtil(HibernateUtils.class);

    /**
     * Generate the FileID To-Do -> Method fetches entire file list for each
     * call, we need to come up with a better way of doing this.
     *
     * @param customHibernate
     * @return
     */
    public static synchronized long generateLongIDOld(CustomHibernate customHibernate) {

        List<TbFileId> fileIdList = customHibernate.fetchOnlyColumn(TbFile.class, "FILE_ID");

        LOG.debug("Records fetched size [TbFile.class] : " + fileIdList.size());

        Set<Long> fileIds = new HashSet<>();
        for (TbFileId id : fileIdList) {
            LOG.debug("file id found: " + id.getFileId());
            fileIds.add(id.getFileId());
        }

        long fileID;

        do {
            fileID = IDCreator.GenerateLong();
        } while (fileIds.contains(fileID));

        return fileID;
    }

    /**
     * Generate the FileID To-Do -> Method fetches entire file list for each
     * call, we need to come up with a better way of doing this.
     *
     * @param customHibernate
     * @return
     */
    public static synchronized long generateLongIDOLD2(CustomHibernate customHibernate) {

        List<Long> fileIdList = customHibernate.fetchOnlyColumn(TbFile.class, "id.fileId");

        LOG.debug("Records fetched size [TbFile.class]: " + fileIdList.size());

        Set<Long> fileIds = GeneralUtils.convertListToSet(fileIdList);

        long fileID;

        do {
            fileID = IDCreator.GenerateLong();
        } while (fileIds.contains(fileID));

        return fileID;
    }

    /**
     * Generate the FileID To-Do -> Method fetches entire file list for each
     * call, we need to come up with a better way of doing this.
     *
     * @param customHibernate
     * @param classType
     * @param idColumnName
     * @return
     */
    public static synchronized long generateLongID(CustomHibernate customHibernate, Class classType, String idColumnName) {

        List<Long> fileIdList = customHibernate.fetchOnlyColumn(classType, idColumnName);
        //List<Long> fileIdList = customHibernate.fetchOnlyColumn(TbFile.class, "id.fileId");

        LOG.debug("Records fetched size [TbFile.class]: " + fileIdList.size());

        Set<Long> fileIds = GeneralUtils.convertListToSet(fileIdList);

        long fileID;

        do {
            fileID = IDCreator.GenerateLong();
        } while (fileIds.contains(fileID));

        return fileID;
    }

    /**
     * Generate a customer ID
     *
     * @param customHibernate
     * @return
     */
    public static synchronized int generateIntegerIDOLD2(CustomHibernate customHibernate) {

        //List<Integer> idList = customHibernate.fetchOnlyColumn(TbCustomer.class, "CSTM_ID");
        List<Integer> fileIdList = customHibernate.fetchOnlyColumn(TbCustomer.class, "cstmId");

        Set<Integer> set = convertListToSet(fileIdList);

        int customerID;

        do {
            customerID = IDCreator.GenerateInt();
        } while (set.contains(customerID));

        return customerID;
    }

    /**
     * Generate a customer ID
     *
     * @param customHibernate
     * @param classType
     * @param idColumnName
     * @return
     */
    public static synchronized int generateIntegerID(CustomHibernate customHibernate, Class classType, String idColumnName) {

        List<Integer> idList = customHibernate.fetchOnlyColumn(classType, idColumnName);

        Set<Integer> set = convertListToSet(idList);

        int generatedId;

        do {
            generatedId = IDCreator.GenerateInt();
        } while (set.contains(generatedId));

        return generatedId;
    }

}
