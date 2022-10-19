package com.example.userbackend;

import com.example.userbackend.entity.User;
import com.example.userbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserBackendApplicationTests {

	@Autowired
	private UserRepository userRepository;


	@Test
	void save_user() {
		User user = User.builder()
				.name("Nguyễn Văn A")
				.email("a@gmail.com")
				.phone("0988887777")
				.address("Thành phố Hà Nội")
				.password("111")
				.build();

		User user1 = User.builder()
				.name("Nguyễn Văn B")
				.email("b@gmail.com")
				.phone("0988886666")
				.address("Thành phố Hải Phòng")
				.password("222")
				.build();

		User user2 = User.builder()
				.name("Nguyễn Văn C")
				.email("c@gmail.com")
				.phone("09888855555")
				.address("Thành phố Quảng Ninh")
				.password("333")
				.build();

		userRepository.saveAll(List.of(user, user1, user2));
	}

}
