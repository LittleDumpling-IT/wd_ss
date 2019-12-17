package com.wendu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@ApiModel("店铺基础数据实体")
@Data
@Table(name = "wd_basics")
public class Basics implements Serializable {
    @Id
    private Integer id;

    @Column(name = "effective_order")
    @ApiModelProperty("有效订单")
    private Integer effectiveOrder;

    @Column(name = "old_standing_order")
    @ApiModelProperty("老有效订单")
    private Integer oldEffectiveOrder;

    @Column(name = "order_income")
    @ApiModelProperty("订单收入")
    private Integer orderIncome;

    @Column(name = "old_order_income")
    @ApiModelProperty("老订单收入")
    private Integer oldOrderIncome;

    @Column(name = "guest_price")
    @ApiModelProperty("客单价")
    private Integer  guestPrice;

    @Column(name = "old_guest_price")
    @ApiModelProperty("老客单价")
    private Integer oldGuestPrice;

    @Column(name = "net_profit")
    @ApiModelProperty("净利润")
    private Integer netProfit;

    @Column(name = "old_net_profit")
    @ApiModelProperty("老净利润")
    private Integer oldNetProfit;

    @Column(name = "store_ratings")
    @ApiModelProperty("店铺评分")
    private String storeRatings;

    @Column(name = "old_store_ratings")
    @ApiModelProperty("老店铺评分")
    private String oldStoreRatings;

    @Column(name = "customer")
    @ApiModelProperty("新客户")
    private Integer customer;

    @Column(name = "old_customer")
    @ApiModelProperty("老客户")
    private Integer oldCustomer;

    @Column(name = "praise")
    @ApiModelProperty("好评")
    private Integer praise;

    @Column(name = "old_praise")
    @ApiModelProperty("老好评")
    private Integer oldPraise;

    @Column(name = "half_evaluation")
    @ApiModelProperty("中评")
    private Integer halfEvaluation;

    @Column(name = "old_half_evaluation")
    @ApiModelProperty("老中评")
    private Integer oldHalfEvaluation;

    @Column(name = "bad_review")
    @ApiModelProperty("差评")
    private Integer badReview;

    @Column(name = "old_bad_review")
    @ApiModelProperty("老差评")
    private Integer oldBadReview;

    @Column(name = "no_reply")
    @ApiModelProperty("未回复")
    private Integer noReply;

    @Column(name = "old_no_reply")
    @ApiModelProperty("老未回复")
    private Integer oldNoReply;

    @Column(name = "time_type")
    @ApiModelProperty("时间类型 码值：1：今日2：昨日3：近7日4：近30日")
    private String timeType;

    @Column(name = "store_id")
    @ApiModelProperty("店铺ID")
    private Integer storeId;

    @Column(name = "new_time")
    @ApiModelProperty("生成时间")
    private Date newTime;

    @Column(name = "update_time")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @Column(name = "standby_one")
    @ApiModelProperty("备用1")
    private String standbyOne;

    @Column(name = "standby_two")
    @ApiModelProperty("备用2")
    private String standbyTwo;

    @Column(name = "is_delete")
    @ApiModelProperty("删除")
    private String isDelete;


}
