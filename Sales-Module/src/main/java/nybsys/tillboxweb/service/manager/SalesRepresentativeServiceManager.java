/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 12:21
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.SalesRepresentativeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.SalesRepresentativeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SalesRepresentativeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SalesRepresentativeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private SalesRepresentativeBllManager salesRepresentativeBllManager;

    public ResponseMessage saveSalesRepresentative(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SalesRepresentativeModel salesRepresentativeModel = new SalesRepresentativeModel();
        try {
            salesRepresentativeModel = Core.getRequestObject(requestMessage, SalesRepresentativeModel.class);

            /*Set<ConstraintViolation<SalesRepresentativeModel>> violations = this.validator.validate(salesRepresentativeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            salesRepresentativeModel = this.salesRepresentativeBllManager.saveOrUpdate(salesRepresentativeModel);

            responseMessage.responseObj = salesRepresentativeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SALES_REPRESENTATIVE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.SALES_REPRESENTATIVE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SalesRepresentativeServiceManager -> saveSalesRepresentative got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchSalesRepresentative(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<SalesRepresentativeModel> lstSalesRepresentativeModel = new ArrayList<>();
        SalesRepresentativeModel salesRepresentativeModel = new SalesRepresentativeModel();
        try {
            salesRepresentativeModel = Core.getRequestObject(requestMessage, SalesRepresentativeModel.class);

            lstSalesRepresentativeModel = this.salesRepresentativeBllManager.searchSalesRepresentative(salesRepresentativeModel);

            responseMessage.responseObj = lstSalesRepresentativeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SALES_REPRESENTATIVE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SALES_REPRESENTATIVE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SalesRepresentativeServiceManager -> searchSalesRepresentative got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage getSalesRepresentativeByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SalesRepresentativeModel salesRepresentativeModel = new SalesRepresentativeModel();
        try {
            salesRepresentativeModel = Core.getRequestObject(requestMessage, SalesRepresentativeModel.class);

            salesRepresentativeModel = this.salesRepresentativeBllManager.getSalesRepresentativeByID(salesRepresentativeModel.getSalesRepresentativeID());

            responseMessage.responseObj = salesRepresentativeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SALES_REPRESENTATIVE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SALES_REPRESENTATIVE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SalesRepresentativeServiceManager -> getSalesRepresentativeByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
