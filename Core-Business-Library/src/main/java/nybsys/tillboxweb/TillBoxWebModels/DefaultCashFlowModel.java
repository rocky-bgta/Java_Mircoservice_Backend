/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:19 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DefaultCashFlowModel extends BaseModel {

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
        if (!(o instanceof DefaultCashFlowModel)) return false;
        if (!super.equals(o)) return false;
        DefaultCashFlowModel that = (DefaultCashFlowModel) o;
        return Objects.equals(getCashFlowID(), that.getCashFlowID()) &&
                Objects.equals(getCashFlowName(), that.getCashFlowName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCashFlowID(), getCashFlowName());
    }

    @Override
    public String toString() {
        return "DefaultCashFlowModel{" +
                "cashFlowID=" + cashFlowID +
                ", cashFlowName='" + cashFlowName + '\'' +
                '}';
    }
}
