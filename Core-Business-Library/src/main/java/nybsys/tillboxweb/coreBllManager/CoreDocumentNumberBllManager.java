/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:27 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.DocumentNumber;
import nybsys.tillboxweb.coreModels.DocumentNumberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CoreDocumentNumberBllManager extends BaseBll<DocumentNumber> {

    private static final Logger log = LoggerFactory.getLogger(CoreDocumentNumberBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DocumentNumber.class);
        Core.runTimeModelType.set(DocumentNumberModel.class);
    }
}