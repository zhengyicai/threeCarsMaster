/* 
 * 文件名：SysParameterService.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.service.web;

import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.common.resp.Paging;

import java.util.List;

/**
 * 货品修改记录
 * @author zyc
 * @version v1.0
 * @date 2017年6月15日
 */
public interface GoodsRecordService {

	/**
	 * 查找所有数据
	 * @param paging 分页
	 * @return 集合
	 * @throws Exception 
	 */
	public List<GoodsRecordPo> findAll(Paging paging) throws Exception;

	/**
	 * 新增
	 * @param goodsPo
	 * @throws Exception 
	 */
	public void add(GoodsRecordPo goodsPo) throws Exception;

	/**
	 * 修改
	 * @param goodsPo
	 * @throws Exception 
	 */
	public void update(GoodsRecordPo goodsPo) throws Exception;

	/**
	 * 删除
	 * @param goodsPo
	 */
	public void delete(GoodsRecordPo goodsPo);

	/**
	 * 查找总记录数
	 * @return
	 */
	public long findCount();
	
}
