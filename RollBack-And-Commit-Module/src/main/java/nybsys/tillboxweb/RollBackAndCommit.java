/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 31-Jan-18
 * Time: 5:13 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb;

import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.dao.Dao;
import nybsys.tillboxweb.entities.History;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class RollBackAndCommit extends BaseDao {

    public static SessionFactory sessionFactory;

    private static final Logger log = LoggerFactory.getLogger(RollBackAndCommit.class);
    private static Dao dao;


    public void rollBack(String messageId){
        Boolean insertDataInHistory = false;
        String jsonString, HistoryEntityClassPath, entityClassPath, hql;
        Class clazz;
        List<BaseHistoryEntity> historyEntityList;
        String historyEntityName;
        HistoryEntityClassPath = History.class.toString();
        historyEntityName = StringUtils.substringAfterLast(HistoryEntityClassPath, ".").trim();
        Object entity;
        List<Object> entityList;
        Map<Object,Object> primaryKeyValueMap;
        Boolean isExist;
        List<Map<Object,Object>> keyValueWithTypeList;

        int actionType;

        try {

            if(RollBackAndCommit.dao==null)
                RollBackAndCommit.dao = new Dao(sessionFactory);

            if(messageId==null){
                //hql = "SELECT e FROM " + historyEntityName + " e" ;
                throw new Exception("Must provide messageId");
            }else {
                hql = "SELECT e FROM " + historyEntityName + " e WHERE e.messageId = " + "'" + messageId + "'";
            }

            historyEntityList = (List<BaseHistoryEntity>) this.dao.executeHqlQuery(hql,
                    History.class,
                    TillBoxAppEnum.QueryType.Select.get(),
                    insertDataInHistory);

            for (BaseHistoryEntity historyEntity : historyEntityList) {
                jsonString = historyEntity.getJsonObject();
                entityClassPath = historyEntity.getEntityClassPath();
                actionType = historyEntity.getActionType();
                clazz = Class.forName(entityClassPath);

                if(TillBoxAppEnum.QueryType.Insert.get()==actionType /*|| TillBoxAppEnum.QueryType.Update.get()==actionType*/){
                    entity = Core.jsonMapper.readValue(jsonString,clazz);
                    keyValueWithTypeList = this.getPrimaryKeyValueWithType(clazz,entity);
                    isExist = this.dao.isRowExist(keyValueWithTypeList,clazz);
                    if(isExist)
                        RollBackAndCommit.dao.delete(entity);
                    isExist = false;
                }

                if(TillBoxAppEnum.QueryType.Update.get()==actionType){
                    entity =  Core.jsonMapper.readValue(jsonString,clazz);
                    keyValueWithTypeList = this.getPrimaryKeyValueWithType(clazz,entity);
                    isExist = RollBackAndCommit.dao.isRowExist(keyValueWithTypeList,clazz);
                    if(isExist)
                        RollBackAndCommit.dao.update(entity,insertDataInHistory);
                    isExist = false;
                }

                if(TillBoxAppEnum.QueryType.UpdateByConditions.get()==actionType){
                    entityList = (List<Object>) Core.jsonMapper.readValue(jsonString,List.class);
                    for(Object updateEntity: entityList){
                        entity = Core.modelMapper.map(updateEntity, clazz);
                        keyValueWithTypeList = this.getPrimaryKeyValueWithType(clazz,entity);
                        isExist = this.dao.isRowExist(keyValueWithTypeList,clazz);
                        if(isExist)
                            RollBackAndCommit.dao.update(entity,insertDataInHistory);
                        isExist = false;
                    }
                }
                RollBackAndCommit.dao.delete(historyEntity);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commit(String messageId){
        Boolean insertDataInHistory = false;
        String jsonString, HistoryEntityClassPath, entityClassPath, hql;
        List<BaseHistoryEntity> historyEntityList;
        String historyEntityName;
        HistoryEntityClassPath = History.class.toString();
        historyEntityName = StringUtils.substringAfterLast(HistoryEntityClassPath, ".").trim();

        try {

            if(RollBackAndCommit.dao==null)
                RollBackAndCommit.dao = new Dao(sessionFactory);

            if(messageId==null){
                throw new Exception("Must provide messageId");
            }else {
                hql = "SELECT e FROM " + historyEntityName + " e WHERE e.messageId = " + "'" + messageId + "'";
            }

            historyEntityList = (List<BaseHistoryEntity>) this.dao.executeHqlQuery(hql,
                    History.class,
                    TillBoxAppEnum.QueryType.Select.get(),
                    insertDataInHistory);

            for (BaseHistoryEntity historyEntity : historyEntityList) {
                RollBackAndCommit.dao.delete(historyEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
