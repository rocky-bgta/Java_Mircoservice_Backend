/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 04/05/2018
 * Time: 05:39
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import java.util.Date;

public class VMCurrencyExchangeRateRequestModel {
    private Date transactionDate;
    private Integer contactType;
    private Integer contactID;
    private Integer preferredCurrencyID;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public Integer getPreferredCurrencyID() {
        return preferredCurrencyID;
    }

    public void setPreferredCurrencyID(Integer preferredCurrencyID) {
        this.preferredCurrencyID = preferredCurrencyID;
    }
}
