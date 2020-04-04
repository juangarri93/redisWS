package tasks;

import java.util.concurrent.Callable;

import com.example.demo.controllers.redis.Operacion;
import com.example.demo.controllers.redis.Pool;

import redis.clients.jedis.Jedis;

public class SetCallable implements Callable<Operacion>{

	String key;
	String value;
	
	public SetCallable(String key,
	String value) {
		this.key=key;
		this.value=value;
	}
		
	@Override
	public Operacion call() throws Exception {
		Operacion resultado = null;
    	
    	try (Jedis jedis = Pool.getResource()) {

    		resultado= new Operacion("Set", key, jedis.set(key,value));
            
        } catch (Exception e){
             e.printStackTrace();
        }
    	
    	return resultado;
	}

}
