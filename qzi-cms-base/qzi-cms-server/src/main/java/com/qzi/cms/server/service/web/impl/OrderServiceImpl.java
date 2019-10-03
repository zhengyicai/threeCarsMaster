/* 
 * 文件名：SysParameterServiceImpl.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.service.web.impl;

import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.OrderSumVo;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.server.mapper.OrderSumMapper;
import com.qzi.cms.server.mapper.UseGoodsMapper;
import com.qzi.cms.server.mapper.UseResidentOrderMapper;
import com.qzi.cms.server.service.web.GoodsService;
import com.qzi.cms.server.service.web.OrderService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 参数设置业务层实现类
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Resource
	private UseResidentOrderMapper  useResidentOrderMapper;

	@Resource
	private OrderSumMapper orderSumMapper;

	@Override
	public List<ResidentOrderVo> findAll(Paging paging,ResidentOrderVo vo) throws Exception {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return useResidentOrderMapper.findAll(startRow,pageSize,vo);
	}



	@Override
	public void add(ResidentOrderPo residentOrderPo) throws Exception {

		//调用dao保存数据
		residentOrderPo.setId(ToolUtils.getUUID());
		residentOrderPo.setCreateTime(new Date());
		residentOrderPo.setState("10");

		useResidentOrderMapper.insert(residentOrderPo);
	}


	@Override
	public void update(ResidentOrderPo residentOrderPo) throws Exception {

		//调用dao修改数据
		useResidentOrderMapper.updateByPrimaryKey(residentOrderPo);
	}



	@Override
	public void delete(ResidentOrderPo residentOrderPo) {
		//调用dao删除数据
		useResidentOrderMapper.deleteByPrimaryKey(residentOrderPo.getId());
	}

	@Override
	public long findCount(ResidentOrderVo vo) {
		return useResidentOrderMapper.findCount(vo);
	}

	@Override
	public List<OrderSumVo> findSum(Paging paging, OrderSumVo vo) throws Exception {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return orderSumMapper.findAll(startRow,pageSize,vo);
	}

	@Override
	public long findCountSum(OrderSumVo vo) {
		return orderSumMapper.findCount(vo);
	}

	@Override
	public List<OrderSumVo> mouthfindSum(Paging paging, OrderSumVo vo) throws Exception {
		int startRow=0;int pageSize=0;
			if(null!=paging){
				startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
				pageSize=paging.getPageSize();
			}else{
				pageSize=Integer.MAX_VALUE;
			}
		return orderSumMapper.mouthfindAll(startRow,pageSize,vo);
	}

	@Override
	public long mouthfindCountSum(OrderSumVo vo) {
		return orderSumMapper.mouthfindCount(vo);
	}

}
