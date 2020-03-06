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
package io.jrb.labs.common.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomUtils;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class TestUtils {

    private TestUtils() {}

    public static final Function<Consumer<JSONObject>, String> JSON_BUILDER = (consumer) -> {
        final JSONObject jsonObject = new JSONObject();
        consumer.accept(jsonObject);
        return jsonObject.toString();
    };

    public static final Supplier<ObjectMapper> OBJECT_MAPPER = () -> {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    };

    public static final Supplier<String> RANDOM_UUID = () -> UUID.randomUUID().toString();

    public static <T> List<T> buildList(final int maxSize, final Consumer<ImmutableList.Builder<T>> consumer) {
        final int size = RandomUtils.nextInt(1, maxSize);
        final ImmutableList.Builder<T> builder = ImmutableList.builder();
        IntStream.range(1, size).forEach(i -> consumer.accept(builder));
        return builder.build();
    }

    public static <K,V> Map<K,V> buildMap(final int maxSize, final Consumer<ImmutableMap.Builder<K,V>> consumer) {
        final int size = RandomUtils.nextInt(1, maxSize);
        final ImmutableMap.Builder<K,V> builder = ImmutableMap.builder();
        IntStream.range(1, size).forEach(i -> consumer.accept(builder));
        return builder.build();
    }

}
