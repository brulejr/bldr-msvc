package io.jrb.labs.bldr.msvc.module.song.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static io.jrb.labs.common.test.TestUtils.JSON_BUILDER;
import static io.jrb.labs.common.test.TestUtils.OBJECT_MAPPER;
import static io.jrb.labs.common.test.TestUtils.RANDOM_UUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SongSourceTest {

    private static final String SOURCE_ID = RANDOM_UUID.get();
    private static final String SOURCE_SYSTEM = randomAlphanumeric(10, 25);

    @Test
    void shouldCreateBeanFromCode() {
        final SongSource songSource = SongSource.builder()
                .sourceId(SOURCE_ID)
                .sourceSystem(SOURCE_SYSTEM)
                .build();

        assertAll("songSource",
                () -> assertEquals(SOURCE_ID, songSource.getSourceId()),
                () -> assertEquals(SOURCE_SYSTEM, songSource.getSourceSystem())
        );
    }

    @Test
    void shouldCreateBeanFromJson() throws JsonProcessingException {
        final String json = JSON_BUILDER.apply((jo) -> {
            jo.put("sourceId", SOURCE_ID);
            jo.put("sourceSystem", SOURCE_SYSTEM);
        });

        final ObjectMapper objectMapper = OBJECT_MAPPER.get();
        final SongSource songSource = objectMapper.readValue(json, SongSource.class);

        assertAll("songSource",
                () -> assertEquals(SOURCE_ID, songSource.getSourceId()),
                () -> assertEquals(SOURCE_SYSTEM, songSource.getSourceSystem())
        );
    }

}
