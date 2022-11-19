package com.tourbest.erp.chat.util;

import lombok.Data;

import java.util.StringTokenizer;

@Data
public class PayLoadVo {
    String payload;
    String command;
    String value;

    public PayLoadVo(String payload) {
        this.payload = payload;

        StringTokenizer stringTokenizer = new StringTokenizer(payload, "/");
        this.command = stringTokenizer.nextToken();
        this.value = stringTokenizer.nextToken();
    }
}
