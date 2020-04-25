package org.physicscode.domain.repository;

import org.bson.types.ObjectId;
import org.physicscode.domain.entity.FreelancerUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FreelancerRepository  extends ReactiveMongoRepository<FreelancerUser, ObjectId> {

    Mono<FreelancerUser> findByUserId(String userId);
}
