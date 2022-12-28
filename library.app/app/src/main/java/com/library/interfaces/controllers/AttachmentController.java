package com.library.interfaces.controllers;


import reactor.core.publisher.Mono;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

/**
 * @developer Shahidul Hasan
 * interface AttachmentController
 *
 */
public interface AttachmentController<E, ID> {
    Mono<E> viewAttachment(ID uid);
    Mono<ResponseEntity<byte[]>> viewPhoto(ID uid);
    Mono<ResponseEntity<Resource>> downloadAttachment(ID uid, ServerHttpResponse response);
    Mono<Page<E>> listAttachment(int page, int size, Authentication authentication, ID id);
    Mono<ResponseEntity<?>> addAttachment(FilePart filePart, ID uid, long lo, ServerHttpRequest httpRequest);
    Mono<ResponseEntity<E>> updateAttachment(ID id, E entity);
    Mono<ResponseEntity<E>> deleteAttachment(ID id, E entity);
}
