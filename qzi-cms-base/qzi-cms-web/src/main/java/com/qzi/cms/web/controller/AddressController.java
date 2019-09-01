package com.qzi.cms.web.controller;


import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.common.po.ResidentAddressPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.server.mapper.UseResidentAddressMapper;
import com.qzi.cms.server.service.web.GoodsRecordService;
import com.qzi.cms.server.service.web.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 地址管理
 * @author zyc
 * @version v1.0
 * @date 2017年6月28日
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    
    @Resource
    private UseResidentAddressMapper useResidentAddressMapper;

    @GetMapping("/findAll")
    public RespBody findAll(Paging paging){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有货品数据成功", useResidentAddressMapper.findAll(paging));
            //保存分页对象
            paging.setTotalCount(useResidentAddressMapper.findCount());
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有货品数据失败");
            LogUtils.error("查找所有货品数据失败！",ex);
        }
        return respBody;
    }


    @GetMapping("/findAllWxId")
      public RespBody findAllWxId(String  wxId){
        
          RespBody respBody = new RespBody();
          try {
              //保存返回数据
              respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有地址数据成功", useResidentAddressMapper.findAllWxId(wxId));
              //保存分页对象

          } catch (Exception ex) {
              respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有地址数据失败");
              LogUtils.error("查找所有地址数据失败！",ex);
          }
          return respBody;
      }





    @PostMapping("/add")
    @SystemControllerLog(description="新增货品")
    public RespBody add(@RequestBody ResidentAddressPo residentAddressPo){
        RespBody respBody = new RespBody();
        try {
            residentAddressPo.setId(ToolUtils.getUUID());
            residentAddressPo.setCreateTime(new Date());
            residentAddressPo.setUpdateTime(new Date());
            residentAddressPo.setState("10");
            useResidentAddressMapper.insert(residentAddressPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
            LogUtils.error("新增货品保存失败！",ex);
        }
        return respBody;
    }

    @PostMapping("/update")
    @SystemControllerLog(description="修改货品设置")
    public RespBody update(@RequestBody ResidentAddressPo residentAddressPo){
        RespBody respBody = new RespBody();
               try {
                   residentAddressPo.setUpdateTime(new Date());
                   useResidentAddressMapper.updateByPrimaryKey(residentAddressPo);
               } catch (Exception ex) {
                   respBody.add(RespCodeEnum.ERROR.getCode(), "修改货品保存失败");
                   LogUtils.error("修改货品保存失败！",ex);
               }

        return respBody;
    }

    @PostMapping("/delete")
    @SystemControllerLog(description="删除货品设置")
    public RespBody delete(@RequestBody ResidentAddressPo residentAddressPo){
        RespBody respBody = new RespBody();
        try {
            useResidentAddressMapper.delete(residentAddressPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "删除货品成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "删除货品失败");
            LogUtils.error("删除货品失败！",ex);
        }
        return respBody;
    }


}
