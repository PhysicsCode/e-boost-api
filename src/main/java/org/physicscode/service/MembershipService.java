package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.repository.FreelancerRepository;
import org.physicscode.domain.repository.MembershipRepository;
import org.physicscode.dto.mapper.MembershipMapper;
import org.physicscode.dto.pojo.output.membership.MembershipPlanDTO;
import org.physicscode.dto.pojo.output.membership.MembershipPlanHolderDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final FreelancerRepository freelancerRepository;
    private final MembershipMapper membershipMapper;

    public Mono<MembershipPlanHolderDTO> retrieveMembershipPlans(String userId) {

        return Mono.zip(membershipRepository.findAll(), freelancerRepository.findByUserId(userId))
                .map(tuple -> {
                    MembershipPlanHolderDTO membershipPlanHolderDTO = new MembershipPlanHolderDTO();
                    membershipPlanHolderDTO.setActivePlans(tuple.getT2().getActiveMemberships().stream().map(membershipMapper::mapToMembershipHolder).collect(Collectors.toList()));
                    membershipPlanHolderDTO.setInactivePlans(tuple.getT1().stream().filter(availablePlan ->
                                membershipPlanHolderDTO.getActivePlans().stream().map(MembershipPlanDTO::getMembershipId).noneMatch(id -> id.equals(availablePlan.getMembershipId()))
                            ).map(membershipMapper::mapToMembershipHolder).collect(Collectors.toList()));

                    return membershipPlanHolderDTO;
                });
    }
}
