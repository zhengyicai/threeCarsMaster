package com.qzi.cms.web.controller;


import com.qzi.cms.common.annotation.SystemControllerLog;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.GoodsPo;
import com.qzi.cms.common.po.GoodsRecordPo;
import com.qzi.cms.common.resp.Paging;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.vo.SysParameterVo;
import com.qzi.cms.server.service.web.GoodsRecordService;
import com.qzi.cms.server.service.web.GoodsService;
import com.qzi.cms.server.service.web.ParameterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 物品价格
 * @author zyc
 * @version v1.0
 * @date 2017年6月28日
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Resource
    private GoodsService goodsService;


    @Resource
    private GoodsRecordService goodsRecordService;

    @GetMapping("/findAll")
    public RespBody findAll(Paging paging){
        RespBody respBody = new RespBody();
        try {
            //保存返回数据
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有货品数据成功", goodsService.findAll(paging));
            //保存分页对象
            paging.setTotalCount(goodsService.findCount());
            respBody.setPage(paging);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有货品数据失败");
            LogUtils.error("查找所有货品数据失败！",ex);
        }
        return respBody;
    }

    @PostMapping("/add")
    @SystemControllerLog(description="新增货品")
    public RespBody add(@RequestBody GoodsPo goodsPo){
        RespBody respBody = new RespBody();
        try {
           goodsService.add(goodsPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "新增货品保存成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "新增货品保存失败");
            LogUtils.error("新增货品保存失败！",ex);
        }
        return respBody;
    }

    @PostMapping("/update")
    @SystemControllerLog(description="修改货品设置")
    public RespBody update(@RequestBody GoodsPo goodsPo){
        RespBody respBody = new RespBody();
        try {
            goodsService.update(goodsPo);

            //增加一个记录
            GoodsRecordPo goodsRecordPo = new GoodsRecordPo();
            goodsRecordPo.setName(goodsPo.getName());
            goodsRecordPo.setPrice(goodsPo.getPrice());
            goodsRecordPo.setGoodsId(goodsPo.getId());
            goodsRecordService.add(goodsRecordPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改货品保存成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "修改货品保存失败");
            LogUtils.error("修改货品保存失败！",ex);
        }
        return respBody;
    }

    @PostMapping("/delete")
    @SystemControllerLog(description="删除货品设置")
    public RespBody delete(@RequestBody GoodsPo goodsPo){
        RespBody respBody = new RespBody();
        try {
            goodsService.delete(goodsPo);
            GoodsRecordPo goodsRecordPo = new GoodsRecordPo();
            goodsRecordPo.setId(goodsPo.getId());
            goodsRecordService.delete(goodsRecordPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "删除货品成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "删除货品失败");
            LogUtils.error("删除货品失败！",ex);
        }
        return respBody;
    }


}
