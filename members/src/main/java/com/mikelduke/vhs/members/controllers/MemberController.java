package com.mikelduke.vhs.members.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.mikelduke.vhs.members.MovieDTO;
import com.mikelduke.vhs.members.model.Member;
import com.mikelduke.vhs.members.model.MemberRepo;
import com.mikelduke.vhs.members.model.MovieRentalRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class MemberController {

	@Autowired
	MemberRepo memberRepo;

	@Autowired
	MovieRentalRepo movieRentalRepo;

	@Autowired
	RestTemplate restTemplate;

	@Value("${movieservice.domain:localhost}")
	String movieService;

	@Value("${movieservice.port:4567}")
	String port;

	@Value("${movieservice.scheme:http}")
	String scheme;

	@Autowired
	Tracer tracer;

	@GetMapping(value = "/members")
	public List<Integer> getMember() {
		List<Integer> ids = new ArrayList<>();

		memberRepo.findAll().forEach(m -> ids.add(m.getId()));
		return ids;
	}

	@GetMapping(value = "/members/{id}")
	public Member getMember(@PathVariable Integer id) {
		return memberRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
	}

	@GetMapping(value = "/members/{id}/rentals")
	public List<MovieDTO> getMemberMovies(@PathVariable Integer id) {
		tracer.activeSpan().setBaggageItem("testBaggageItem", "value");
		
		Span span = tracer.buildSpan("getMemberMovies")
				.asChildOf(tracer.activeSpan())
				.withTag("member-id", id)
				.start();
		
		Member m = memberRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		List<MovieDTO> movies = new ArrayList<>();
		movieRentalRepo.findAllByMember(m).forEach(mr -> {
			String url = scheme + "://" + movieService + ":" + port + "/movies/" + mr.getMovieId();
			System.out.println("URL : " + url);
			try {
				RequestEntity<Void> req = RequestEntity.get(new URI(url)).accept(MediaType.APPLICATION_JSON).build();
				MovieDTO movie = restTemplate.exchange(req, MovieDTO.class).getBody();
				System.out.println("Rented Movie: " + movie);
	
				movies.add(movie);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		});

		span.finish();

		return movies;
	}
}
