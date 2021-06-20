package com.cvc.cvcms.common;

public class CouponCodeUtil {
    private String CouponCode;
    /**
     * @description: CouponCode 由26位小写字母，随机组成，长度为5的字符串
     */

    public static String getCouponCode(){

        int n = 5;
        //最终生成的字符串
        String str = "";
        for (int i = 0; i < n; i++) {
            str = str + (char)(Math.random()*26+'a');
        }
        return str;
    }
}
