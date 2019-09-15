/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.ResidentAddressPo;
import com.qzi.cms.common.po.ResidentOrderDetailPo;
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
public interface UseResidentAddressMapper extends BaseMapper<ResidentAddressPo>{

	/**
	 * @param rwoBounds
	 * @return
	 */
	@Select("select * from use_resident_address order by createTime desc")
	public List<ResidentAddressPo> findAll(RowBounds rwoBounds);

	
	/**
	 * @return
	 */
	@Select("select count(1) from use_resident_address")
	public long findCount();



	@Select("select * from use_resident_address where  state='10' and  wxId=#{wxId} order by type asc")
	public  List<ResidentAddressPo> findAllWxId(@Param("wxId") String wxId);

	@Select("select * from use_resident_address where id = #{id}")
	public ResidentAddressPo findOne(@Param("id") String id);
	






}
