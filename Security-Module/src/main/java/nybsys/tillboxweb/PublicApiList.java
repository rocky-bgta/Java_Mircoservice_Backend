/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 07-Feb-18
 * Time: 3:38 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;

import java.util.ArrayList;
import java.util.List;

public final class PublicApiList {
    public static final List<String> PUBLIC_API_LIST = new ArrayList<>();
    static {
        PUBLIC_API_LIST.add("api/user/signUp");
        PUBLIC_API_LIST.add("api/user/login");
        PUBLIC_API_LIST.add("api/forgetPassword/userID/get");
        PUBLIC_API_LIST.add("api/userInvite/search");
        PUBLIC_API_LIST.add("api/forgetPassword/update");
        PUBLIC_API_LIST.add("api/user/signUpConfirmation");
    }
}
