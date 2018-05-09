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
import nybsys.tillboxweb.entities.DocumentMessage;
import nybsys.tillboxweb.models.DocumentMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DocumentMessageBllManager extends BaseBll<DocumentMessage> {

    private static final Logger log = LoggerFactory.getLogger(DocumentMessageBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DocumentMessage.class);
        Core.runTimeModelType.set(DocumentMessageModel.class);
    }

    public List<DocumentMessageModel> saveDocumentMessage(List<DocumentMessageModel> lstDocumentMessageModel, Integer businessID) throws Exception {

        DocumentMessageModel documentMessageModel = new DocumentMessageModel();
        try {
            for (DocumentMessageModel documentMessageModel1 : lstDocumentMessageModel) {
                documentMessageModel1.setBusinessID(businessID);
                if (documentMessageModel1.getDocumentMessageID() == null || documentMessageModel1.getDocumentMessageID() == 0) {
                    documentMessageModel = this.save(documentMessageModel1);
                    if (documentMessageModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                    }
                } else {
                    documentMessageModel = this.update(documentMessageModel1);
                    if (documentMessageModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                    }
                }
            }
            //save

        } catch (Exception ex) {
            log.error("DocumentMessageBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstDocumentMessageModel;
    }


    public List<DocumentMessageModel> searchDocumentMessage(DocumentMessageModel documentMessageModel) throws Exception {

        List<DocumentMessageModel> documentMessageModels = new ArrayList<>();
        try {
            documentMessageModels = this.getAllByConditions(documentMessageModel);
        } catch (Exception ex) {
            log.error("DocumentMessageBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return documentMessageModels;
    }


}
