/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 30-Mar-18
 * Time: 3:09 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */


package nybsys.tillboxweb;

import java.util.ArrayList;
import java.util.List;

public final class CommonApiList {
    public static final List<String> COMMON_API_LIST = new ArrayList<>();
    static {

        COMMON_API_LIST.add("api/business/select");
        COMMON_API_LIST.add("api/business/save");
        COMMON_API_LIST.add("api/business/getByUserID");
        COMMON_API_LIST.add("api/business/getByID");
        COMMON_API_LIST.add("api/userInvite/invite");
        COMMON_API_LIST.add("api/userInvite/reInvite");
        COMMON_API_LIST.add("api/userInvite/removeInvitation");
        COMMON_API_LIST.add("api/userInvite/createUserWithBusinessId");
        COMMON_API_LIST.add("api/user/getUserListByBusinessID");
        COMMON_API_LIST.add("api/user/getUserListActiveAndInactiveAndInvited");
        COMMON_API_LIST.add("api/changePassword/update");
        COMMON_API_LIST.add("api/forgetPassword/userID/get");
    }
}

