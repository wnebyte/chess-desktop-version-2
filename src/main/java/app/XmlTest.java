package app;

import net.config.Configuration;
import javax.xml.bind.JAXBContext;
import java.io.File;
import java.io.FileReader;

public class XmlTest
{
    private static Configuration configuration;

    public static void main(String[] args) throws Exception
    {
        configuration = readConfiguration();
    }
    
    public static Configuration getConfiguration() { return configuration; }

    private static Configuration readConfiguration() throws Exception
    {
        String path = XmlTest.class.getResource("/xml/config.xml").getPath();
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        return (Configuration) context.createUnmarshaller().unmarshal(new FileReader(path));
    }

    public static void writeConfiguration() throws Exception
    {
        String path = XmlTest.class.getResource("/xml/config.xml").getPath();
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        context.createMarshaller().marshal(configuration, new File(path));
    }
}
