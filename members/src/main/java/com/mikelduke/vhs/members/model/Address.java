package com.mikelduke.vhs.members.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Table(name = "address")
@Data
@JsonInclude(Include.NON_NULL)
public class Address {
    
    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String streetAddress;
    
    @NotBlank
    private String zip;

}
