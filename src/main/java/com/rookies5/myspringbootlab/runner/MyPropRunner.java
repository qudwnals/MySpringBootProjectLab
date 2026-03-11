package com.rookies5.myspringbootlab.runner;

import com.rookies5.myspringbootlab.environment.MyEnvironment;
import com.rookies5.myspringbootlab.property.MyPropProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyPropRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    private final MyPropProperties myPropProperties;
    private final MyEnvironment myEnvironment;

    @Override
    public void run(ApplicationArguments args) {
        logger.debug("@Value myprop.username = {}", username);
        logger.info("@Value myprop.port = {}", port);

        logger.debug("Properties myprop.username = {}", myPropProperties.getUsername());
        logger.info("Properties myprop.port = {}", myPropProperties.getPort());

        logger.info("현재 프로파일 환경 모드 = {}", myEnvironment.getMode());

        args.getOptionNames().forEach(option -> logger.debug("실행 인자 옵션: {}", option));
    }
}
