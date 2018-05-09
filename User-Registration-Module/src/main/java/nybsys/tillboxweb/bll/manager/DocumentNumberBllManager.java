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
import nybsys.tillboxweb.entities.DocumentNumber;
import nybsys.tillboxweb.models.CompanyAddressModel;
import nybsys.tillboxweb.models.DocumentNumberModel;
import nybsys.tillboxweb.models.VMDocumentNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DocumentNumberBllManager extends BaseBll<DocumentNumber> {

    private static final Logger log = LoggerFactory.getLogger(DocumentNumberBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DocumentNumber.class);
        Core.runTimeModelType.set(DocumentNumberModel.class);
    }

    public List<DocumentNumberModel> saveDocumentNumber(List<DocumentNumberModel> lstDocumentNumberModel, Integer businessID) throws Exception {

        DocumentNumberModel cDetailModel = new DocumentNumberModel();
        try {

            for (DocumentNumberModel documentNumberModel : lstDocumentNumberModel) {
                documentNumberModel.setBusinessID(businessID);

                if (documentNumberModel.getDocumentNumberID() == null || documentNumberModel.getDocumentNumberID() == 0) {
                    cDetailModel = this.save(documentNumberModel);
                    if (cDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                    }
                } else {
                    cDetailModel = this.update(documentNumberModel);
                    if (cDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                    }
                }
            }


            //save

        } catch (Exception ex) {
            log.error("DocumentNumberBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstDocumentNumberModel;
    }


    public List<DocumentNumberModel> searchDocumentNumber(DocumentNumberModel documentNumberModel) throws Exception {

        List<DocumentNumberModel> DocumentNumberModels = new ArrayList<>();
        try {
            DocumentNumberModels = this.getAllByConditions(documentNumberModel);
        } catch (Exception ex) {
            log.error("DocumentNumberBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return DocumentNumberModels;
    }


}
