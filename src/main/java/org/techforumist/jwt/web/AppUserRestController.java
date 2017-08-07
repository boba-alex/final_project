package org.techforumist.jwt.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.techforumist.jwt.domain.user.Achievement;
import org.techforumist.jwt.domain.user.AchievementController;
import org.techforumist.jwt.domain.user.AppUser;
import org.techforumist.jwt.domain.Instruction;
import org.techforumist.jwt.domain.step.Step;
import org.techforumist.jwt.domain.comment.InstructionComment;
import org.techforumist.jwt.domain.comment.StepComment;
import org.techforumist.jwt.domain.step.StepBlock;
import org.techforumist.jwt.domain.user.UserProfile;
import org.techforumist.jwt.repository.AchievementRepository;
import org.techforumist.jwt.repository.AppUserRepository;
import org.techforumist.jwt.repository.InstructionRepository;
import org.techforumist.jwt.repository.StepRepository;

/**
 * Rest controller for authentication and user details. All the web services of
 * this rest controller will be only accessible for ADMIN users only
 * 
 * @author Sarath Muraleedharan
 *
 */
@RestController
@RequestMapping(value = "/api")
public class AppUserRestController {
	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private InstructionRepository instructionRepository;

	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private AchievementRepository achievementRepository;

	/**
	 * Web service for getting all the appUsers in the application.
	 * 
	 * @return list of all AppUser
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<AppUser> users() {
		return appUserRepository.findAll();
	}

	/**
	 * Web service for getting a user by his ID
	 * 
	 * @param id
	 *            appUser ID
	 * @return appUser
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<AppUser> userById(@PathVariable Long id) {
		AppUser appUser = appUserRepository.findOne(id);
		if (appUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
		}
	}

	/**
	 * Method for deleting a user by his ID
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AppUser> deleteUser(@PathVariable Long id) {
		AppUser appUser = appUserRepository.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		if (appUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		} else if (appUser.getUsername().equalsIgnoreCase(loggedUsername)) {
			throw new RuntimeException("You cannot delete your account");
		} else {
			appUserRepository.delete(appUser);
			return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
		}

	}

	/**
	 * Method for adding a appUser
	 * 
	 * @param appUser
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		return new ResponseEntity<AppUser>(appUserRepository.save(appUser), HttpStatus.CREATED);
	}

	/**
	 * Method for editing an user details
	 * 
	 * @param appUser
	 * @return modified appUser
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public AppUser updateUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null
				&& appUserRepository.findOneByUsername(appUser.getUsername()).getId() != appUser.getId()) {
			throw new RuntimeException("Username already exist");
		}
		return appUserRepository.save(appUser);
	}

	@RequestMapping(value = "/create-instruction", method = RequestMethod.GET)
	public List<AppUser> users2() {
		return appUserRepository.findAll();
	}

	@RequestMapping(value = "/create-instruction", method = RequestMethod.PUT)
	public void createInstruction(@RequestBody Instruction instruction) {

		System.out.println(instruction.getName() + " " + instruction.getCategory() +
				" " +instruction.getTags());

		AppUser appUser = appUserRepository.findOneByUsername(instruction.getCreatorName());
		System.out.println(appUser);
		if (appUser == null ){
			System.out.println("null");
			return;
		}

		appUser.getInstruction().add(instruction);
		chekAchivements(appUser.getUsername());
		appUserRepository.save(appUser);

		addStep(appUser.getInstruction().get(appUser.getInstruction().size()-1));
	}



	/*
	ALL METHODS BELOW USED FOR TESTING.
	THEY SHOULD BE REMOVED BEFORE THE PRODUCTION.
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	ALL METHODS BELOW USED FOR TESTING.
	THEY SHOULD BE REMOVED BEFORE THE PRODUCTION.
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	ALL METHODS BELOW USED FOR TESTING.
	THEY SHOULD BE REMOVED BEFORE THE PRODUCTION.
	 */

	private void chekAchivements(String userName){

		AppUser appUser = appUserRepository.findOneByUsername(userName);

        if (appUser.getInstruction().size() > 0 ){
        	String achievementName = "Bronze instruction creator";
        	if(appUser.findAchievementByname(achievementName)){

			} else {
				Achievement achievement = new Achievement();
				achievement.setUsername(userName);
				achievement.setName(achievementName);
				achievement.setDescription("For make 0 instruction !!!");
				achievement.setImageLink("http://res.cloudinary.com/demo/image/upload/pg_3/strawberries.png");
				appUser.getAchievements().add(achievement);
			}
        }

		if (appUser.getInstruction().size() > 0 ){
			String achievementName = "Silver instruction creator";
			if(appUser.findAchievementByname(achievementName)){

			} else {
				Achievement achievement = new Achievement();
				achievement.setUsername(userName);
				achievement.setName(achievementName);
				achievement.setDescription("For make 0 instruction !!");
				achievement.setImageLink("http://res.cloudinary.com/demo/image/upload/pg_3/strawberries.png");
				appUser.getAchievements().add(achievement);
			}
		}

		for (Instruction instruction:instructionRepository.findAllByCreatorName(userName)) {
			if (instruction.getSteps().size() > 0 ){
				String achievementName = "Bronze step creator";
				if(appUser.findAchievementByname(achievementName)){
					return;
				} else {
					Achievement achievement = new Achievement();
					achievement.setUsername(userName);
					achievement.setName(achievementName);
					achievement.setDescription("For make 0 steps !!!");
					achievement.setImageLink("http://res.cloudinary.com/demo/image/upload/w_200," +
							"h_200,c_crop,g_auto/fat_cat.jpg");
					appUser.getAchievements().add(achievement);
				}
			}
		}

		appUserRepository.save(appUser);
	}

