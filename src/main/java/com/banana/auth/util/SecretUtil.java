package com.banana.auth.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * 加密工具类
 *
 * @author : li.yang
 * @version :
 * @date : 2019/12/11 13:57
 */
public class SecretUtil {
    /**
     * MD5加密
     *
     * @param string 需要加密的字符串
     * @return 加密结果
     * @throws UnsupportedEncodingException 转码异常
     */
    public static String encryptMd5(String string) throws UnsupportedEncodingException {
        return encryptMd5(string, "UTF-8");
    }

    /**
     * MD5加密
     *
     * @param string  需要加密的字符串
     * @param charSet 加密的字符编码
     * @return 加密结果
     * @throws UnsupportedEncodingException 转码异常
     */
    public static String encryptMd5(String string, String charSet) throws UnsupportedEncodingException {
        return DigestUtils.md5Hex(string.getBytes(charSet));
    }
}
