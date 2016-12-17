/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.datamodel.dsmbridge.TbCustomer;
import com.library.datamodel.dsmbridge.TbFile;
import com.library.hibernate.CustomHibernate;
import com.library.utilities.dsmbridge.IDCreator;
import static com.library.utilities.GeneralUtils.convertListToSet;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Class contains all the helper methods when dealing with Hibernate
 *
 * @author smallgod
 */
public class HibernateUtils {

    /**
     * Generate the FileID To-Do -> Method fetches entire file list for each
     * call, we need to come up with a better way of doing this.
     *
     * @param customHibernate
     * @return
     */
    public static synchronized long generateLongID(CustomHibernate customHibernate) {

        List<Long> fileIdList = customHibernate.fetchOnlyColumn(TbFile.class, "FILE_ID");

        Set<Long> set = convertListToSet(fileIdList);

        long fileID;

        do {
            fileID = IDCreator.GenerateLong();
        } while (set.contains(fileID));

        return fileID;
    }

    /**
     * Generate a customer ID
     * @param customHibernate
     * @return 
     */
    public static synchronized int generateIntegerID(CustomHibernate customHibernate) {

        List<Integer> fileIdList = customHibernate.fetchOnlyColumn(TbCustomer.class, "CSTM_ID");

        Set<Integer> set = convertListToSet(fileIdList);

        int customerID;

        do {
            customerID = IDCreator.GenerateInt();
        } while (set.contains(customerID));

        return customerID;
    }

}
