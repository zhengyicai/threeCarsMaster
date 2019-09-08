package com.qzi.cms.app.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.ResidentAddressPo;
import com.qzi.cms.common.po.ResidentOrderDetailPo;
import com.qzi.cms.common.po.ResidentOrderPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.vo.ResidentOrderDetailVo;
import com.qzi.cms.common.vo.ResidentOrderVo;
import com.qzi.cms.server.mapper.UseGoodsMapper;
import com.qzi.cms.server.mapper.UseOrderDetailMapper;
import com.qzi.cms.server.mapper.UseResidentAddressMapper;
import com.qzi.cms.server.mapper.UseResidentOrderMapper;
import com.qzi.cms.server.service.web.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2019/9/7.
 */

@RestController
@RequestMapping("/homeApp")
public class HomeController {

    @Resource
    private UseGoodsMapper useGoodsMapper;

    @Resource
    private UseResidentAddressMapper useResidentAddressMapper;

    @Resource
    private UseResidentOrderMapper useResidentOrderMapper;

    @Resource
    private OrderService orderService;


    private UseOrderDetailMapper useOrderDetailMapper;





    //获取当前货品价格
    @GetMapping("/goodsList")
   	public RespBody goodsList(){
   		RespBody respBody = new RespBody();
   		try {
   			//保存返回数据
   			respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户管理机成功", useGoodsMapper.findAllApp());
   		} catch (Exception ex) {
   			respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户管理机失败");
   		}
   		return respBody;
   	}

    //获取当前货品价格
//    @GetMapping("/goodsList")
//    public RespBody findMgrMachines(){
//        RespBody respBody = new RespBody();
//        try {
//            //保存返回数据
//            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户管理机成功", useGoodsMapper.findAllApp());
//        } catch (Exception ex) {                                                           
//            respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户管理机失败");
//        }
//        return respBody;
//    }

    //获取当前用户的地址
   @GetMapping("/getAddress")
   public RespBody getAddress(String wxId){
       RespBody respBody = new RespBody();
       try {
           //保存返回数据
           respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户管理机成功", useResidentAddressMapper.findAllWxId(wxId));
       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户管理机失败");
       }
       return respBody;
   }


    @PostMapping("/address/add")
    @SystemControllerLog(description="新增地址")
    public RespBody addressAdd(@RequestBody ResidentAddressPo po){
        RespBody respBody = new RespBody();
        try {


            po.setId(ToolUtils.getUUID());
            po.setState("10");
            po.setCreateTime(new Date());
            useResidentAddressMapper.insert(po);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
            LogUtils.error("新增货品保存失败！",ex);
        }
        return respBody;
    }


    @PostMapping("/order/add")
    @SystemControllerLog(description="新增订单")
    public RespBody orderAdd(@RequestBody ResidentOrderPo po){
        RespBody respBody = new RespBody();
        try {


            po.setId(ToolUtils.getUUID());
            po.setState("10");
            po.setType("10");
            po.setCreateTime(new Date());
            useResidentOrderMapper.insert(po);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
            LogUtils.error("新增货品保存失败！",ex);
        }
        return respBody;
    }


    /**
     * add订单详情
     * @param vo
     * @return
     */

    @PostMapping("/orderDatail/add")
   @SystemControllerLog(description="新增订单")
   public RespBody orderAdd(@RequestBody ResidentOrderDetailVo vo){
       RespBody respBody = new RespBody();
       try {
          for(int i = 0; i<vo.getDetailList().size();i++){
              ResidentOrderDetailPo residentOrderDetailPo = new ResidentOrderDetailPo();
              residentOrderDetailPo.setId(ToolUtils.getUUID());
              residentOrderDetailPo.setState("10");
              residentOrderDetailPo.setCreateTime(new Date());
              useOrderDetailMapper.insert(residentOrderDetailPo);
          }

           respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");

       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
           LogUtils.error("新增货品保存失败！",ex);
       }
       return respBody;
   }


    @PostMapping("/order/update")
    @SystemControllerLog(description="修改订单")
    public RespBody orderUpdate(@RequestBody ResidentOrderPo po){
        RespBody respBody = new RespBody();
        try {
             ResidentOrderPo oldPo  =  useResidentOrderMapper.findId(po.getId());
             if(po.getBuyprice()=="" || po.getBuyprice()==null){
             }else{
                 oldPo.setBuyprice(po.getBuyprice());
             }
             oldPo.setType(po.getType());
            useResidentOrderMapper.updateByPrimaryKey(oldPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
            LogUtils.error("新增货品保存失败！",ex);
        }
        return respBody;
    }



        //获取当前用户所有的订单
      @GetMapping("/getOrderList")
      public RespBody getOrderList(Paging paging, String wxId,String state){
          RespBody respBody = new RespBody();
          try {
              //保存返回数据
              ResidentOrderVo vo = new ResidentOrderVo();
              vo.setWxId(wxId);
              vo.setState(state);

              respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", orderService.findAll(paging,vo));
            //保存分页对象
            paging.setTotalCount(orderService.findCount(vo));
            respBody.setPage(paging);
              respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户管理机成功", useResidentAddressMapper.findAllWxId(wxId));
          } catch (Exception ex) {
              respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户管理机失败");
          }
          return respBody;
      }










   	
}
