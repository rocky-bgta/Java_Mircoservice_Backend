/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/18/2018
 * Time: 9:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import java.util.ArrayList;
import java.util.List;

public class VMCompanyDetail {

    public CompanyDetailModel companyDetailModel;
    public List<CompanyAddressModel> lstCompanyAddressModel;

    public VMCompanyDetail()
    {
        companyDetailModel=new CompanyDetailModel();
        lstCompanyAddressModel =new ArrayList<>();
    }
}
