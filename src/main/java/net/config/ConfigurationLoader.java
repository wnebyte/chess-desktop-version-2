package net.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Callable;
import javax.xml.bind.JAXBContext;

public final class ConfigurationLoader implements Callable<Configuration> {

    private static final InputStream configInputStream =
            ConfigurationLoader.class.getClassLoader().getResourceAsStream("xml/config.xml");

    private static Reader toInputStreamReader(InputStream in) {
        if (in == null) {
            throw new NullPointerException(
                    "InputStream must not be null."
            );
        }
        return new InputStreamReader(in);
    }

    @Override
    public Configuration call() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        return (Configuration) context.createUnmarshaller()
                .unmarshal(toInputStreamReader(configInputStream));
    }

}