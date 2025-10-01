package com.Main.Repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Main.Entity.Contact;

 
public interface ContactRepo extends JpaRepository<Contact, Integer> {
	
	@Query("from Contact as c where c.user.userid= :id")
	//current  page
	//contact per page  eg 5 ,10 etc
	public Page<Contact> findByUserId( @Param("id")int  userid,Pageable pageable);
}
