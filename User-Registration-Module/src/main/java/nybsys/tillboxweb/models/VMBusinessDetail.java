package nybsys.tillboxweb.models;

import nybsys.tillboxweb.coreModels.CountryModel;
import nybsys.tillboxweb.coreModels.FinancialYearModel;

import java.util.ArrayList;
import java.util.List;

public class VMBusinessDetail {

    public BusinessDetailsModel businessDetailsModel;
    public BusinessContactModel businessContact;
    public List<BusinessAddressModel> lstBusinessAddressModel;
    public List<FinancialYearModel> lstFinancialYearModel;
    public List<CountryModel> lstCountryModel;
    public BusinessModel business;
    public VMBusinessDetail() {
        //super();
        this.businessDetailsModel = new BusinessDetailsModel();
        this.businessContact = new BusinessContactModel();
        this.lstBusinessAddressModel = new ArrayList<>();
        this.lstFinancialYearModel = new ArrayList<>();
        this.lstCountryModel=new ArrayList<>();
        this.business=new BusinessModel();
    }
}