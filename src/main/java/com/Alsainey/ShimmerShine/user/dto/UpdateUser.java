package com.Alsainey.ShimmerShine.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateUser {

    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters")
    private String profilePicture;

    @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
    private String firstname;

    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
    private String lastname;

    @Pattern(
            regexp = "^(\\+?[0-9]{7,15})$",
            message = "Phone number must contain 7–15 digits and may start with +"
    )
    private String phoneNumber;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Date of birth must be in the format YYYY-MM-DD"
    )
    private String dateOfBirth;
}
