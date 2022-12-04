package com.tourbest.erp.chat.connection.info;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.StringTokenizer;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class PayLoad {
    private String payload;
    // TODO: 2022-12-04 커맨드는 enum 으로 변경 예정
    private String command;
    private String value;
    private String senderId;
    private String receiver;

    public String get() {
        return payload;
    }

    // lombok 으로 사용중이라 경고를 뜨는것이라 무시하도록 처리
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    public static class PayLoadBuilder {
        private String payload;
        private String command;
        private String value;

        public PayLoadBuilder payload(String payload) {
            StringTokenizer stringTokenizer = new StringTokenizer(payload, "/");

            this.payload = payload;
            this.command = stringTokenizer.nextToken();

            if(stringTokenizer.hasMoreTokens()) {
                this.value = stringTokenizer.nextToken();
            }

            return this;
        }
    }
}
