package imserver;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import imserver.server.IMServerService;

@SpringBootApplication
public class IMServerApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
        LOGGER.info("IM Server startted");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
