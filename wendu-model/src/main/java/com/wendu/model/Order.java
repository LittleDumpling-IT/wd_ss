package com.wendu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "wd_order")
@ApiModel("订单实体")
public class Order implements Serializable {
    @Id
    private Integer id;

    @ApiModelProperty("店铺ID")
    @Column(name = "store_id")
    private Integer storeId;

    @ApiModelProperty("订单ID")
    @Column(name = "order_id")
    private Integer orderId;

    @ApiModelProperty("订单状态")
    @Column(name = "state")
    private String state;

    @ApiModelProperty("下单用户ID")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty("订单总价")
    @Column(name = "order_price")
    private Integer orderPrice;

    @ApiModelProperty("店铺实收")
    @Column(name = "store_amount")
    private Integer storeAmount;

    @ApiModelProperty("下单时间")
    @Column(name = "order_time")
    private Date orderTime;

    @ApiModelProperty("0:删除，1未删除")
    @Column(name = "is_delete")
    private String isDelete;
}
