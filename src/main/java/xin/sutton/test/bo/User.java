package xin.sutton.test.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codingZhengsz
 * @since 2018-10-23 20:28
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    private String username;
    private String password;
    private String nickname;
}
