/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 10:31 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.RememberNote;
import nybsys.tillboxweb.coreModels.RememberNoteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RememberNoteBllManager extends BaseBll<RememberNote> {

    private static final Logger log = LoggerFactory.getLogger(UserDefineSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(RememberNote.class);
        Core.runTimeModelType.set(RememberNoteModel.class);
    }

    public RememberNoteModel saveRememberNote(RememberNoteModel rememberNoteModel) throws Exception {
        try {

            if (rememberNoteModel.getRememberNoteID() > 0) {
                rememberNoteModel = this.save(rememberNoteModel);
            } else {
                this.update(rememberNoteModel);
            }

        } catch (Exception ex) {

            log.error("RememberNoteBllManager -> save remember note got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return rememberNoteModel;
    }
}
