package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.models.Name;
import com.shahin.restfulwebservices.models.PersonV1;
import com.shahin.restfulwebservices.models.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping(value="person/param", params="version=1")
    public PersonV1 personV1(){
        return new PersonV1("Ramtin The Cuzz");
    }

    @GetMapping(value="person/param",params="version=2")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Ram","Cuzz"));
    }
    @GetMapping(value="person/header", headers="X-API-VERSION=1")
    public PersonV1 headerV1(){
        return new PersonV1("Ramtin The Cuzz");
    }

    @GetMapping(value="person/header",headers="X-API-VERSION=2")
    public PersonV2 headerV2(){
        return new PersonV2(new Name("Ram","Cuzz"));
    }
    @GetMapping(value="person/produces", produces = "application/shk.company.app-v1+xml")
    public PersonV1 producesV1(){
        return new PersonV1("Ramtin The Cuzz");
    }

    @GetMapping(value="person/produces",produces = "application/shk.company.app-v2+json")
    public PersonV2 producesV2(){
        return new PersonV2(new Name("Ram","Cuzz"));
    }

}
