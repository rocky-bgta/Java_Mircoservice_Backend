/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 4:27 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.entities.CustomerInvoiceDetail;
import nybsys.tillboxweb.models.CustomerInvoiceDetailModel;
import nybsys.tillboxweb.models.VMCustomerInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerInvoiceDetailBllManager extends BaseBll<CustomerInvoiceDetail> {

    private static final Logger log = LoggerFactory.getLogger(CustomerInvoiceDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerInvoiceDetail.class);
        Core.runTimeModelType.set(CustomerInvoiceDetailModel.class);
    }


    public VMCustomerInvoice saveCustomerInvoiceDetail(VMCustomerInvoice vmCustomerInvoice) throws Exception {

        try {

            for (CustomerInvoiceDetailModel sustomerInvoiceDetailModel : vmCustomerInvoice.lstCustomerInvoiceDetailModel) {

                sustomerInvoiceDetailModel.setCustomerInvoiceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
                sustomerInvoiceDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                sustomerInvoiceDetailModel.setCreatedBy("");
                sustomerInvoiceDetailModel.setCreatedDate(new Date());
                sustomerInvoiceDetailModel = this.save(sustomerInvoiceDetailModel);
            }

        } catch (Exception ex) {
            log.error("CustomerInvoiceDetailBllManager -> saveCustomerInvoiceDetail got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCustomerInvoice;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<CustomerInvoiceDetailModel> lstCustomerInvoiceDetailModel;
        CustomerInvoiceDetailModel whereCondition = new CustomerInvoiceDetailModel();
        Double priceSum = 0.0;
        Double tempRowSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setCustomerInvoiceID(invoiceID);
            lstCustomerInvoiceDetailModel = this.getAllByConditions(whereCondition);
            for (CustomerInvoiceDetailModel sustomerInvoiceDetailModel : lstCustomerInvoiceDetailModel) {
                tempRowSum = (sustomerInvoiceDetailModel.getQuantity() * sustomerInvoiceDetailModel.getUnitPrice()) - sustomerInvoiceDetailModel.getDiscount() + sustomerInvoiceDetailModel.getVatAmount();
                priceSum += tempRowSum;
            }
        } catch (Exception ex) {
            log.error("CustomerInvoiceDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }

}
