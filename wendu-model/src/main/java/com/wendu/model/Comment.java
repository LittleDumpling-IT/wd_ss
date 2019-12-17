package com.wendu.model;


import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "wd_comment")
@Data
@ApiModel("店铺评论实体")
public class Comment implements Serializable {

    @Id
    private String id;


    @ApiModelProperty("店铺ID")
    @Column(name = "store_id")
    private Integer storeId;

    @ApiModelProperty("评价分数")
    @Column(name = "comment_score")
    private String commentScore;


    @ApiModelProperty("评价内容")
    @Column(name = "comment_content")
    private String commentContent;


    @ApiModelProperty("点评时间")
    @Column(name = "comment_time")
    private Date commentTime;

    @ApiModelProperty("评价类型 1：商品评价 2：店铺评价  3：订单评价")
    @Column(name = "comment_type")
    private String commentType;

    @ApiModelProperty("0:删除，1未删除")
    @Column(name = "is_delete")
    private String isDelete;
}
