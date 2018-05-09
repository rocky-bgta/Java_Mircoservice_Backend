/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 9:45 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Arrays;
import java.util.Objects;

public class ProductDocumentModel extends BaseModel {
    private Integer productDocumentID;
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
}
