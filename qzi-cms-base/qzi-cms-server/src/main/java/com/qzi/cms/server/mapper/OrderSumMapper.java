/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.ResidentAddressPo;
import com.qzi.cms.common.vo.OrderSumVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 订单详情
 * @author zyc
 * @version v1.0
 * @date 2019年8月29日
 */
public interface OrderSumMapper extends BaseMapper<OrderSumVo>{

	

	public List<OrderSumVo> findAll(@Param("startRow")int startRow,@Param("pageSize")int pageSize,@Param("model") OrderSumVo orderSumVo);

	public OrderSumVo  todayFindAll(@Param("model") OrderSumVo orderSumVo) ;

	
	/**
	 * @return
	 */

	public long findCount(@Param("model") OrderSumVo orderSumVo);



	public List<OrderSumVo> mouthfindAll(@Param("startRow")int startRow,@Param("pageSize")int pageSize,@Param("model") OrderSumVo orderSumVo);
	public long mouthfindCount(@Param("model") OrderSumVo orderSumVo);


	






}
