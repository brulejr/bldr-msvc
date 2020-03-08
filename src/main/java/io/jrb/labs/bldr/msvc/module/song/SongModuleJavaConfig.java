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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jrb.labs.bldr.msvc.module.song.config.SongModuleConfig;
import io.jrb.labs.bldr.msvc.module.song.model.SongEntityConverter;
import io.jrb.labs.bldr.msvc.module.song.repository.ReactiveSongRepository;
import io.jrb.labs.bldr.msvc.module.song.rest.SongRouter;
import io.jrb.labs.bldr.msvc.module.song.rest.SongHandler;
import io.jrb.labs.bldr.msvc.module.song.service.ISongService;
import io.jrb.labs.bldr.msvc.module.song.service.SongService;
import io.jrb.labs.common.module.ModuleJavaConfigSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "module.song.enabled")
@ComponentScan(basePackages = "io.jrb.labs.bldr.msvc.module.song.repository")
@EnableConfigurationProperties(SongModuleConfig.class)
@Import(SongRouter.class)
public class SongModuleJavaConfig extends ModuleJavaConfigSupport {

    private static final String MODULE_NAME = "Song";

    public SongModuleJavaConfig() {
        super(MODULE_NAME, log);
    }

    @Bean
    public SongEntityConverter songEntityConverter() { return new SongEntityConverter(); }

    @Bean
    public SongHandler songWebHandler(
            final ObjectMapper objectMapper,
            final ISongService songService,
            final SongEntityConverter songEntityConverter
    ) {
        return new SongHandler(objectMapper, songService, songEntityConverter);
    }

    @Bean
    public ISongService songService(
            final ApplicationEventPublisher publisher, final ReactiveSongRepository songRepository) {
        return new SongService(publisher, songRepository);
    }

}
