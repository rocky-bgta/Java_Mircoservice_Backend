/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:27 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class ProductDocument extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productDocumentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer productDocumentID;

    @Column
    @NotNull(message = "product id  cannot be empty")
    private Integer productID;
    private Byte[] document;
    private String name;

    public Integer getProductDocumentID() {
        return productDocumentID;
    }

    public void setProductDocumentID(Integer productDocumentID) {
        this.productDocumentID = productDocumentID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Byte[] getDocument() {
        return document;
    }

    public void setDocument(Byte[] document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDocument)) return false;
        if (!super.equals(o)) return false;
        ProductDocument that = (ProductDocument) o;
        return Objects.equals(getProductDocumentID(), that.getProductDocumentID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Arrays.equals(getDocument(), that.getDocument()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductDocumentID(), getProductID(), getDocument(), getName());
    }

    @Override
    public String toString() {
        return "ProductDocument{" +
                "productDocumentID=" + productDocumentID +
                ", productID=" + productID +
                ", document=" + Arrays.toString(document) +
                ", name='" + name + '\'' +
                '}';
    }
}
