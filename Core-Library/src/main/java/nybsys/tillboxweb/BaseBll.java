package nybsys.tillboxweb;


import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.dao.Dao;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
public abstract class BaseBll<T extends BaseEntity> extends Core {

    private static final Logger log = LoggerFactory.getLogger(BaseBll.class);

    public BaseBll() {
        super();
    }

    protected abstract void initEntityModel();

    private Dao dao = new Dao();

    private Boolean insertDataInHistory=true;

    //type                return type
    public <M extends BaseModel> List<M> getAll() throws Exception {
        initEntityModel();
        List<T> entityList;
        List modelList = new ArrayList();
        Object model;
        try {
            entityList = this.dao.getAll();
            if (entityList.size() > 0) {
                modelList = new ArrayList<>();
                for (T entity : entityList) {
                    model = Core.modelMapper.map(entity, Core.runTimeModelType.get());
                    modelList.add(model);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return modelList;
    }

    //type                return type
    public <M extends BaseModel> List<M> getAllByConditions(M whereCondition) throws Exception {
        initEntityModel();
        Map<Object, Object> keyValueParis;
        List<T> entityList;
        List modelList = new ArrayList();
        Object model;
        String hql;
        Integer queryType = TillBoxAppEnum.QueryType.Select.get();
        try {
            hql = this.queryBuilder(whereCondition, queryType);
            keyValueParis = Core.getKeyValuePairFromObject(whereCondition,queryType);
            entityList = this.dao.getAllByConditions(hql, keyValueParis);
            if (entityList.size() > 0) {
                modelList = new ArrayList<>();
                for (T entity : entityList) {
                    model = Core.modelMapper.map(entity, Core.runTimeModelType.get());
                    modelList.add(model);
                }
            }
        } catch (Exception ex) {
             log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return modelList;
    }

    //type                return type
    public <M extends BaseModel> List<M> getAllByConditionWithActive(M whereCondition) throws Exception {
        initEntityModel();
        Map<Object, Object> keyValueParis;
        List<T> entityList;
        List modelList = new ArrayList();
        Object model;
        String hql;
        Integer queryType = TillBoxAppEnum.QueryType.Select.get();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            hql = this.queryBuilder(whereCondition,queryType);
            keyValueParis = Core.getKeyValuePairFromObject(whereCondition,queryType);
            entityList = this.dao.getAllByConditions(hql, keyValueParis);
            if (entityList.size() > 0) {
                modelList = new ArrayList<>();
                for (T entity : entityList) {
                    model = Core.modelMapper.map(entity, Core.runTimeModelType.get());
                    modelList.add(model);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return modelList;
    }

    public <T extends BaseEntity, M extends BaseModel> M save(M m) throws Exception {
        initEntityModel();
        Object model = null, entity = null;
        T t;
        try {
            t = (T) Core.modelMapper.map(m, Core.runTimeEntityType.get());

            // ================================================
            t.setStatus(TillBoxAppEnum.Status.Active.get());
            if(Core.userId.get()== null ||StringUtils.equals(Core.userId.get(),"") || StringUtils.equals(Core.userId.get(),"string")){
                t.setCreatedBy("System");
                t.setUpdatedBy("System");
            }else {
                t.setCreatedBy(Core.userId.get());
              //  t.setUpdatedBy(Core.userId.get());
            }

            t.setCreatedDate(new Date());
            t.setUpdatedDate(new Date());
            // ================================================


        /*    if(Core.isCommonApi.get() || Core.commonDataBase.get())
                insertDataInHistory=false;*/

            entity = this.dao.save(t,insertDataInHistory);
            model = Core.modelMapper.map(entity, Core.runTimeModelType.get());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) model;
    }

   /* public <T extends BaseEntity, M extends BaseModel> M saveOrUpdate(M m) throws Exception {
        initEntityModel();
        Object model = null, entity = null;
        T t;
        try {
            t = (T) Core.modelMapper.map(m, Core.runTimeEntityType.get());
            entity = this.dao.saveOrUpdate(t);
            model = Core.modelMapper.map(entity, Core.runTimeModelType.get());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return (M) model;
    }*/

    public <T extends BaseEntity, M extends BaseModel> M softDelete(M m) throws Exception {
        initEntityModel();
        Object model = null, entity = null;
        T t;
        try {
            t = (T) Core.modelMapper.map(m, Core.runTimeEntityType.get());

            // ================================================
            t.setStatus(TillBoxAppEnum.Status.Deleted.get());
            if(Core.userId.get()== null ||StringUtils.equals(Core.userId.get(),"") || StringUtils.equals(Core.userId.get(),"string")){
                t.setUpdatedBy("System");
            }else {
                t.setUpdatedBy(Core.userId.get());
            }

            t.setUpdatedDate(new Date());
            // ================================================
/*
            if(Core.isCommonApi.get() || Core.commonDataBase.get())
                insertDataInHistory=false;*/

            entity = this.dao.update(t,insertDataInHistory);
            model = Core.modelMapper.map(entity, Core.runTimeModelType.get());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) model;
    }
    public <T extends BaseEntity, M extends BaseModel> M inActive(M m) throws Exception {
        initEntityModel();
        Object model = null, entity = null;
        T t;
        try {
            t = (T) Core.modelMapper.map(m, Core.runTimeEntityType.get());

            // ================================================
            t.setStatus(TillBoxAppEnum.Status.Inactive.get());
            if(Core.userId.get()== null ||StringUtils.equals(Core.userId.get(),"") || StringUtils.equals(Core.userId.get(),"string")){
                t.setUpdatedBy("System");
            }else {
                t.setUpdatedBy(Core.userId.get());
            }

            t.setUpdatedDate(new Date());
            // ================================================

          /*  if(Core.isCommonApi.get() || Core.commonDataBase.get())
                insertDataInHistory=false;*/

            entity = this.dao.update(t,insertDataInHistory);
            model = Core.modelMapper.map(entity, Core.runTimeModelType.get());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) model;
    }

    public <T extends BaseEntity, M extends BaseModel> M update(M m) throws Exception {
        initEntityModel();
        Object model = null, entity = null;
        T t;
        try {
            t = (T) Core.modelMapper.map(m, Core.runTimeEntityType.get());

            // ================================================
            if(Core.userId.get()== null ||StringUtils.equals(Core.userId.get(),"") || StringUtils.equals(Core.userId.get(),"string")){
                t.setUpdatedBy("System");
            }else {
                t.setUpdatedBy(Core.userId.get());
            }

            t.setUpdatedDate(new Date());
            // ================================================

          /*  if(Core.isCommonApi.get() || Core.commonDataBase.get())
                insertDataInHistory=false;*/

            entity = this.dao.update(t,insertDataInHistory);
            model = Core.modelMapper.map(entity, Core.runTimeModelType.get());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) model;
    }

    public <M extends BaseModel> boolean delete(M m) throws Exception {
        initEntityModel();
        T t;
        boolean isDeleted = false;
        try {
            t = (T) Core.modelMapper.map(m, Core.runTimeEntityType.get());
            this.dao.delete(t);
            isDeleted = true;

        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return isDeleted;
    }

    public boolean deleteById(Object id) throws Exception {
        initEntityModel();
        boolean isDeleted = false;
        try {
            this.dao.deleteById(id);
            isDeleted = true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return isDeleted;
    }


    public <M extends BaseModel> Integer updateByConditions(M whereCondition, M objectToUpdate) throws Exception {
        initEntityModel();
        Map<Object, Object> keyValueParisForWhereCondition,keyValuePairForUpdate;
        String updateHql,selectHql;
        Integer numOfUpdatedRows = 0;
        try {

            // ================================================
            if(Core.userId.get()== null ||StringUtils.equals(Core.userId.get(),"") || StringUtils.equals(Core.userId.get(),"string")){
                objectToUpdate.setUpdatedBy("System");
            }else {
                objectToUpdate.setUpdatedBy(Core.userId.get());
            }

            objectToUpdate.setUpdatedDate(new Date());
            // ================================================


            updateHql = this.queryBuilder(whereCondition,
                    TillBoxAppEnum.QueryType.Update.get(),
                    objectToUpdate);

            selectHql = this.queryBuilder(whereCondition,TillBoxAppEnum.QueryType.Select.get());

            keyValuePairForUpdate = Core.getKeyValuePairFromObject(objectToUpdate,TillBoxAppEnum.QueryType.Update.get());
            keyValueParisForWhereCondition = Core.getKeyValuePairFromObject(whereCondition,TillBoxAppEnum.QueryType.Select.get());


          /*  if(Core.isCommonApi.get() || Core.commonDataBase.get())
                insertDataInHistory=false;*/


            numOfUpdatedRows = this.dao.updateByConditions(updateHql,
                    selectHql,
                    keyValuePairForUpdate,
                    keyValueParisForWhereCondition,
                    insertDataInHistory);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numOfUpdatedRows;
    }

    public <M extends BaseModel> Integer deleteByConditions(M whereCondition) throws Exception {
        initEntityModel();
        Map<Object, Object> keyValueParis;
        String hql;
        Integer numOfDeletedRows = 0;
        try {
            hql = this.queryBuilder(whereCondition,
                    TillBoxAppEnum.QueryType.Delete.get());
            keyValueParis = Core.getKeyValuePairFromObject(whereCondition);
            numOfDeletedRows = this.dao.deleteByConditions(hql, keyValueParis);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numOfDeletedRows;
    }

    public <M extends BaseModel> M getById(Object id) throws Exception {
        return getById(id,null);
    }

    public <M extends BaseModel> M getByIdActiveStatus(Object id) throws Exception {
        return getById(id,TillBoxAppEnum.Status.Active.get());
    }

    public <M extends BaseModel> M getById(Object id, Integer status) throws Exception {
        initEntityModel();
        Object model = null, entity = null;
        try {
            entity = this.dao.getById(id, status);
            if (entity != null)
                model = Core.modelMapper.map(entity, Core.runTimeModelType.get());
            else return null;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) model;
    }

    public <M> List<M> executeHqlQuery(String hql, Class<M> clazz, int queryType) throws Exception {
        List<M> modelList = new ArrayList<>();
        List<M> tempList;
        try {
            tempList = (List<M>) this.dao.executeHqlQuery(hql, clazz, queryType, insertDataInHistory);

            int size = tempList.size();
            if (size > 0) {
                for (Object model : tempList) {
                    model = Core.modelMapper.map(model, clazz);
                    modelList.add((M) model);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return modelList;
    }

    public List<Object[]> executeNativeQuery(String hql, int queryType) throws Exception {
        initEntityModel();
        List<Object[]> result;
        try {
            result = this.dao.executeNativeSqlQuery(hql, queryType);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            for(Throwable throwable: ex.getSuppressed()){
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return result;
    }

    protected BllResponseMessage getDefaultBllResponse(){
        BllResponseMessage bllResponseMessage = new BllResponseMessage();
        bllResponseMessage.message="Failed";
        bllResponseMessage.responseCode = 404;
        bllResponseMessage.responseObject = null;
        return bllResponseMessage;
    }

    protected RequestMessage getDefaultRequestMessage() {
        RequestMessage requestMessage = new RequestMessage();
        return requestMessage;
    }

}
