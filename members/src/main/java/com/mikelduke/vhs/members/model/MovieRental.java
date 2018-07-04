package com.mikelduke.vhs.members.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity
@Table(name = "movie_rentals")
@Data
@JsonInclude(Include.NON_NULL)
public class MovieRental {

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@ManyToOne
	private Member member;

	@NotNull
	private Integer movieId;
}
