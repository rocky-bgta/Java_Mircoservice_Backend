/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Apr-18
 * Time: 12:39 PM
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
import nybsys.tillboxweb.entities.Rounding;
import nybsys.tillboxweb.models.RoundingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoundingBllManager extends BaseBll<Rounding> {

    private static final Logger log = LoggerFactory.getLogger(RoundingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Rounding.class);
        Core.runTimeModelType.set(RoundingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        RoundingModel reqRoundingModel =
                Core.getRequestObject(requestMessage, RoundingModel.class);

        Integer primaryKeyValue = reqRoundingModel.getRoundingID();
        RoundingModel savedRoundingModel = null, updatedRoundingModel;


        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                reqRoundingModel.setBusinessID(requestMessage.businessID);
                savedRoundingModel = this.save(reqRoundingModel);
                if (savedRoundingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Rounding Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save Rounding";
                }
            } else {
                // Update Code
                updatedRoundingModel = this.update(reqRoundingModel);
                if (updatedRoundingModel != null) {
                    Core.clientMessage.get().userMessage = "Rounding Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update Rounding";
                }

                savedRoundingModel = updatedRoundingModel;
            }

        } catch (Exception ex) {
            log.error("RoundingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedRoundingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<RoundingModel> search(RoundingModel reqRoundingModel) throws Exception {
        List<RoundingModel> findRoundingList;
        try {
            findRoundingList = this.getAllByConditions(reqRoundingModel);
            if (findRoundingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request Rounding";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested Rounding";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RoundingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findRoundingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        RoundingModel req_RoundingModel =
                Core.getRequestObject(requestMessage, RoundingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_RoundingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested Rounding";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested Rounding";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RoundingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public RoundingModel inActive(RequestMessage requestMessage) throws Exception {
        RoundingModel reqRoundingModel =
                Core.getRequestObject(requestMessage, RoundingModel.class);
        RoundingModel _RoundingModel = null;
        try {
            if (reqRoundingModel != null) {
                _RoundingModel = this.inActive(reqRoundingModel);
                if (_RoundingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested Rounding";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested Rounding";
                }
            }

        } catch (Exception ex) {
            log.error("RoundingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _RoundingModel;
    }


    public RoundingModel delete(RoundingModel reqRoundingModel) throws Exception {
        RoundingModel deletedRoundingModel = null;
        try {
            if (reqRoundingModel != null) {
                deletedRoundingModel = this.softDelete(reqRoundingModel);
                if (deletedRoundingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested Rounding";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested Rounding";
                }
            }

        } catch (Exception ex) {
            log.error("RoundingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedRoundingModel;
    }

    public RoundingModel getByReqId(RoundingModel reqRoundingModel) throws Exception {
        Integer primaryKeyValue = reqRoundingModel.getRoundingID();
        RoundingModel foundRoundingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundRoundingModel = this.getByIdActiveStatus(primaryKeyValue);
                if (foundRoundingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested Rounding successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested Rounding";
                }
            }

        } catch (Exception ex) {
            log.error("RoundingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundRoundingModel;
    }
}