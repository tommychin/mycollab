/**
 * This file is part of mycollab-localization.
 *
 * mycollab-localization is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-localization is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-localization.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.module.user.accountsettings.localization;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

@BaseName("account-billing")
@LocaleData(value = {@Locale("en-US")}, defaultCharset = "UTF-8")
public enum BillingI18nEnum {
    VIEW_CHANGE_BILLING_PLAN_TITLE,
    BUTTON_CANCEL_ACCOUNT,
    HELP_QUESTION,
    HELP_INFO,
    OPT_CURRENT_PLAN,
    OPTION_BILLING_FAQ,
    OPT_CANNOT_CHANGE_PLAN,
    OPT_PRICING_MONTH,
    FORM_BILLING_PRICE,
    QUESTION_CHANGE_PLAN
}
