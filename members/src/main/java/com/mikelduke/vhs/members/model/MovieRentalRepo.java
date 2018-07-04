package com.mikelduke.vhs.members.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRentalRepo extends JpaRepository<MovieRental, Integer> {
    List<MovieRental> findAllByMember(Member member);
}
