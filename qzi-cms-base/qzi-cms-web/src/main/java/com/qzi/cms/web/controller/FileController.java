package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.SysFileUrlPo;
import com.qzi.cms.common.po.UseUnlockEquRecordPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.SysFileUrlVo;
import com.qzi.cms.common.vo.UseEquipmentVo;
import com.qzi.cms.server.mapper.SysFileUrlMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.tools.Tool;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by Administrator on 2019/6/8.
 */


@RestController
@RequestMapping("/file")
public class FileController {

       @Resource
       private SysFileUrlMapper sysFileUrlMapper;


    private String imagepath = "/data/page/uploadImages/";



       @ResponseBody
       @RequestMapping(value = "/addFile",method = RequestMethod.POST)
       public RespBody addFile(@RequestBody SysFileUrlPo po){
           RespBody respBody = new RespBody();


          String[] paths =  po.getFileUrl().split(",");

          for(int i=0 ;i<paths.length;i++){
            SysFileUrlPo sysFileUrlPo = new SysFileUrlPo();
            sysFileUrlPo.setId(ToolUtils.getUUID());
            sysFileUrlPo.setUserName(po.getUserName());
            sysFileUrlPo.setFileUrl(po.getUserName()+"/"+paths[i]);
            sysFileUrlPo.setStandards(po.getStandards());
            sysFileUrlPo.setUserId(po.getUserId());
            sysFileUrlPo.setType(po.getType());
            sysFileUrlPo.setState("10");
            sysFileUrlPo.setCreateTime(new Date());
            sysFileUrlPo.setRemark(po.getRemark());
            sysFileUrlMapper.insert(sysFileUrlPo);

          }
           
           return  respBody;
       }



       /**
         * 添加人脸开锁记录（带开锁图片）
         */
        @ResponseBody
        @RequestMapping(value = "/upload",method = RequestMethod.POST)
        public RespBody addEquRecord(@RequestPart("file") MultipartFile file, SysFileUrlPo po, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {
            RespBody respBody=new RespBody();


            String imagepath = po.getUserName();
            if (file!=null) {// 判断上传的文件是否为空
                String path=null;// 文件路径
                String type=null;// 文件类型
                String fileName=file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:"+fileName);
                // 判断文件类型
                type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
                if (type!=null) {// 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase()) ||"mp4".equals(type.toUpperCase())  ||"MP4".equals(type.toUpperCase()) ) {




                        // 项目在容器中实际发布运行的根路径
                        String realPath=request.getSession().getServletContext().getRealPath("/");
                        // 自定义的文件名称
                        String trueFileName=String.valueOf(System.currentTimeMillis())+"."+type.toUpperCase();
                        // 设置存放图片文件的路径


                        path = "/data/page/uploadImages/"+po.getUserName()+"/"+/*System.getProperty("file.separator")+*/trueFileName;
                        //path = realPath+po.getUserName()+"\\"+/*System.getProperty("file.separator")+*/trueFileName;

                        System.out.println("存放图片文件的路径:"+path);
                        // 转存文件到指定的路径
                        file.transferTo(new File(path));
                        Runtime.getRuntime().exec("chmod 777 -R " +"/data/page/uploadImages/"+po.getUserName()+"/"+trueFileName);

                        respBody.add(RespCodeEnum.SUCCESS.getCode(), "添加成功",trueFileName);
                    }else {
                        System.out.println("不是我们想要的文件类型,请按要求重新上传");
                        respBody.add(RespCodeEnum.ERROR.getCode(), "文件类型不对，请重新上传");
                        return respBody;
                    }
                }else {
                    System.out.println("文件类型为空");
                    respBody.add(RespCodeEnum.ERROR.getCode(), "文件类型为空");
                    return respBody;
                }
            }else {
                System.out.println("没有找到相对应的文件");
                respBody.add(RespCodeEnum.ERROR.getCode(), "没有找到相对应的文件");
                return respBody;
            }
            return respBody;
        }


        @GetMapping("/findAll")
    	public RespBody findAll(Paging paging,SysFileUrlPo sysFileUrlPo){
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

    		  List<SysFileUrlVo> list =  sysFileUrlMapper.findAll(sysFileUrlPo,startRow,pageSize);
//    		  for(SysFileUrlVo vo:list){
//
//
//
//    		  }
    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找文件所有数据成功", list);
    			//保存分页对象
    			paging.setTotalCount(sysFileUrlMapper.findCount(sysFileUrlPo));
    			respBody.setPage(paging);
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "查找文件所有设备数据失败");
    			LogUtils.error("查找所有设备数据失败！",ex);
    		}
    		return respBody;
    	}


    @PostMapping("/update")
    	@SystemControllerLog(description="修改设备")
    	public RespBody update(@RequestBody SysFileUrlPo sysFileUrlPo){
    		RespBody respBody = new RespBody();
    		try {
    			sysFileUrlMapper.updateByPrimaryKey(sysFileUrlPo);
    			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
    		} catch (Exception ex) {
    			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
    			LogUtils.error("操作失败！",ex);
    		}
    		return respBody;
    	}




        @PostMapping("/delete")
       	@SystemControllerLog(description="删除设备")
       	public RespBody delete(@RequestBody SysFileUrlPo sysFileUrlPo){
       		RespBody respBody = new RespBody();
       		try {
       			sysFileUrlMapper.delete(sysFileUrlPo);
       			respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
       		} catch (Exception ex) {
       			respBody.add(RespCodeEnum.ERROR.getCode(), "操作失败");
       			LogUtils.error("操作失败！",ex);
       		}
       		return respBody;
       	}





}
