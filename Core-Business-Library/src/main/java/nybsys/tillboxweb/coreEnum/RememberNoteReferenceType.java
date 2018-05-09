/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/27/2018
 * Time: 11:04 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreEnum;

public enum RememberNoteReferenceType {
    Account(1),
    Customer(2),
    Supplier(3),
    Invoice(4);

    private int rememberNoteReferenceType;

    RememberNoteReferenceType(int referenceType) {
        this.rememberNoteReferenceType = referenceType;
    }

    public int get() {
        return this.rememberNoteReferenceType;
    }
}
