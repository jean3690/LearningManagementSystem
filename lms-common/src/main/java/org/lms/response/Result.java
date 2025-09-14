package org.lms.response;

import lombok.Data;

@Data
public class Result {
    private String msg;
    private Integer code;
    private Object data;
    public void success(String msg){
        this.msg = msg;
        this.code = 200;
    }
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        return result;
    }
    public static Result success(String msg,Object data){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result error(){
        Result result = new Result();
        result.setCode(400);
        return result;
    }
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }
}
