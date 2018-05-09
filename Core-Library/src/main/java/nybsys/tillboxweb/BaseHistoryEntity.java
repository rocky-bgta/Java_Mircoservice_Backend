/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 29-Jan-18
 * Time: 4:27 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@DynamicInsert
@DynamicUpdate
public abstract @Data
class BaseHistoryEntity {


    private String messageId;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

  /*  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;*/



    private String entityClassPath;
    private String entityName;
    private Integer actionType;

    @Column(columnDefinition = "TEXT")
    private String jsonObject;

    private Date DateTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEntityClassPath() {
        return entityClassPath;
    }

    public void setEntityClassPath(String entityClassPath) {
        this.entityClassPath = entityClassPath;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }
}
