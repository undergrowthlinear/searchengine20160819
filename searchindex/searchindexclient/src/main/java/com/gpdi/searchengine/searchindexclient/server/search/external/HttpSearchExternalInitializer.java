package com.gpdi.searchengine.searchindexclient.server.search.external;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class HttpSearchExternalInitializer extends
		ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		// 请求解码器
		p.addLast("httpRequestDecoder", new HttpRequestDecoder());
		// 响应加码器
		p.addLast("httpResponseEncoder", new HttpResponseEncoder());
		// 自定义处理器
		p.addLast("httpSearchExternalHandler", new HttpSearchExternalHandler());
	}

}
