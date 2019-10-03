package com.qzi.cms.common.vo;


import javax.persistence.Table;
import java.util.Date;
import java.util.List;


/**
 * 用户订单详情
 */


public class ResidentOrderDetailVo {
    private String id;
    private String goodsId;
    private String goodsName;
    private String buyPrice;
    private String sellPrice;
    private String buyWeight;
    private String sellWeight;
    private String orderId;
    private String state;
    private Date createTime;
    private List<OrderDatailListVo> detailList;
    private List<ResidentOrderDetailVo> detailLists;


    public List<ResidentOrderDetailVo> getDetailLists() {
        return detailLists;
    }

    public void setDetailLists(List<ResidentOrderDetailVo> detailLists) {
        this.detailLists = detailLists;
    }

    public List<OrderDatailListVo> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDatailListVo> detailList) {
        this.detailList = detailList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String bugPrice) {
        this.buyPrice = bugPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBuyWeight() {
        return buyWeight;
    }

    public void setBuyWeight(String bugWeight) {
        this.buyWeight = bugWeight;
    }

    public String getSellWeight() {
        return sellWeight;
    }

    public void setSellWeight(String sellWeight) {
        this.sellWeight = sellWeight;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
