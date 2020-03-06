package io.jrb.labs.bldr.msvc;

import io.jrb.labs.bldr.msvc.module.song.SongModuleJavaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackageClasses = {
		SongModuleJavaConfig.class
})
public class BldrMsvcApplication {

	public static void main(final String[] args) {
		final SpringApplication application = new SpringApplicationBuilder(BldrMsvcApplication.class)
				.headless(true)
				.build();
		application.run(args);
	}

}
