/**
 * This file is part of mycollab-scheduler.
 *
 * mycollab-scheduler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-scheduler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-scheduler.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.module.user.schedule.email.service

import java.util.{Arrays, Locale}

import com.mycollab.common.UrlEncodeDecoder
import com.mycollab.module.mail.service.IContentGenerator
import com.mycollab.module.user.domain.SimpleUser
import com.mycollab.module.user.domain.criteria.UserSearchCriteria
import com.mycollab.module.user.service.UserService
import com.mycollab.common.domain.MailRecipientField
import com.mycollab.common.{GenericLinkUtils, UrlEncodeDecoder}
import com.mycollab.configuration.SiteConfiguration
import com.mycollab.db.arguments.{BasicSearchRequest, SetSearchField}
import com.mycollab.i18n.LocalizationHelper
import com.mycollab.module.billing.UserStatusConstants
import com.mycollab.module.mail.service.ExtMailService
import com.mycollab.module.user.accountsettings.localization.UserI18nEnum
import com.mycollab.schedule.jobs.GenericQuartzJobBean
import org.quartz.{JobExecutionContext, JobExecutionException}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * @author MyCollab Ltd.
 * @since 4.6.0
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class UserSignUpEmailNotificationJob extends GenericQuartzJobBean {
    private val LOG: Logger = LoggerFactory.getLogger(classOf[UserSignUpEmailNotificationJob])
    @Autowired var userService: UserService = _
    @Autowired var extMailService: ExtMailService = _
    @Autowired var contentGenerator: IContentGenerator = _

    @SuppressWarnings(Array("unchecked"))
    @throws(classOf[JobExecutionException])
    def executeJob(context: JobExecutionContext) {
        val criteria = new UserSearchCriteria
        val statusField = new SetSearchField[String](UserStatusConstants.EMAIL_NOT_VERIFIED)
        criteria.setStatuses(statusField)
        criteria.setSaccountid(null)

        import scala.collection.JavaConverters._
        val users = userService.findPagableListByCriteria(new BasicSearchRequest[UserSearchCriteria](criteria,
            0, Integer.MAX_VALUE)).asScala.toList.asInstanceOf[List[SimpleUser]]
        if (users != null && users.nonEmpty) {
            for (user <- users) {
                sendConfirmEmailToUser(user)
                user.setStatus(UserStatusConstants.EMAIL_VERIFIED_REQUEST)
                userService.updateWithSession(user, user.getUsername)
            }
        }
    }

    def sendConfirmEmailToUser(user: SimpleUser) {
        try {
            contentGenerator.putVariable("user", user)
            val siteUrl = GenericLinkUtils.generateSiteUrlByAccountId(user.getAccountId)
            contentGenerator.putVariable("siteUrl", siteUrl)
            val confirmLink = siteUrl + "user/confirm_signup/" + UrlEncodeDecoder.encode(user.getUsername + "/" + user.getAccountId)
            contentGenerator.putVariable("linkConfirm", confirmLink)
            extMailService.sendHTMLMail(SiteConfiguration.getNotifyEmail, SiteConfiguration.getDefaultSiteName,
                Arrays.asList(new MailRecipientField(user.getEmail, user.getDisplayName)), null, null,
                LocalizationHelper.getMessage(Locale.US, UserI18nEnum.MAIL_CONFIRM_PASSWORD_SUBJECT),
                contentGenerator.parseFile("mailConfirmUserSignUpNotification.ftl", Locale.US), null)
        } catch {
            case e: Exception => LOG.error("Can not send confirm email ", e)
        }
    }
}
