package kr.co.oasis.product.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setValues(String key, String value) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, values);
    }

    @Override
    public void setValues(String key, String value, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, values, duration);
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if(values.get(key) == null) return "";

        return String.valueOf(values.get(key));
    }

    @Override
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setHashValues(String key, Map<String, Object> map) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(key, map);
    }

    @Override
    public Object getHashValue(String key, String hashKey) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.get(key, hashKey);
    }

    @Override
    public Map<String, Object> getAllHashValues(String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }

    @Override
    public void deleteHashValue(String key, String hashKey) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        hashOps.delete(key, hashKey);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
