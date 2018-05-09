/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:52 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class VMCustomerReturn extends BaseModel {
    public CustomerReturnModel customerReturnModel;
    public List<CustomerReturnDetailModel> lstCustomerReturnDetailModel;

    public VMCustomerReturn()
    {
        customerReturnModel=new CustomerReturnModel();
        lstCustomerReturnDetailModel=new ArrayList<>();
    }

}
