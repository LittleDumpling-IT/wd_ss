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
@ApiModel("店铺商品实体")
@Table(name="wd_goods")
public class Goods  implements Serializable {

    @Id
    private Integer id;

    @ApiModelProperty("店铺ID")
    @Column(name = "store_id")
    private Integer storeId;

    @ApiModelProperty("商品ID")
    @Column(name = "good_id")
    private Integer goodId;

    @ApiModelProperty("商品名")
    @Column(name = "good_name")
    private String goodName;

    @ApiModelProperty("商品分类")
    @Column(name = "good_category")
    private String goodCategory;

    @ApiModelProperty("商品单价")
    @Column(name = "good_price")
    private String goodPrice;

    @ApiModelProperty("商品实际单价")
    @Column(name = "good_actual_price")
    private String goodActualPrice;

    @ApiModelProperty("商品图片")
    @Column(name = "good_url")
    private String goodUrl;

    @ApiModelProperty("商品图片")
    @Column(name = "good_spec")
    private String goodSpec;

    @ApiModelProperty("0:删除，1未删除")
    @Column(name = "is_delete")
    private String isDelete;
}
