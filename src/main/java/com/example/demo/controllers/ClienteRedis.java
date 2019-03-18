package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/redis")
public class ClienteRedis {

    public ClienteRedis() {
    }

    /**
     * http://localhost:8080/redis/test
     */
    @GetMapping(value="/test") 
    public String test() {
    	
    	String test = "Fail";
    	
    	try (Jedis jedis = Pool.getResource()) {

            jedis.set("Test", "Hola Mundo!");
            test=jedis.get("Test");
         } catch (Exception e){
             e.printStackTrace();
         }
    	
    	
    	return test ;  
       
    }



    /**
     * http://localhost:8080/redis/get?x=1
     */
    @GetMapping(value="/get", params= {"x"}) //, produces =  "text/plain"
    public String set(@RequestParam("x") String x) {

    	String test = "Fail";
    	
    	try (Jedis jedis = Pool.getResource()) {

            test=jedis.get(x);

    	} catch (Exception e){
             e.printStackTrace();
        }
    	
    	
    	return test ;  
       
    }


    /**
     * http://localhost:8080/redis/get?x=2&y=2
     */
    @GetMapping(value="/set", params= {"x","y"}) //, produces =  "text/plain"
    public String set(@RequestParam("x") String x, @RequestParam("y") String y) {

    	String resultado = "Fail";
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado=jedis.set(x, y);
            
         } catch (Exception e){
             e.printStackTrace();
         }
    	
       return resultado;  
       
    }
	
    
    
    
}
