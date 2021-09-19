package api.auth.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import api.auth.dto.CustomUserDto;
import api.auth.entity.User;
import api.auth.repository.UserRepository;

@Component("userDetailService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		try {
			Optional<User> user = userRepo.findByUsername(username);
			if (!user.isPresent()) {
				throw new UsernameNotFoundException("User " + username + " cannot be found");
			}

			List<GrantedAuthority> authorities = user.get().getUserRoles().stream().map((x) -> {
				String roleCode = x.getRole().getRoleCode();
				return new SimpleGrantedAuthority(roleCode);
			}).collect(Collectors.toList());

			UserDetails result = new CustomUserDto(user.get().getUsername(), user.get().getPassword(), authorities,
					user.get().getNickname());

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}