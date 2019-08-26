/* 
 * 文件名：SysParameterMapper.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.server.mapper;

import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysParameterPo;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysParameterVo;
import com.qzi.cms.server.base.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 设置file
 * @author qsy
 * @version v1.0
 * @date 2017年6月15日
 */
public interface SysEquipmentFileMapper extends BaseMapper<SysEquipmentFilePo>{

	/**
	 * @param equipmentId
	 * @return
	 */
	@Select("select ef.*, f.fileUrl,f.standards from sys_equipment_file ef  left join  sys_fileUrl f  on ef.fileId = f.id where ef.equipmentId = #{equipmentId}  order by ef.createTime desc")
	public List<SysEquipmentFileVo> findAll(@Param("equipmentId") String equipmentId);

	@Delete("delete from  sys_equipment_file where equipmentId = #{equipmentId}")
	public void deleteList(@Param("equipmentId") String equipmentId);

	@Delete("delete from  sys_equipment_file where userId = #{userId}")
	public void deleteListSelect(@Param("userId") String userId);


	@Update("update sys_equipment_file set titleDetail = #{detail} where  equipmentId=#{equipmentId} and fileId =#{fileId} ")
	public  void updateTitle(@Param("equipmentId") String equipmentId,@Param("fileId") String fileId,@Param("detail") String detail);
	

    @Select("select * from sys_equipment_file where equipmentId=#{equipmentId} and fileId =#{fileId} limit 1 ")
	public SysEquipmentFileVo findOne(@Param("equipmentId") String equipmentId,@Param("fileId") String fileId);

	

}
