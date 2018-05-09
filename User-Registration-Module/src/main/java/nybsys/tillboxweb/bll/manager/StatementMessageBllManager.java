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
import nybsys.tillboxweb.entities.StatementMessage;
import nybsys.tillboxweb.models.DocumentNumberModel;
import nybsys.tillboxweb.models.StatementMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StatementMessageBllManager extends BaseBll<StatementMessage> {

    private static final Logger log = LoggerFactory.getLogger(StatementMessageBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(StatementMessage.class);
        Core.runTimeModelType.set(StatementMessageModel.class);
    }

    public List<StatementMessageModel> saveStatementMessage(List<StatementMessageModel> lstStatementMessageModel, Integer businessID) throws Exception {

        StatementMessageModel statementMessageModel1 = new StatementMessageModel();
        try {
            for (StatementMessageModel statementMessageModel : lstStatementMessageModel) {
                statementMessageModel.setBusinessID(businessID);
                if (statementMessageModel.getStatementMessageID() == null || statementMessageModel.getStatementMessageID() == 0) {
                    statementMessageModel1 = this.save(statementMessageModel);
                    if (statementMessageModel1 == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                    }
                } else {
                    statementMessageModel1 = this.update(statementMessageModel);
                    if (statementMessageModel1 == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                    }
                }
            }
            //save

        } catch (Exception ex) {
            log.error("StatementMessageBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstStatementMessageModel;
    }


    public List<StatementMessageModel> searchStatementMessage(StatementMessageModel statementMessageModel) throws Exception {

        List<StatementMessageModel> StatementMessageModels = new ArrayList<>();
        try {
            StatementMessageModels = this.getAllByConditions(statementMessageModel);
        } catch (Exception ex) {
            log.error("StatementMessageBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return StatementMessageModels;
    }


}
