package org.sea.chat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/25 00:37
 */
@Slf4j
public class JsonUtils {

    public static ObjectMapper objectMapper = SpringBeanFactory.getBean(ObjectMapper.class);

    public static <T> T jsonToBean(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);

        } catch (Exception e) {
            log.error("method=toBean() is convert error, errorMsg:{}", e.getMessage(), e);
        }
        return null;
    }


}
