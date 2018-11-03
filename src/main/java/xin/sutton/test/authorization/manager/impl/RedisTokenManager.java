package xin.sutton.test.authorization.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import xin.sutton.test.authorization.manager.TokenManager;
import xin.sutton.test.authorization.model.TokenModel;
import xin.sutton.test.vo.Const;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token实现类
 *
 * @author codingZhengsz
 * @since 2018-10-30 16:59
 **/
@Component
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<Long,String> redis;

    @Autowired
    public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public TokenModel createToken(long userId) {
        // 使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-","");
        TokenModel model = new TokenModel(userId, token);
        redis.boundValueOps(userId).set(token, Const.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    /**
     * 获取TokenModel
     * @param authorization
     * @return {@link TokenModel}
     */
    public TokenModel getToken(String authorization){
        if(authorization == null || authorization.length() == 0){
            return null;
        }
        String[] param = authorization.split("_");
        if(param.length != 2) {
            return null;
        }
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenModel(userId,token);
    }

    /**
     * 验证Token的有效性
     * @param model -- TokenModel
     * @return Token的有效性
     */
    public boolean checkToken(TokenModel model) {
        if(model == null) {
            return false;
        }
        String token = redis.boundValueOps(model.getUserId()).get();
        if(token == null || !token.equals(model.getToken())) {
            return false;
        }
        // 如果验证成功，说明进行了一次有效操作，token时间自动延长
        redis.boundValueOps(model.getUserId()).expire(Const.TOKEN_EXPIRES_HOUR,TimeUnit.HOURS);
        return true;
    }

    public boolean checkUser(TokenModel model) {
        if(model == null) {
            return false;
        }
        String token = redis.boundValueOps(model.getUserId()).get();
        if(token != null) {
            return true;
        }
        return false;
    }

    public void deleteToken(long userId) {
        redis.delete(userId);
    }
}
