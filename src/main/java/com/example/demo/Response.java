package com.example.demo;
//前后端的格式
public class Response<T> {//泛型
    private T data;
    private boolean success;//是否成功
    private String errorMsg;//报错


    //封装
    //成功了
public static <K> Response<K> newSuccess(K data){
    Response<K> response=new Response<>();
    response.setData(data);
    response.setSuccess(true);
    return response;
}

//失败了
public   static Response<Void>newFail(String errorMsg){
    Response<Void>response=new Response<>();
    response.setErrorMsg(errorMsg);
    response.setSuccess(false);
    return response;
}


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
