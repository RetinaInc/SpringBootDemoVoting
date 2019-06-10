package com.eduonix.votingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduonix.votingsystem.entity.Candidate;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
	public Candidate findById(long id);
	//public Candidate findByName(String name);
	
}
