package kr.co.oasis.product.repository.redis;

import java.time.Duration;
import java.util.Map;

public interface RedisRepository {
    void setValues(String key, String value);

    void setValues(String key, String value, Duration duration);

    String getValue(String key);

    void deleteValue(String key);

    void setHashValues(String key, Map<String, Object> map);

    Object getHashValue(String key, String hashKey);

    Map<String, Object> getAllHashValues(String key);

    void deleteHashValue(String key, String hashKey);

    boolean hasKey(String key);
}

