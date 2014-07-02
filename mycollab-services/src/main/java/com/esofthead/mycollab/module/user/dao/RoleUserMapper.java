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
package com.esofthead.mycollab.module.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.esofthead.mycollab.core.persistence.ICrudGenericDAO;
import com.esofthead.mycollab.module.user.domain.RoleUser;
import com.esofthead.mycollab.module.user.domain.RoleUserExample;

public interface RoleUserMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int countByExample(RoleUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int deleteByExample(RoleUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int insert(RoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int insertSelective(RoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    List<RoleUser> selectByExample(RoleUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    RoleUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int updateByExampleSelective(@Param("record") RoleUser record, @Param("example") RoleUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int updateByExample(@Param("record") RoleUser record, @Param("example") RoleUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int updateByPrimaryKeySelective(RoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    int updateByPrimaryKey(RoleUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    Integer insertAndReturnKey(RoleUser value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_roleuser
     *
     * @mbggenerated Tue Jan 15 16:44:21 GMT+07:00 2013
     */
    void removeKeysWithSession(List primaryKeys);
}