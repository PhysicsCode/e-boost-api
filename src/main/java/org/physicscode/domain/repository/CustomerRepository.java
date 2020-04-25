package org.physicscode.domain.repository;

import org.bson.types.ObjectId;
import org.physicscode.domain.entity.CustomerUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<CustomerUser, ObjectId> {

    Mono<CustomerUser> findByUserId(String userId);
}
