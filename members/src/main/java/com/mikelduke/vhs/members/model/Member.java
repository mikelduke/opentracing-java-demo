package com.mikelduke.vhs.members.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Table(name = "member")
@Data
@JsonInclude(Include.NON_NULL)
public class Member {

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	private String email;

	@NotNull
	private String memberName;

	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;	
}