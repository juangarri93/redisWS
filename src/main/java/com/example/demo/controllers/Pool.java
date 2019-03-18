package com.example.demo.controllers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Pool {
    private static JedisPool pool = new JedisPool("192.168.131.128",6379);

    public static Jedis getResource(){
        return pool.getResource();
    }
}