/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.controller;


import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.service.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ApiRouter extends BaseController {

    private final Logger log = LoggerFactory.getLogger(ApiRouter.class);

    @Autowired
    private CountryServiceManager countryServiceManager;


    @Autowired
    private VATRateServiceManager vatRateServiceManager;


    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {

        this.checkSecurityAndExecuteService(serviceName, requestMessage);
        return this.responseMessage;
    }

    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {

            case "api/country/getAll":
                this.responseMessage = this.countryServiceManager.getAll(requestMessage);
                log.info("User Registration Module -> api/country/getAll executed");
                break;


            case "api/vATRate/search":
                this.responseMessage = this.vatRateServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/vatRateServiceManager/search executed");
                break;

            default:
                log.warn("INVALID REQUEST");
        }
    }
}
