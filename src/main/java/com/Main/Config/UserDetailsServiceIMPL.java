package com.Main.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Main.Entity.User;
import com.Main.Repo.UserRepo;

public class UserDetailsServiceIMPL implements   UserDetailsService{
	@Autowired
	private UserRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= repo.getUserByUserName(username);
		if(user==null) {
			 throw new UsernameNotFoundException("User Not Found!!");
		}
		CustomUSerDetails customUSerDetails=new CustomUSerDetails(user);
		return customUSerDetails;
	}

}
