/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.ResidentOrderDetailPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 订单详情
 * @author zyc
 * @version v1.0
 * @date 2019年8月29日
 */
public interface UseOrderDetailMapper extends BaseMapper<ResidentOrderDetailPo>{

	/**
	 * @param rwoBounds
	 * @return
	 */
	@Select("select * from use_order_detail order by createTime desc")
	public List<ResidentOrderDetailPo> findAll(RowBounds rwoBounds);

	
	/**
	 * @return
	 */
	@Select("select count(1) from use_order_detail")
	public long findCount();


}
