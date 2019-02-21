package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {

    private Properties prop;


    public PropertiesGetter(String resource) throws IOException {
        this.prop = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + resource + "' not found in the classpath");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public Properties getProperties() {
        return prop;
    }

    public String getProperty(String name) {
        return this.prop.getProperty(name);
    }

    public String[] getNodesList() {
        String nodes = this.prop.getProperty("nodes");
        return nodes.split(",");
    }

    public int getNumberOfOfficers() {
        return Integer.parseInt(this.prop.getProperty("numberOfOfficers"));
    }

    public int getMapWidth() {
        return Integer.parseInt(this.prop.getProperty("mapWidth"));
    }

    public int getMapHeight() {
        return Integer.parseInt(this.prop.getProperty("mapHeight"));
    }

    public int getNumberOfReports() {
        return Integer.parseInt(this.prop.getProperty("numberOfReports"));
    }

    public long getWaitTime() {
        return Integer.parseInt(this.prop.getProperty("waitTime"));
    }
}
