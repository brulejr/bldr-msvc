package io.jrb.labs.bldr.msvc.module.song.rest;

import io.jrb.labs.bldr.msvc.module.song.config.SongModuleConfig;
import io.jrb.labs.bldr.msvc.module.song.model.Song;
import io.jrb.labs.bldr.msvc.module.song.model.SongEntity;
import io.jrb.labs.bldr.msvc.module.song.model.SongEntityConverter;
import io.jrb.labs.bldr.msvc.module.song.service.ISongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.jrb.labs.bldr.msvc.module.song.SongTestUtils.createSong;
import static io.jrb.labs.bldr.msvc.module.song.SongTestUtils.createSongEntity;
import static io.jrb.labs.common.test.TestUtils.RANDOM_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SongRouter.class,
        SongHandler.class,
        SongEntityConverter.class
})
@EnableConfigurationProperties(SongModuleConfig.class)
@WebFluxTest
class SongHandlerTest {

    @MockBean
    ISongService songService;

    @Autowired
    private SongEntityConverter songEntityConverter;

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldCreateSong() {
        final Song song = createSong();
        final SongEntity songEntity = songEntityConverter.dtoToEntity(song);

        final String songEntityId = RANDOM_UUID.get();
        final SongEntity songEntityMock = songEntity.withId(songEntityId);

        when(songService.create(any(SongEntity.class))).thenReturn(Mono.just(songEntityMock));

        webClient.post()
                .uri("/api/v1/song")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(song))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Song.class)
                .value(s -> {
                    assertEquals(songEntityId, s.getId());
                    assertEquals(songEntityMock.getTitle(), s.getTitle());
                    assertEquals(songEntityMock.getSource(), s.getSource());
                    assertEquals(songEntityMock.getType(), s.getType());
                    assertEquals(songEntityMock.getAuthors(), s.getAuthors());
                    assertEquals(songEntityMock.getAdditionalTitles(), s.getAdditionalTitles());
                    assertEquals(songEntityMock.getLyricOrder(), s.getLyricOrder());
                    assertEquals(songEntityMock.getLyrics(), s.getLyrics());
                });

        verify(songService, times(1)).create(songEntity);
    }

    @Test
    void shouldDeleteSongById() {
        final String songEntityId = RANDOM_UUID.get();
        final SongEntity songEntityMock = createSongEntity(songEntityId);

        when(songService.delete(songEntityId)).thenReturn(Mono.just(songEntityMock));

        webClient.delete()
                .uri("/api/v1/song/" + songEntityId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(songService, times(1)).delete(songEntityId);
    }

    @Test
    void shouldGetSongById() {
        final String songEntityId = RANDOM_UUID.get();
        final SongEntity songEntityMock = createSongEntity(songEntityId);

        when(songService.get(songEntityId)).thenReturn(Mono.just(songEntityMock));

        webClient.get()
                .uri("/api/v1/song/" + songEntityId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Song.class)
                .value(s -> {
                    assertEquals(songEntityId, s.getId());
                    assertEquals(songEntityMock.getTitle(), s.getTitle());
                    assertEquals(songEntityMock.getSource(), s.getSource());
                    assertEquals(songEntityMock.getType(), s.getType());
                    assertEquals(songEntityMock.getAuthors(), s.getAuthors());
                    assertEquals(songEntityMock.getAdditionalTitles(), s.getAdditionalTitles());
                    assertEquals(songEntityMock.getLyricOrder(), s.getLyricOrder());
                    assertEquals(songEntityMock.getLyrics(), s.getLyrics());
                });

        verify(songService, times(1)).get(songEntityId);
    }

    @Test
    void shouldGetSongs() {

        final SongEntity songEntity1 = createSongEntity(RANDOM_UUID.get());
        final SongEntity songEntity2 = createSongEntity(RANDOM_UUID.get());
        final SongEntity songEntity3 = createSongEntity(RANDOM_UUID.get());

        when(songService.all()).thenReturn(Flux.just(songEntity1, songEntity2, songEntity3));

        webClient.get()
                .uri("/api/v1/song")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Song.class)
                .value(songList -> {
                    assertEquals(3, songList.size());
                    assertEquals(songEntity1.getId(), songList.get(0).getId());
                    assertEquals(songEntity2.getId(), songList.get(1).getId());
                    assertEquals(songEntity3.getId(), songList.get(2).getId());
                });

        verify(songService, times(1)).all();
    }

    @Test
    void shouldUpdateSongById() {
        final String songId = RANDOM_UUID.get();
        final Song song = createSong(songId);
        final SongEntity songEntity = songEntityConverter.dtoToEntity(song);
        final SongEntity songEntityMock = createSongEntity(songId);

        when(songService.update(songId, songEntity)).thenReturn(Mono.just(songEntityMock));

        webClient.put()
                .uri("/api/v1/song/" + songId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(song))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Song.class)
                .value(s -> {
                    assertEquals(songId, s.getId());
                    assertEquals(songEntityMock.getTitle(), s.getTitle());
                    assertEquals(songEntityMock.getSource(), s.getSource());
                    assertEquals(songEntityMock.getType(), s.getType());
                    assertEquals(songEntityMock.getAuthors(), s.getAuthors());
                    assertEquals(songEntityMock.getAdditionalTitles(), s.getAdditionalTitles());
                    assertEquals(songEntityMock.getLyricOrder(), s.getLyricOrder());
                    assertEquals(songEntityMock.getLyrics(), s.getLyrics());
                });

        verify(songService, times(1)).update(songId, songEntity);
    }

}
