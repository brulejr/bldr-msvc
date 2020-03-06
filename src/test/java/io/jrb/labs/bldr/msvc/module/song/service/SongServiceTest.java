package io.jrb.labs.bldr.msvc.module.song.service;

import io.jrb.labs.bldr.msvc.module.song.model.SongEntity;
import io.jrb.labs.bldr.msvc.module.song.model.SongType;
import io.jrb.labs.bldr.msvc.module.song.repository.ReactiveSongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.jrb.labs.common.test.TestUtils.RANDOM_UUID;
import static io.jrb.labs.common.test.TestUtils.buildList;
import static io.jrb.labs.common.test.TestUtils.buildMap;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    private static final String DETACHED = null;

    private static final Function<Integer, List<String>> RANDOM_STRING_LIST =
            (size) -> buildList(size, (b) -> b.add(randomAlphanumeric(1, size * 2)));

    private static final Function<Integer, Map<String, List<String>>> RANDOM_STRING_MAP =
            (size) -> buildMap(size, (builder) -> {
                final List<String> stanza = buildList(size, (b) -> b.add(randomAlphanumeric(1, size * 2)));
                builder.put(randomAlphanumeric(1, size * 2), stanza);
            });

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private ReactiveSongRepository songRepository;

    private ISongService songService;

    @BeforeEach
    void init() {
        songService = new SongService(eventPublisher, songRepository);
    }

    @Test
    void shouldCreateSong() {
        final SongEntity songEntityMock = createSongEntity(DETACHED);

        final String songEntityId = RANDOM_UUID.get();
        given(songRepository.save(any(SongEntity.class)))
                .willReturn(Mono.just(songEntityMock.withId(songEntityId)));

        final Mono<SongEntity> songEntityMono = songService.create(songEntityMock);

        StepVerifier
                .create(songEntityMono)
                .assertNext(saved -> {
                    assertAll(
                            "createSong",
                            () -> assertThat(saved, is(notNullValue())),
                            () -> assertThat(saved.getId(), is(notNullValue())),
                            () -> assertThat(saved.getTitle(), is(songEntityMock.getTitle())),
                            () -> assertThat(saved.getType(), is(songEntityMock.getType())),
                            () -> assertThat(saved.getAuthors(), is(songEntityMock.getAuthors())),
                            () -> assertThat(saved.getAdditionalTitles(), is(songEntityMock.getAdditionalTitles())),
                            () -> assertThat(saved.getThemes(), is(songEntityMock.getThemes())),
                            () -> assertThat(saved.getLyricOrder(), is(songEntityMock.getLyricOrder())),
                            () -> assertThat(saved.getLyrics(), is(songEntityMock.getLyrics()))
                    );
                })
                .verifyComplete();
    }

    @Test
    public void shouldFindSongByTitle() {
        final String songId = RANDOM_UUID.get();
        final SongEntity songEntityMock = createSongEntity(songId);
        given(songRepository.findFirstByTitle(songEntityMock.getTitle()))
                .willReturn(Mono.just(songEntityMock));

        final Mono<SongEntity> songEntityMono = songService.findByTitle(songEntityMock.getTitle());

        StepVerifier
                .create(songEntityMono)
                .expectNextMatches(songEntity -> songEntity.getId().equalsIgnoreCase(songId))
                .verifyComplete();
    }

    @Test
    void shouldGetAllSongs() {
        given(songRepository.findAll())
                .willReturn(Flux.just(
                        createSongEntity(RANDOM_UUID.get()),
                        createSongEntity(RANDOM_UUID.get()),
                        createSongEntity(RANDOM_UUID.get())
                ));

        final Flux<SongEntity> songsFlux = songService.all();

        final Predicate<SongEntity> match = song -> songsFlux.any(saveItem -> saveItem.equals(song)).block();

        StepVerifier
                .create(songsFlux)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }

    @Test
    void shouldUpdateSong() throws Exception {
        final String songEntityId = RANDOM_UUID.get();
        final SongEntity songEntityMock = createSongEntity(songEntityId);

        given(songRepository.findById(songEntityId))
                .willReturn(Mono.just(songEntityMock));
        given(songRepository.save(any(SongEntity.class)))
                .willReturn(Mono.just(songEntityMock.withId(songEntityId)));

        final Mono<SongEntity> songEntityMono = songService.update(songEntityId, songEntityMock);

        StepVerifier
                .create(songEntityMono)
                .assertNext(saved -> {
                    assertAll(
                            "updateSong",
                            () -> assertThat(saved, is(notNullValue())),
                            () -> assertThat(saved.getId(), is(notNullValue())),
                            () -> assertThat(saved.getTitle(), is(songEntityMock.getTitle())),
                            () -> assertThat(saved.getType(), is(songEntityMock.getType())),
                            () -> assertThat(saved.getAuthors(), is(songEntityMock.getAuthors())),
                            () -> assertThat(saved.getAdditionalTitles(), is(songEntityMock.getAdditionalTitles())),
                            () -> assertThat(saved.getThemes(), is(songEntityMock.getThemes())),
                            () -> assertThat(saved.getLyricOrder(), is(songEntityMock.getLyricOrder())),
                            () -> assertThat(saved.getLyrics(), is(songEntityMock.getLyrics()))
                    );
                })
                .verifyComplete();
    }


    private SongEntity createSongEntity(final String id) {
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
