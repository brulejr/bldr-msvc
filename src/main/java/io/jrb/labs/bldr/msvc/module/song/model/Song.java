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
package io.jrb.labs.bldr.msvc.module.song.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.jrb.labs.common.rest.DTO;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

/**
 * Defines a deep data transfer object for a song, including all its details.
 */
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Song.SongBuilder.class)
public class Song implements DTO<Song> {

    private final String id;

    private final SongType type;

    private final String title;

    private final List<String> authors;

    private final List<String> additionalTitles;

    private final List<String> themes;

    private final Map<String, List<String>> lyrics;

    private final List<String> lyricOrder;

    private final SongSource source;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SongBuilder {
    }

}
