/* 
 * 文件名：SysParameterServiceImpl.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.service.web.impl;

import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.server.mapper.UseGoodsMapper;
import com.qzi.cms.server.mapper.UseGoodsRecordMapper;
import com.qzi.cms.server.service.web.GoodsRecordService;
import com.qzi.cms.server.service.web.GoodsService;
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
public class GoodsRecordServiceImpl implements GoodsRecordService{
	@Resource
	private UseGoodsRecordMapper useGoodsRecordMapper;

	@Override
	public List<GoodsRecordPo> findAll(Paging paging) throws Exception {
		RowBounds rwoBounds = new RowBounds(paging.getPageNumber(),paging.getPageSize());
		return useGoodsRecordMapper.findAll(rwoBounds);
	}



	@Override
	public void add(GoodsRecordPo goodsPo) throws Exception {

		//调用dao保存数据
		goodsPo.setId(ToolUtils.getUUID());
		goodsPo.setCreateTime(new Date());
		goodsPo.setState("10");
		useGoodsRecordMapper.insert(goodsPo);
	}


	@Override
	public void update(GoodsRecordPo goodsPo) throws Exception {
		//调用dao修改数据
		useGoodsRecordMapper.updateByPrimaryKey(goodsPo);
	}



	@Override
	public void delete(GoodsRecordPo goodsPo) {
		//调用dao删除数据
		useGoodsRecordMapper.removePrice(goodsPo);
	}

	@Override
	public long findCount() {
		return useGoodsRecordMapper.findCount();
	}

}
