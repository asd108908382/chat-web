package org.sea.chat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/24 18:54
 */
@Component
@Data
public class AppConfiguration {

    @Value("${chat.server.port:9000}")
    private int chatServerPort;


    @Value("${chat.heartbeat.time :15000}")
    private long heartBeatTime;

}
