package com.smoopay.sts.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.entity.client.Client;

@Service
@Transactional(readOnly = true)
public class CustomUserSecurityService implements UserDetailsService {

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client = clientCustomRepository.findClientByLogin(username);
		if (client != null) {
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			return new User(client.getAuthdata().getLogin(), client.getAuthdata().getPassword().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
					getGrantedAuthorities(Arrays.asList(client.getAuthdata().getRole().getValue().split(","))));
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}