/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.module.user.accountsettings.customize.view;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.configuration.SiteConfiguration;
import com.mycollab.core.utils.BeanUtility;
import com.mycollab.module.user.accountsettings.localization.AdminI18nEnum;
import com.mycollab.module.user.domain.BillingAccount;
import com.mycollab.module.user.domain.SimpleBillingAccount;
import com.mycollab.module.user.service.BillingAccountService;
import com.mycollab.module.user.ui.components.LanguageSelectionField;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppContext;
import com.mycollab.vaadin.ui.AbstractBeanFieldGroupEditFieldFactory;
import com.mycollab.vaadin.ui.AbstractFormLayoutFactory;
import com.mycollab.vaadin.ui.AdvancedEditBeanForm;
import com.mycollab.vaadin.ui.CurrencyComboBoxField;
import com.mycollab.vaadin.web.ui.TimeZoneSelectionField;
import com.mycollab.vaadin.web.ui.UIConstants;
import com.mycollab.vaadin.web.ui.field.DateFormatField;
import com.mycollab.vaadin.web.ui.grid.GridFormLayoutHelper;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author MyCollab Ltd
 * @since 5.0.10
 */
class AccountInfoChangeWindow extends Window {
    private SimpleBillingAccount billingAccount;

    private AdvancedEditBeanForm<SimpleBillingAccount> editForm;

    AccountInfoChangeWindow() {
        super(AppContext.getMessage(AdminI18nEnum.OPT_CHANGE_ACCOUNT_INFO));
        this.setModal(true);
        this.setResizable(false);
        this.setWidth("700px");

        billingAccount = BeanUtility.deepClone(AppContext.getBillingAccount());
        MVerticalLayout content = new MVerticalLayout();
        this.setContent(content);
        editForm = new AdvancedEditBeanForm<>();
        editForm.setFormLayoutFactory(new AbstractFormLayoutFactory() {
            private GridFormLayoutHelper gridFormLayoutHelper = GridFormLayoutHelper.defaultFormLayoutHelper(1, 9, "200px");

            @Override
            public ComponentContainer getLayout() {
                return gridFormLayoutHelper.getLayout();
            }

            @Override
            public Component onAttachField(Object propertyId, Field<?> field) {
                if (BillingAccount.Field.sitename.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_SITE_NAME), 0, 0);
                } else if (BillingAccount.Field.subdomain.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_SITE_ADDRESS), 0, 1);
                } else if (BillingAccount.Field.defaulttimezone.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_DEFAULT_TIMEZONE), 0, 2);
                } else if (BillingAccount.Field.defaultcurrencyid.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_DEFAULT_CURRENCY), 0, 3);
                } else if (BillingAccount.Field.defaultyymmddformat.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_DEFAULT_YYMMDD_FORMAT),
                            AppContext.getMessage(GenericI18Enum.FORM_DATE_FORMAT_HELP), 0, 4);
                } else if (BillingAccount.Field.defaultmmddformat.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_DEFAULT_MMDD_FORMAT),
                            AppContext.getMessage(GenericI18Enum.FORM_DATE_FORMAT_HELP), 0, 5);
                } else if (BillingAccount.Field.defaulthumandateformat.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_DEFAULT_HUMAN_DATE_FORMAT),
                            AppContext.getMessage(GenericI18Enum.FORM_DATE_FORMAT_HELP), 0, 6);
                } else if (BillingAccount.Field.defaultlanguagetag.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_DEFAULT_LANGUAGE), 0, 7);
                } else if (BillingAccount.Field.displayemailpublicly.equalTo(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, AppContext.getMessage(AdminI18nEnum.FORM_SHOW_EMAIL_PUBLICLY),
                            AppContext.getMessage(AdminI18nEnum.FORM_SHOW_EMAIL_PUBLICLY_HELP), 0, 8);
                }
                return null;
            }
        });

        editForm.setBeanFormFieldFactory(new AbstractBeanFieldGroupEditFieldFactory<SimpleBillingAccount>(editForm) {
            @Override
            protected Field<?> onCreateField(Object propertyId) {
                if (BillingAccount.Field.subdomain.equalTo(propertyId)) {
                    return new SubDomainField();
                } else if (BillingAccount.Field.defaulttimezone.equalTo(propertyId)) {
                    return new TimeZoneSelectionField(false);
                } else if (BillingAccount.Field.defaultcurrencyid.equalTo(propertyId)) {
                    return new CurrencyComboBoxField();
                } else if (BillingAccount.Field.defaultyymmddformat.equalTo(propertyId)) {
                    return new DateFormatField(billingAccount.getDateFormatInstance());
                } else if (BillingAccount.Field.defaultmmddformat.equalTo(propertyId)) {
                    return new DateFormatField(billingAccount.getShortDateFormatInstance());
                } else if (BillingAccount.Field.defaulthumandateformat.equalTo(propertyId)) {
                    return new DateFormatField(billingAccount.getLongDateFormatInstance());
                } else if (BillingAccount.Field.defaultlanguagetag.equalTo(propertyId)) {
                    return new LanguageSelectionField();
                }
                return null;
            }

        });

        editForm.setBean(billingAccount);

        MHorizontalLayout buttonControls = new MHorizontalLayout().withMargin(true);
        MButton saveBtn = new MButton(AppContext.getMessage(GenericI18Enum.BUTTON_SAVE), clickEvent -> {
            if (editForm.validateForm()) {
                BillingAccountService billingAccountService = AppContextUtil.getSpringBean(BillingAccountService.class);
                billingAccountService.updateSelectiveWithSession(billingAccount, AppContext.getUsername());
                close();
                String siteUrl = SiteConfiguration.getSiteUrl(billingAccount.getSubdomain());
                String assignExec = String.format("window.location.assign(\'%s\');", siteUrl);
                Page.getCurrent().getJavaScript().execute(assignExec);
            }
        }).withIcon(FontAwesome.SAVE).withStyleName(UIConstants.BUTTON_ACTION);

        MButton cancelBtn = new MButton(AppContext.getMessage(GenericI18Enum.BUTTON_CANCEL), clickEvent -> close())
                .withStyleName(UIConstants.BUTTON_OPTION);
        buttonControls.with(cancelBtn, saveBtn);

        content.with(editForm, buttonControls).withAlign(buttonControls, Alignment.MIDDLE_RIGHT);
    }

    private static class SubDomainField extends CustomField<String> {
        private TextField subDomainField = new TextField();

        @Override
        protected Component initContent() {
            MHorizontalLayout layout = new MHorizontalLayout().withSpacing(false);
            layout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
            Label httpsLabel = new Label("https://");
            Label domainLbl = new Label(".mycollab.com");
            layout.with(httpsLabel, subDomainField, domainLbl);
            return layout;
        }

        @Override
        public void setPropertyDataSource(Property newDataSource) {
            String value = (String) newDataSource.getValue();
            if (value != null) {
                subDomainField.setValue(value);
            }
            super.setPropertyDataSource(newDataSource);
        }

        @Override
        public void commit() throws SourceException, Validator.InvalidValueException {
            setInternalValue(subDomainField.getValue());
            super.commit();
        }

        @Override
        public Class<? extends String> getType() {
            return String.class;
        }
    }
}
