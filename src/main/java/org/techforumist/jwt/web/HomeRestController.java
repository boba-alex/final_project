package org.techforumist.jwt.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.techforumist.jwt.domain.user.AppUser;
import org.techforumist.jwt.domain.Instruction;
import org.techforumist.jwt.domain.user.UserProfile;
import org.techforumist.jwt.domain.step.Step;
import org.techforumist.jwt.repository.AppUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.techforumist.jwt.repository.InstructionRepository;

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
		userProfile.setReserve(appUser.getUsername());
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

	@RequestMapping(value = "/instructions", method = RequestMethod.GET)
	public List<Instruction> users() {
		return instructionRepository.findAll();
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


	@RequestMapping(value = "/test{id}", method = RequestMethod.GET)
	public Instruction test(@PathVariable String id)
	{
		System.out.println(" " + id);
		return null;
	}
}
