package com.wtx.riptide.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxySupport {
		
	public static boolean isReachable(final Proxy proxy, final int timeout) {
        if (proxy.type() == Proxy.Type.SOCKS) {
            final InetSocketAddress addr = (InetSocketAddress)proxy.address();
            if (addr.getPort() != 1080) {
                return false;
            }
            try {
                return addr.getAddress().isReachable(timeout);
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
	
    public static boolean isReachable(final Proxy proxy) {
        if (proxy.type() == Proxy.Type.SOCKS) {
            final InetSocketAddress addr = (InetSocketAddress)proxy.address();
            try {
                return addr.getAddress().isReachable(1000);
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
		return false;
    }

}
