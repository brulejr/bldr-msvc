package io.jrb.labs.bldr.msvc.module.song.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.jrb.labs.common.test.TestUtils.JSON_BUILDER;
import static io.jrb.labs.common.test.TestUtils.OBJECT_MAPPER;
import static io.jrb.labs.common.test.TestUtils.RANDOM_UUID;
import static io.jrb.labs.common.test.TestUtils.buildList;
import static io.jrb.labs.common.test.TestUtils.buildMap;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SongTest {

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
        final Song song = Song.builder()
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
                () -> assertEquals(ID, song.getId()),
                () -> assertEquals(TITLE, song.getTitle()),
                () -> assertEquals(TYPE, song.getType()),
                () -> assertEquals(AUTHORS, song.getAuthors()),
                () -> assertEquals(ADDITIONAL_TITLES, song.getAdditionalTitles()),
                () -> assertEquals(THEMES, song.getThemes()),
                () -> assertEquals(LYRIC_ORDER, song.getLyricOrder()),
                () -> assertEquals(LYRICS, song.getLyrics())
        );
    }

    @Test
    void shouldCreateBeanFromJson() throws JsonProcessingException {
        final String json = JSON_BUILDER.apply((jo) -> {
            jo.put("id", ID);
            jo.put("title", TITLE);
            jo.put("type", TYPE);
            jo.put("authors", AUTHORS);
            jo.put("additionalTitles", ADDITIONAL_TITLES);
            jo.put("themes", THEMES);
            jo.put("lyricOrder", LYRIC_ORDER);
            jo.put("lyrics", LYRICS);
        });

        final ObjectMapper objectMapper = OBJECT_MAPPER.get();
        final Song song = objectMapper.readValue(json, Song.class);

        assertAll("song",
                () -> assertEquals(ID, song.getId()),
                () -> assertEquals(TITLE, song.getTitle()),
                () -> assertEquals(TYPE, song.getType()),
                () -> assertEquals(AUTHORS, song.getAuthors()),
                () -> assertEquals(ADDITIONAL_TITLES, song.getAdditionalTitles()),
                () -> assertEquals(THEMES, song.getThemes()),
                () -> assertEquals(LYRIC_ORDER, song.getLyricOrder()),
                () -> assertEquals(LYRICS, song.getLyrics())
        );
    }

}
