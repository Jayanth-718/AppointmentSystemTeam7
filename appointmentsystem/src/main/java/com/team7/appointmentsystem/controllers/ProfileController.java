package com.team7.appointmentsystem.controllers;

import com.team7.appointmentsystem.entity.Users;
import com.team7.appointmentsystem.exceptions.UserNotFoundException;
import com.team7.appointmentsystem.models.PasswordObject;
import com.team7.appointmentsystem.models.ProfileModel;
import com.team7.appointmentsystem.repository.UserRepository;
import com.team7.appointmentsystem.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserRepository userRepo;


    @Autowired
    private ProfileService profileService;

    @PostMapping("/profile/{userId}/changePassword")
    public ResponseEntity<PasswordObject> changePassword(@RequestBody PasswordObject object, @PathVariable Long userId) {
        PasswordObject result = profileService.changePassword(object.getOldPassword(), object.getNewPassword(), userId);
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/profile/uploadPhoto/{userId}")
    public ResponseEntity<String> saveProfile(@RequestParam("profileImg") MultipartFile multipartFile,
                                             @PathVariable long userId) throws IOException
    {
        String profileImg = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Users user = userRepo.findByUserid(userId);
        String uploadDir = "./profile-image/" + user.getUserid();
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(profileImg);
            user.setProfileImage(filePath.toFile().getAbsolutePath());
            Users savedUser = userRepo.save(user);
//            System.out.println(filePath.toString());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(filePath.toFile().getAbsolutePath());
            System.out.println(savedUser.getProfileImage());
        }catch (IOException e) {
            throw  new IOException("Could not save uploaded file:" + profileImg);
        }
        return ResponseEntity.ok("File uploaded Successfully");
    }
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Users>  getProfileData(@PathVariable long userId) throws UserNotFoundException {
        Users user = userRepo.findByUserid(userId);
        if(user == null) {
            throw new UserNotFoundException("User does not Exists");
        }else{
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping("/profile/save/{userId}")
    public ResponseEntity<String> saveProfile(@RequestBody ProfileModel profile, @PathVariable Long userId )
            throws UserNotFoundException{
        Users user = profileService.getUser(userId);
        if(user == null) {
            throw new UserNotFoundException("User does not Exist");
        }else{
            user.setFirstName(profile.getFirstName());
            user.setEmail(profile.getEmail());
            user.setMobileNumber(profile.getContact());
            user.setLastName(profile.getLastName());
            userRepo.save(user);
            return ResponseEntity.ok("Saved Changes");
        }
    }
}
