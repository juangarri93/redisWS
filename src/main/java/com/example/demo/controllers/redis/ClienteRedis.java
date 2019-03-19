package com.example.demo.controllers.redis;

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
    public Operacion set(@RequestParam("x") String x) {

    	Operacion test = new Operacion();
    	
    	try (Jedis jedis = Pool.getResource()) {

    		test.setOperacion("Get");
    		test.setKey(x);
    	    test.setValue(jedis.get(x));

    	} catch (Exception e){
             e.printStackTrace();
        }
    	
    	
    	return test ;  
       
    }


    /**
     * http://localhost:8080/redis/set?x=2&y=2
     */
    @GetMapping(value="/set", params= {"x","y"}) //, produces =  "text/plain"
    public Operacion set(@RequestParam("x") String x, @RequestParam("y") String y) {

    	Operacion resultado = null;
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado= new Operacion("Set", x, jedis.set(x, y));
            
         } catch (Exception e){
             e.printStackTrace();
         }
    	
       return resultado;  
       
    }
	

    /**
     * http://localhost:8080/redis/lpush?x=2&y=2
     */
    @GetMapping(value="/lpush", params= {"x","y"}) //, produces =  "text/plain"
    public Operacion lpush(@RequestParam("x") String x, @RequestParam("y") String y) {

    	Operacion resultado = null;
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado= new Operacion("LPush", x, jedis.lpush(x, y));
            
         } catch (Exception e){
             e.printStackTrace();
         }
    	
       return resultado;  
       
    }
	


    /**
     * http://localhost:8080/redis/lrange?x=2&y=2&z=2
     */
    @GetMapping(value="/lrange", params= {"x","y","z"}) //, produces =  "text/plain"
    public Operacion lrange(@RequestParam("x") String x, @RequestParam("y") long y, @RequestParam("z") long z){

    	Operacion resultado = null;
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado= new Operacion("LRange", x, jedis.lrange(x, y, z));
            
         } catch (Exception e){
             e.printStackTrace();
         }
    	
       return resultado;  
       
    }
    
    
    
}
