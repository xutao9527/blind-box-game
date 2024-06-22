package com.bbg.third.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackConfig {

    @Bean
    public CustomAppender customAppender() {
        return new CustomAppender();
    }

    @Bean
    public Logger logger() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);

        // 移除默认配置的所有 Appender
        //rootLogger.detachAndStopAllAppenders();

        // 添加自定义的 Appender
        rootLogger.addAppender(customAppender());

        // 设置日志级别为 INFO（可选）
        rootLogger.setLevel(Level.INFO);

        return rootLogger;
    }

    public static class CustomAppender extends AppenderBase<ILoggingEvent> {
        @Override
        protected void append(ILoggingEvent event) {
            System.out.println("CustomAppender: " + event.getMessage());
        }
    }
}
