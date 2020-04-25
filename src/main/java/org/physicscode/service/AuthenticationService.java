package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.entity.CustomerUser;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.domain.entity.FreelancerUser;
import org.physicscode.constants.UserType;
import org.physicscode.domain.repository.CustomerRepository;
import org.physicscode.domain.repository.EboostAuthenticationUserRepository;
import org.physicscode.domain.repository.FreelancerRepository;
import org.physicscode.dto.mapper.UserMapper;
import org.physicscode.dto.pojo.input.EBoostUserCustomerRegisterDTO;
import org.physicscode.dto.pojo.input.EBoostUserFreelanceRegisterDTO;
import org.physicscode.dto.pojo.input.EBoostUserLoginCredentialsDTO;
import org.physicscode.dto.pojo.input.RegisterDTO;
import org.physicscode.dto.pojo.output.LoginFeedbackDTO;
import org.physicscode.exception.ErrorCode;
import org.physicscode.exception.ServiceException;
import org.physicscode.utils.SecurityUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final SecurityUtils securityUtils;
    private final EboostAuthenticationUserRepository eboostAuthenticationUserRepository;
    private final FreelancerRepository freelancerRepository;
    private final CustomerRepository customerRepository;
    private final UserMapper userMapper;
    private static final List<GrantedAuthority> CUSTOMER_AUTHORITY_LIST = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    private static final List<GrantedAuthority> FREELANCER_AUTHORITY_LIST = List.of(new SimpleGrantedAuthority("ROLE_FREELANCER"));
    private static final Map<UserType, List<GrantedAuthority>> AUTHORITY_MAP = Map.of(UserType.CUSTOMER, CUSTOMER_AUTHORITY_LIST,
                                                                                            UserType.FREELANCER, FREELANCER_AUTHORITY_LIST);


    public Mono<LoginFeedbackDTO> loginUser(EBoostUserLoginCredentialsDTO eBoostUserLoginCredentialsDTO) {

        return eboostAuthenticationUserRepository.findByEmailAndPassword(eBoostUserLoginCredentialsDTO.getEmail(),
                securityUtils.hashPass(eBoostUserLoginCredentialsDTO.getPassword()))
                .switchIfEmpty(Mono.error(new ServiceException(ErrorCode.USER_NOT_FOUND)))
                .map(user -> {
                    LoginFeedbackDTO loginFeedbackDTO = new LoginFeedbackDTO();
                    loginFeedbackDTO.setType(user.getUserType());
                    loginFeedbackDTO.setToken(securityUtils.createToken(user));
                    return loginFeedbackDTO;
                });

    }

    public Mono<LoginFeedbackDTO> registerFreelancer(EBoostUserFreelanceRegisterDTO eBoostUserFreelanceRegisterDTO) {

        EBoostAuthenticationUser user = buildAuthenticationUser(eBoostUserFreelanceRegisterDTO, UserType.FREELANCER);

        return eboostAuthenticationUserRepository.save(user)
                .flatMap(savedUser -> {
                    FreelancerUser freelancerUser = userMapper.mapToFreelancerUser(eBoostUserFreelanceRegisterDTO);
                    freelancerUser.setUserId(savedUser.getUserId());
                    freelancerUser.setEmail(savedUser.getEmail());
                    freelancerUser.setUsername(savedUser.getUsername());
                    return freelancerRepository.save(freelancerUser)
                            .thenReturn(savedUser);
                })
                .onErrorMap(DuplicateKeyException.class, e -> new ServiceException(ErrorCode.EMAIL_ALREADY_IN_USE))
                .map(savedUser -> {
                    LoginFeedbackDTO loginFeedbackDTO = new LoginFeedbackDTO();
                    loginFeedbackDTO.setType(savedUser.getUserType());
                    loginFeedbackDTO.setToken(securityUtils.createToken(savedUser));
                    return loginFeedbackDTO;
                });
    }

    public Mono<LoginFeedbackDTO> registerCustomer(EBoostUserCustomerRegisterDTO eBoostUserCustomerRegisterDTO) {

        EBoostAuthenticationUser user = buildAuthenticationUser(eBoostUserCustomerRegisterDTO, UserType.CUSTOMER);

        return eboostAuthenticationUserRepository.save(user)
                .flatMap(savedUser -> {
                    CustomerUser customerUser = userMapper.mapToCustomerUser(eBoostUserCustomerRegisterDTO);
                    customerUser.setUserId(savedUser.getUserId());
                    customerUser.setEmail(savedUser.getEmail());
                    customerUser.setUsername(savedUser.getUsername());
                    return customerRepository.save(customerUser)
                            .thenReturn(savedUser);
                })
                .onErrorMap(org.springframework.dao.DuplicateKeyException.class, e -> new ServiceException(ErrorCode.EMAIL_ALREADY_IN_USE))
                .map(savedUser -> {
                    LoginFeedbackDTO loginFeedbackDTO = new LoginFeedbackDTO();
                    loginFeedbackDTO.setType(savedUser.getUserType());
                    loginFeedbackDTO.setToken(securityUtils.createToken(savedUser));
                    return loginFeedbackDTO;
                });
    }


    EBoostAuthenticationUser buildAuthenticationUser(RegisterDTO registerDTO, UserType userType) {

        EBoostAuthenticationUser user = new EBoostAuthenticationUser();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(registerDTO.getEmail());
        user.setAuthorities(AUTHORITY_MAP.get(userType));
        user.setPassword(securityUtils.hashPass(registerDTO.getPassword()));
        user.setUsername(registerDTO.getUsername());
        user.setAuthenticated(true);
        user.setUserType(userType);
        user.setPrincipal(user);

        return user;
    }
}
