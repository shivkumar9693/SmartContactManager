package com.Main.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Main.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	@Query("select e from User e where e.email = :email")
	public User getUserByUserName(@Param("email") String email);

}
