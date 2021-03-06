/**
 * This file is part of mycollab-mobile.
 *
 * mycollab-mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-mobile.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.mobile.module.crm.view.opportunity;

import com.mycollab.common.UrlEncodeDecoder;
import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.eventmanager.EventBusFactory;
import com.mycollab.mobile.module.crm.events.OpportunityEvent;
import com.mycollab.mobile.shell.events.ShellEvent;
import com.mycollab.mobile.module.crm.view.AbstractCrmPresenter;
import com.mycollab.module.crm.domain.Opportunity;
import com.mycollab.module.crm.domain.SimpleOpportunity;
import com.mycollab.module.crm.service.OpportunityService;
import com.mycollab.security.RolePermissionCollections;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppContext;
import com.mycollab.vaadin.events.DefaultEditFormHandler;
import com.mycollab.vaadin.mvp.ScreenData;
import com.mycollab.vaadin.ui.NotificationUtil;
import com.vaadin.ui.ComponentContainer;

/**
 * @author MyCollab Ltd.
 * @since 4.1
 */
public class OpportunityAddPresenter extends AbstractCrmPresenter<OpportunityAddView> {
    private static final long serialVersionUID = 5202691686429793555L;

    public OpportunityAddPresenter() {
        super(OpportunityAddView.class);
    }

    @Override
    protected void postInitView() {
        view.getEditFormHandlers().addFormHandler(new DefaultEditFormHandler<SimpleOpportunity>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onSave(final SimpleOpportunity item) {
                saveOpportunity(item);
                EventBusFactory.getInstance().post(new ShellEvent.NavigateBack(this, null));
            }

            @Override
            public void onSaveAndNew(final SimpleOpportunity item) {
                saveOpportunity(item);
                EventBusFactory.getInstance().post(new OpportunityEvent.GotoAdd(this, null));
            }
        });
    }

    @Override
    protected void onGo(ComponentContainer container, ScreenData<?> data) {
        if (AppContext.canWrite(RolePermissionCollections.CRM_OPPORTUNITY)) {

            SimpleOpportunity opportunity = null;
            if (data.getParams() instanceof SimpleOpportunity) {
                opportunity = (SimpleOpportunity) data.getParams();
            } else if (data.getParams() instanceof Integer) {
                OpportunityService accountService = AppContextUtil.getSpringBean(OpportunityService.class);
                opportunity = accountService.findById((Integer) data.getParams(), AppContext.getAccountId());
            }
            if (opportunity == null) {
                NotificationUtil.showRecordNotExistNotification();
                return;
            }
            super.onGo(container, data);
            view.editItem(opportunity);

            if (opportunity.getId() == null) {
                AppContext.addFragment("crm/opportunity/add", AppContext.getMessage(GenericI18Enum.BROWSER_ADD_ITEM_TITLE, "Opportunity"));
            } else {
                AppContext.addFragment("crm/opportunity/edit/" + UrlEncodeDecoder.encode(opportunity.getId()),
                        AppContext.getMessage(GenericI18Enum.BROWSER_EDIT_ITEM_TITLE, "Opportunity", opportunity.getOpportunityname()));
            }
        } else {
            NotificationUtil.showMessagePermissionAlert();
        }
    }

    private void saveOpportunity(Opportunity opportunity) {
        OpportunityService opportunityService = AppContextUtil.getSpringBean(OpportunityService.class);

        opportunity.setSaccountid(AppContext.getAccountId());
        if (opportunity.getId() == null) {
            opportunityService.saveWithSession(opportunity, AppContext.getUsername());
        } else {
            opportunityService.updateWithSession(opportunity, AppContext.getUsername());
        }
    }
}
