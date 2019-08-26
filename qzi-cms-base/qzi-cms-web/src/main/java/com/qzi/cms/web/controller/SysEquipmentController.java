package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysEquipmentFilePo;
import com.qzi.cms.common.po.SysEquipmentPo;
import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysEquipmentFileVo;
import com.qzi.cms.common.vo.SysEquipmentVo;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.server.mapper.SysEquipmentFileMapper;
import com.qzi.cms.server.mapper.SysEquipmentMapper;
import com.qzi.cms.server.mapper.SysFileUrlMapper;
import com.qzi.cms.server.mapper.SysParameterMapper;
import org.springframework.jdbc.core.ParameterMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/sysEquipment")
public class SysEquipmentController {

       @Resource
       private SysEquipmentMapper sysEquipmentMapper;


       @Resource
	   private SysEquipmentFileMapper sysEquipmentFileMapper;

	   @Resource
	   private SysFileUrlMapper sysFileUrlMapper;

	   @Resource
	   private SysParameterMapper parameterMapper;




    
       private String imagepath = "/data/page/uploadImages/";



       @ResponseBody
       @RequestMapping(value = "/addFile",method = RequestMethod.POST)
       public RespBody addFile(@RequestBody SysEquipmentPo po){
           RespBody respBody = new RespBody();

		   SysEquipmentPo po2 =  sysEquipmentMapper.selectOne1(po.getEquipmentNo());
		   if(po2!=null){
			   respBody.add(RespCodeEnum.ERROR.getCode(), "该设备号已经存在");
		   	 return  respBody;
		   }


           SysEquipmentPo sysEquipmentPo = new SysEquipmentPo();

		   sysEquipmentPo.setId(ToolUtils.getUUID());
		   sysEquipmentPo.setCreateTime(new Date());
		   sysEquipmentPo.setState(po.getState());
		   sysEquipmentPo.setType(po.getType());
		   sysEquipmentPo.setEquipmentName1(po.getEquipmentName1());
		   sysEquipmentPo.setEquipmentNo(po.getEquipmentNo());
		   sysEquipmentPo.setRemark(po.getRemark());
		   sysEquipmentPo.setUserId(po.getUserId());
		   sysEquipmentPo.setUserName(po.getUserName());
		   sysEquipmentPo.setStatus("20");
		   sysEquipmentPo.setTitleStatus("20");
		   sysEquipmentPo.setTitleDetail("");
		   sysEquipmentPo.setRunTime(po.getRunTime());
		   sysEquipmentPo.setUpdateTimes(po.getUpdateTimes());
		   sysEquipmentMapper.insert(sysEquipmentPo);
            


           return  respBody;
       }





        @GetMapping("/findAll")
    	public RespBody findAll(Paging paging,SysEquipmentPo sysEquipmentPo){
    		RespBody respBody = new RespBody();


            int startRow=0;int pageSize=0;
            if(null!=paging){
                startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
                pageSize=paging.getPageSize();
            }else{
                pageSize=Integer.MAX_VALUE;
            }
    		try {
    			//保存返回数据

    		  List<SysEquipmentVo> list =  sysEquipmentMapper.findAll(sysEquipmentPo,startRow,pageSize);

    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
    			//保存分页对象
    			paging.setTotalCount(sysEquipmentMapper.findCount(sysEquipmentPo));
    			respBody.setPage(paging);
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
    			LogUtils.error("查找所有设备数据失败！",ex);
    		}
    		return respBody;
    	}


        @PostMapping("/update")
    	@SystemControllerLog(description="修改设备")
    	public RespBody update(@RequestBody SysEquipmentPo sysEquipmentPo){
    		RespBody respBody = new RespBody();
    		try {
				sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
                sysEquipmentMapper.updateByPrimaryKey(sysEquipmentPo);
    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
    			LogUtils.error("操作失败！",ex);
    		}
    		return respBody;
    	}


