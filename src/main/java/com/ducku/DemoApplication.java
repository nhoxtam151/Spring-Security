package com.ducku;

import com.ducku.entity.User;
import com.ducku.repository.MyUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories
public class DemoApplication {

  @Autowired
  private MyUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

	@PostConstruct
	public void initUsers() {
		List<User> users = Stream.of(
				new User("nhoxtam151", passwordEncoder.encode("123"), "ROLE_ADMIN"),
				new User("kudeptrai", passwordEncoder.encode("123"), "ROLE_SALES"),
				new User("kutaolao", passwordEncoder.encode("123"), "ROLE_MARKETING")
				).collect(Collectors.toList());
		userRepository.saveAll(users);
	}

}
