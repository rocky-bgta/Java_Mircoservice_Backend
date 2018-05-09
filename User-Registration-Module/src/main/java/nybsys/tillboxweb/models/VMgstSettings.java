package nybsys.tillboxweb.models;

import nybsys.tillboxweb.Enum.EnumsOfUserRegistrationModule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VMgstSettings {
    public GstSettingModel gstSettingModel;
    public Object accountingOption;
    public Object reportingOption;
    public Object gstOption;
    public Object baseLodgementOption;

    public VMgstSettings() {
        //super(); //////////
        this.gstSettingModel = new GstSettingModel();
        this.gstSettingModel.setRegistered(true);
        this.gstSettingModel.setAccountingBasic(1);
        this.gstSettingModel.setReportingFrequency(1);
        this.gstSettingModel.setSelectedGstOption(1);
        this.gstSettingModel.setSelectedBasLodgement(1);
        this.accountingOption = EnumsOfUserRegistrationModule.AccountingBasis.getMAP().entrySet().toArray();
        this.reportingOption = EnumsOfUserRegistrationModule.ReportingFrequency.getMAP().entrySet().toArray();
        this.gstOption = EnumsOfUserRegistrationModule.GstOption.getMAP().entrySet().toArray();
        this.baseLodgementOption = EnumsOfUserRegistrationModule.BaseLodgementOption.getMAP().entrySet().toArray();
    }
}
