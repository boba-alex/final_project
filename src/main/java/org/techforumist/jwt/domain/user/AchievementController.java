package org.techforumist.jwt.domain.user;

import org.springframework.stereotype.Controller;
import org.techforumist.jwt.repository.AppUserRepository;

import java.util.List;

public class AchievementController {


    public void chekAchivements(AppUserRepository appUserRepository, String userName){
        System.out.println("userName " + userName);
        AppUser appUser = appUserRepository.findOne(1L);
        System.out.println("userName " + appUser.getUsername());
//        if (appUser.getInstruction().size() > 0 ){
//            Achievement achievement = new Achievement();
//            achievement.setUsername(userName);
//            achievement.setName("Bronze instruction creator");
//            achievement.setDescription("For make 0 instruction !!!");
//            achievement.setImageLink("http://res.cloudinary.com/demo/image/upload/pg_3/strawberries.png");
//            appUser.getAchievements().add(achievement);
//            appUserRepository.save(appUser);
//
//            System.out.println("chekAchivements.save");
//        }

    }
}
