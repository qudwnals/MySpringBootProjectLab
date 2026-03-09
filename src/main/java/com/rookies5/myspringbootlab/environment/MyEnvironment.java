package com.rookies5.myspringbootlab.environment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MyEnvironment {
    private String mode;
}
