/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.datamodel.Constants.NamedConstants;
import com.library.datamodel.Constants.TransactionAggregatorStatus;

/**
 *
 * @author smallgod
 */
public class MamboPayStatusConverter {

    public static final class ConvertedStatus {

        TransactionAggregatorStatus status;
        String statusDescription;

        public ConvertedStatus(TransactionAggregatorStatus status, String statusDescription) {
            this.status = status;
            this.statusDescription = statusDescription;
        }

        public TransactionAggregatorStatus getStatus() {
            return status;
        }

        public void setStatus(TransactionAggregatorStatus status) {
            this.status = status;
        }

        public String getStatusDescription() {
            return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
        }

    }

    /**
     * Convert a mambo pay status to an appropriate internal status code &
     * description
     *
     * @param mamboPayStatusCode
     * @param mamboPayStatusDescription
     * @return
     */
    public static ConvertedStatus convert(String mamboPayStatusCode, String mamboPayStatusDescription) {

        TransactionAggregatorStatus status = TransactionAggregatorStatus.UNKNOWN;
        String statusDescription = mamboPayStatusDescription;

        if (!(mamboPayStatusCode == null || mamboPayStatusCode.isEmpty())) {

            switch (mamboPayStatusCode) {

                case NamedConstants.MAMBOPAY_DEBIT_PROCESSING:
                    status = TransactionAggregatorStatus.PROCESSING;
                    statusDescription = "Processing: " + mamboPayStatusDescription;
                    break;

                case NamedConstants.MAMBOPAY_DEBIT_DUPLICATE:
                    status = TransactionAggregatorStatus.DUPLICATE;
                    statusDescription = "Duplicate payment: " + mamboPayStatusDescription;
                    break;

                case NamedConstants.MAMBOPAY_DEBIT_MISSING_SUBSCRIPTION_KEY_2:
                    status = TransactionAggregatorStatus.FAILED;
                    statusDescription = "Missing subscription key: " + mamboPayStatusDescription;
                    break;

                default:
                    status = TransactionAggregatorStatus.UNKNOWN;
                    statusDescription = "Failed to interprete status code: " + mamboPayStatusDescription;
                    break;
            }
        }

        return new ConvertedStatus(status, statusDescription);
    }

    /**
     * Convert a mambo pay status to an appropriate internal status code &
     * description
     *
     * @param mamboPayStatusCode
     * @param mamboPayStatusDescription
     * @return
     */
    public static ConvertedStatus convert(int mamboPayStatusCode, String mamboPayStatusDescription) {

        TransactionAggregatorStatus status;
        String statusDescription;

        switch (mamboPayStatusCode) {

            case NamedConstants.MAMBOPAY_DEBIT_ACCOUNT_UNREGISTERED:
                status = TransactionAggregatorStatus.FAILED;
                statusDescription = "Processing: " + mamboPayStatusDescription;
                break;

            case NamedConstants.MAMBOPAY_DEBIT_BELOW_THRESHOLD:
                status = TransactionAggregatorStatus.FAILED;
                statusDescription = "Payment below threshold: " + mamboPayStatusDescription;
                break;

            case NamedConstants.MAMBOPAY_DEBIT_PAYMENT_EXPIRED:
                status = TransactionAggregatorStatus.FAILED;
                statusDescription = "Failed to approve payment in 6 minutes: " + mamboPayStatusDescription;
                break;

            case NamedConstants.MAMBOPAY_DEBIT_INSUFFICIENT_FUNDS:
                status = TransactionAggregatorStatus.FAILED;
                statusDescription = "Insufficient funds: " + mamboPayStatusDescription;
                break;

            case NamedConstants.MAMBOPAY_DEBIT_SUCCESS:
                status = TransactionAggregatorStatus.SUCCESSFUL;
                statusDescription = mamboPayStatusDescription;
                break;

            case NamedConstants.MAMBOPAY_DEBIT_MISSING_SUBSCRIPTION_KEY:
                status = TransactionAggregatorStatus.FAILED;
                statusDescription = "Missing subscription key: " + mamboPayStatusDescription;
                break;

            default:
                status = TransactionAggregatorStatus.UNKNOWN;
                statusDescription = "Failed to interprete status code: " + mamboPayStatusDescription;

                break;
        }

        return new ConvertedStatus(status, statusDescription);
    }

}
