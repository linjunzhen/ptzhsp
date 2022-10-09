/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.constant;

/**
 * 描述 好差评系统错误常量
 * 
 * @author Luffy Cai
 * @created 2020年7月14日 下午10:38:45
 */
public class ErrorConstant {
    /**
     * 请求成功
     */
    public static final int OK = 200;
    /**
     * 系统内部错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    /**
     * 请求参数有误
     */
    public static final int BAD_REQUEST = 400;
    /**
     * 未登录
     */
    public static final int UNAUTHORIZED = 401;
    /**
     * 请求资源不允许访问
     */
    public static final int FORBIDDEN = 403;
    /**
     * 请求资源不存在
     */
    public static final int NOT_FOUND = 404;
    /**
     * 验证码错误
     */
    public static final int ERROR_KAPTCHA = 1;
    /**
     * 用户名或密码错误
     */
    public static final int ERROR_PASSWORD = 2;
    /**
     * 重复值错误
     */
    public static final int DUPLICATED_VALUE = 3;
    /**
     * 保存文件失败
     */
    public static final int ERROR_UPLOAD_FILE = 4;
    /**
     * 获取角色失败
     */
    public static final int ERROR_GET_ROLE = 5;
    /**
     * 错误信息
     */
    private static final String[] MSG = { "验证码错误", "登录失败", "重复值错误", "上传文件失败", "获取角色失败" };

    /**
     * 
     * @Description
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param code
     * @return String
     */
    public static final String getMsg(int code) {
        switch (code) {
        case OK:
            return "成功";
        case INTERNAL_SERVER_ERROR:
            return "系统内部错误";
        case BAD_REQUEST:
            return "请求参数有误";
        case UNAUTHORIZED:
            return "未登录";
        case FORBIDDEN:
            return "请求资源不允许访问";
        case NOT_FOUND:
            return "请求资源不存在";
        default:
            if (code > 0 && MSG.length >= code) {
                return MSG[code - 1];
            } else {
                return "未知错误";
            }
        }
    }

}
