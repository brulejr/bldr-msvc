package io.jrb.labs.bldr.msvc.module.song.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.jrb.labs.common.test.TestUtils.RANDOM_UUID;
import static io.jrb.labs.common.test.TestUtils.buildList;
import static io.jrb.labs.common.test.TestUtils.buildMap;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SongEntityTest {

    private static final String ID = RANDOM_UUID.get();
    private static final String TITLE = randomAlphanumeric(10, 25);
    private static final SongType TYPE = SongType.NORMAL;
    private static final List<String> AUTHORS = buildList(3, (builder) -> builder.add(randomAlphanumeric(3, 8)));
    private static final List<String> ADDITIONAL_TITLES = buildList(3, (builder) -> builder.add(randomAlphanumeric(3, 8)));
    private static final List<String> THEMES = buildList(3, (builder) -> builder.add(randomAlphanumeric(3, 8)));
    private static final List<String> LYRIC_ORDER = buildList(3, (builder) -> builder.add(randomAlphanumeric(3, 8)));
    private static final Map<String,List<String>> LYRICS = buildMap(3, (builder) -> {
        final List<String> stanza = buildList(3, (b) -> b.add(randomAlphanumeric(3, 8)));
        builder.put(randomAlphanumeric(3, 8), stanza);
    });

    @Test
    void shouldCreateBeanFromCode() {
        final SongEntity songEntity = SongEntity.builder()
                .id(ID)
                .title(TITLE)
                .type(TYPE)
                .authors(AUTHORS)
                .additionalTitles(ADDITIONAL_TITLES)
                .themes(THEMES)
                .lyricOrder(LYRIC_ORDER)
                .lyrics(LYRICS)
                .build();
        assertAll("song",
                () -> assertEquals(ID, songEntity.getId()),
                () -> assertEquals(TITLE, songEntity.getTitle()),
                () -> assertEquals(TYPE, songEntity.getType()),
                () -> assertEquals(AUTHORS, songEntity.getAuthors()),
                () -> assertEquals(ADDITIONAL_TITLES, songEntity.getAdditionalTitles()),
                () -> assertEquals(THEMES, songEntity.getThemes()),
                () -> assertEquals(LYRIC_ORDER, songEntity.getLyricOrder()),
                () -> assertEquals(LYRICS, songEntity.getLyrics())
        );
    }

    @Test
    void shouldCloneBeanFromCode() {
        final SongEntity songEntity1 = SongEntity.builder()
                .id(ID)
                .title(TITLE)
                .type(TYPE)
                .authors(AUTHORS)
                .additionalTitles(ADDITIONAL_TITLES)
                .themes(THEMES)
                .lyricOrder(LYRIC_ORDER)
                .lyrics(LYRICS)
                .build();
        final SongEntity songEntity2 = songEntity1.withId(RANDOM_UUID.get());
        assertAll("songEntity2",
                () -> assertNotEquals(songEntity1.getId(), songEntity2.getId()),
                () -> assertEquals(songEntity1.getTitle(), songEntity2.getTitle()),
                () -> assertEquals(songEntity1.getType(), songEntity2.getType()),
                () -> assertEquals(songEntity1.getAuthors(), songEntity2.getAuthors()),
                () -> assertEquals(songEntity1.getAdditionalTitles(), songEntity2.getAdditionalTitles()),
                () -> assertEquals(songEntity1.getThemes(), songEntity2.getThemes()),
                () -> assertEquals(songEntity1.getLyricOrder(), songEntity2.getLyricOrder()),
                () -> assertEquals(songEntity1.getLyrics(), songEntity2.getLyrics())
        );
    }

}
