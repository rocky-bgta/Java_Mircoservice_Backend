/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 4:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.DiscountGiveMoney;
import nybsys.tillboxweb.models.DiscountGiveMoneyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DiscountGiveMoneyBllManager extends BaseBll<DiscountGiveMoney> {
    private static final Logger log = LoggerFactory.getLogger(DiscountGiveMoneyBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DiscountGiveMoney.class);
        Core.runTimeModelType.set(DiscountGiveMoneyModel.class);
    }

    public DiscountGiveMoneyModel saveOrUpdate(DiscountGiveMoneyModel discountGiveMoneyModelReq) throws Exception {
        DiscountGiveMoneyModel discountGiveMoneyModel = new DiscountGiveMoneyModel();
        List<DiscountGiveMoneyModel> lstDiscountGiveMoneyModel = new ArrayList<>();
        try {
            discountGiveMoneyModel = discountGiveMoneyModelReq;
            //save
            if (discountGiveMoneyModel.getDiscountMoneyID() == null || discountGiveMoneyModel.getDiscountMoneyID() == 0)
            {
                discountGiveMoneyModel = this.save(discountGiveMoneyModel);
                if (discountGiveMoneyModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_MONEY_SAVE_FAILED;
                }
            } else { //update

                discountGiveMoneyModel = this.update(discountGiveMoneyModel);
                if (discountGiveMoneyModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_MONEY_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("DiscountGiveMoneyBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return discountGiveMoneyModel;
    }

    public List<DiscountGiveMoneyModel> searchDiscountGiveMoney(DiscountGiveMoneyModel discountGiveMoneyModelReq) throws Exception {
        DiscountGiveMoneyModel discountGiveMoneyModel = new DiscountGiveMoneyModel();
        List<DiscountGiveMoneyModel> lstDiscountGiveMoneyModel = new ArrayList<>();
        try {
            discountGiveMoneyModel = discountGiveMoneyModelReq;
            lstDiscountGiveMoneyModel = this.getAllByConditions(discountGiveMoneyModel);
            if (lstDiscountGiveMoneyModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_MONEY_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("DiscountGiveMoneyBllManager -> searchDiscountGiveMoney got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstDiscountGiveMoneyModel;
    }
    public DiscountGiveMoneyModel deleteDiscountGiveMoney(DiscountGiveMoneyModel discountGiveMoneyModelReq) throws Exception {
        DiscountGiveMoneyModel discountGiveMoneyModel = new DiscountGiveMoneyModel();
        try {
            discountGiveMoneyModel = discountGiveMoneyModelReq;
            discountGiveMoneyModel = this.softDelete(discountGiveMoneyModel);
            if (discountGiveMoneyModel == null) {
                Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_MONEY_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("DiscountGiveMoneyBllManager -> deleteDiscountGiveMoney got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return discountGiveMoneyModel;
    }
    
}
