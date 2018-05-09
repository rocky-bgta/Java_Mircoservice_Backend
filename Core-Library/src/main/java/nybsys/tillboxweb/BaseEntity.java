/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 29-Dec-17
 * Time: 3:03 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity implements Serializable{

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "status")
    private Integer status;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getCreatedBy(), that.getCreatedBy()) &&
                Objects.equals(getUpdatedBy(), that.getUpdatedBy()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getUpdatedDate(), that.getUpdatedDate()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCreatedBy(), getUpdatedBy(), getCreatedDate(), getUpdatedDate(), getStatus());
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", status=" + status +
                '}';
    }
}
