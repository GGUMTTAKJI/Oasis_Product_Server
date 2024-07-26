package kr.co.oasis.product.provider.socialAuthApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class KakaoResponse {
    private long id;
    private String connected_at;
    private KakaoAccount kakao_account;
}


