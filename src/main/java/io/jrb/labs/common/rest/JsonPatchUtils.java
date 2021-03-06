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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.MediaType;

public class JsonPatchUtils {

    public static final String APPLICATION_JSON_PATCH_VALUE = "application/json-patch+json";
    public static final MediaType APPLICATION_JSON_PATCH = MediaType.valueOf(APPLICATION_JSON_PATCH_VALUE);

    private JsonPatchUtils() {}

    public static <T> T patch(
            final ObjectMapper objectMapper,
            final JsonPatch patch,
            final T targetBean,
            final Class<T> beanClass
    ) {
        try {
            final JsonNode target = objectMapper.convertValue(targetBean, JsonNode.class);
            final JsonNode patched = patch.apply(target);
            return objectMapper.convertValue(patched, beanClass);
        } catch (final JsonPatchException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
