package org.techforumist.jwt.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.techforumist.jwt.domain.comment.InstructionComment;
import org.techforumist.jwt.domain.comment.StepComment;
import org.techforumist.jwt.domain.step.StepBlock;
import org.techforumist.jwt.domain.user.AppUser;
import org.techforumist.jwt.domain.Instruction;
import org.techforumist.jwt.domain.user.UserProfile;
import org.techforumist.jwt.domain.step.Step;
import org.techforumist.jwt.repository.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * All web services in this controller will be available for all the users
 *
 * @author Sarath Muraleedharan
 *
 */
@RestController
public class HomeRestController {
	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private InstructionRepository instructionRepository;

	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private StepBlockRepository stepBlockRepository;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		List<String> roles = new ArrayList<>();
		List<Instruction> instructions = new ArrayList<>();
		Instruction instruction = new Instruction("Amazing instruction from " + appUser.getUsername());
		instruction.setCreatorName(appUser.getUsername());
		instructions.add(instruction);

		UserProfile userProfile = new UserProfile();
		userProfile.setAppUser(appUser);
		roles.add("USER");

		appUser.setRoles(roles);
		appUser.setUserProfile(userProfile);
		appUser.setInstruction(instructions);
		return new ResponseEntity<AppUser>(appUserRepository.save(appUser), HttpStatus.CREATED);
	}

	@RequestMapping("/user")
	public AppUser user(Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		return appUserRepository.findOneByUsername(loggedUsername);
	}


	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) throws IOException {
		String token = null;
		AppUser appUser = appUserRepository.findOneByUsername(username);
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		if (appUser != null && appUser.getPassword().equals(password)) {
			token = Jwts.builder().setSubject(username).claim("roles", appUser.getRoles()).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
			tokenMap.put("token", token);
			tokenMap.put("user", appUser);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
		} else {
			tokenMap.put("token", null);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/view-thread/{id}", method = RequestMethod.POST)
	public ResponseEntity<StepComment> createInstructionComment(@PathVariable Long id, @RequestBody InstructionComment instructionComment) {
		Instruction instruction = instructionRepository.findOne(id);
		System.out.println("here");
		System.out.println("id: " + id);
		System.out.println("Creator: " + instructionComment.getCreatorName());
		System.out.println("Creator: " + instructionComment.getText());
		System.out.println(instructionComment.getCreatorName().equals(""));

		if(!instructionComment.getCreatorName().equals("")){
			instruction.getInstructionComments().add(instructionComment);
			instructionRepository.save(instruction);
		}
		return null;
	}

	@RequestMapping(value = "/step/{id}", method = RequestMethod.POST)
	public ResponseEntity<StepComment> createStepComment(@PathVariable Long id, @RequestBody StepComment stepComment) {
		Step step = stepRepository.findOne(id);
		System.out.println("here2");
		System.out.println("id: " + id);
		System.out.println("Creator: " + stepComment.getCreatorName());
		System.out.println("Creator: " + stepComment.getText());
		System.out.println(stepComment.getCreatorName().equals(""));

		if(!stepComment.getCreatorName().equals("")){
			step.getStepComments().add(stepComment);
			stepRepository.save(step);
		}
		return null;
	}


	@RequestMapping(value = "/instructions", method = RequestMethod.GET)
	public List<Instruction> users() {
		return instructionRepository.findAll();
	}

	@RequestMapping(value = "/view-thread/{id}", method = RequestMethod.PUT)
	public void createStep(@PathVariable Long id, @RequestBody Step step) {
		Instruction instruction = instructionRepository.findOne(id);
		if(step.getCreatorName() != null) {
			if (step.getCreatorName().equals(instruction.getCreatorName())) {
				step.setInstructionId(instruction.getId());
				instruction.getSteps().add(step);
				instructionRepository.save(instruction);
			} else {
				AppUser appUser = appUserRepository.findOneByUsername(step.getCreatorName());
				if (appUser.getRoles().contains("ADMIN")) {
					step.setInstructionId(instruction.getId());
					instruction.getSteps().add(step);
					instructionRepository.save(instruction);
				}
			}
		}
	}

	@RequestMapping(value = "/view-thread/{id}/{stepID}/{userID}", method = RequestMethod.DELETE)
	public void deleteStep(@PathVariable Long id, @PathVariable Long stepID, @PathVariable Long userID) {
		Step step = stepRepository.findOne(stepID);
		Instruction instruction = instructionRepository.findOne(id);
		AppUser appUser = appUserRepository.findOne(userID);
		if(instruction.getCreatorName().equals(appUser.getUsername())){
			List<Step> steps = instruction.getSteps();
			steps.remove(step);
			instruction.setSteps(steps);
			instructionRepository.save(instruction);
			stepRepository.delete(step);
			return;
		} else if(appUser.getRoles().contains("ADMIN")){
			List<Step> steps = instruction.getSteps();
			steps.remove(step);
			instruction.setSteps(steps);
			instructionRepository.save(instruction);
			stepRepository.delete(step);;
		}
	}

	@RequestMapping(value = "/step/{id}/{userID}", method = RequestMethod.PUT)
	public void createBlock(@PathVariable Long id, @PathVariable Long userID, @RequestBody StepBlock stepBlock) {
		Step step = stepRepository.findOne(id);
		AppUser appUser = appUserRepository.findOne(userID);
		if (step.getCreatorName().equals(appUser.getUsername())){
			step.getStepBlocks().add(stepBlock);
			stepRepository.save(step);
			return;
		} else if (appUser.getRoles().contains("ADMIN")){
			step.getStepBlocks().add(stepBlock);
			stepRepository.save(step);
		}
	}

	@RequestMapping(value = "/step/{stepID}/{stepBlockID}/{userID}", method = RequestMethod.DELETE)
	public void deleteStepBlock(@PathVariable Long stepID, @PathVariable Long stepBlockID, @PathVariable Long userID) {
		StepBlock stepBlock = stepBlockRepository.findOne(stepBlockID);
		Step step = stepRepository.findOne(stepID);
		AppUser appUser = appUserRepository.findOne(userID);
		if(step.getCreatorName().equals(appUser.getUsername())){
			List<StepBlock> stepBlocks = step.getStepBlocks();
			stepBlocks.remove(stepBlock);
			step.setStepBlocks(stepBlocks);
			stepRepository.save(step);
			stepBlockRepository.delete(stepBlock);
			return;
		} else if(appUser.getRoles().contains("ADMIN")){
			List<StepBlock> stepBlocks = step.getStepBlocks();
			stepBlocks.remove(stepBlock);
			step.setStepBlocks(stepBlocks);
			stepRepository.save(step);
			stepBlockRepository.delete(stepBlock);
		}
	}

	@RequestMapping(value = "/view-thread/{id}", method = RequestMethod.GET)
	public Instruction userss(@PathVariable Long id) {
		return instructionRepository.findOne(id);
	}

	@RequestMapping(value = "/step/{id}", method = RequestMethod.GET)
	public Step step(@PathVariable String id) {
		Instruction instruction = instructionRepository.findOne(Long.parseLong(id.split(" ")[0]));
		return instruction.getSteps().get(Integer.parseInt(id.split(" ")[1])-1);
	}

	@RequestMapping(value = "/view-profile/{id}", method = RequestMethod.GET)
	public AppUser profile(@PathVariable Long id) {
		System.out.println("view-profile");
		return appUserRepository.findOne(id);
	}


	@RequestMapping(value = "/test{id}", method = RequestMethod.GET)
	public Instruction test(@PathVariable String id)
	{
		System.out.println(" " + id);
		return null;
	}
}
