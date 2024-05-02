package com.bbg.core.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ApiRet<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private boolean ok;

    private String msg;

    private T data;

    public static <T> ApiRet<T> buildOk(T data){
        return new ApiRet<T>().setData(data).setOk(true).setMsg("操作成功");
    }

    public static <T> ApiRet<T> buildNo(T data,String msg){
        return new ApiRet<T>().setData(data).setOk(false).setMsg(msg);
    }
}
