package com.gpdi.searchengine.searchindexclient.server.search.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @description: TODO(用于发布对外的netty服务)
 * @author zhangwu
 * @date 2016年8月15日
 * @version 1.0.0
 */
public class HttpSearchExternalServer {

	private Logger logger = LoggerFactory
			.getLogger(HttpSearchExternalServer.class);

	private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean initServer() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			logger.info("initServer...");
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new HttpSearchExternalInitializer());

			Channel ch = b.bind(getPort()).sync().channel();
			logger.info("initServer success...,you can navigate to http"
					+ "://127.0.0.1:" + getPort() + '/');
			//阻塞等待监听服务
			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("initServer failed..." + e.getMessage());
		} finally {
			// TODO: handle finally clause
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		return true;
	}

}
