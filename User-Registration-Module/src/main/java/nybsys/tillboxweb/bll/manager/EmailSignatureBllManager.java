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
import nybsys.tillboxweb.entities.EmailSignature;
import nybsys.tillboxweb.models.EmailSignatureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmailSignatureBllManager extends BaseBll<EmailSignature> {

    private static final Logger log = LoggerFactory.getLogger(EmailSignatureBllManager.class);

  

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(EmailSignature.class);
        Core.runTimeModelType.set(EmailSignatureModel.class);
    }

    public EmailSignatureModel saveEmailSignature(EmailSignatureModel emailSignatureModel) throws Exception {

        EmailSignatureModel cDetailModel = new EmailSignatureModel();
        try {

            //save
            if (emailSignatureModel.getEmailSignatureID() == null || emailSignatureModel.getEmailSignatureID() == 0) {
                cDetailModel = this.save(emailSignatureModel);

                if (cDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                }
            } else {
                cDetailModel = this.update(emailSignatureModel);
                if (cDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                }
            }
                

        } catch (Exception ex) {
            log.error("EmailSignatureBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return emailSignatureModel;
    }


    public List<EmailSignatureModel> searchEmailSignature(EmailSignatureModel EmailSignatureModel) throws Exception {


        List<EmailSignatureModel> emailSignatureModels = new ArrayList<>();
        try {
            emailSignatureModels = this.getAllByConditions(EmailSignatureModel);
        } catch (Exception ex) {
            log.error("EmailSignatureBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return emailSignatureModels;
    }


}
