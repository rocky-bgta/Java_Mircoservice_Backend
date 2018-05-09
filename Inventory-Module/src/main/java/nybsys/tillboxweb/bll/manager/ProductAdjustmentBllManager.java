/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 4:29 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreBllManager.CoreJournalBllManager;
import nybsys.tillboxweb.coreEnum.*;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreModels.VMJournalListModel;
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.ProductAdjustment;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.ProductAdjustmentModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductAdjustmentBllManager extends BaseBll<ProductAdjustment> {

    private static final Logger log = LoggerFactory.getLogger(ProductAdjustmentBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductAdjustment.class);
        Core.runTimeModelType.set(ProductAdjustmentModel.class);
    }

    @Autowired
    private CoreJournalBllManager coreJournalBllManager;

    public ProductAdjustmentModel saveOrUpdateProductAdjustment(ProductAdjustmentModel productAdjustmentModel, CurrencyModel currencyModel,Integer entryCurrencyID) throws Exception {
        Integer primaryKeyValue = productAdjustmentModel.getProductAdjustmentID();
        ProductAdjustmentModel processedProductAdjustmentModel = null;
        JournalModel drJournalModel , crJournalModel;
        List<JournalModel> journalModelList = new ArrayList<>();
        Boolean isJournalEntrySuccess;
        JournalModel whereConditionForJournal,modelToUpdateJournal;


        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                processedProductAdjustmentModel = this.save(productAdjustmentModel);

                if (processedProductAdjustmentModel == null) {
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ADJUSTMENT;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
            } else {
                // Update Code
                processedProductAdjustmentModel = this.update(productAdjustmentModel);

                // ==================== delete journal ====================================================
                whereConditionForJournal = new JournalModel();
                whereConditionForJournal.setReferenceType(ReferenceType.ProductAdjustment.get());
                whereConditionForJournal.setReferenceID(processedProductAdjustmentModel.getProductAdjustmentID());

                modelToUpdateJournal = new JournalModel();
                modelToUpdateJournal.setStatus(TillBoxAppEnum.Status.Deleted.get());

                this.coreJournalBllManager.updateByConditions(whereConditionForJournal,modelToUpdateJournal);
                // ==================== delete journal end =================================================

                if (processedProductAdjustmentModel == null) {
                    Core.clientMessage.get().message = MessageConstant.UPDATE_FAILED_PRODUCT_ADJUSTMENT;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }

            }

            // save journal
            String journalReferenceNo = TillBoxUtils.getUUID();
            if(processedProductAdjustmentModel.getAdjustmentType() == Adjustment.Decrease.get()) {

                drJournalModel = CoreUtils.buildJournalEntry(
                        journalReferenceNo,
                        DefaultCOA.AdjustmentLoss.get(),
                        processedProductAdjustmentModel.getProductAdjustmentID(),
                        ReferenceType.ProductAdjustment.get(),
                        null,//processedProductAdjustmentModel.getProductAdjustmentID(),
                        null,//PartyType.ItemAdjustment.get(),
                        processedProductAdjustmentModel.getTotalPrice(),
                        DebitCreditIndicator.Debit.get(),
                        processedProductAdjustmentModel.getReason(),
                        currencyModel,
                        processedProductAdjustmentModel.getExchangeRate(),
                        entryCurrencyID
                );

                crJournalModel = CoreUtils.buildJournalEntry(
                        journalReferenceNo,
                        DefaultCOA.Inventory.get(),
                        processedProductAdjustmentModel.getProductAdjustmentID(),
                        ReferenceType.ProductAdjustment.get(),
                        null,//processedProductAdjustmentModel.getProductAdjustmentID(),
                        null,//PartyType.ItemAdjustment.get(),
                        processedProductAdjustmentModel.getTotalPrice(),
                        DebitCreditIndicator.Credit.get(),
                        processedProductAdjustmentModel.getReason(),
                        currencyModel,
                        processedProductAdjustmentModel.getExchangeRate(),
                        entryCurrencyID
                );
            }else {
                drJournalModel = CoreUtils.buildJournalEntry(
                        journalReferenceNo,
                        DefaultCOA.Inventory.get(),
                        processedProductAdjustmentModel.getProductAdjustmentID(),
                        ReferenceType.ProductAdjustment.get(),
                        null,//processedProductAdjustmentModel.getProductAdjustmentID(),
                        null,//PartyType.ItemAdjustment.get(),
                        processedProductAdjustmentModel.getTotalPrice(),
                        DebitCreditIndicator.Debit.get(),
                        processedProductAdjustmentModel.getReason(),
                        currencyModel,
                        processedProductAdjustmentModel.getExchangeRate(),
                        entryCurrencyID
                );

                crJournalModel = CoreUtils.buildJournalEntry(
                        journalReferenceNo,
                        DefaultCOA.AdjustmentIncome.get(),
                        processedProductAdjustmentModel.getProductAdjustmentID(),
                        ReferenceType.ProductAdjustment.get(),
                         null,//processedProductAdjustmentModel.getProductAdjustmentID(),
                        null, //PartyType.ItemAdjustment.get(),
                        processedProductAdjustmentModel.getTotalPrice(),
                        DebitCreditIndicator.Credit.get(),
                        processedProductAdjustmentModel.getReason(),
                        currencyModel,
                        processedProductAdjustmentModel.getExchangeRate(),
                        entryCurrencyID
                );
            }

            journalModelList.add(drJournalModel);
            journalModelList.add(crJournalModel);

            isJournalEntrySuccess = this.coreJournalBllManager.saveOrUpdate(journalModelList);
            if(isJournalEntrySuccess){
               log.info("Journal save successfully");
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentBllManager -> saveOrUpdateProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return processedProductAdjustmentModel;
    }



    private ResponseMessage journalEntry(ProductAdjustmentModel modelForJournalEntry) throws Exception {
        List<JournalModel> lstJournalModel = new ArrayList<>();
        JournalModel drJournalModel = new JournalModel();
        JournalModel crJournalModel = new JournalModel();
        //build dr model
        //user-input (1-decrease)
        // 1 -  defaultCOA.AdjustmentLoss.get() dr
        // 1 -  defaultCOA.Inventory.get() cr

        //user-input (2-increase)
        // 2 -  defaultCOA.Inventory.get() dr
        // 2 -  defaultCOA.AdjustmentIncome.get() cr

        if(modelForJournalEntry.getAdjustmentType() == Adjustment.Decrease.get()){

            drJournalModel.setAccountID(DefaultCOA.StockAdjustmentLoss.get());
            drJournalModel.setPartyID(modelForJournalEntry.getProductAdjustmentID());
            drJournalModel.setBusinessID(Core.businessId.get());
            drJournalModel.setPartyType(PartyType.ItemAdjustment.get());
            drJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            drJournalModel.setNote(modelForJournalEntry.getReason());
            drJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            drJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());
            drJournalModel.setDate(new Date());

            crJournalModel.setAccountID(DefaultCOA.Inventory.get());
            crJournalModel.setBusinessID(Core.businessId.get());
            crJournalModel.setPartyID(modelForJournalEntry.getProductAdjustmentID());
            crJournalModel.setPartyType(PartyType.ItemAdjustment.get());
            crJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            crJournalModel.setNote(modelForJournalEntry.getReason());
            crJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            crJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());
            crJournalModel.setDate(new Date());
        }

        if(modelForJournalEntry.getAdjustmentType() == Adjustment.Increase.get()){
            drJournalModel.setAccountID(DefaultCOA.Inventory.get());
            drJournalModel.setPartyID(modelForJournalEntry.getProductAdjustmentID());
            drJournalModel.setBusinessID(Core.businessId.get());
            drJournalModel.setPartyType(PartyType.ItemAdjustment.get());
            drJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            drJournalModel.setNote(modelForJournalEntry.getReason());
            drJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            drJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());
            drJournalModel.setDate(new Date());

            crJournalModel.setAccountID(DefaultCOA.StockAdjustmentIncome.get());
            crJournalModel.setBusinessID(Core.businessId.get());
            crJournalModel.setPartyID(modelForJournalEntry.getProductAdjustmentID());
            crJournalModel.setPartyType(PartyType.ItemAdjustment.get());
            crJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            crJournalModel.setNote(modelForJournalEntry.getReason());
            crJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            crJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());
            crJournalModel.setDate(new Date());
        }

        // add double entry
        lstJournalModel.add(drJournalModel);
        lstJournalModel.add(crJournalModel);
        VMJournalListModel vmJournalListModel = new VMJournalListModel();

        //================== inter-module communication for journal save ===========================
        boolean workCompleteWithInAllowTime;
        ResponseMessage responseMessage;
        RequestMessage requestMessage;
        CallBack callBackForJournal;
        MqttClient mqttClientForJournal;
        Object lockObject = new Object();
        String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
        this.barrier = TillBoxUtils.getBarrier(1, lockObject);

        //======================= Start of one ===========================
        requestMessage = Core.getDefaultWorkerRequestMessage();
        requestMessage.brokerMessage.serviceName = "api/journal/save";
        requestMessage.businessID = Core.businessId.get();
        vmJournalListModel.lstJournalModel = lstJournalModel;
        requestMessage.requestObj =  vmJournalListModel;
        SubscriberForWorker subForWorker = new SubscriberForWorker(requestMessage.brokerMessage.messageId, this.barrier);
        mqttClientForJournal = subForWorker.subscribe();
        callBackForJournal = subForWorker.getCallBack();
        PublisherForWorker pubForWorkerToSaveJournal = new PublisherForWorker(pubTopic, mqttClientForJournal);
        pubForWorkerToSaveJournal.publishedMessageToWorker(requestMessage);
        //======================= End of one ===========================

        synchronized (lockObject) {
            long startTime = System.nanoTime();
            lockObject.wait(this.allowedTime);
            workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);
            if (workCompleteWithInAllowTime) {
                responseMessage = callBackForJournal.getResponseMessage();
            } else {
                //timeout
                throw new Exception("Response not get within allowed time");
            }
        }
        this.closeBrokerClient(mqttClientForJournal, requestMessage.brokerMessage.messageId);
        return responseMessage;
    }

}
