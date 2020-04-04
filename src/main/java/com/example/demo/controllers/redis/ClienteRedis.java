package com.example.demo.controllers.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import tasks.SetCallable;

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
    public Operacion get(@RequestParam("x") String x) {

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
    @PostMapping(value="/set", params= {"x","y"}) //, produces =  "text/plain"
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
     * http://localhost:30000/redis/setSingleCallable?x=2&y=2
     */
    @GetMapping(value="/setSingleCallable", params= {"x","y"}) //, produces =  "text/plain"
    public Operacion setSingleCallable(@RequestParam("x") String x, @RequestParam("y") String y) {
    	
    	ExecutorService singleThread = Executors.newSingleThreadExecutor();
    	
    	SetCallable setCall = new SetCallable(x, y);
    	Future<Operacion> future = singleThread.submit(setCall);
    	
    	try {
    		return future.get();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new Operacion();
    	}
       
    }


    /**
     * http://localhost:8080/redis/lpush?x=2&y=2
     */
    @PostMapping(value="/lpush", params= {"x","y"}) //, produces =  "text/plain"
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
     * http://localhost:30000/redis/saddMasivo?x=2
     */
    @GetMapping(value="/saddMasivo", params= {"x"}) //, produces =  "text/plain"
    public List<Operacion> saddMasivo(@RequestParam("x") String x) {

    	List<Operacion> resultado = null;
    	List<Future<Operacion>> futures = null;
    	ExecutorService executor = Executors.newFixedThreadPool(20);
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado = new ArrayList<Operacion>();
    		
    		resultado.add(new Operacion("Start", null, new Date()));
    		
    		futures = new ArrayList<Future<Operacion>>();
    		
    		for(int i=1; i<=100; i++) {
    			String value = "Value" + String.valueOf(i);
    			//futures.add(executor.submit( () -> new Operacion("LPush", x, jedis.sadd(x, value))));
    			futures.add(executor.submit( () -> {
    			TimeUnit.SECONDS.sleep(1);
    			return new Operacion("Test", x, value);
    			}
    			));
        	}
    		    		
    		for(Future<Operacion> future : futures) {
    			resultado.add(future.get());
    		}
    		
    		resultado.add(new Operacion("End", null, new Date()));
    		
         } catch (Exception e){
             e.printStackTrace();
         }
    	
       return resultado;  
       
    }

    /**
     * http://localhost:30000/redis/saddMasivoLento?x=2
     */
    @GetMapping(value="/saddMasivoLento", params= {"x"}) //, produces =  "text/plain"
    public List<Operacion> saddMasivoLento(@RequestParam("x") String x) {

    	List<Operacion> resultado = null;
    	List<Future<Operacion>> futures = null;
    	ExecutorService executor = Executors.newFixedThreadPool(20);
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado = new ArrayList<Operacion>();
    		
    		resultado.add(new Operacion("Start", null, new Date()));
    		
    		futures = new ArrayList<Future<Operacion>>();
    		
    		for(int i=1; i<=100; i++) {
    			String value = "Value" + String.valueOf(i);
    			TimeUnit.SECONDS.sleep(1);
    			resultado.add(new Operacion("Test", x, value));
    			
        	}
    		
    		resultado.add(new Operacion("End", null, new Date()));
    		    		
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
