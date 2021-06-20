package com.cvc.cvcms.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZJ
 * @date 2021/3/24 13:20
 * @desc json标准返回
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonStandard implements Serializable {
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 普通消息
     */
    private String message;

    /**
     * 数据
     */
    private Map<String,Object> data;

    public static JsonStandard success() {
        JsonStandard jsonStandard = new JsonStandard();
        jsonStandard.setStatus(ErrorEnum.SUCCESS.getStatus());
        jsonStandard.setError(ErrorEnum.SUCCESS.getError());
        jsonStandard.setMessage("success");
        jsonStandard.setData(new HashMap<>(0));
        return jsonStandard;
    }

    public static JsonStandard success(String message) {
        JsonStandard jsonStandard = new JsonStandard();
        jsonStandard.setStatus(ErrorEnum.SUCCESS.getStatus());
        jsonStandard.setError(ErrorEnum.SUCCESS.getError());
        jsonStandard.setMessage(message);
        jsonStandard.setData(new HashMap<>(0));
        return jsonStandard;
    }

    public static JsonStandard success(Map<String,Object> data) {
        JsonStandard jsonStandard = new JsonStandard();
        jsonStandard.setStatus(ErrorEnum.SUCCESS.getStatus());
        jsonStandard.setError(ErrorEnum.SUCCESS.getError());
        jsonStandard.setMessage("success");
        jsonStandard.setData(data);
        return jsonStandard;
    }

    public static JsonStandard success(String message, Map<String,Object> data) {
        JsonStandard jsonStandard = new JsonStandard();
        jsonStandard.setStatus(ErrorEnum.SUCCESS.getStatus());
        jsonStandard.setError(ErrorEnum.SUCCESS.getError());
        jsonStandard.setMessage(message);
        jsonStandard.setData(data);
        return jsonStandard;
    }

    public static JsonStandard error(ErrorEnum errorEnum,String message){
        JsonStandard jsonStandard = new JsonStandard();
        jsonStandard.setStatus(errorEnum.getStatus());
        jsonStandard.setError(errorEnum.getError());
        jsonStandard.setMessage(message);
        jsonStandard.setData(new HashMap<>(0));
        return jsonStandard;
    }

    public static JsonStandard error(ErrorEnum errorEnum,String message, Map<String,Object> data){
        JsonStandard jsonStandard = new JsonStandard();
        jsonStandard.setStatus(errorEnum.getStatus());
        jsonStandard.setError(errorEnum.getError());
        jsonStandard.setMessage(message);
        jsonStandard.setData(data);
        return jsonStandard;
    }

    @Override
    public String toString() {
        return "JsonStandard{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}



