
package nybsys.tillboxweb;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public abstract class BaseDao extends Core {
    public BaseDao() {
        super();
    }

    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);

    public String getPrimaryKeyFieldName(Class clazz) {
        if(clazz==null)
            clazz = Core.runTimeEntityType.get();

        String primaryKeyField = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (StringUtils.endsWithIgnoreCase(field.getName(), "ID")) {
                primaryKeyField = field.getName();
                break;
            }
        }
        return primaryKeyField;
    }

    public /*<T>*/ String getPrimaryKeyFieldName(/*Class<T> clazz*/) {

        return getPrimaryKeyFieldName(null);

      /*  Class clazz = Core.runTimeEntityType.get();
        String primaryKeyField = null;
        Field[] fields = clazz.getDeclaredFields();
        //System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
           *//* System.out.printf("%s %s %s%n",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName()
            );*//*
            //System.out.println(field.getName());
            if (StringUtils.endsWithIgnoreCase(field.getName(), "ID")) {
                primaryKeyField = field.getName();
                break;
            }
        }
        return primaryKeyField;*/
    }

    public<T> Object getPrimaryFieldValue(Class clazz, T t) throws IllegalAccessException {
        //Map<String,Object> keyValue = new HashMap();
        if(clazz == null)
            clazz = Core.runTimeEntityType.get();

        //String primaryKeyField;
        Field[] fields = clazz.getDeclaredFields();
        Object value = null;

        for (Field field : fields) {
            field.setAccessible(true);
            if (StringUtils.endsWithIgnoreCase(field.getName(), "ID")) {
                //primaryKeyField = field.getName();
                value = field.get(t);
                //keyValue.put(primaryKeyField,value);
                //log.info("key: " + primaryKeyField);
                //log.info("Value: " + value);
                break;
            }
        }
        //return keyValue;
        return value;
    }

    public<T> List getPrimaryKeyValueWithType(Class clazz, T t) throws IllegalAccessException {
        Map<Object,Object> keyValue;
        List<Map<Object,Object>> keyValueWithTypeList = new ArrayList<>();
        String primaryKeyField;
        Field[] fields = clazz.getDeclaredFields();
        Object value;
        String fieldType;

        for (Field field : fields) {
            field.setAccessible(true);
            if (StringUtils.endsWithIgnoreCase(field.getName(), "ID")) {
                primaryKeyField = field.getName();
                value = field.get(t);
                fieldType = field.getType().toString();
                fieldType = StringUtils.substringAfterLast(fieldType, "class").trim();
                keyValue = new HashMap<>();
                keyValue.put(primaryKeyField,value);

                keyValueWithTypeList.add(keyValue);

                keyValue = new HashMap<>();
                keyValue.put("Type",fieldType);

                keyValueWithTypeList.add(keyValue);
                break;
            }
        }
        return keyValueWithTypeList;
    }

    public<T> Object getPrimaryFieldValue(T t) throws IllegalAccessException {
        return getPrimaryFieldValue(null,t);

        /*

        //Map<String,Object> keyValue = new HashMap();
        Class clazz = Core.runTimeEntityType.get();
        //String primaryKeyField;
        Field[] fields = clazz.getDeclaredFields();
        Object value = null;

        for (Field field : fields) {
            field.setAccessible(true);
            if (StringUtils.endsWithIgnoreCase(field.getName(), "ID")) {
                //primaryKeyField = field.getName();
                value = field.get(t);
                //keyValue.put(primaryKeyField,value);
                //log.info("key: " + primaryKeyField);
                //log.info("Value: " + value);
                break;
            }
        }
        //return keyValue;
        return value;

        */

    }

    public <M> List<M> getObjectListFromObjectArray(List<Object[]> objectList, Class<M> clazz) throws Exception{
        List<M> convertedModels = new ArrayList<>();
        M model;
        try {
            String jsonArray = Core.jsonMapper.writeValueAsString(objectList);
            jsonArray = StringUtils.replace(jsonArray, "[", "{");
            jsonArray = StringUtils.replace(jsonArray, "]", "}");
            jsonArray = jsonArray.substring(1, jsonArray.length() - 1);
            jsonArray = StringUtils.replace(jsonArray, ",", ":");
            jsonArray = StringUtils.replace(jsonArray, "}:", "},");

            String token[] = jsonArray.split(",");
            String fieldName;
            Object fieldValue;
            Field[] fields = clazz.getDeclaredFields();

            String temJson = "";
            String buildJson = "";

            for (String item : token) {
                String keyValue = item.substring(1, item.length() - 1);
                String subToken[] = keyValue.split(":");
                for (int i = 0; i < subToken.length; i++) {
                    fieldName = fields[i].getName();
                    fieldValue = subToken[i];
                    temJson += "\"" + fieldName+ "\":"+fieldValue +",";
                }
                temJson = temJson.substring(0,temJson.length()-1);
                buildJson += "{"+temJson+"}";
                model = clazz.newInstance();
                model =(M) Core.jsonMapper.readValue(buildJson, model.getClass());
                convertedModels.add(model);
                temJson="";
                buildJson="";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return convertedModels;
    }

    protected String buildSelectHql(String hql){
        String entityName, whereConditionString, allies="", selectQuery = null, lowerCaseQueryString;
        int setIndex, updateIndex, whereIndex;

        //hql="UPDATE Professor e SET e.salary = 60 WHERE e.salary = 50";
        try{
            lowerCaseQueryString = hql.toLowerCase();

            setIndex = StringUtils.indexOf(lowerCaseQueryString,"set");
            updateIndex = StringUtils.indexOfAnyBut(lowerCaseQueryString,"update");
            entityName = StringUtils.substring(hql,updateIndex+1,setIndex-1);
            entityName = StringUtils.substringBefore(entityName," ").trim();


            whereIndex = StringUtils.indexOf(lowerCaseQueryString,"where");
            whereConditionString = StringUtils.substring(hql,whereIndex);
            allies = StringUtils.substringBetween(hql.toLowerCase(),entityName.toLowerCase(),"set").trim();
            if(StringUtils.equals(allies,""))
                selectQuery = "SELECT " + entityName + " " + whereConditionString;
            else
                selectQuery = "SELECT " + entityName + " " + allies + " " + whereConditionString;

        }catch (Exception ex){
            log.error("Error in building select query before update for history table");
            ex.printStackTrace();
        }

        return selectQuery;
    }

    protected String getEntityNameFromHql(String hql){
        String entityName=null, lowerCaseQueryString;
        int setIndex, updateIndex;
        try{
            lowerCaseQueryString = hql.toLowerCase();
            setIndex = StringUtils.indexOf(lowerCaseQueryString,"set");
            updateIndex = StringUtils.indexOfAnyBut(lowerCaseQueryString,"update");
            entityName = StringUtils.substring(hql,updateIndex+1,setIndex-1);
            entityName = StringUtils.substringBefore(entityName," ").trim();

        }catch (Exception ex){
            log.error("Error in building select query before update for history table");
            ex.printStackTrace();
        }

        return entityName;
    }
}
