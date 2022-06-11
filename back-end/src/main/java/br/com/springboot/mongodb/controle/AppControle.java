package br.com.springboot.mongodb.controle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class AppControle {

    @GetMapping
    public String index() {
        return "Hello World";
    }

}
