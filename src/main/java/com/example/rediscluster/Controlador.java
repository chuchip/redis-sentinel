package com.example.rediscluster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@Slf4j
public class Controlador {

    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("{id}")
    public String valor(@PathVariable String id)
    {
      log.info("Buscando id: {}",id);
      if (!redisTemplate.hasKey(id))
            return "Not found key: "+id;
      return redisTemplate.opsForValue().get(id) .toString();
    }
    @PutMapping("{id}/{value}")
    public String valor(@PathVariable String id,@PathVariable String value)
    {
        log.info("Put id: {} with value {}",id,value);
        if (redisTemplate.hasKey(id))
            log.info("{} exists with value  {} ",id,redisTemplate.opsForValue().get(id).toString());
        redisTemplate.opsForValue().set(id, value, Duration.ofMinutes(5));
        return value;
    }

}
