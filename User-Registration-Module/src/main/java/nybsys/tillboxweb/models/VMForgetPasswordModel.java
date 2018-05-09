/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 20/02/2018
 * Time: 12:54
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

public class VMForgetPasswordModel {
    private String token ;
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
