/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:28 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.DefaultDocumentNumber;
import nybsys.tillboxweb.TillBoxWebModels.DefaultDocumentNumberModel;
import org.springframework.stereotype.Service;

@Service
public class DefaultDocumentNumberBllManager extends BaseBll<DefaultDocumentNumber> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DefaultDocumentNumber.class);
        Core.runTimeModelType.set(DefaultDocumentNumberModel.class);
    }
}