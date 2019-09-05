/* 
 * 文件名：SysParameterService.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.service.web;

import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.common.vo.UseResidentVo;

import java.util.List;

/**
 * 订单
 * @author zyc
 * @version v1.0
 * @date 2017年6月15日
 */
public interface OrderService {

	/**
	 * 查找所有数据
	 * @param paging 分页
	 * @return 集合
	 * @throws Exception 
	 */
	public List<ResidentOrderVo> findAll(Paging paging,ResidentOrderVo vo) throws Exception;

	/**
	 * 新增
	 * @param residentOrderPo
	 * @throws Exception 
	 */
	public void add(ResidentOrderPo residentOrderPo) throws Exception;

	/**
	 * 修改
	 * @param residentOrderPo
	 * @throws Exception 
	 */
	public void update(ResidentOrderPo residentOrderPo) throws Exception;

	/**
	 * 删除
	 * @param residentOrderPo
	 */
	public void delete(ResidentOrderPo residentOrderPo);

	/**
	 * 查找总记录数
	 * @return
	 */
	public long findCount(ResidentOrderVo vo);
	
}