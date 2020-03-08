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
package io.jrb.labs.bldr.msvc.module.song;

import io.jrb.labs.bldr.msvc.module.song.model.Song;
import io.jrb.labs.bldr.msvc.module.song.model.SongEntity;
import io.jrb.labs.bldr.msvc.module.song.model.SongType;

import java.util.Optional;

import static io.jrb.labs.common.test.TestUtils.RANDOM_STRING_LIST;
import static io.jrb.labs.common.test.TestUtils.RANDOM_STRING_MAP;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class SongTestUtils {

    public static final String DETACHED = null;

    private SongTestUtils() {}

    public static Song createSong() { return createSong(DETACHED); }

    public static Song createSong(final String id) {
        final Song.SongBuilder builder = Song.builder()
                .title(randomAlphanumeric(10, 25))
                .type(SongType.NORMAL)
                .authors(RANDOM_STRING_LIST.apply(3))
                .additionalTitles(RANDOM_STRING_LIST.apply(3))
                .themes(RANDOM_STRING_LIST.apply(3))
                .lyricOrder(RANDOM_STRING_LIST.apply(3))
                .lyrics(RANDOM_STRING_MAP.apply(3));
        Optional.ofNullable(id).ifPresent(builder::id);
        return builder.build();
    }

    public static SongEntity createSongEntity() { return createSongEntity(DETACHED); }

    public static SongEntity createSongEntity(final String id) {
        final SongEntity.SongEntityBuilder builder = SongEntity.builder()
                .title(randomAlphanumeric(10, 25))
                .type(SongType.NORMAL)
                .authors(RANDOM_STRING_LIST.apply(3))
                .additionalTitles(RANDOM_STRING_LIST.apply(3))
                .themes(RANDOM_STRING_LIST.apply(3))
                .lyricOrder(RANDOM_STRING_LIST.apply(3))
                .lyrics(RANDOM_STRING_MAP.apply(3));
        Optional.ofNullable(id).ifPresent(builder::id);
        return builder.build();
    }

}
