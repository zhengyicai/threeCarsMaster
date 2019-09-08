/* 
 * 文件名：SysRoleVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月20日
 * 版本号：v1.0
*/
package com.qzi.cms.common.vo;

/**
 * 订单详情vo
 * 
 * @author zyc
 * @version v1.0
 * @date 2017年6月20日
 */
public class OrderDatailListVo {
	/**
	 * 主键编号
	 */
	private String id;
	/**
	 * 重量
	 */
	private String weight;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
}
