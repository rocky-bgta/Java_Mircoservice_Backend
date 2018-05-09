/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 10:31 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.RememberNote;
import nybsys.tillboxweb.coreModels.RememberNoteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RememberNoteBllManager extends BaseBll<RememberNote> {

    private static final Logger log = LoggerFactory.getLogger(RememberNoteBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(RememberNote.class);
        Core.runTimeModelType.set(RememberNoteModel.class);
    }

    public List<RememberNoteModel> getAllRememberNoteModelByBusinessID(Integer businessID) throws Exception {
        List<RememberNoteModel> rememberNoteModels = new ArrayList<>();
        RememberNoteModel rememberNoteModel = new RememberNoteModel();

        try {
            rememberNoteModel.setStatus(TillBoxAppEnum.Status.Active.get());
            rememberNoteModel.setBusinessID(businessID);
            rememberNoteModels = this.getAllByConditions(rememberNoteModel);

        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> getAllOpeningBalance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return rememberNoteModels;
    }

    public List<RememberNoteModel> saveRememberNote(List<RememberNoteModel> lstRememberNoteModel) throws Exception {

        List<RememberNoteModel> lstSavedRememberNoteModel=new ArrayList<>();
        try {

            for (RememberNoteModel rememberNoteModel : lstRememberNoteModel) {
                if (rememberNoteModel.getRememberNoteID() != null && rememberNoteModel.getRememberNoteID() > 0) {
                    this.update(rememberNoteModel);
                } else {
                    rememberNoteModel = this.save(rememberNoteModel);
                    lstSavedRememberNoteModel.add(rememberNoteModel);
                }
            }

        } catch (Exception ex) {
            log.error("RememberNoteBllManager -> save remember note got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSavedRememberNoteModel;
    }
}
