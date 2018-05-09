/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 04:01
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import java.util.ArrayList;
import java.util.List;

public class VMCustomerModel {
    public CustomerModel customerModel = new CustomerModel();
    public OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
    public List<CustomerAddressModel> lstCustomerAddressModel = new ArrayList<>();
    public List<CustomerContactModel> lstCustomerContactModel = new ArrayList<>();
    // public VMRememberNoteModel vmRememberNoteModel = new VMRememberNoteModel();
    public VMUserDetailSettingDetailModel vmUserDetailSettingDetailModel = new VMUserDetailSettingDetailModel();
    public ReportingLayoutModel reportingLayoutModel = new ReportingLayoutModel();
}
