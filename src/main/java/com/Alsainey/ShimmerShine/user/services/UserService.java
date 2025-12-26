package com.Alsainey.ShimmerShine.user.services;

import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import com.Alsainey.ShimmerShine.user.dto.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private User findUserById(User currentuser) {
        return userRepository.findById(currentuser.getId())
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User updateUser(User currentUser, UpdateUser updatedUser) {
        User user = findUserById(currentUser);
        if (updatedUser.getProfilePicture() != null ) {
            user.setProfilePicture(updatedUser.getProfilePicture());
        }
        if (updatedUser.getFirstname() != null ){
            user.setFirstname(updatedUser.getFirstname());
        }
        if (updatedUser.getLastname() != null ) {
            user.setPhoneNumber(updatedUser.getLastname());
        }
        if (updatedUser.getPhoneNumber() != null ) {
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getDateOfBirth() != null ) {
            user.setDateOfBirth(LocalDate.parse(updatedUser.getDateOfBirth()));
        }
        return userRepository.save(user);

    }
}
