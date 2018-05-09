package nybsys.tillboxweb.service.manager;
import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.ProductTypeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.ProductTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    @Autowired
    private ProductTypeBllManager productTypeBllManager;

    public ResponseMessage save(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        ProductTypeModel productTypeModel;
        try {
            productTypeModel = Core.getRequestObject(requestMessage, ProductTypeModel.class);

           /* Set<ConstraintViolation<ProductTypeModel>> violations = this.validator.validate(productTypeModel);
            for (ConstraintViolation<ProductTypeModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            productTypeModel = this.productTypeBllManager.saveOrUpdate(productTypeModel);

            responseMessage.responseObj = productTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.PRODUCT_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.PRODUCT_TYPE_SAVE_FAILED;
                }
                this.rollBack();
            }
        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("ProductTypeServiceManager -> save got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        ProductTypeModel productTypeModel;
        try {
            productTypeModel = Core.getRequestObject(requestMessage,ProductTypeModel.class);

            productTypeModel = this.productTypeBllManager.save(productTypeModel);
            responseMessage.responseObj = productTypeModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("ProductTypeServiceManager -> getByID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        ProductTypeModel productTypeModel;
        List<ProductTypeModel> lstProductTypeModel;
        try {
            productTypeModel = Core.getRequestObject(requestMessage,ProductTypeModel.class);

            lstProductTypeModel = this.productTypeBllManager.getAllByConditions(productTypeModel);

            responseMessage.responseObj = lstProductTypeModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("ProductTypeServiceManager -> search got exception");
        }
        return responseMessage;
    }
}
