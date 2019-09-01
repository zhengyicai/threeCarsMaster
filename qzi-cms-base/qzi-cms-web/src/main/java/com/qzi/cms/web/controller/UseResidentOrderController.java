package com.qzi.cms.web.controller;


import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.server.service.web.GoodsRecordService;
import com.qzi.cms.server.service.web.GoodsService;
import com.qzi.cms.server.service.web.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单
 * @author zyc
 * @version v1.0
 * @date 2017年6月28日
 */
@RestController
@RequestMapping("/order")
public class UseResidentOrderController {


    @Resource
    private OrderService orderService;


    @Resource
    private GoodsRecordService goodsRecordService;

    @GetMapping("/findAll")
    public RespBody findAll(Paging paging,String country,String city,String town){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据

            ResidentOrderVo vo = new ResidentOrderVo();
            vo.setTown(town);
            vo.setCountry(country);
            vo.setTown(city);




            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", orderService.findAll(paging,vo));
            //保存分页对象
            paging.setTotalCount(orderService.findCount(vo));
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
            LogUtils.error("查找所有订单数据失败！",ex);
        }
        return respBody;
    }

    @PostMapping("/add")
    @SystemControllerLog(description="新增订单")
    public RespBody add(@RequestBody ResidentOrderPo residentOrderPo){
        RespBody respBody = new RespBody();
        try {
            orderService.add(residentOrderPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增订单保存成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增订单保存失败");
            LogUtils.error("新增订单保存失败！",ex);
        }
        return respBody;
    }


    @PostMapping("/update")
    @SystemControllerLog(description="修改订单设置")
    public RespBody update(@RequestBody ResidentOrderPo residentOrderPo){
        RespBody respBody = new RespBody();
        try {
            orderService.update(residentOrderPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改订单保存成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "修改订单保存失败");
            LogUtils.error("修改订单保存失败！",ex);
        }
        return respBody;
    }

    
    @PostMapping("/delete")
    @SystemControllerLog(description="删除订单设置")
    public RespBody delete(@RequestBody ResidentOrderPo residentOrderPo){
        RespBody respBody = new RespBody();
        try {
            orderService.delete(residentOrderPo);
            GoodsRecordPo goodsRecordPo = new GoodsRecordPo();
            goodsRecordPo.setId(residentOrderPo.getId());
            goodsRecordService.delete(goodsRecordPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "删除订单成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "删除订单失败");
            LogUtils.error("删除订单失败！",ex);
        }
        return respBody;
    }
    
}
