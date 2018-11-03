package xin.sutton.test.authorization.manager;

import xin.sutton.test.authorization.model.TokenModel;

/**
 * 对Token进行操作的接口
 *
 * @author codingZhengsz
 * @since 2018-10-30 16:58
 **/
public interface TokenManager {

    /**
     * 创建一个token关联上指定的用户
     * @param userId 指定的用户id
     * @return 生成token
     */
    TokenModel createToken(long userId);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(TokenModel model);

    /**
     * 从字符串中解析token
     * @param authorization 加密之后的字符串
     * @return token
     */
    TokenModel getToken(String authorization);

    /**
     * 删除token
     * @param userId 登录用户的id
     */
    void deleteToken(long userId);

    /**
     * 检查用户是否登录
     * @param model
     * @return
     */
    boolean checkUser(TokenModel model);
}
