/* 
 * 文件名：ResidentController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年7月18日
 * 版本号：v1.0
*/
package com.qzi.cms.web.controller;

import javax.annotation.Resource;

import com.qzi.cms.server.mapper.*;
import com.qzi.cms.server.service.common.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.enums.YNEnum;
import com.qzi.cms.common.exception.CommException;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.vo.UseResidentVo;
import com.qzi.cms.server.service.web.ResidentService;

/**
 * 住户信息控制器
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
@RestController
@RequestMapping("/resident")
public class ResidentController {
	@Resource
	private ResidentService residentService;


	@Resource
	private UseResidentMapper useResidentMapper;



	@Resource
	private CommonService commonService;








	@GetMapping("/findCommunitys")
	public RespBody findCommunitys(){
		RespBody respBody = new RespBody();
		try {
			//查找数据并返回
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户小区信息成功",residentService.findCommunitys());
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户小区信息异常");
			LogUtils.error("获取用户小区信息异常！",ex);
		}
		return respBody;
	}
	
	@GetMapping("/findAll")
	public RespBody findAll(Paging paging,String criteria){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有住户数据成功", residentService.findAll(paging,criteria));
			//保存分页对象
			paging.setTotalCount(residentService.findCount(criteria));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有住户数据失败");
			LogUtils.error("查找所有住户数据失败！",ex);
		}
		return respBody;
	}


	/**
	 * 2018-11-20 1.0.0
	 * @param paging
	 * @param criteria
	 * @return
	 */

	@GetMapping("/residentList")
	public RespBody residentList(Paging paging,String criteria){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有住户数据成功", residentService.residentList(paging,criteria));
			//保存分页对象
			paging.setTotalCount(residentService.residentCount(criteria));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有住户数据失败");
			LogUtils.error("查找所有住户数据失败！",ex);
		}
		return respBody;
	}


	
}
