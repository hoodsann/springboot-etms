package com.yingda.lkj.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author hood  2020/6/2
 */
public class IpUtil {
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null)
            return request.getRemoteAddr();
        return request.getHeader("x-forwarded-for");
    }

    public static String getIp(HttpServletRequest request) {
        String ip = null;

        // X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        // Proxy-Client-IP：apache 服务代理
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))
            ipAddresses = request.getHeader("Proxy-Client-IP");

        // WL-Proxy-Client-IP：weblogic 服务代理
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");

        // HTTP_CLIENT_IP：有些代理服务器
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");

        // X-Real-IP：nginx服务代理
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))
            ipAddresses = request.getHeader("X-Real-IP");

        // 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0)
            ip = ipAddresses.split(",")[0];

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))
            ip = request.getRemoteAddr();

        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}