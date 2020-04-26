package org.physicscode.controller;

import lombok.RequiredArgsConstructor;
import org.physicscode.dto.pojo.input.EBoostUserCustomerRegisterDTO;
import org.physicscode.dto.pojo.input.EBoostUserFreelanceRegisterDTO;
import org.physicscode.dto.pojo.input.EBoostUserLoginCredentialsDTO;
import org.physicscode.dto.pojo.output.LoginFeedbackDTO;
import org.physicscode.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public Mono<ResponseEntity<LoginFeedbackDTO>> login(@RequestBody EBoostUserLoginCredentialsDTO eBoostUserLoginCredentialsDTO) {

        return authenticationService.loginUser(eBoostUserLoginCredentialsDTO)
                .map(ResponseEntity::ok);
    }

    @PostMapping(path = "/register/freelancer")
    public Mono<ResponseEntity<LoginFeedbackDTO>> register(@RequestBody EBoostUserFreelanceRegisterDTO eBoostUserFreelanceRegisterDTO) {

        return authenticationService.registerFreelancer(eBoostUserFreelanceRegisterDTO)
                .map(ResponseEntity::ok);
    }

    @PostMapping(path = "/register/customer")
    public Mono<ResponseEntity<LoginFeedbackDTO>> register(@RequestBody EBoostUserCustomerRegisterDTO eBoostUserCustomerRegisterDTO) {

        return authenticationService.registerCustomer(eBoostUserCustomerRegisterDTO)
                .map(ResponseEntity::ok);

    }
}
