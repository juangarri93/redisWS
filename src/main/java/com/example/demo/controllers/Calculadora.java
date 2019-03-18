package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/calculadora")
public class Calculadora {

    public Calculadora() {
    }

    /**
     * http://localhost:8080/calculadora/multiplicar?x=2&y=2
     */
    @GetMapping(value="/multiplicar", params= {"x","y"}) //, produces =  "text/plain"
    public int multiplicar(@RequestParam("x") int x, @RequestParam("y") int y) {

    	int resultado = x * y;
    	
       return resultado;  
       
    }

    /**
     * http://localhost:8080/calculadora/multiplicar
     */
    @GetMapping(value="/multiplicar") 
    public int multiplicar() {

    	return  2*2 ;  
       
    }
	
    
    
}
