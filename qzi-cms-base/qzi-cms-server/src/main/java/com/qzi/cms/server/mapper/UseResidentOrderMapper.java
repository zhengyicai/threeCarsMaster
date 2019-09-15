/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.*;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.common.vo.TotalCountVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * goodDAO
 * @author zyc
 * @version v1.0
 * @date 2019年8月29日
 */
public interface UseResidentOrderMapper extends BaseMapper<ResidentOrderPo>{

	/**
	 * @param rwoBounds
	 * @return
	 */

	public List<ResidentOrderVo> findAll(RowBounds rwoBounds,@Param("model") ResidentOrderVo vo);

	
	/**
	 * @return
	 */
	
	public long findCount(@Param("model") ResidentOrderVo vo);

	@Select("select * from use_order_detail where orderId = #{orderId}")
	public List<ResidentOrderDetailPo> findRecord(@Param("orderId") String orderId);

	@Select("select * from use_resident_order where id = #{id}")
	public ResidentOrderPo findId(@Param("id") String id);


	@Update("update use_resident_order set buyPrice =#{buyPrice} where id = #{id}")
	void updateBuyprice(@Param("buyPrice") String buyPrice,@Param("id") String id);


	@Update("update use_resident_order set sellPrice =#{sellPrice} where id = #{id}")
	void updateSellprice(@Param("sellPrice") String sellPrice,@Param("id") String id);



	@Select("select IFNULL(sum(sellPrice),0) as sumCount , count(1) as totalCount from  use_resident_order where TO_DAYS(createTime)=TO_DAYS(NOW()) and carId = #{wxId}")
	public TotalCountVo findToDay(@Param("wxId") String wxId);

	@Select("select IFNULL(sum(sellPrice),0) as sumCount , count(1) as totalCount from use_resident_order where YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d'))=YEARWEEK(NOW())  and carId = #{wxId}")
	public TotalCountVo findToWeek(@Param("wxId") String wxId);


	@Select("select IFNULL(sum(sellPrice),0) as sumCount , count(1) as totalCount from use_resident_order where DATE_FORMAT(createTime,'%Y%m')=DATE_FORMAT(CURDATE(),'%Y%m') and carId = #{wxId}")
	public TotalCountVo findToMonth(@Param("wxId") String wxId);


	@Select("select * from  use_resident_order where TO_DAYS(createTime)=TO_DAYS(NOW()) and carId = #{wxId}")
	public List<UseResidentPo> dayList(@Param("wxId") String wxId);


	@Select("select * from use_resident_order where YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d'))=YEARWEEK(NOW()) and carId = #{wxId}")
	public List<UseResidentPo> weekList(@Param("wxId") String wxId);


	@Select("select * from use_resident_order where DATE_FORMAT(createTime,'%Y%m')=DATE_FORMAT(CURDATE(),'%Y%m') and carId = #{wxId}")
	public List<UseResidentPo> monthList(@Param("wxId") String wxId);
		




	
}
