package project;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.asm.Advice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

@Log4j2
@SpringBootApplication
public class ProjektJavaApplication {

	private static final Logger logger = LogManager.getLogger(ProjektJavaApplication.class);

	public static void main(String[] args) {
		try{
			log.info("app running");
			log.warn("varovani");
			log.error("error");
			ResourceBundle resourceBundle = ResourceBundle.getBundle("Texts", new Locale("cs"));
			LocalDateTime now = LocalDateTime.now();
			logger.debug(resourceBundle.getString("info") + now);
			logger.info("Info message: "+ now);
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		SpringApplication.run(ProjektJavaApplication.class, args);
	}

}
