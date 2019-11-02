/* 
 * 文件名：SysParameterServiceImpl.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.service.web.impl;

import com.qzi.cms.common.po.ResidentOrderDetailPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.ResidentOrderDetailSumVo;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.server.mapper.UseOrderDetailMapper;
import com.qzi.cms.server.mapper.UseResidentOrderMapper;
import com.qzi.cms.server.service.web.OrderDetailService;
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
public class OrderServiceDetailImpl implements OrderDetailService{
	@Resource
	private UseOrderDetailMapper useOrderDetailMapper;

	@Override
	public List<ResidentOrderDetailPo> findAll(Paging paging) throws Exception {
		RowBounds rwoBounds = new RowBounds(paging.getPageNumber(),paging.getPageSize());
		return useOrderDetailMapper.findAll(rwoBounds);
	}

	@Override
	public List<ResidentOrderDetailSumVo> findAllSum(Paging paging) throws Exception {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return useOrderDetailMapper.findAllSum(startRow,pageSize);
	}


	@Override
	public void add(ResidentOrderDetailPo residentOrderDetailPo) throws Exception {

		//调用dao保存数据
		residentOrderDetailPo.setId(ToolUtils.getUUID());
		residentOrderDetailPo.setCreateTime(new Date());
		residentOrderDetailPo.setState("10");

		useOrderDetailMapper.insert(residentOrderDetailPo);
	}


	@Override
	public void update(ResidentOrderDetailPo residentOrderDetailPo) throws Exception {

		//调用dao修改数据
		useOrderDetailMapper.updateByPrimaryKey(residentOrderDetailPo);
	}



	@Override
	public void delete(ResidentOrderDetailPo residentOrderPo) {
		//调用dao删除数据
		useOrderDetailMapper.deleteByPrimaryKey(residentOrderPo.getId());
	}

	@Override
	public long findCount() {
		return useOrderDetailMapper.findCount();
	}

	@Override
	public long findCountSum() {
		return useOrderDetailMapper.findCountSum();
	}

}
