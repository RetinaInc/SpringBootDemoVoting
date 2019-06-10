package com.eduonix.votingsystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduonix.votingsystem.repositories.CandidateRepo;
import com.eduonix.votingsystem.repositories.CitizenRepo;



import com.eduonix.votingsystem.entity.Candidate;
import com.eduonix.votingsystem.entity.Citizen;

@Controller
public class VotingController {
	
	public final Logger logger = Logger.getLogger(VotingController.class);
	@Autowired
	CitizenRepo citizenRepo;
	
	@Autowired
	CandidateRepo candidateRepo;
	
	/**
	@RequestMapping("/doAction")
	public String doAction() {
		Citizen ctzn = new Citizen((long)1, "Bob");
		citizenRepo.save(ctzn);
		Candidate cndt = new Candidate((long)1, "Tom");
		candidateRepo.save(cndt);
		return "Success";
		
	}*/
	
	@RequestMapping("/")
	public String goToVote() {
		logger.info("Returning vote.html file");
		return "vote.html";
	}
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam String name, Model model, HttpSession session) {
		logger.info("gettingg citizen from db");
		Citizen citizen = citizenRepo.findByName(name);
		logger.info("puttingg citizen in to session");
		session.setAttribute("citizen", citizen);
		if (!citizen.getHasVoted()) {
			logger.info("listing candidates for citizen to vote.");
			List <Candidate> candidates = candidateRepo.findAll();
			//model.addAllAttributes(candidates);
			model.addAttribute("candidates", candidates);
			return "/performVote.html";
		} else {
			return "/alreadyVoted.html";
		}
	}
	@RequestMapping("/voteFor")
	public String voteFor(@RequestParam Long id, HttpSession session) {
		
		Citizen ctzn = (Citizen) session.getAttribute("citizen");
		if (!ctzn.getHasVoted()) {
			ctzn.setHasVoted(true);
			Candidate cndt = candidateRepo.findById((long) id);
			logger.info("Voting for candidate" +cndt.getName());
			cndt.setNumOfVotes(cndt.getNumOfVotes()+1);
			candidateRepo.save(cndt);
			citizenRepo.save(ctzn);
			return "/voted.html";
		}
		return "/alreadyVoted.html";
		
		
	}
	
	/*
	@RequestMapping("/voteFor")
	public String voteFor(@RequestParam Long id) {
		Candidate cndt = candidateRepo.findById((long)id);
		cndt.setNumOfVotes(cndt.getNumOfVotes()+1);
		candidateRepo.save(cndt);
		
		return "voted";
		
	}*/

}
