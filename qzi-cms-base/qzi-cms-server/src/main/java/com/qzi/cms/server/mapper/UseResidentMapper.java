/* 
 * 文件名：UseResidentMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年7月18日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import java.util.List;

import com.qzi.cms.common.vo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.qzi.cms.common.po.UseResidentPo;
import com.qzi.cms.server.base.BaseMapper;

import javax.swing.*;

/**
 * 住户信息DAO
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
public interface UseResidentMapper  extends BaseMapper<UseResidentPo>{

	/**
	 * @param rwoBounds
	 * @param criteria
	 * @param id
	 * @return
	 */
	public List<UseResidentVo> findAll(RowBounds rwoBounds,@Param("criteria") String criteria,@Param("uid") String id);


	/**
	 * @param criteria
	 * @param id
	 * @return
	 */
	public long findCount(@Param("criteria") String criteria,@Param("uid") String id);


	/**
	 * @param rwoBounds
	 * @param criteria
	 * @param
	 * @return
	 */
	public List<UseResidentVo> residentList(RowBounds rwoBounds,@Param("criteria") String criteria);


	/**
	 * @param criteria
	 * @param id
	 * @return
	 */
	public long residentCount(@Param("criteria") String criteria);

	/**
	 * @param rwoBounds
	 * @param criteria
	 * @param id
	 * @return
	 */



	/**
	 * 删除用户
	 * @param id
	 */
	@Delete("DELETE FROM use_resident WHERE id = #{id}")
	public void delResident(@Param("id") String id);

	/**
	 * 修改用户
	 * @param id
	 */
	@Delete("update use_resident  set state = #{state}  WHERE id = #{id}")
	public void updateResident(@Param("id") String id,@Param("state") String state);


	/**
	 * 删除授权
	 */
	@Delete("DELETE FROM use_resident_room WHERE id = #{id}")
	public void delAuth(@Param("id") String id);

	/**
	 * 删除用户授权
	 */
	@Delete("DELETE FROM use_resident_room WHERE residentId = #{id}")
	public void delAuthResidentId(@Param("id") String id);

	/**
	 * 修改授权
	 */
	@Update("update use_resident_room set owner =#{owner} where id= #{id}")
	public void updateAuth(@Param("id") String id,@Param("owner") String  owner);

	/**
	 * 修改授权
	 */
	@Update("update use_resident set residentType =#{type} where id= #{id}")
	public void updateCars(@Param("id") String id,@Param("type") String  type);



	/**
	 * @param criteria
	 * @param
	 * @return
	 */
	public long authCount(@Param("criteria") String criteria,@Param("communityId") String communityId);


	/**
	 * @param mobile
	 * @param
	 * @return
	 */
	@Select("select count(1)>0 from use_resident where mobile=#{mobile}")
	public boolean existsMobile(@Param("mobile") String mobile);


	/**
	 * @param loginName
	 * @return
	 */
	@Select("select * from use_resident where mobile=#{mobile}")
	public UseResidentPo findMobile(@Param("mobile") String loginName);



	@Select("select * from use_resident where mobile=#{mobile} and residentType ='20'")
		public UseResidentPo findMobileType(@Param("mobile") String loginName);



	@Select("select * from use_resident where wxId=#{wxId} limit 1")
	public UseResidentPo findWxId(@Param("wxId") String wxId);


	@Select("select * from use_resident where id=#{id} limit 1")
	public UseResidentPo findId(@Param("id") String id);


	@Select("select r.*,u.communityId from use_resident r left join use_community_resident u on u.residentId = r.id  where r.wxId=#{wxId} limit 1")
	public UseResidentVo findWxIds(@Param("wxId") String wxId);






	/**
	 * 新住户查询
	 * @param rwoBounds
	 * @param criteria
	 * @return
	 */
	public List<UseResidentVo> findAllByCriteria(RowBounds rwoBounds,@Param("criteria") String criteria);


	/**
	 *  新住户分页查询
	 * @param criteria
	 * @return
	 */
	public long findCountByCriteria(@Param("criteria") String criteria);



	/**
	 * @param mobile
	 * @param password
	 */
	@Select("update use_resident set password=#{password},salt=#{salt} where mobile=#{mobile}")
	public void updatePwd(@Param("mobile") String mobile,@Param("password") String password,@Param("salt") String salt);

	@Select("update use_resident set name=#{name} where mobile=#{mobile}")
	public void updateName(@Param("name") String name,@Param("mobile") String mobile);





	/**
	 * 查找房间对应的用户
	 * @param roomId 房间编号
	 * @return 用户集合
	 */
	@Select("SELECT ur.mobile,ur.`name`,urr.`owner`,ur.openPwd from use_resident ur,use_resident_room urr,use_room uro where ur.id = urr.residentId and uro.id = urr.roomId and uro.roomNo=#{roomId}")
	public List<CallVo> findCall(@Param("roomId") String roomId);




	@Update("update use_resident set createTime = now() where id= #{residentId}")
	public void updateCreateTime(@Param("residentId") String residentId);


	@Select("SELECT  * from use_resident where residentType = #{type}")
	public List<UseResidentPo> findType(@Param("type") String type);


	@Select("SELECT  * from use_resident where state='10'")
		public List<UseResidentPo> findAllRes();

}
