/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 02/05/2018
 * Time: 04:18
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.coreModels.CurrencyExchangeRateModel;
import nybsys.tillboxweb.coreModels.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

public class VMCurrencySettingModel {
   public List<CurrencyModel> lstCurrencyModel = new ArrayList<>();
   public List<CurrencyExchangeRateModel> lstCurrencyExchangeRateModel = new ArrayList<>();
   public CurrencySettingModel currencySettingModel = new CurrencySettingModel();
}
