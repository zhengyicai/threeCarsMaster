package com.qzi.cms.web.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.po.UseSuggestPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.server.mapper.UseSuggestMapper;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2019/8/31.
 */

@RestController
@RequestMapping("/suggest")
public class UseSuggestController {
    private UseSuggestMapper useSuggestMapper;



    @GetMapping("/findAll")
       public RespBody findAll(Paging paging,String criteria){
           RespBody respBody = new RespBody();
           try {
               //保存返回数据
               respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", useSuggestMapper.findAll(paging,criteria));
               //保存分页对象
               paging.setTotalCount(useSuggestMapper.findCount(criteria));
               respBody.setPage(paging);
           } catch (Exception ex) {
               respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
               LogUtils.error("查找所有订单数据失败！",ex);
           }
           return respBody;
       }

       @PostMapping("/add")
       @SystemControllerLog(description="新增建议")
       public RespBody add(@RequestBody UseSuggestPo useSuggestPo){
           RespBody respBody = new RespBody();
           try {
               useSuggestMapper.insert(useSuggestPo);
               respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增订单保存成功");
           } catch (Exception ex) {
               respBody.add(RespCodeEnum.ERROR.getCode(), "新增订单保存失败");
               LogUtils.error("新增订单保存失败！",ex);
           }
           return respBody;
       }

    


}
