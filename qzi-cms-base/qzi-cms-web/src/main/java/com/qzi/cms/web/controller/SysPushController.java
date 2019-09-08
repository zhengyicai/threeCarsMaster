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
import com.qzi.cms.common.po.SysPushPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysParameterVo;
import com.qzi.cms.server.mapper.SysNoticeMapper;
import com.qzi.cms.server.mapper.SysPushMapper;
import com.qzi.cms.server.mapper.UseResidentMapper;
import com.qzi.cms.server.service.web.ParameterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 推送控制器
 * @author zyc
 * @version v1.0
 * @date 2017年6月15日
 */
@RestController
@RequestMapping("/sysPush")
public class SysPushController {
	@Resource
	private SysPushMapper sysPushMapper;


	@Resource
	private UseResidentMapper useResidentMapper;
	
	@GetMapping("/findAll")
	public RespBody findAll(Paging paging){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "推送查找所有数据成功", sysPushMapper.findAll(paging));
			//保存分页对象
			paging.setTotalCount(sysPushMapper.findCount());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "推送查找所有数据失败");
			LogUtils.error("推送查找所有数据失败！",ex);
		}
		return respBody;
	}


	@GetMapping("/findAllRes")
		public RespBody findAllRes(){
			RespBody respBody = new RespBody();
			try {
				//保存返回数据
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户查找所有数据成功", useResidentMapper.findAllRes());
				//保存分页对象

			} catch (Exception ex) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户查找所有数据失败");
				LogUtils.error("用户查找所有数据失败！",ex);
			}
			return respBody;
		}
	
	@PostMapping("/add")
	@SystemControllerLog(description="新增推送")
	public RespBody add(@RequestBody SysPushPo sysPushPo){
		RespBody respBody = new RespBody();
		try {
			sysPushPo.setId(ToolUtils.getUUID());
			sysPushPo.setCreateTime(new Date());
			sysPushPo.setState("10");
			sysPushMapper.insert(sysPushPo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "推送保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "推送保存失败");
			LogUtils.error("推送保存失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/update")
	@SystemControllerLog(description="修改推送")
	public RespBody update(@RequestBody SysPushPo sysPushPo){
		RespBody respBody = new RespBody();
		try {
			sysPushMapper.updateByPrimaryKey(sysPushPo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "推送保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "推送保存失败");
			LogUtils.error("推送保存失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/delete")
	@SystemControllerLog(description="删除推送")
	public RespBody delete(@RequestBody SysPushPo sysPushPo){
		RespBody respBody = new RespBody();
		try {
			sysPushMapper.delete(sysPushPo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "推送删除成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "推送删除失败");
			LogUtils.error("推送删除失败！",ex);
		}
		return respBody;
	}

}
