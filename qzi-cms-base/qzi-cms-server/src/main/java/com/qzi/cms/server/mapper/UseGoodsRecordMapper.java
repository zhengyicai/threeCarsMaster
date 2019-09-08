/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Delete;
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
public interface UseGoodsRecordMapper extends BaseMapper<GoodsRecordPo>{

	/**
	 * @param rwoBounds
	 * @return
	 */
	@Select("select * from use_goods_record order by createTime desc")
	public List<GoodsRecordPo> findAll(RowBounds rwoBounds);


	@Select("select * from use_goods_record where goodsId = #{id} order by createTime desc")
	public List<GoodsRecordPo> findAllId( @Param("id") String  id);

	@Select("select * from use_goods_record order by createTime desc")
	public List<GoodsRecordPo> findAllApp();


	@Delete("delete from use_goods_record where id = #{model.id}")
	public void removePrice(@Param("model") GoodsRecordPo po);

	/**
	 * @return
	 */
	@Select("select count(1) from use_goods_record")
	public long findCount();


}
