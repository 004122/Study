package com.example.entity.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(description = "响应实体封装类")
@AllArgsConstructor //生产构造函数
public class RestBean<T> {

    @ApiModelProperty("状态码")
    int code;
    @ApiModelProperty("状态码描述")
    String reason;
    @ApiModelProperty("数据实体")
    T data;

    public RestBean(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}

