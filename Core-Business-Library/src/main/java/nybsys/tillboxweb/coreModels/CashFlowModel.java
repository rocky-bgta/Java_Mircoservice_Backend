/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:22 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CashFlowModel extends BaseModel {

    private Integer cashFlowID;
    private String cashFlowName;

    public Integer getCashFlowID() {
        return cashFlowID;
    }

    public void setCashFlowID(Integer cashFlowID) {
        this.cashFlowID = cashFlowID;
    }

    public String getCashFlowName() {
        return cashFlowName;
    }

    public void setCashFlowName(String cashFlowName) {
        this.cashFlowName = cashFlowName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashFlowModel)) return false;
        if (!super.equals(o)) return false;
        CashFlowModel that = (CashFlowModel) o;
        return Objects.equals(getCashFlowID(), that.getCashFlowID()) &&
                Objects.equals(getCashFlowName(), that.getCashFlowName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCashFlowID(), getCashFlowName());
    }

    @Override
    public String toString() {
        return "CashFlowModel{" +
                "cashFlowID=" + cashFlowID +
                ", cashFlowName='" + cashFlowName + '\'' +
                '}';
    }
}
