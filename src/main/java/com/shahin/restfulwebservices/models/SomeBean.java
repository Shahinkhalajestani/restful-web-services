package com.shahin.restfulwebservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//we can also Use this One
@JsonIgnoreProperties(value={"field2"})
public class SomeBean {
    private String field1;
    private String field2;
    @JsonIgnore
    private String field3;

    public SomeBean(String value1, String value2, String value3) {
        field1 = value1;
        field2 = value2;
        field3 = value3;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public SomeBean() {
    }
}
