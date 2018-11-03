package xin.sutton.test.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Tolen的Model类，可以增加字段来提高安全性，例如URL签名以及时间戳
 *
 * @author codingZhengsz
 * @since 2018-10-30 17:41
 **/
@Data
@Builder
@AllArgsConstructor
public class TokenModel {
    private long userId;
    private String token;
}