	@PostMapping("/updateSelect")
	@SystemControllerLog(description="修改选中设备")
	public RespBody updateSelect(@RequestBody SysEquipmentVo sysEquipmentVo){
		RespBody respBody = new RespBody();
		try {



			for(int i = 0 ;i<sysEquipmentVo.getEquipmentIds().length;i++){

				sysEquipmentFileMapper.deleteList(sysEquipmentVo.getId());
				sysEquipmentMapper.updateOne(sysEquipmentVo.getEquipmentIds()[i],sysEquipmentVo.getType());
			}
//			sysEquipmentFileMapper.deleteListSelect(sysEquipmentVo.getUserId());
//			sysEquipmentMapper.updateSelect(sysEquipmentVo.getUserId(),sysEquipmentVo.getType());
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
			LogUtils.error("操作失败！",ex);
		}
		return respBody;
	}



	@PostMapping("/updateSelectAll")
		@SystemControllerLog(description="修改选中设备")
		public RespBody updateSelectAll(@RequestBody SysEquipmentVo sysEquipmentVo){
			RespBody respBody = new RespBody();
			try {
				sysEquipmentFileMapper.deleteListSelect(sysEquipmentVo.getUserId());
				sysEquipmentMapper.updateSelect(sysEquipmentVo.getUserId(),sysEquipmentVo.getType());
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
			} catch (Exception ex) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
				LogUtils.error("操作失败！",ex);
			}
			return respBody;
		}

	


	@PostMapping("/delete")
   	@SystemControllerLog(description="删除设备")
   	public RespBody delete(@RequestBody SysEquipmentPo sysEquipmentPo){
   		RespBody respBody = new RespBody();
   		try {
			sysEquipmentFileMapper.deleteList(sysEquipmentPo.getId());
               sysEquipmentMapper.delete(sysEquipmentPo);
   			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
   		} catch (Exception ex) {
   			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
   			LogUtils.error("操作失败！",ex);
   		}
   		return respBody;
   	}



	    @GetMapping("/equipmentFile/findAll")
    	public RespBody equipmentFileAll(SysEquipmentFilePo sysEquipmentFilePo){
    		RespBody respBody = new RespBody();

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findAll(sysEquipmentFilePo.getEquipmentId()));

    		return respBody;
    	}

	@GetMapping("/equipmentFile/findOne")
    	public RespBody equipmentFilefindOne(SysEquipmentFilePo sysEquipmentFilePo){
    		RespBody respBody = new RespBody();

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findOne(sysEquipmentFilePo.getEquipmentId(),sysEquipmentFilePo.getFileId()));

    		return respBody;
    	}






	    @PostMapping("/equipmentFile/updateTitle")
	   	@SystemControllerLog(description="修改参数")
	   	public RespBody updateTitle(@RequestBody SysEquipmentFileVo sysEquipmentFileVo){
	   		RespBody respBody = new RespBody();
	   		try {
				sysEquipmentFileMapper.updateTitle(sysEquipmentFileVo.getEquipmentId(),sysEquipmentFileVo.getFileId(),sysEquipmentFileVo.getTitleDetail());
				sysEquipmentMapper.updateTitleOne(sysEquipmentFileVo.getEquipmentId(),"10");
	   			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
	   		} catch (Exception ex) {
	   			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
	   			LogUtils.error("操作失败！",ex);
	   		}
	   		return respBody;
	   	}


	   @ResponseBody
	   @RequestMapping(value = "/equipmentFile/addFile",method = RequestMethod.POST)
	   public RespBody equipmentFileAdd(@RequestBody SysEquipmentFileVo  vo){
		   RespBody respBody = new RespBody();


		   if(vo.getImageList().length>0){
		   		for(int i= 0 ;i<vo.getImageList().length;i++){
		   			SysEquipmentFilePo po = new SysEquipmentFilePo();
		   			po.setId(ToolUtils.getUUID());
		   			po.setCreateTime(new Date());
		   			po.setState("10");
		   			po.setFileId(vo.getImageList()[i]);
		   			po.setType("10");
		   			po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setTitleDetail("");
					sysEquipmentFileMapper.insert(po);
				}

		   }

			if(vo.getVideoList().length>0){
				for(int i= 0 ;i<vo.getVideoList().length;i++){
					SysEquipmentFilePo po = new SysEquipmentFilePo();
					po.setId(ToolUtils.getUUID());
					po.setCreateTime(new Date());
					po.setState("10");
					po.setFileId(vo.getVideoList()[i]);
					po.setType("20");
					po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setTitleDetail("");
					sysEquipmentFileMapper.insert(po);
				}

		   }

		   return  respBody;
	   }


	   @ResponseBody
	   @RequestMapping(value = "/equipmentFile/updateFile",method = RequestMethod.POST)
	   public RespBody equipmentFileUpdate(@RequestBody SysEquipmentFileVo  vo){
		   RespBody respBody = new RespBody();

			//删除
		   sysEquipmentFileMapper.deleteList(vo.getEquipmentId());

		   if(vo.getImageList().length>0){
				for(int i= 0 ;i<vo.getImageList().length;i++){
					SysEquipmentFilePo po = new SysEquipmentFilePo();
					po.setId(ToolUtils.getUUID());
					po.setCreateTime(new Date());
					po.setState("10");
					po.setFileId(vo.getImageList()[i]);
					po.setType("10");
					po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setAlign("10");
					sysEquipmentFileMapper.insert(po);
				}

		   }

			if(vo.getVideoList().length>0){
				for(int i= 0 ;i<vo.getVideoList().length;i++){
					SysEquipmentFilePo po = new SysEquipmentFilePo();
					po.setId(ToolUtils.getUUID());
					po.setCreateTime(new Date());
					po.setState("10");
					po.setFileId(vo.getVideoList()[i]);

					if("".equals(vo.getAlign())){
						po.setType("20");
						po.setAlign("10");
					}else{
						po.setType("10");
						po.setAlign("20");
					}

					po.setUserId(vo.getUserId());
					po.setEquipmentId(vo.getEquipmentId());
					po.setTitleDetail("null,null,null,null,null");

					sysEquipmentFileMapper.insert(po);
				}

		   }


		  sysEquipmentMapper.updateOne(vo.getEquipmentId(),"10");

		   

		   return  respBody;
	   }





	//多台设备修改素材
	@ResponseBody
	@RequestMapping(value = "/equipmentFile/updateSelectFile",method = RequestMethod.POST)
   public RespBody updateSelectFile(@RequestBody SysEquipmentFileVo  vo){
	   RespBody respBody = new RespBody();


		sysEquipmentFileMapper.deleteListSelect(vo.getUserId());


		List<SysEquipmentPo> listAll = sysEquipmentMapper.listAll(vo.getUserId());

	   for(int aa =0;aa<listAll.size();aa++){

		   	   if(vo.getImageList().length>0){
		   			for(int i= 0 ;i<vo.getImageList().length;i++){
		   				SysEquipmentFilePo po = new SysEquipmentFilePo();
		   				po.setId(ToolUtils.getUUID());
		   				po.setCreateTime(new Date());
		   				po.setState("10");
		   				po.setFileId(vo.getImageList()[i]);
		   				po.setType("10");
		   				po.setUserId(vo.getUserId());
		   				po.setEquipmentId(listAll.get(aa).getId());
		   				po.setAlign("10");
						po.setTitleDetail("null,null,null,null,null");
		   				sysEquipmentFileMapper.insert(po);
		   			}

		   	   }

		   		if(vo.getVideoList().length>0){
		   			for(int i= 0 ;i<vo.getVideoList().length;i++){
		   				SysEquipmentFilePo po = new SysEquipmentFilePo();
		   				po.setId(ToolUtils.getUUID());
		   				po.setCreateTime(new Date());
		   				po.setState("10");
		   				po.setFileId(vo.getVideoList()[i]);

		   				if("".equals(vo.getAlign())){
		   					po.setType("20");
		   					po.setAlign("10");
		   				}else{
		   					po.setType("10");
		   					po.setAlign("20");
		   				}

		   				po.setUserId(vo.getUserId());
		   				po.setEquipmentId(listAll.get(aa).getId());
						po.setTitleDetail("null,null,null,null,null");

		   				sysEquipmentFileMapper.insert(po);
		   			}

		   	   }






	   }

		//修改全部
		sysEquipmentMapper.updateAll(vo.getUserId(),"10");





	   return  respBody;
	}



	//多台设备修改素材
		@ResponseBody
		@RequestMapping(value = "/equipmentFile/updateSelectAllFile",method = RequestMethod.POST)
	   public RespBody updateSelectAllFile(@RequestBody SysEquipmentFileVo  vo){
		   RespBody respBody = new RespBody();



		   for(int aa =0;aa<vo.getEquipmentIds().length;aa++){

			   //删除
			   	   sysEquipmentFileMapper.deleteList(vo.getEquipmentId());

			   	   if(vo.getImageList().length>0){
			   			for(int i= 0 ;i<vo.getImageList().length;i++){
			   				SysEquipmentFilePo po = new SysEquipmentFilePo();
			   				po.setId(ToolUtils.getUUID());
			   				po.setCreateTime(new Date());
			   				po.setState("10");
			   				po.setFileId(vo.getImageList()[i]);
			   				po.setType("10");
			   				po.setUserId(vo.getUserId());
			   				po.setEquipmentId(vo.getEquipmentIds()[aa]);
			   				po.setAlign("10");
			   				po.setTitleDetail("null,null,null,null,null");
			   				sysEquipmentFileMapper.insert(po);
			   			}

			   	   }

			   		if(vo.getVideoList().length>0){
			   			for(int i= 0 ;i<vo.getVideoList().length;i++){
			   				SysEquipmentFilePo po = new SysEquipmentFilePo();
			   				po.setId(ToolUtils.getUUID());
			   				po.setCreateTime(new Date());
			   				po.setState("10");
			   				po.setFileId(vo.getVideoList()[i]);

			   				if("".equals(vo.getAlign())){
			   					po.setType("20");
			   					po.setAlign("10");
			   				}else{
			   					po.setType("10");
			   					po.setAlign("20");
			   				}

			   				po.setUserId(vo.getUserId());
			   				po.setEquipmentId(vo.getEquipmentIds()[aa]);

			   				sysEquipmentFileMapper.insert(po);
			   			}

			   	   }


			   	  sysEquipmentMapper.updateOne(vo.getEquipmentId(),"10");



		   }





		   return  respBody;
		}





	@GetMapping("/equipmentFile/findAllType")
	public RespBody equipmentFileAllType(String userId){
		RespBody respBody = new RespBody();
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysFileUrlMapper.findAllList(userId));

		return respBody;
	}

	//获取选中值
	@GetMapping("/equipmentFile/findSelectType")
	public RespBody equipmentFileSelectType(String equipmentId){
			RespBody respBody = new RespBody();

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentFileMapper.findAll(equipmentId));

			return respBody;
	}






	@GetMapping("/equipment/findOne")
	public RespBody findOne(String equipmentId){
			RespBody respBody = new RespBody();
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", sysEquipmentMapper.selectOne1(equipmentId));

			return respBody;
	}


	@GetMapping("/equipment/updateStatus")
	public RespBody updateStatus(String equipmentId){
			RespBody respBody = new RespBody();
			sysEquipmentMapper.updateOne(equipmentId,"20");
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", "ok");

			return respBody;
	}

	@GetMapping("/equipment/updateOneTitleStatus")
	public RespBody updateOneTitleStatus(String equipmentId){
		RespBody respBody = new RespBody();
		sysEquipmentMapper.updateOneTitleStatus(equipmentId,"20");
		respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", "ok");

		return respBody;
	}





	@GetMapping("/equipment/findParam")
		public RespBody findParam(String paramName){
				RespBody respBody = new RespBody();

				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", parameterMapper.findParam(paramName));

				return respBody;
		}








}
