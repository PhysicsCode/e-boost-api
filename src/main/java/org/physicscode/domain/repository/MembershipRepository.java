package org.physicscode.domain.repository;

import org.physicscode.domain.entity.membership.MembershipEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class MembershipRepository {

    private final List<MembershipEntity> membershipEntityList;

    public MembershipRepository() {

        MembershipEntity membershipEntity1 = new MembershipEntity();
        membershipEntity1.setMembershipId("1");
        membershipEntity1.setDescription("1 Week");
        membershipEntity1.setPrice(new BigDecimal("15.00"));
        MembershipEntity membershipEntity2 = new MembershipEntity();
        membershipEntity2.setMembershipId("2");
        membershipEntity2.setDescription("2 Week");
        membershipEntity2.setPrice(new BigDecimal("15.00"));
        MembershipEntity membershipEntity3 = new MembershipEntity();
        membershipEntity3.setMembershipId("3");
        membershipEntity3.setDescription("Free trial");
        membershipEntity3.setPrice(new BigDecimal("0.00"));
        MembershipEntity membershipEntity4 = new MembershipEntity();
        membershipEntity4.setMembershipId("4");
        membershipEntity4.setDescription("Drop in session");
        membershipEntity4.setPrice(new BigDecimal("5.00"));


        membershipEntityList = new ArrayList<>();
        membershipEntityList.add(membershipEntity1);
        membershipEntityList.add(membershipEntity2);
        membershipEntityList.add(membershipEntity3);
        membershipEntityList.add(membershipEntity4);
    }

    public Mono<List<MembershipEntity>> findAll() {

        return Mono.just(new ArrayList<>(membershipEntityList));
    }
}