	private void addStep(Instruction instruction){
		List<Step> steps = new ArrayList<>();
		Step step = new Step();
		step.setInstructionId(instruction.getId());
		step.setName("First super step 1");
		step.setCreatorName(instruction.getCreatorName());

		Step step1 = new Step();
		step1.setInstructionId(instruction.getId());
		step1.setName("Small step 1");
		step1.setCreatorName(instruction.getCreatorName());

		Step step2 = new Step();
		step2.setInstructionId(instruction.getId());
		step2.setName("Very big and bla bla " +
				"The term initialism uses a similar method, but the " +
				"word is pronounced letter by letterFirst super step 1");
		step2.setCreatorName(instruction.getCreatorName());

		steps.add(step);
		steps.add(step1);
		steps.add(step2);
		instruction.setSteps(steps);

		instructionRepository.save(instruction);
		addStepComments(instruction.getSteps().get(instruction.getSteps().size()-1));
		addStepBlocks(instruction.getSteps().get(instruction.getSteps().size()-1));
		addInstructionComments(instruction);
	}

	private void addInstructionComments(Instruction instruction){
		List<InstructionComment> instructionComments = new ArrayList<>();

		InstructionComment instructionComment = new InstructionComment();
		instructionComment.setInstructionId(instruction.getId());
		instructionComment.setText("All men must die !!");
		instructionComment.setCreatorName(instruction.getCreatorName());

		InstructionComment instructionComment1 = new InstructionComment();
		instructionComment1.setInstructionId(instruction.getId());
		instructionComment1.setText("text !!");
		instructionComment1.setCreatorName(instruction.getCreatorName());

		InstructionComment instructionComment2 = new InstructionComment();
		instructionComment2.setInstructionId(instruction.getId());
		instructionComment2.setText("Please note: An acronym (from Greek: " +
				"-acro = sharp, pointed; -onym = name) in its pure form denotes" +
				" a combination of letters (usually from an abbreviation) ");
		instructionComment2.setCreatorName(instruction.getCreatorName());

		instructionComments.add(instructionComment);
		instructionComments.add(instructionComment1);
		instructionComments.add(instructionComment2);
		instruction.setInstructionComments(instructionComments);

		instructionRepository.save(instruction);
	}

	private void addStepBlocks(Step step){

		List<StepBlock> stepBlocks = new ArrayList<>();

		StepBlock stepBlock = new StepBlock();
		stepBlock.setType("text");
		stepBlock.setText("Hamsters are rodents belonging to the subfamily Cricetinae." +
				" The subfamily contains about 25 species, classified in six or seven" +
				" genera. They have become established as popular small house pets," +
				" and, partly because they are easy to breed in captivity, hamsters are " +
				"often used as laboratory animals.\n" +
				"\n" +
				"In the wild, hamsters are crepuscular and remain underground during the " +
				"day to avoid being caught by predators. They feed primarily on seeds," +
				" fruits, and vegetation, and will occasionally eat burrowing insects." +
				" They have elongated cheek pouches extending to their shoulders in " +
				"which they carry food back to their burrows.");

		StepBlock stepBlock1 = new StepBlock();
		stepBlock1.setType("image");
		stepBlock1.setImageLink("http://res.cloudinary.com/demo/image/upload/front_face.png");

		StepBlock stepBlock2 = new StepBlock();
		stepBlock2.setType("video");
		stepBlock2.setVideoLink("video link ");

		stepBlocks.add(stepBlock);
		stepBlocks.add(stepBlock1);
		stepBlocks.add(stepBlock2);
		step.setStepBlocks(stepBlocks);

		stepRepository.save(step);
	}


	private void addStepComments(Step step){

		List<StepComment> stepComments = new ArrayList<>();

		StepComment stepComment = new StepComment();
		stepComment.setStepId(step.getId());
		stepComment.setText("You need to rest as well as work");
		stepComment.setCreatorName(step.getCreatorName());

		StepComment stepComment1 = new StepComment();
		stepComment1.setStepId(step.getId());
		stepComment1.setText("You");
		stepComment1.setCreatorName(step.getCreatorName());

		StepComment stepComment2 = new StepComment();
		stepComment2.setStepId(step.getId());
		stepComment2.setText("To use caps for the acronyms themselves " +
				"is generally not considered poor netiquette;");
		stepComment2.setCreatorName(step.getCreatorName());

		stepComments.add(stepComment);
		stepComments.add(stepComment1);
		stepComments.add(stepComment2);
		step.setStepComments(stepComments);

		stepRepository.save(step);
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public List<AppUser> profile() {
		return appUserRepository.findAll();
	}

	@RequestMapping(value = "/profile", method = RequestMethod.PUT)
	public void editProfile(@RequestBody UserProfile userProfile) {

		System.out.println(userProfile.getCity());
		AppUser appUser = appUserRepository.findOneByUsername(userProfile.getCreatorName());
		if (appUser == null ){
			System.out.println("null");
			return;
		}

		appUser.setUserProfile(userProfile);
//		chekAchivements(appUser.getUsername());
		appUserRepository.save(appUser);

//		addStep(appUser.getInstruction().get(appUser.getInstruction().size()-1));
	}

}