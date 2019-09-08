/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SysNoticePo;
import com.qzi.cms.common.po.SysPushPo;
import com.qzi.cms.common.vo.SysNoticeVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 公告
 * zyc
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SysNoticeMapper extends BaseMapper<SysNoticePo>{

	/**
	 * @param rwoBounds
	 * @return
	 */
	@Select("select * from sys_notice s left join  use_community u on s.communityId = u.id order by s.createTime desc")
	public List<SysNoticeVo> findAll(RowBounds rwoBounds);



	@Select("select * from sys_notice order by createTime desc")
	public List<SysNoticePo> findAllApp();


	/**
	 * @return
	 */
	@Select("select count(1) from sys_push")
	public long findCount();



}
