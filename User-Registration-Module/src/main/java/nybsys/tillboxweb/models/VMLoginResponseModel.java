/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 03/04/2018
 * Time: 11:51
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.TillBoxWebModels.UserModel;

import java.util.ArrayList;
import java.util.List;

public class VMLoginResponseModel {
    public UserModel userModel = new UserModel();
    public List<BusinessModel> lstBusinessModel = new ArrayList<>();
}
