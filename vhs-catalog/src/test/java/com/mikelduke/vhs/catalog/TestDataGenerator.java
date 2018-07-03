package com.mikelduke.vhs.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikelduke.jtest.data.DataType;
import com.mikelduke.jtest.data.JTestData;

public class TestDataGenerator {

	private static List<String> nouns = JTestData.get(DataType.NOUNS);
	private static List<String> verbs = JTestData.get(DataType.VERBS);

	private ThreadLocalRandom random = ThreadLocalRandom.current();
	
	public static void main(String[] args) {
		System.out.println("Generating Test Data");

		new TestDataGenerator().generate();
	}

	public void generate() {
		int count = 100;
		List<Movie> moviesList = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			String name = generateName();
			System.out.println(name);

			Movie movie = new Movie();
			movie.setId(i);
			movie.setName(name);
			moviesList.add(i, movie);
		}

		Movies movies = new Movies();
		movies.setMovies(moviesList);

		save(movies);
	}

	private void save(Movies movies) {
		ObjectMapper mapper = new ObjectMapper();

		File f = new File("src/main/resources/movies.json");
		if (f.exists()) {
			f.delete();
		}
		try {
			mapper.writeValue(f, movies);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String generateName() {
		int type = ThreadLocalRandom.current().nextInt(3);
		String name = null;

		String noun = capitolizeFirst(nouns.get(random.nextInt(nouns.size())));
		String verb = capitolizeFirst(verbs.get(random.nextInt(verbs.size())));

		if (type == 0) {
			name = noun;
		} else if (type == 1) {
			name = verb + " " + noun;
		} else if (type == 2) {
			name = noun + " " + verb;
		}

		if (random.nextBoolean()) {
			name = "The " + name;
		}

		return name;
	}

	private static String capitolizeFirst(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}
}
