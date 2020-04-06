package com.banana.auth.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailVo {
    private String to;
    private String subject;
    private String text;
}
