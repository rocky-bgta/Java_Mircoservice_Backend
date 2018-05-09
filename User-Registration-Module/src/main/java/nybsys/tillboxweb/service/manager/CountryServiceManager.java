package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.CountryBllManager;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.CountryModel;
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
public class CountryServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Autowired
    private CountryBllManager countryBllManager;



    public ResponseMessage getByID(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        CountryModel countryModel;
        try {
            countryModel = Core.getRequestObject(requestMessage,CountryModel.class);

            countryModel = this.countryBllManager.getById(countryModel.getCountryID());
            responseMessage.responseObj = countryModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("CountryServiceManager -> getByID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        List<CountryModel> lstCountryModel;
        CountryModel countryModel;
        try {
            countryModel = Core.getRequestObject(requestMessage,CountryModel.class);


            lstCountryModel = this.countryBllManager.getAllByConditions(countryModel);
            responseMessage.responseObj = lstCountryModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("CountryServiceManager -> search got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getAll(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        List<CountryModel> lstCountryModel;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            lstCountryModel = this.countryBllManager.getAll();
            responseMessage.responseObj = lstCountryModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("CountryServiceManager -> getAll got exception");
        }
        return responseMessage;
    }

}
