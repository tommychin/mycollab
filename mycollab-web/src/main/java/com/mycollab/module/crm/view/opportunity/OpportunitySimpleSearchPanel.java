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
package com.mycollab.module.crm.view.opportunity;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.db.arguments.NumberSearchField;
import com.mycollab.db.arguments.SetSearchField;
import com.mycollab.db.arguments.StringSearchField;
import com.mycollab.module.crm.domain.criteria.OpportunitySearchCriteria;
import com.mycollab.module.user.ui.components.ActiveUserComboBox;
import com.mycollab.vaadin.AppContext;
import com.mycollab.vaadin.web.ui.GenericSearchPanel;
import com.mycollab.vaadin.web.ui.UIConstants;
import com.mycollab.vaadin.web.ui.ValueComboBox;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class OpportunitySimpleSearchPanel extends GenericSearchPanel<OpportunitySearchCriteria> {
    private TextField textValueField;
    private ActiveUserComboBox userBox;
    private GridLayout searchPanel;
    private ValueComboBox group;

    public OpportunitySimpleSearchPanel() {
        createBasicSearchLayout();
    }

    private void createBasicSearchLayout() {
        searchPanel = new GridLayout(3, 3);
        searchPanel.setSpacing(true);

        group = new ValueComboBox(false, "Name", "Account Name", "Sales Stage", AppContext.getMessage(GenericI18Enum.FORM_ASSIGNEE));
        group.select("Name");
        group.setImmediate(true);
        group.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                removeComponents();
                String searchType = (String) group.getValue();
                if (searchType.equals("Name")) {
                    addTextFieldSearch();
                } else if (searchType.equals("Account Name")) {
                    addTextFieldSearch();
                } else if (searchType.equals("Sales Stage")) {
                    addTextFieldSearch();
                } else if (searchType.equals(AppContext
                        .getMessage(GenericI18Enum.FORM_ASSIGNEE))) {
                    addUserListSelectField();
                }
            }
        });

        searchPanel.addComponent(group, 1, 0);
        searchPanel.setComponentAlignment(group, Alignment.MIDDLE_CENTER);
        addTextFieldSearch();

        Button searchBtn = new Button(AppContext.getMessage(GenericI18Enum.BUTTON_SEARCH));
        searchBtn.setStyleName(UIConstants.BUTTON_ACTION);
        searchBtn.setIcon(FontAwesome.SEARCH);
        searchBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                doSearch();
            }
        });
        searchPanel.addComponent(searchBtn, 2, 0);
        searchPanel.setComponentAlignment(searchBtn, Alignment.MIDDLE_CENTER);
        this.setCompositionRoot(searchPanel);
    }

    private void doSearch() {
        OpportunitySearchCriteria searchCriteria = new OpportunitySearchCriteria();
        searchCriteria.setSaccountid(new NumberSearchField(AppContext.getAccountId()));

        String searchType = (String) group.getValue();
        if (StringUtils.isNotBlank(searchType)) {
            if (textValueField != null) {
                String strSearch = textValueField.getValue();
                if (StringUtils.isNotBlank(strSearch)) {
                    if (searchType.equals("Name")) {
                        searchCriteria.setOpportunityName(StringSearchField.and(strSearch));
                    }
                }
            }

            if (userBox != null) {
                String user = (String) userBox.getValue();
                if (StringUtils.isNotBlank(user)) {
                    searchCriteria.setAssignUsers(new SetSearchField<>(user));
                }
            }
        }

        notifySearchHandler(searchCriteria);
    }

    private void addTextFieldSearch() {
        textValueField = new TextField();
        textValueField.addShortcutListener(new ShortcutListener("OpportunitySearchField",
                ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object o, Object o1) {
                doSearch();
            }
        });
        searchPanel.addComponent(textValueField, 0, 0);
        searchPanel.setComponentAlignment(textValueField, Alignment.MIDDLE_CENTER);
    }

    private void addUserListSelectField() {
        userBox = new ActiveUserComboBox();
        userBox.setImmediate(true);
        searchPanel.addComponent(userBox, 0, 0);
        searchPanel.setComponentAlignment(userBox, Alignment.MIDDLE_CENTER);
    }

    private void removeComponents() {
        searchPanel.removeComponent(0, 0);
        userBox = null;
        textValueField = null;
    }

    @Override
    public void setTotalCountNumber(int totalCountNumber) {

    }
}