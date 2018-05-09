/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 4:38 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.entities.CustomerReturnDetail;
import nybsys.tillboxweb.models.CustomerReturnDetailModel;
import nybsys.tillboxweb.models.VMCustomerReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerReturnDetailBllManager extends BaseBll<CustomerReturnDetail> {

    private static final Logger log = LoggerFactory.getLogger(CustomerReturnDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerReturnDetail.class);
        Core.runTimeModelType.set(CustomerReturnDetailModel.class);
    }


    public VMCustomerReturn saveCustomerReturnDetail(VMCustomerReturn vmCustomerReturn) throws Exception {

        try {
            for (CustomerReturnDetailModel customerReturnDetailModel : vmCustomerReturn.lstCustomerReturnDetailModel) {

                customerReturnDetailModel.setCustomerReturnID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
                customerReturnDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                customerReturnDetailModel.setCreatedBy("");
                customerReturnDetailModel.setCreatedDate(new Date());
                customerReturnDetailModel = this.save(customerReturnDetailModel);
            }


        } catch (Exception ex) {
            log.error("PurchaseOrderDetailBllManager -> save Purchase OrderDetail manager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCustomerReturn;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<CustomerReturnDetailModel> lstCustomerReturnDetailModel;
        CustomerReturnDetailModel whereCondition = new CustomerReturnDetailModel();
        Double priceSum = 0.0;
        Double tempRowSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setCustomerReturnID(invoiceID);
            lstCustomerReturnDetailModel = this.getAllByConditions(whereCondition);
            for (CustomerReturnDetailModel customerReturnDetailModel : lstCustomerReturnDetailModel) {
                tempRowSum = (customerReturnDetailModel.getQuantity() * customerReturnDetailModel.getPrice()) - customerReturnDetailModel.getDiscount() + customerReturnDetailModel.getVat();
                priceSum += tempRowSum;
            }
        } catch (Exception ex) {
            log.error("PurchaseOrderDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }


}
