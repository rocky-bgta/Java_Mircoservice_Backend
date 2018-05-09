/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 10:41 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.InvoiceAndStatementLayoutType;
import nybsys.tillboxweb.models.DocumentNumberModel;
import nybsys.tillboxweb.models.InvoiceAndStatementLayoutTypeModel;
import nybsys.tillboxweb.models.VMInvoiceStatementLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InvoiceAndStatementLayoutTypeBllManager extends BaseBll<InvoiceAndStatementLayoutType> {

    private static final Logger log = LoggerFactory.getLogger(InvoiceAndStatementLayoutTypeBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(InvoiceAndStatementLayoutType.class);
        Core.runTimeModelType.set(InvoiceAndStatementLayoutTypeModel.class);
    }


    public List<InvoiceAndStatementLayoutTypeModel> saveInvoiceAndStatementLayoutType(List<InvoiceAndStatementLayoutTypeModel> lstInvoiceStatementLayoutType, Integer businessID) throws Exception {

        InvoiceAndStatementLayoutTypeModel cDetailModel = new InvoiceAndStatementLayoutTypeModel();
        try {

            for (InvoiceAndStatementLayoutTypeModel invoiceAndStatementLayoutTypeModel : lstInvoiceStatementLayoutType) {
                invoiceAndStatementLayoutTypeModel.setBusinessID(businessID);
                if (invoiceAndStatementLayoutTypeModel.getInvoiceAndStatementLayoutTypeID() == null || invoiceAndStatementLayoutTypeModel.getInvoiceAndStatementLayoutTypeID() == 0) {
                    cDetailModel = this.save(invoiceAndStatementLayoutTypeModel);
                    if (cDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                    }
                } else {
                    cDetailModel = this.update(invoiceAndStatementLayoutTypeModel);
                    if (cDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                    }
                }
            }


            //save

        } catch (Exception ex) {
            log.error("DocumentNumberBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstInvoiceStatementLayoutType;
    }


    public List<InvoiceAndStatementLayoutTypeModel> searchInvoiceAndStatementLayoutType(InvoiceAndStatementLayoutTypeModel invoiceAndStatementLayoutTypeModel) throws Exception {

        List<InvoiceAndStatementLayoutTypeModel> InvoiceAndStatementLayoutTypeModels = new ArrayList<>();
        try {
            InvoiceAndStatementLayoutTypeModels = this.getAllByConditions(invoiceAndStatementLayoutTypeModel);
        } catch (Exception ex) {
            log.error("InvoiceAndStatementLayoutTypeBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return InvoiceAndStatementLayoutTypeModels;
    }


}
