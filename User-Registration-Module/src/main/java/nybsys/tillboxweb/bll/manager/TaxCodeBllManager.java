package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.TaxCode;
import nybsys.tillboxweb.models.TaxCodeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaxCodeBllManager extends BaseBll<TaxCode> {
    private static final Logger log = LoggerFactory.getLogger(TaxCodeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(TaxCode.class);
        Core.runTimeModelType.set(TaxCodeModel.class);
    }

    public TaxCodeModel saveOrUpdate(TaxCodeModel taxCodeModelReq) throws Exception {
        TaxCodeModel taxCodeModel = new TaxCodeModel();
        try {
            taxCodeModel = taxCodeModelReq;
            //save
            if (taxCodeModel.getTaxCodeID() == null || taxCodeModel.getTaxCodeID() == 0) {
                taxCodeModel = this.save(taxCodeModel);
                if (taxCodeModel == null) {
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_TAX_CODE;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
            } else //update
            {
                taxCodeModel = this.update(taxCodeModel);
                if (taxCodeModel == null) {
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_TAX_CODE;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
            }

        } catch (Exception ex) {
            log.error("TaxCodeBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return taxCodeModel;
    }
    public List<TaxCodeModel> getAllTaxCode() throws Exception {
        List<TaxCodeModel> lstTaxCodeModel = new ArrayList<>();
        TaxCodeModel whereCondition = new TaxCodeModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstTaxCodeModel = this.getAllByConditions(whereCondition);
            if (lstTaxCodeModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_TAX_CODE;
            }
        } catch (Exception ex) {
            log.error("TaxCodeBllManager -> getAllTaxCode got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstTaxCodeModel;
    }
}
