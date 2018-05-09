/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:30
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

public enum AccessRight {

    Administrator(1),
    StandardUser(2),
    Gust(3);
    private int accessRight;

    AccessRight(int accessRight) {
        this.accessRight = accessRight;
    }

    public int get() {
        return this.accessRight;
    }
}
