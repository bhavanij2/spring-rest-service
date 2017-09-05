package com.example;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GreetingController {

    @RequestMapping(path="/greet", method = RequestMethod.GET)
    public Greeting greet(@RequestParam(name="name", defaultValue = "World") String name) {
        return new Greeting(1, String.format("Hello %s!", name));
    }


    @RequestMapping(path="/hateoas-greet", method = RequestMethod.GET)
    public ResponseEntity<HaeteoasGreeting> hateoasGreet(@RequestParam(name="name", defaultValue = "World") String name) {
        HaeteoasGreeting greeting = new HaeteoasGreeting(String.format("Hello %s!", name));
        greeting.add(linkTo(methodOn(GreetingController.class).hateoasGreet(name)).withSelfRel());
        greeting.add(linkTo(methodOn(GreetingController.class).greet(name)).withRel("/greet"));

        return new ResponseEntity(greeting, HttpStatus.OK);
    }


    @RequestMapping(value = "/post-greeting", method = RequestMethod.POST)
    public HttpEntity<Resource> postGreeting(@Valid @RequestBody Greeting greeting) {
        System.out.println("Entered!!");
        System.out.println(greeting);
        return new ResponseEntity(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/post-greeting-object", method = RequestMethod.POST)
    public Greeting postGreetingObject(@Valid @RequestBody Greeting greeting) {
        System.out.println("Entered!!");
        System.out.println(greeting);
        greeting.setContent("Received");
        return greeting;
    }

    @RequestMapping(value = "/post-greeting-no-return", method = RequestMethod.POST)
    public void postGreetingNoReturn(@Valid @RequestBody Greeting greeting) {
        System.out.println("Entered!!");
        System.out.println(greeting);
    }

}
