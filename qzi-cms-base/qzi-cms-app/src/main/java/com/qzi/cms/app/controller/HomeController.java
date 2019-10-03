package com.qzi.cms.app.controller;

import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.*;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.common.util.YBBeanUtils;
import com.qzi.cms.common.vo.*;
import com.qzi.cms.server.mapper.*;
import com.qzi.cms.server.service.web.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private UseOrderDetailMapper useOrderDetailMapper;

    @Resource
    private UseCommunityMapper useCommunityMapper;


    @Resource
    private UseSuggestMapper useSuggestMapper;

    @Resource
    private SysParameterMapper sysParameterMapper;

    @Resource
    private OrderSumMapper orderSumMapper;

    @Resource
    private UseResidentMapper useResidentMapper;






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
           respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户地址成功", useResidentAddressMapper.findAllWxId(wxId));
       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户地址成功失败");
       }
       return respBody;
   }


    //获取当前用户的详细地址
   @GetMapping("/address/getAddressId")
   public RespBody getAddressId(String id){
       RespBody respBody = new RespBody();
       try {
           //保存返回数据
           respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户地址成功", useResidentAddressMapper.findOne(id));
       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户地址成功失败");
       }
       return respBody;
   }



    //获取当前用户的详细地址
    @GetMapping("/addressList")
    public RespBody addressList(){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据
            List<String> city =   useCommunityMapper.findCity();
            List<UseCommunityPo> area = useCommunityMapper.findAreas();
            List<UseCommunityPo> address  = useCommunityMapper.findAddresss();


            List<CityCodeVo> list = new ArrayList<CityCodeVo>();

            for(int i = 0;i<city.size();i++){
                CityCodeVo vo = new CityCodeVo();
                vo.setText(city.get(i));
                vo.setValue(city.get(i));

                List<CityCodeVo> list1 = new ArrayList<CityCodeVo>();
                    for(int j = 0;j<area.size();j++){
                        if(city.get(i).equals(area.get(j).getCity())){
                            CityCodeVo vo1 = new CityCodeVo();
                            vo1.setText(area.get(j).getArea());
                            vo1.setValue(area.get(j).getArea());
                            List<CityCodeVo> list2 = new ArrayList<CityCodeVo>();
                            for(int k = 0;k<address.size();k++){
                                if(area.get(j).getArea().equals(address.get(k).getArea())) {
                                    CityCodeVo vo2 = new CityCodeVo();
                                    vo2.setText(address.get(k).getAddress());
                                    vo2.setValue(address.get(k).getAddress());
                                    list2.add(vo2);
                                }


                            }
                            vo1.setChildren(list2);


                            list1.add(vo1);
                        }
                    //    vo.setChildren(list1);

                    }



                vo.setChildren(list1);

                list.add(vo);
            }




            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户地址成功", list);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户地址成功失败");
        }
        return respBody;
    }


   //


    //获取当前用户的详细地址
   @PostMapping("/address/update")
   public RespBody addressUpdate(@RequestBody ResidentAddressPo po){
       RespBody respBody = new RespBody();
       try {
           //保存返回数据


           respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改用户地址成功", useResidentAddressMapper.updateByPrimaryKey(po));
       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "修改用户地址失败");
       }
       return respBody;
   }






   @GetMapping("/address/delAddress")
   public RespBody delAddress(String id){
       RespBody respBody = new RespBody();
       try {

           ResidentAddressPo po =   useResidentAddressMapper.findOne(id);
           if(po !=null){
               if("10".equals(po.getType())){
                   respBody.add(RespCodeEnum.ERROR.getCode(), "用户默认地址无法删除");
                   return respBody;
               }

               po.setState("30");
               respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户地址删除成功", useResidentAddressMapper.updateByPrimaryKey(po));
           } else{
               respBody.add(RespCodeEnum.ERROR.getCode(), "用户地址删除失败" );
           }
           //保存返回数据

       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户地址失败");
       }
       return respBody;
   }



    @PostMapping("/address/getCity")
   @SystemControllerLog(description="获取地址")
   public RespBody getCity(@RequestBody ResidentAddressPo po){
       RespBody respBody = new RespBody();
        List<String> list = new ArrayList<String>();
       try {
           //保存返回数据
           if(po.getCity()!=""){
               if("2".equals(po.getType())){
                list =     useCommunityMapper.findArea(po.getCity());
               }else if("3".equals(po.getType())){
                   list =     useCommunityMapper.findAddress(po.getCity());

               }

           }else{
               list =   useCommunityMapper.findCity();
           }


           respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取地址成功",list );
       } catch (Exception ex) {
           respBody.add(RespCodeEnum.ERROR.getCode(), "获取地址失败");
       }
       return respBody;
   }






    @PostMapping("/address/add")
    @SystemControllerLog(description="新增地址")
    public RespBody addressAdd(@RequestBody ResidentAddressVo vo) throws Exception {
        RespBody respBody = new RespBody();

        ResidentAddressPo po = YBBeanUtils.copyProperties(vo, ResidentAddressPo.class);


        try {
            if(vo.getTitle() != "" && vo.getTitle() != null){
                UseResidentPo useResidentPo =   useResidentMapper.findWxId(vo.getWxId());
                if(useResidentPo!=null){
                    useResidentPo.setRemark(vo.getTitle());
                    
                    useResidentMapper.updateByPrimaryKey(useResidentPo);
                }
                po.setType("10");
            }

            po.setId(ToolUtils.getUUID());
            po.setState("10");
            po.setCreateTime(new Date());
            po.setUpdateTime(new Date());

            useResidentAddressMapper.insert(po);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增地址保存成功");

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增地址保存失败");
            LogUtils.error("新增地址保存失败！",ex);
        }
        return respBody;
    }


    @PostMapping("/order/add")
    @SystemControllerLog(description="新增订单")
    public RespBody orderAdd(@RequestBody ResidentOrderVo vo){
        RespBody respBody = new RespBody();
        try {




         ResidentAddressPo residentAddressPo =  useResidentAddressMapper.findOne(vo.getAddressId());

          ResidentOrderPo po = new ResidentOrderPo();

         if(residentAddressPo!=null){
             po.setId(ToolUtils.getUUID());


             //ResidentOrderPo ucPo = YBBeanUtils.copyProperties(vo, ResidentOrderPo.class);

             po.setCreateTime(new Date());
             po.setDoorTime(new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss").parse(vo.getDoorTimes()));
             po.setState("10");
             po.setWxId(vo.getWxId());
             po.setAddressId(vo.getAddressId());
             po.setWeight(vo.getWeight());
             po.setCarId("");
             po.setSellprice("0");
             po.setType(vo.getType());
             po.setBuyprice("0");

             useResidentOrderMapper.insert(po);
         }

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "订单提交成功");

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "订单提交失败");
            LogUtils.error("订单提交失败！",ex);
        }
        return respBody;
    }


      @PostMapping("/order/updateState")
       @SystemControllerLog(description="修改订单")
       public RespBody updateState(@RequestBody ResidentOrderVo vo){
           RespBody respBody = new RespBody();
           try {


               ResidentOrderPo  po  =   useResidentOrderMapper.findId(vo.getId());
               if(po!=null){

                   po.setType(vo.getType());
                   po.setCarId(vo.getCarId());
                   useResidentOrderMapper.updateByPrimaryKey(po);

               }
               respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改货品保存成功");

           } catch (Exception ex) {
               respBody.add(RespCodeEnum.ERROR.getCode(), "修改货品保存失败");
               LogUtils.error("修改货品保存失败！",ex);
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

           Double as = 0.00;
          for(int i = 0; i<vo.getDetailLists().size();i++){
              ResidentOrderDetailPo residentOrderDetailPo = new ResidentOrderDetailPo();

              residentOrderDetailPo.setBuyWeight(vo.getDetailLists().get(i).getBuyWeight());
              residentOrderDetailPo.setBuyPrice(vo.getDetailLists().get(i).getBuyPrice());
              residentOrderDetailPo.setGoodsName(vo.getDetailLists().get(i).getGoodsName());
              residentOrderDetailPo.setGoodsId(vo.getDetailLists().get(i).getGoodsId());
              residentOrderDetailPo.setOrderId(vo.getDetailLists().get(i).getOrderId());
              residentOrderDetailPo.setSellPrice("0");
              residentOrderDetailPo.setSellWeight("0");
              as+=Double.valueOf(vo.getDetailLists().get(i).getBuyWeight())*Double.valueOf(vo.getDetailLists().get(i).getBuyPrice());

              residentOrderDetailPo.setId(ToolUtils.getUUID());
              residentOrderDetailPo.setState("10");
              residentOrderDetailPo.setCreateTime(new Date());


              useOrderDetailMapper.insert(residentOrderDetailPo);
          }

           ResidentOrderPo residentOrderPo =   useResidentOrderMapper.findId(vo.getDetailLists().get(0).getOrderId());
           residentOrderPo.setType("40");
            String prices =  String.format("%.2f", as);
           residentOrderPo.setBuyprice(prices);
           useResidentOrderMapper.updateByPrimaryKey(residentOrderPo);
           //useResidentOrderMapper.updateType("40",vo.getOrderId());
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
      public RespBody getOrderList(Paging paging, String wxId,String state,String today,String isError,String type){
          RespBody respBody = new RespBody();
          try {
              //保存返回数据
              ResidentOrderVo vo = new ResidentOrderVo();
              vo.setWxId(wxId);
              vo.setState(state);
              vo.setToday(today);
              vo.setIsError(isError);
              vo.setType(type);

              respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", orderService.findAll(paging,vo));
            //保存分页对象
            paging.setTotalCount(orderService.findCount(vo));
            respBody.setPage(paging);
             // respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户管理机成功", useResidentAddressMapper.findAllWxId(wxId));
          } catch (Exception ex) {
              respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户管理机失败");
          }
          return respBody;
      }


       //获取当前用户所有的订单
        @GetMapping("/getOrderDetail")
        public RespBody getOrderDetail(String id){
            RespBody respBody = new RespBody();
            try {
                //保存返回数据

                respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找订单详情数据成功", useResidentOrderMapper.findIds(id));
              //保存分页对象

            } catch (Exception ex) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "获取订单详情失败");
            }
            return respBody;
        }







       @PostMapping("/suggest/add")
       @SystemControllerLog(description="新增建议")
       public RespBody add(@RequestBody UseSuggestPo useSuggestPo){
           RespBody respBody = new RespBody();
           try {


               useSuggestPo.setId(ToolUtils.getUUID());
               useSuggestPo.setState("10");
               useSuggestPo.setCreateTime(new Date());
               useSuggestMapper.insert(useSuggestPo);
               respBody.add(RespCodeEnum.SUCCESS.getCode(), "建议提交成功");
           } catch (Exception ex) {
               respBody.add(RespCodeEnum.ERROR.getCode(), "建议提交失败");
               LogUtils.error("建议提交失败！",ex);
           }
           return respBody;
       }




         @GetMapping("/para/list")
       public RespBody findParamList(){
           RespBody respBody = new RespBody();
           try {
               respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数详情获取成功", sysParameterMapper.findParamList());


           } catch (Exception ex) {
               respBody.add(RespCodeEnum.ERROR.getCode(), "参数详情获取失败");
           }
           return respBody;
       }


        @PostMapping("/userOrder/update")
        @SystemControllerLog(description="车夫修改订单")
        public RespBody userOrderUpdate(@RequestBody ResidentOrderDetailVo  vo){
            RespBody respBody = new RespBody();
            try {

                Double total = 0.00;
                for(int i = 0 ; i<vo.getDetailList().size();i++){
                   GoodsPo goodsPo =    useGoodsMapper.findOne(vo.getDetailList().get(i).getId());


                    ResidentOrderDetailPo po = new ResidentOrderDetailPo();
                    po.setId(ToolUtils.getUUID());
                    po.setBuyPrice(goodsPo.getPrice());
                    po.setBuyWeight(vo.getDetailList().get(i).getWeight());
                    po.setGoodsName(goodsPo.getName());
                    po.setOrderId(vo.getOrderId());
                    po.setState("10");
                    po.setSellPrice("0");
                    po.setSellWeight("0");
                    po.setCreateTime(new Date());
                    useOrderDetailMapper.insert(po);

                    total+=Double.valueOf(po.getBuyPrice())*Double.valueOf(po.getBuyWeight());

                }


                useResidentOrderMapper.updateBuyprice(total.toString(),vo.getOrderId());



                //list  add
                respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");

            } catch (Exception ex) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
                LogUtils.error("新增货品保存失败！",ex);
            }
            return respBody;
        }



      @GetMapping("/orderList/findAll")
       public RespBody findAll(Paging paging, OrderSumVo orderSumVo){
           RespBody respBody = new RespBody();
           try {
               //保存返回数据


               respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功",orderService.findSum(paging,orderSumVo));
               //保存分页对象
               paging.setTotalCount(orderService.findCountSum(orderSumVo));
               respBody.setPage(paging);
           } catch (Exception ex) {
               respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
               LogUtils.error("查找所有订单数据失败！",ex);
           }
           return respBody;
       }

    @GetMapping("/orderList/mouthfindAll")
           public RespBody mouthfindAll(Paging paging, OrderSumVo orderSumVo){
               RespBody respBody = new RespBody();
               try {
                   //保存返回数据


                   respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功",orderService.mouthfindSum(paging,orderSumVo));
                   //保存分页对象
                   paging.setTotalCount(orderService.mouthfindCountSum(orderSumVo));
                   respBody.setPage(paging);
               } catch (Exception ex) {
                   respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
                   LogUtils.error("查找所有订单数据失败！",ex);
               }
               return respBody;
           }


    @GetMapping("/orderList/toDayFindAll")
           public RespBody toDayFindAll(OrderSumVo orderSumVo){
               RespBody respBody = new RespBody();
               try {
                   //保存返回数据

                   respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有订单数据成功", orderSumMapper.todayFindAll(orderSumVo));
                   //保存分页对象


               } catch (Exception ex) {
                   respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有订单数据失败");
                   LogUtils.error("查找所有订单数据失败！",ex);
               }
               return respBody;
           }













   	
}
