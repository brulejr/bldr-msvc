package io.jrb.labs.bldr.msvc.module.song.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.jrb.labs.common.test.TestUtils.JSON_BUILDER;
import static io.jrb.labs.common.test.TestUtils.OBJECT_MAPPER;
import static io.jrb.labs.common.test.TestUtils.RANDOM_UUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.*;

class SongMetadataTest {

    private static final String ID = RANDOM_UUID.get();
    private static final String TITLE = randomAlphanumeric(10, 25);
    private static final SongSource SOURCE = SongSource.builder()
            .sourceId(RANDOM_UUID.get())
            .sourceSystem(randomAlphanumeric(10, 25))
            .build();
    private static final SongType TYPE = SongType.NORMAL;

    @Test
    void shouldCreateBeanFromCode() {
        final SongMetadata songMetadata = SongMetadata.builder()
                .id(ID)
                .title(TITLE)
                .source(SOURCE)
                .type(TYPE)
                .build();
        assertAll("songMetadata",
                () -> assertEquals(ID, songMetadata.getId()),
                () -> assertEquals(TITLE, songMetadata.getTitle()),
                () -> assertEquals(SOURCE, songMetadata.getSource()),
                () -> assertEquals(TYPE, songMetadata.getType())
        );
    }

    @Test
    void shouldCreateBeanFromJson() throws JsonProcessingException {
        final String json = JSON_BUILDER.apply((jo) -> {
            jo.put("id", ID);
            jo.put("title", TITLE);
            jo.put("type", TYPE);
            final JSONObject joSource = new JSONObject();
            joSource.put("sourceId", SOURCE.getSourceId());
            joSource.put("sourceSystem", SOURCE.getSourceSystem());
            jo.put("source", joSource);
        });

        final ObjectMapper objectMapper = OBJECT_MAPPER.get();
        final SongMetadata songMetadata = objectMapper.readValue(json, SongMetadata.class);

        assertAll("songMetadata",
                () -> assertEquals(ID, songMetadata.getId()),
                () -> assertEquals(TITLE, songMetadata.getTitle()),
                () -> assertEquals(SOURCE, songMetadata.getSource()),
                () -> assertEquals(TYPE, songMetadata.getType())
        );
    }

}
