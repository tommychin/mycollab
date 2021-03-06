/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.module.billing.service;

import com.mycollab.common.domain.CustomerFeedbackWithBLOBs;
import com.mycollab.core.cache.CacheEvict;
import com.mycollab.core.cache.CacheKey;
import com.mycollab.core.cache.Cacheable;
import com.mycollab.db.persistence.service.IService;
import com.mycollab.module.user.domain.BillingAccountWithOwners;
import com.mycollab.module.user.domain.BillingPlan;

import java.util.List;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public interface BillingService extends IService {

    void registerAccount(String subdomain, int billingPlanId, String username,
                         String password, String email, String timezoneId,
                         boolean isEmailVerified);

    void cancelAccount(Integer accountid, CustomerFeedbackWithBLOBs feedback);

    @Cacheable
    BillingPlan findBillingPlan(@CacheKey Integer sAccountId);

    @CacheEvict
    void updateBillingPlan(@CacheKey Integer accountId, int newBillingPlanId);

    List<String> getSubDomainsOfUser(String username);

    List<BillingPlan> getAvailablePlans();

    BillingPlan getFreeBillingPlan();

    List<BillingAccountWithOwners> getTrialAccountsWithOwners();
}
