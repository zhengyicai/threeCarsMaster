package com.qzi.cms.common.po;


import javax.persistence.Table;
import java.util.Date;


/**
 * 用户订单详情
 */

@Table(name="use_order_detail")
public class ResidentOrderDetailPo {
    private String id;
    private String goodsId;
    private String goodsName;
    private String bugPrice;
    private String sellPrice;
    private String bugWeight;
    private String sellWeight;
    private String orderId;
    private String state;
    private Date createTime;


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

    public String getBugPrice() {
        return bugPrice;
    }

    public void setBugPrice(String bugPrice) {
        this.bugPrice = bugPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBugWeight() {
        return bugWeight;
    }

    public void setBugWeight(String bugWeight) {
        this.bugWeight = bugWeight;
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
