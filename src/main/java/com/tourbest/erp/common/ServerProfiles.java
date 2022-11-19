package com.tourbest.erp.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class ServerProfiles {

    private String imgUrl; //이미지 주소
    private String imgPath; //이미지 경로
    
    private String kisaSeedEcbKey; // KISA SEED-ECB 암호화 기본 키
    
    private String comDataFilePath; //고객사 자료가 저장되는 root 폴더 파일 패스
    private String comDataFileUrl; //고객사 자료가 저장되는 root 폴더  HTTP URL

}
