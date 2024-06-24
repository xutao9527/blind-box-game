package com.bbg.third.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.bbg.core.entity.WebSocketMsg;
import com.bbg.core.feign.socket.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class LogbackConfig {


    @Bean
    public Logger logger(WebSocketFilter webSocketFilter) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        ConsoleAppender<ILoggingEvent> console = (ConsoleAppender<ILoggingEvent>) rootLogger.getAppender("CONSOLE");
        if (console.getEncoder() instanceof LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder) {
            webSocketFilter.setEncoder(layoutWrappingEncoder);
            console.addFilter(webSocketFilter);
        }
        return rootLogger;
    }

    @Setter
    @RequiredArgsConstructor
    @Component
    public static class WebSocketFilter extends Filter<ILoggingEvent> {
        public final WebSocketService webSocketService;
        private LayoutWrappingEncoder<ILoggingEvent> encoder;
        public static AtomicBoolean isFilter = new AtomicBoolean(false);

        @Override
        public FilterReply decide(ILoggingEvent event) {
            if (isFilter.get() && encoder != null) {
                String logContent1 =  event.getFormattedMessage();
                String logContent = encoder.getLayout().doLayout(event);

                System.out.print("logContent\n"+logContent);
                webSocketService.sendAdminMessage(new WebSocketMsg().setMessage(logContent));
            }
            if (event.getLevel().equals(Level.INFO)) {
                return FilterReply.ACCEPT;
            } else {
                return FilterReply.DENY;
            }
        }
    }


}
