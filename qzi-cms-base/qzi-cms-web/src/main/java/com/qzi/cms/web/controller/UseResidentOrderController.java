package com.qzi.cms.web.controller;


import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.common.po.ResidentOrderDetailPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.server.mapper.UseOrderDetailMapper;
import com.qzi.cms.server.mapper.UseResidentOrderMapper;
import com.qzi.cms.server.service.web.GoodsRecordService;
import com.qzi.cms.server.service.web.GoodsService;
import com.qzi.cms.server.service.web.OrderDetailService;
import com.qzi.cms.server.service.web.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private UseResidentOrderMapper useResidentOrderMapper;


    @Resource
    private UseOrderDetailMapper useOrderDetailMapper;

    @Resource
    private OrderDetailService orderDetailService;

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


    //按天数统计商品重量

    @GetMapping("/orderDetailSumFindAll")
    public RespBody orderDetailSumFindAll(Paging paging){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", orderDetailService.findAllSum(paging));
            //保存分页对象
            paging.setTotalCount(orderDetailService.findCountSum());
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
            LogUtils.error("查找所有订单数据失败！",ex);
        }
        return respBody;
    }


    //按30天的数统计商品重量
    @GetMapping("/orderDetailSumMouthFindAll")
    public RespBody orderDetailSumMouthFindAll(){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", useOrderDetailMapper.findAllSumMouth());
            //保存分页对象
//            paging.setTotalCount(orderDetailService.findCountSum());
//            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
            LogUtils.error("查找所有订单数据失败！",ex);
        }
        return respBody;
    }

    @GetMapping("/findAllDownLoad")
    public RespBody findAllDownLoad() {
        RespBody respBody = new RespBody();

        List<ResidentOrderVo> list =  useResidentOrderMapper.findAllDownLoad();



        respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", list);

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

    @PostMapping("/updatesell")
    @SystemControllerLog(description="修改订单设置")
    public RespBody updatesell(@RequestBody ResidentOrderPo residentOrderPo){
        RespBody respBody = new RespBody();
        try {
            useResidentOrderMapper.updateSellprice1(residentOrderPo.getSellprice(),residentOrderPo.getType(),residentOrderPo.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改订单保存成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "修改订单保存失败");
            LogUtils.error("修改订单保存失败！",ex);
        }
        return respBody;
    }



    @PostMapping("/updateFinishPrice")
    @SystemControllerLog(description="修改订单详情价格")
    public RespBody updateFinishPrice(@RequestBody ResidentOrderDetailPo residentOrderDetailPo){
        RespBody respBody = new RespBody();
        try {
            useOrderDetailMapper.update1(residentOrderDetailPo.getId(),residentOrderDetailPo.getSellWeight());
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


    @GetMapping("/findDetail")
    public RespBody findDetail(String orderId){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找中国城市数据成功", useResidentOrderMapper.findRecord(orderId));
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找中国城市数据失败");
            LogUtils.error("查找中国城市数据失败！",ex);
        }
        return respBody;
    }
    
}
