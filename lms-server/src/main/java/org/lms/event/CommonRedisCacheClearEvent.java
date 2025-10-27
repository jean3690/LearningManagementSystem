package org.lms.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonRedisCacheClearEvent {
    private String prefix;
}
