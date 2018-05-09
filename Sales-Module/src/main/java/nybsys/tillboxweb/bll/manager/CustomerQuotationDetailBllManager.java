/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 3/19/2018
 * Time: 11:109 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.entities.CustomerQuotation;
import nybsys.tillboxweb.entities.CustomerQuotationDetail;
import nybsys.tillboxweb.models.CustomerQuotationDetailModel;
import nybsys.tillboxweb.models.VMCustomerQuotationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerQuotationDetailBllManager extends BaseBll<CustomerQuotationDetail> {

    private static final Logger log = LoggerFactory.getLogger(CustomerQuotationDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerQuotationDetail.class);
        Core.runTimeModelType.set(CustomerQuotationDetailModel.class);
    }


    public VMCustomerQuotationModel saveCustomerQuotationDetail(VMCustomerQuotationModel vmCustomerQuotation) throws Exception {

        List<CustomerQuotationDetailModel> lstCustomerQuotationDetailModel = new ArrayList<>();
        try {
            for (CustomerQuotationDetailModel customerQuotationDetailModel : vmCustomerQuotation.lstCustomerQuotationDetailModel) {
                customerQuotationDetailModel.setCustomerQuotationID(vmCustomerQuotation.customerQuotationModel.getCustomerQuotationID());
                customerQuotationDetailModel = this.save(customerQuotationDetailModel);
                lstCustomerQuotationDetailModel.add(customerQuotationDetailModel);
            }
            vmCustomerQuotation.lstCustomerQuotationDetailModel = lstCustomerQuotationDetailModel;

        } catch (Exception ex) {
            log.error("CustomerQuotationDetailBllManager -> saveCustomerQuotationDetail got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCustomerQuotation;
    }


}
