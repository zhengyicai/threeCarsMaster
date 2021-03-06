/* 
 * 文件名：ResidentServiceImpl.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年7月18日
 * 版本号：v1.0
*/
package com.qzi.cms.server.service.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.qzi.cms.common.po.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qzi.cms.common.exception.CommException;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.util.YBBeanUtils;
import com.qzi.cms.common.util.YzsClientUtils;
import com.qzi.cms.common.vo.OptionVo;
import com.qzi.cms.common.vo.SysUserVo;
import com.qzi.cms.common.vo.TreeVo;

import com.qzi.cms.common.vo.UseResidentVo;

import com.qzi.cms.server.mapper.UseCommunityResidentMapper;
import com.qzi.cms.server.mapper.UseResidentMapper;

import com.qzi.cms.server.service.common.CommonService;

import com.qzi.cms.server.service.web.ResidentService;

/**
 * 珠海业务层实现类
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
@Service
public class ResidentServiceImpl implements ResidentService {
	@Resource
	private CommonService commonService;
	@Resource
	private UseResidentMapper residentMapper;


	@Resource
	private YzsClientUtils clientUtils;
	@Resource
	private UseCommunityResidentMapper communityResidentMapper;
	
	@Override
	public List<UseResidentVo> findAll(Paging paging, String criteria) throws Exception {
		//读取用户信息
		SysUserVo userVo = commonService.findUser();
		//分页对象
		RowBounds rwoBounds = new RowBounds(paging.getPageNumber(),paging.getPageSize());
		return residentMapper.findAll(rwoBounds,criteria,userVo.getId());
	}


	@Override
	public long findCount(String criteria) throws Exception {
		//读取用户信息
		SysUserVo userVo = commonService.findUser();
		return residentMapper.findCount(criteria,userVo.getId());
		
	}

	@Override
	public List<UseResidentVo> residentList(Paging paging, String criteria,String type) throws Exception {
		//读取用户信息
		SysUserVo userVo = commonService.findUser();
		//分页对象
		RowBounds rwoBounds = new RowBounds(paging.getPageNumber(),paging.getPageSize());
		return residentMapper.residentList(rwoBounds,criteria,type);
	}

	@Override
	public long residentCount(String criteria,String type) throws Exception {
		return residentMapper.residentCount(criteria,type);
	}


	@Override
	public void add(UseResidentVo residentVo) throws CommException {
		UseResidentPo residentPo = residentMapper.findMobile(residentVo.getMobile());
		if(residentPo != null){
			if(communityResidentMapper.existsCR(residentPo.getId(),residentVo.getCommunityId())){
				throw new CommException("此手机号已经关联了本小区");
			}else{
				UseCommunityResidentPo crPo = new UseCommunityResidentPo();
				crPo.setResidentId(residentPo.getId());
				crPo.setCommunityId(residentVo.getCommunityId());
				crPo.setState(residentVo.getState());
				communityResidentMapper.insert(crPo);
			}
			
		}else{
			throw new CommException("手机号未注册,请先注册");
		}
	}

	@Override
	public List<TreeVo> findCommunitys() throws Exception {
		return null;
	}

	@Override
	public List<OptionVo> findBuildings(String communityId) {
		return null;
	}

	@Override
	public List<OptionVo> findUnits(String buildingId) {
		return null;
	}

	@Override
	public List<OptionVo> findRooms(String buildingId, String unitNo) {
		return null;
	}


	@Override
	@Transactional(rollbackFor=Exception.class)
	public void delete(UseResidentVo residentVo) throws Exception {
		//注销云之讯账户
//		ClientVo client = new ClientVo();
//		client.setUserId(residentVo.getMobile());
//		clientUtils.deleteClient(client);
		//删除住户授权
		residentMapper.delAuthResidentId(residentVo.getId());
		//删除住户
		residentMapper.delResident(residentVo.getId());
		//communityResidentMapper.deleteByCriteria(residentVo.getId(),residentVo.getCommunityId());
	}

	@Override
	public void updateState(UseResidentVo residentVo) {
		residentMapper.updateResident(residentVo.getId(),residentVo.getState());
	}

	@Override
	public void update(UseResidentVo useResidentVo) throws Exception {
		UseResidentPo useResidentPo = YBBeanUtils.copyProperties(useResidentVo, UseResidentPo.class);
		residentMapper.updateByPrimaryKey(useResidentPo);
	}

	@Override
	public void updateCreateTime(String residentId) throws Exception {
		residentMapper.updateCreateTime(residentId);
	}


	@Override
	public boolean exist(String mobile) {
		return residentMapper.existsMobile(mobile);
	}

}
