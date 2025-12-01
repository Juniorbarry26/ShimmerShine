package com.Alsainey.ShimmerShine.controllers.subscription;

import com.Alsainey.ShimmerShine.entities.subscription.Subscription;
import com.Alsainey.ShimmerShine.entities.subscription.dtos.SubscriptionResponse;
import com.Alsainey.ShimmerShine.entities.subscription.enums.PlanName;
import com.Alsainey.ShimmerShine.services.subscription.SubscriptionService;
import com.Alsainey.ShimmerShine.user.User;
import com.Alsainey.ShimmerShine.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription Controller", description = "Manage user subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;

    @Operation(summary = "Change the logged-in user's subscription plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription changed successfully"),
            @ApiResponse(responseCode = "404", description = "No active subscription found")
    })
    @PutMapping("/change")
    public ResponseEntity<SubscriptionResponse> changeSubscription(
            @RequestParam PlanName newPlanName,
            Authentication authentication
    ) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Subscription updatedSubscription = subscriptionService.changeSubscription(currentUser, newPlanName);


        SubscriptionResponse response = SubscriptionResponse.builder()
                .id(updatedSubscription.getId())
                .planName(updatedSubscription.getSubscriptionPlan().getName())
                .startDate(updatedSubscription.getStartDate())
                .endDate(updatedSubscription.getEndDate())
                .status(updatedSubscription.getStatus())
                .remainingWashes(updatedSubscription.getRemainingWashes())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a subscription for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponse> createSubscription(Authentication authentication) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Subscription subscription = subscriptionService.saveSubscription(currentUser);


        SubscriptionResponse response = SubscriptionResponse.builder()
                .id(subscription.getId())
                .planName(subscription.getSubscriptionPlan().getName())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .status(subscription.getStatus())
                .remainingWashes(subscription.getRemainingWashes())
                .build();


        return ResponseEntity.status(201).body(response);
    }

//
//    @Operation(summary = "Use a car wash for the logged-in user's active subscription")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Car wash used successfully"),
//            @ApiResponse(responseCode = "404", description = "No active subscription found"),
//            @ApiResponse(responseCode = "400", description = "No washes remaining")
//    })
//    @PostMapping("/use")
//    public ResponseEntity<String> useCarWash(Authentication authentication) {
//
//        User currentUser = userRepository.findByEmail(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        subscriptionService.useCarWash(currentUser);
//        return ResponseEntity.ok("Car wash used successfully");
//    }



}
