package com.mikelduke.vhs.catalog;

import java.util.List;

import lombok.Data;

@Data
public class Movies {
    
    private List<Movie> movies;

    public Movie findOneById(int id) {
        return movies.stream().filter((m) -> m.getId() == id)
                .findFirst().orElseThrow(() -> new NotFoundException());
    }
}
