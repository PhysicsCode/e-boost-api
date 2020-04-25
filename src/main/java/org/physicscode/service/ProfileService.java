package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.repository.CustomerRepository;
import org.physicscode.domain.repository.FreelancerRepository;
import org.physicscode.dto.mapper.ProfileMapper;
import org.physicscode.dto.pojo.output.CustomerUserProfileDTO;
import org.physicscode.dto.pojo.output.FreelancerUserProfileDTO;
import org.physicscode.exception.ErrorCode;
import org.physicscode.exception.ServiceException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final CustomerRepository customerRepository;
    private final FreelancerRepository freelancerRepository;
    private final ProfileMapper profileMapper;

    public Mono<FreelancerUserProfileDTO> retrieveFreelancerProfile(String userId) {

        return freelancerRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new ServiceException(ErrorCode.EMPTY_INFORMATION_FOR_USER)))
                .map(profileMapper::mapToFreelancerProfileDTO);
    }

    public Mono<CustomerUserProfileDTO> retrieveCustomerProfile(String userId) {

        return customerRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new ServiceException(ErrorCode.EMPTY_INFORMATION_FOR_USER)))
                .map(profileMapper::mapToCustomerProfile);
    }
}
