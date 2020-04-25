package org.physicscode.domain.repository;

import org.bson.types.ObjectId;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

public interface EboostAuthenticationUserRepository extends ReactiveMongoRepository<EBoostAuthenticationUser, ObjectId> {

    Mono<EBoostAuthenticationUser> findByEmailAndPassword(String email, String password);
    Mono<EBoostAuthenticationUser> findByUserId(String userId);
}
