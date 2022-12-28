package com.library.interfaces.controllers;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

/**
 * @developer Shahidul Hasan
 * interface BasicController
 *
 */
public interface BasicController<E, ID> {
    Mono<?> create(E entity);
    Mono<ResponseEntity<E>> update(ID id, E entity);
    Mono<ResponseEntity<E>> view(ID id);
    Mono<Page<E>> list(int page, int size, Authentication authentication);
    Flux<E> all(Authentication authentication);
    Mono<ResponseEntity<E>> delete(ID id, E entity);
    Mono<?> createById(ID id, E entity);
}
