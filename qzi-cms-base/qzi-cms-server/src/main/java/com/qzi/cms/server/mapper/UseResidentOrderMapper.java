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
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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



	



}
