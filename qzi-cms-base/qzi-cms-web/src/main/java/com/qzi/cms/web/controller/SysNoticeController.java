/* 
 * 文件名：ParameterController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月15日
 * 版本号：v1.0
*/
package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysNoticePo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysParameterVo;
import com.qzi.cms.server.mapper.SysNoticeMapper;
import com.qzi.cms.server.mapper.UseCommunityMapper;
import com.qzi.cms.server.mapper.UseCommunityResidentMapper;
import com.qzi.cms.server.service.web.ParameterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 公告控制器
 * @author zyc
 * @version v1.0
 * @date 2017年6月15日
 */
@RestController
@RequestMapping("/sysNotice")
public class SysNoticeController {
	@Resource
	private SysNoticeMapper sysNoticeMapper;

	@Resource
	private UseCommunityMapper useCommunityMapper;
	
	@GetMapping("/findAll")
	public RespBody findAll(Paging paging){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "公告查找所有数据成功", sysNoticeMapper.findAll(paging));
			//保存分页对象
			paging.setTotalCount(sysNoticeMapper.findCount());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "公告查找所有数据失败");
			LogUtils.error("公告查找所有数据失败！",ex);
		}
		return respBody;
	}


    //查询项目列表
	@GetMapping("/findAllCom")
	public RespBody findAllCom(){
		RespBody respBody = new RespBody();
		try {

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "项目查找所有数据成功", useCommunityMapper.findAllCom());
		
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "项目查找所有数据失败");
			LogUtils.error("项目查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/add")
	@SystemControllerLog(description="新增公告")
	public RespBody add(@RequestBody SysNoticePo sysNoticePo){
		RespBody respBody = new RespBody();
		try {
			sysNoticePo.setId(ToolUtils.getUUID());
			sysNoticePo.setCreateTime(new Date());
			sysNoticePo.setState("10");
			sysNoticeMapper.insert(sysNoticePo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "公告保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "公告保存失败");
			LogUtils.error("公告保存失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/update")
	@SystemControllerLog(description="修改公告")
	public RespBody update(@RequestBody SysNoticePo sysNoticePo){
		RespBody respBody = new RespBody();
		try {
			sysNoticeMapper.updateByPrimaryKey(sysNoticePo);

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "公告保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "公告保存失败");
			LogUtils.error("公告保存失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/delete")
	@SystemControllerLog(description="删除公告")
	public RespBody delete(@RequestBody SysNoticePo sysNoticePo){
		RespBody respBody = new RespBody();
		try {
			sysNoticeMapper.delete(sysNoticePo);
			
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "公告删除成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "公告删除失败");
			LogUtils.error("公告删除失败！",ex);
		}
		return respBody;
	}

}
