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
import nybsys.tillboxweb.entities.DocumentDescription;
import nybsys.tillboxweb.models.DocumentDescriptionModel;
import nybsys.tillboxweb.models.DocumentNumberModel;
import nybsys.tillboxweb.models.VMDocumentDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DocumentDescriptionBllManager extends BaseBll<DocumentDescription> {

    private static final Logger log = LoggerFactory.getLogger(DocumentDescriptionBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DocumentDescription.class);
        Core.runTimeModelType.set(DocumentDescriptionModel.class);
    }

    public List<DocumentDescriptionModel> saveDocumentDescription(List<DocumentDescriptionModel> lstDocumentDescriptionModel, Integer businessID) throws Exception {

        DocumentDescriptionModel cDetailModel = new DocumentDescriptionModel();
        try {

            for (DocumentDescriptionModel documentDescriptionModel : lstDocumentDescriptionModel) {
                documentDescriptionModel.setBusinessID(businessID);
                if (documentDescriptionModel.getDocumentDescriptionID() == null || documentDescriptionModel.getDocumentDescriptionID() == 0) {
                    cDetailModel = this.save(documentDescriptionModel);
                    if (cDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                    }
                } else {
                    cDetailModel = this.update(documentDescriptionModel);
                    if (cDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                    }
                }
            }

        } catch (Exception ex) {
            log.error("DocumentDescriptionBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstDocumentDescriptionModel;
    }


    public List<DocumentDescriptionModel> searchDocumentDescription(DocumentDescriptionModel DocumentDescriptionModel) throws Exception {

        List<DocumentDescriptionModel> DocumentDescriptionModels = new ArrayList<>();
        try {
            DocumentDescriptionModels = this.getAllByConditions(DocumentDescriptionModel);
        } catch (Exception ex) {
            log.error("DocumentDescriptionBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return DocumentDescriptionModels;
    }


}
