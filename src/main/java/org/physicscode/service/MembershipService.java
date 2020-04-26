package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.entity.FreelancerUser;
import org.physicscode.domain.entity.membership.MembershipEntity;
import org.physicscode.domain.repository.FreelancerRepository;
import org.physicscode.domain.repository.MembershipRepository;
import org.physicscode.dto.mapper.MembershipMapper;
import org.physicscode.dto.pojo.output.membership.MembershipPlanDTO;
import org.physicscode.dto.pojo.output.membership.MembershipPlanHolderDTO;
import org.physicscode.exception.ErrorCode;
import org.physicscode.exception.ServiceException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final FreelancerRepository freelancerRepository;
    private final MembershipMapper membershipMapper;

    public Mono<Tuple2<List<MembershipEntity>, FreelancerUser>> retrieveTupleFromRepositories(String userId) {

        return Mono.zip(membershipRepository.findAll(), freelancerRepository.findByUserId(userId));
    }

    public Mono<MembershipPlanHolderDTO> retrieveMembershipPlans(String userId) {

        return this.calculateMembershipPlans(retrieveTupleFromRepositories(userId));

    }

    public Mono<MembershipPlanHolderDTO> calculateMembershipPlans(Mono<Tuple2<List<MembershipEntity>, FreelancerUser>> tuple2Mono) {

        return tuple2Mono.map(tuple -> {
            MembershipPlanHolderDTO membershipPlanHolderDTO = new MembershipPlanHolderDTO();

            membershipPlanHolderDTO.setActivePlans(tuple.getT2().getActiveMemberships().stream()
                    .map(membershipMapper::mapToMembershipHolder)
                    .sorted(Comparator.comparing(MembershipPlanDTO::getMembershipId))
                    .collect(Collectors.toList()));

            membershipPlanHolderDTO.setInactivePlans(tuple.getT1().stream().filter(availablePlan ->
                    membershipPlanHolderDTO.getActivePlans().stream()
                            .map(MembershipPlanDTO::getMembershipId)
                            .noneMatch(id -> id.equals(availablePlan.getMembershipId()))
            ).map(membershipMapper::mapToMembershipHolder)
                    .sorted(Comparator.comparing(MembershipPlanDTO::getMembershipId))
                    .collect(Collectors.toList()));

            return membershipPlanHolderDTO;
        });
    }

    public Mono<MembershipPlanHolderDTO> triggerMembershipPlanStatus(String userId, Boolean trigger, String membershipId) {

        return calculateMembershipPlans(
                retrieveTupleFromRepositories(userId)
                        .flatMap(tuple -> {
                            if (trigger) {
                                MembershipEntity membershipEntityToActivate = tuple.getT1().stream().filter(membership -> membership.getMembershipId().equals(membershipId)).findFirst().orElseThrow(() -> new ServiceException(ErrorCode.INVALID_MEMBERSHIP_PROVIDED));
                                if (!tuple.getT2().getActiveMemberships().contains(membershipEntityToActivate)) {
                                    tuple.getT2().getActiveMemberships().add(membershipEntityToActivate);
                                }
                            } else {
                                tuple.getT2().getActiveMemberships().removeIf(membershipEntity -> membershipEntity.getMembershipId().equals(membershipId));
                            }

                            return Mono.zip(Mono.just(tuple.getT1()), freelancerRepository.save(tuple.getT2()));
                        })
        );
    }
}
