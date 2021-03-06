package com.book.admin.user;

import com.book.common.entity.Setting;
import com.book.common.entity.SettingBag;
import java.util.List;

public class GeneralSettingBag extends SettingBag {

	public GeneralSettingBag(List<Setting> listSettings) {
		super(listSettings);
	}

	public void updateCurrencySymbol(String value) {
		super.update("CURRENCY_SYMBOL", value);
	}

}