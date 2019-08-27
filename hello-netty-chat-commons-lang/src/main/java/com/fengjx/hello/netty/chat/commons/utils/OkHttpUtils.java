package com.fengjx.hello.netty.chat.commons.utils;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

	public static OkHttpClient build() {
		return build(500, 3, 5);
	}

	public static OkHttpClient build(int maxIdleConnections, int timeOut, int keepAliveDuration) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS))
				.connectTimeout(timeOut, TimeUnit.SECONDS).readTimeout(timeOut, TimeUnit.SECONDS)
				.writeTimeout(timeOut, TimeUnit.SECONDS);
		return builder.build();
	}

}