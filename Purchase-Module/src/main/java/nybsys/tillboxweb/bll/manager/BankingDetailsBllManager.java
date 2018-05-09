/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 23/02/2018
 * Time: 12:01
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.BankingDetails;
import nybsys.tillboxweb.models.BankingDetailsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BankingDetailsBllManager extends BaseBll<BankingDetails> {
    private static final Logger log = LoggerFactory.getLogger(BankingDetailsBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(BankingDetails.class);
        Core.runTimeModelType.set(BankingDetailsModel.class);
    }

    public BankingDetailsModel saveOrUpdate(BankingDetailsModel bankingDetailsModelReq) throws Exception {
        BankingDetailsModel bankingDetailsModel = new BankingDetailsModel();
        List<BankingDetailsModel> lstBankingDetailsModel = new ArrayList<>();
        try {
            bankingDetailsModel = bankingDetailsModelReq;
            //save
            if (bankingDetailsModel.getBankingDetailID() == null || bankingDetailsModel.getBankingDetailID() == 0) {
                bankingDetailsModel = this.save(bankingDetailsModel);
                if (bankingDetailsModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.BANKING_DETAILS_SAVE_FAILED;
                }
            } else { //update

                bankingDetailsModel = this.update(bankingDetailsModel);
                if (bankingDetailsModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.BANKING_DETAILS_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("BankingDetailsBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return bankingDetailsModel;
    }

    public BankingDetailsModel searchBankingDetailsByReferenceIDAndReferenceType(int referenceID, int referenceType, int businessID) throws Exception {
        BankingDetailsModel bankingDetailsModel = new BankingDetailsModel();
        List<BankingDetailsModel> lstBankingDetailsModel = new ArrayList<>();
        try {
            bankingDetailsModel.setBusinessID(businessID);
            bankingDetailsModel.setReferenceID(referenceID);
            bankingDetailsModel.setReferenceType(referenceType);
            bankingDetailsModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstBankingDetailsModel = this.getAllByConditions(bankingDetailsModel);
            if (lstBankingDetailsModel.size() > 0) {
                bankingDetailsModel = lstBankingDetailsModel.get(0);
            } else {
                bankingDetailsModel = null;
                Core.clientMessage.get().message = MessageConstant.BANKING_DETAILS_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierBllManager -> searchSupplierByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return bankingDetailsModel;
    }

    public Integer deleteBankingDetailByReferenceIDAndReferenceType(Integer referenceID, Integer referenceType, Integer businessID) throws Exception {
        BankingDetailsModel whereConditions = new BankingDetailsModel();
        BankingDetailsModel modelToUpdate = new BankingDetailsModel();
        Integer numberOfRowDeleted = 0;

        try {
            whereConditions.setBusinessID(businessID);
            whereConditions.setReferenceID(referenceID);
            whereConditions.setReferenceType(referenceType);
            numberOfRowDeleted = this.updateByConditions(whereConditions, modelToUpdate);

            modelToUpdate.setStatus(TillBoxAppEnum.Status.Deleted.get());
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierBllManager -> deleteSupplierByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return numberOfRowDeleted;
    }

}
