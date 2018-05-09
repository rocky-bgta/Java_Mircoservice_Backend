/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 3:11 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.OutStandingBalance;
import nybsys.tillboxweb.models.OutStandingBalanceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OutStandingBalanceBllManager extends BaseBll<OutStandingBalance> {

    private static final Logger log = LoggerFactory.getLogger(OutStandingBalanceBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(OutStandingBalance.class);
        Core.runTimeModelType.set(OutStandingBalanceModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        OutStandingBalanceModel reqOutStandingBalanceModel =
                Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);

        Integer primaryKeyValue = reqOutStandingBalanceModel.getOutStandingBalanceID();
        OutStandingBalanceModel savedOutStandingBalanceModel = null, updatedOutStandingBalanceModel;


        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                reqOutStandingBalanceModel.setBusinessID(requestMessage.businessID);
                savedOutStandingBalanceModel = this.save(reqOutStandingBalanceModel);
                if (savedOutStandingBalanceModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Outstanding Balance Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save Outstanding Balance";
                }
            } else {
                // Update Code
                updatedOutStandingBalanceModel = this.update(reqOutStandingBalanceModel);
                if (updatedOutStandingBalanceModel != null) {
                    Core.clientMessage.get().userMessage = "OutStanding Balance Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update OutStanding Balance";
                }

                savedOutStandingBalanceModel = updatedOutStandingBalanceModel;
            }

        } catch (Exception ex) {
            log.error("OutStandingBalanceBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedOutStandingBalanceModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<OutStandingBalanceModel> search(OutStandingBalanceModel reqOutStandingBalanceModel) throws Exception {
        List<OutStandingBalanceModel> findOutStandingBalanceList;
        try {
            findOutStandingBalanceList = this.getAllByConditions(reqOutStandingBalanceModel);
            if (findOutStandingBalanceList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request Outstanding Balance";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested Outstanding Balance";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("OutStandingBalanceBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findOutStandingBalanceList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        OutStandingBalanceModel req_OutStandingBalanceModel =
                Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_OutStandingBalanceModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested OutStandingBalance";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested OutStandingBalance";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("OutStandingBalanceBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public OutStandingBalanceModel inActive(RequestMessage requestMessage) throws Exception {
        OutStandingBalanceModel reqOutStandingBalanceModel =
                Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);
        OutStandingBalanceModel _OutStandingBalanceModel = null;
        try {
            if (reqOutStandingBalanceModel != null) {
                _OutStandingBalanceModel = this.inActive(reqOutStandingBalanceModel);
                if (_OutStandingBalanceModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested OutStandingBalance";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested OutStandingBalance";
                }
            }

        } catch (Exception ex) {
            log.error("OutStandingBalanceBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _OutStandingBalanceModel;
    }


    public OutStandingBalanceModel delete(OutStandingBalanceModel reqOutStandingBalanceModel) throws Exception {
        OutStandingBalanceModel deletedOutStandingBalanceModel = null;
        try {
            if (reqOutStandingBalanceModel != null) {
                deletedOutStandingBalanceModel = this.softDelete(reqOutStandingBalanceModel);
                if (deletedOutStandingBalanceModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested OutStandingBalance";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested Outstanding Balance";
                }
            }

        } catch (Exception ex) {
            log.error("OutStandingBalanceBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedOutStandingBalanceModel;
    }

    public OutStandingBalanceModel getByReqId(OutStandingBalanceModel reqOutStandingBalanceModel) throws Exception {
        Integer primaryKeyValue = reqOutStandingBalanceModel.getOutStandingBalanceID();
        OutStandingBalanceModel foundOutStandingBalanceModel = null;
        try {
            if (primaryKeyValue != null) {
                foundOutStandingBalanceModel = this.getByIdActiveStatus(primaryKeyValue);
                if (foundOutStandingBalanceModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested OutStandingBalance successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested Outstanding Balance";
                }
            }

        } catch (Exception ex) {
            log.error("OutStandingBalanceBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundOutStandingBalanceModel;
    }
}