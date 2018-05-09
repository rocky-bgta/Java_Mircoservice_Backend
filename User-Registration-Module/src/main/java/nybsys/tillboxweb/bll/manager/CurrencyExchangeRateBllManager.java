/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 11:12 AM
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
import nybsys.tillboxweb.coreModels.CurrencyExchangeRateModel;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreEntities.CurrencyExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencyExchangeRateBllManager extends BaseBll<CurrencyExchangeRate> {
    private static final Logger log = LoggerFactory.getLogger(CurrencyBllManager.class);

    private CurrencyBllManager currencyBllManager = new CurrencyBllManager();

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CurrencyExchangeRate.class);
        Core.runTimeModelType.set(CurrencyExchangeRateModel.class);
    }

    public CurrencyExchangeRateModel saveOrUpdate(CurrencyExchangeRateModel currencyExchangeRateModelReq) throws Exception {
        CurrencyExchangeRateModel currencyExchangeRateModel = new CurrencyExchangeRateModel();
        try {
            currencyExchangeRateModel = currencyExchangeRateModelReq;

            //save
            if (currencyExchangeRateModel.getCurrencyExchangeRateID() == null || currencyExchangeRateModel.getCurrencyExchangeRateID() == 0) {
                currencyExchangeRateModel = this.save(currencyExchangeRateModel);
                if (currencyExchangeRateModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_EXCHANGE_RATE_SAVE_FAILED;
                }
            } else {

                //make history
                currencyExchangeRateModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                currencyExchangeRateModel = this.update(currencyExchangeRateModel);
                if (currencyExchangeRateModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_EXCHANGE_RATE_UPDATE_FAILED;
                    return currencyExchangeRateModel;
                }

                //then insert again
                currencyExchangeRateModel.setCurrencyExchangeRateID(null);
                currencyExchangeRateModel = this.save(currencyExchangeRateModel);
                if (currencyExchangeRateModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_EXCHANGE_RATE_SAVE_FAILED;
                    return currencyExchangeRateModel;
                }

                //update latest currency exchange rate to currency
                CurrencyModel currencyModel = new CurrencyModel();
                //get currency
                currencyModel = this.currencyBllManager.getById(currencyExchangeRateModel.getCurrencyID());
                if (currencyModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_GET_FAILED;
                    return currencyExchangeRateModel;
                }
                //update currency
                //currencyModel.setExchangeRate(currencyExchangeRateModel.getRate());
                this.currencyBllManager.saveOrUpdate(currencyModel);


            }
        } catch (Exception ex) {
            log.error("CurrencyExchangeRateBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return currencyExchangeRateModel;
    }

    public CurrencyExchangeRateModel getExchangeRateByCurrencyID(Integer currencyID,Date ceransactionDate) throws Exception {
        List<CurrencyExchangeRateModel> lstCurrencyExchangeRateModel;
        CurrencyExchangeRateModel currencyExchangeRateModel = new CurrencyExchangeRateModel();
        try {

            currencyExchangeRateModel.setCurrencyID(currencyID);
            if(ceransactionDate == null) {
                currencyExchangeRateModel.setDate(new Date());
            }else {
                currencyExchangeRateModel.setDate(ceransactionDate);
            }

            Calendar cFrom = Calendar.getInstance();
            cFrom.setTime(new Date()); /* today */
            cFrom.set(Calendar.HOUR_OF_DAY, 0);
            cFrom.set(Calendar.MINUTE, 0);
            cFrom.set(Calendar.SECOND, 0);
            cFrom.set(Calendar.MILLISECOND, 0);
            Timestamp from = new Timestamp(cFrom.getTime().getTime());
            Calendar cTo = Calendar.getInstance();
            cTo.setTime(new Date()); /* today */
            cTo.set(Calendar.HOUR_OF_DAY, 23);
            cTo.set(Calendar.MINUTE, 59);
            cTo.set(Calendar.SECOND, 59);
            cTo.set(Calendar.MILLISECOND, 999);
            Timestamp to = new Timestamp(cTo.getTime().getTime());

            final String hql = "FROM CurrencyExchangeRate cr WHERE cr.currencyExchangeRateID =( SELECT max(tr.currencyExchangeRateID) FROM CurrencyExchangeRate tr WHERE tr.date >= '"+from+"'  AND tr.date <= '"+to+"' )";

            lstCurrencyExchangeRateModel = this.executeHqlQuery(hql, CurrencyExchangeRateModel.class, TillBoxAppEnum.QueryType.Select.get());
            if (lstCurrencyExchangeRateModel.size() == 0) {
                currencyExchangeRateModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.CURRENCY_EXCHANGE_RATE_GET_FAILED;
            } else {
                currencyExchangeRateModel = lstCurrencyExchangeRateModel.get(0);
            }

        } catch (Exception ex) {
            log.error("CurrencyExchangeRateBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return currencyExchangeRateModel;
    }
}
