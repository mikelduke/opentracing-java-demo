package com.mikelduke.vhs.members;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mikelduke.jtest.data.DataType;
import com.mikelduke.jtest.data.JTestData;
import com.mikelduke.vhs.members.model.Address;
import com.mikelduke.vhs.members.model.AddressRepo;
import com.mikelduke.vhs.members.model.Member;
import com.mikelduke.vhs.members.model.MemberRepo;
import com.mikelduke.vhs.members.model.MovieRental;
import com.mikelduke.vhs.members.model.MovieRentalRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DatabaseLoader {

    private static List<String> nouns = JTestData.get(DataType.NOUNS);
	private static List<String> firstNames = JTestData.get(DataType.FIRST_NAMES);
	private static List<String> lastNames = JTestData.get(DataType.LAST_NAMES);
	private static List<String> streetNames = JTestData.get(DataType.STREET_NAMES);
	private static List<String> addressType = JTestData.get(DataType.ADDRESS_TYPE);

	private ThreadLocalRandom random = ThreadLocalRandom.current();

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    MovieRentalRepo movieRentalRepo;

    @Autowired
    AddressRepo addressRepo;

    @Bean
	@Order(1)
	public ApplicationRunner loadDB() {
		return (args) -> {
            generate();
		};
    }
    
    public void generate() {
		int count = 100;

		for (int i = 0; i < count; i++) {
			String name = firstNames.get(random.nextInt(firstNames.size())) + " " 
					+ lastNames.get(random.nextInt(lastNames.size()));

			Member m = new Member();
			m.setMemberName(name);
			m.setEmail(name.replace(" ", "") + "@" + nouns.get(random.nextInt(nouns.size())) + ".com");
			
			Address address = new Address();
			String street = capitolizeFirst(streetNames.get(random.nextInt(streetNames.size()))) + " "
					+ capitolizeFirst(addressType.get(random.nextInt(addressType.size())));

			address.setStreetAddress(random.nextInt(1000) + " " + street);
			address.setZip(random.nextInt(1, 99999) + "");

			addressRepo.save(address);
			m.setAddress(address);

			memberRepo.save(m);

			List<MovieRental> rentals = new ArrayList<>();
			for (int j = 0; j < random.nextInt(1, 5); j++) {
				MovieRental mr = new MovieRental();
				mr.setMovieId(random.nextInt(100));
				mr.setMember(m);
				rentals.add(mr);
			}
			movieRentalRepo.saveAll(rentals);
		}
	}

	private static String capitolizeFirst(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}
}
