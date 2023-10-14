package me.mocadev.springtest.service;

import static org.junit.jupiter.api.Assertions.*;

import me.mocadev.springtest.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RedisServiceTest extends IntegrationTest {

  @Autowired private RedisService redisService;

  @Test
  void redisGetSet() {
    // given
    String expected = "mocadev";
    String key = "name";

    // when
    redisService.set(key, expected);

    // then
    assertEquals(expected, redisService.get(key));
  }
}
