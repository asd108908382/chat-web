package org.sea.chat.config.constant.protocol;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiaweiguo
 */
@Data
public class UserEntity {
    private Long id;
    private String username;
    private String password;

    private String nickname;

    private Boolean blocked;

    private LocalDateTime updatedTime;
}
