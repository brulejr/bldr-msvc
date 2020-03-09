/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Jon Brule <brulejr@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.jrb.labs.common.rest;

import io.jrb.labs.common.crud.Entity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public interface ICrudHandler<E extends Entity<E>, D extends DTO<D>, M extends DTO<M>> {

    Mono<ServerResponse> createEntity(ServerRequest request);

    Mono<ServerResponse> deleteEntity(ServerRequest request);

    Mono<ServerResponse> getEntity(ServerRequest request);

    Mono<ServerResponse> patchEntity(ServerRequest request);

    Mono<ServerResponse> retrieveEntities(ServerRequest request);

    Mono<ServerResponse> updateEntity(ServerRequest request);

    default RouterFunction<ServerResponse> createCrudEndpoints(
            final String baseResource,
            final String resourceId,
            final ICrudHandler<E, D, M> handler
    ) {
        final String individualResource = baseResource + "/{" + resourceId + "}";
        return route(
                POST(baseResource)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::createEntity
        ).andRoute(
                DELETE(individualResource)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::deleteEntity
        ).andRoute(
                GET(individualResource)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::getEntity
        ).andRoute(
                GET(baseResource)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::retrieveEntities
        ).andRoute(
                PATCH(individualResource)
                        .and(RequestPredicates.accept(JsonPatchUtils.APPLICATION_JSON_PATCH)),
                handler::patchEntity
        ).andRoute(
                PUT(individualResource)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::updateEntity
        );
    }

}
