package com.coursemanager.util.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryUtil {

	private final static Logger logger = LoggerFactory.getLogger(ThreadFactoryUtil.class);

	public static ThreadFactory getLogThreadFactory(final String threadName) {
		return new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName(threadName);
				thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						logger.error(e.getMessage());
					}
				});
				return thread;
			}
		};
	}
}
