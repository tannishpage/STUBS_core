import java.io.*;
import java.util.*;

/**
 * Settings/Configuration Interface
 */
public class Configuration {
    private final Properties properties;
    private final String ConfigName = ".." + File.separator + "SETTINGS";
    private boolean loaded = false;

    public Configuration() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(ConfigName));
            loaded = true;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

  /* public static void main (String[] args) throws IOException {
        Configuration conf = new Configuration();
        if (conf.networkTransfer()) {

        }
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\punug\\Documents\\STUBS_core\\SETTINGS"));
        Set propertiesEntrySet = p.entrySet();
        Iterator it = propertiesEntrySet.iterator();
        //while (it.hasNext()){
            //Map.Entry e = (Map.Entry) it.next();
            //System.out.println(e.getKey() + " = " + e.getValue());
        //}


        String[] files = p.getProperty("THINGSTOBACKUP").split(File.pathSeparator);
        for (int i = 0; i <= files.length - 1; i++){
            System.out.println(files[i]);
        }
        //p.setProperty("SERVERADDR", "192.168.0.16");
        //p.store(new FileOutputStream("C:\\Users\\punug\\Documents\\STUBS_core\\SETTINGS"), "test");
    } */

    public String[] getThingsToBackup () {
        return properties.getProperty("THINGSTOBACKUP").split(File.pathSeparator);
    }

    public void addToThingsToBackup (String value) {
        String newThingsToBackup = properties.getProperty("THINGSTOBACKUP") + File.pathSeparator + value;
        properties.setProperty("THINGSTOBACKUP", newThingsToBackup);
    }

    public void removeThingsToBackup(String[] values) {
        for (String value : values) {
            this.removeThingsToBackup(value);
        }
    }

    public void removeThingsToBackup(String value) {
        String[] thingsToBackup = this.getThingsToBackup();
        StringBuilder newThingsToBackup = new StringBuilder();
        for (String thing : thingsToBackup) {
            if (!thing.equals(value)){
                newThingsToBackup.append(thing);
                newThingsToBackup.append(File.pathSeparator);
            }
        }
        properties.setProperty("THINGSTOBACKUP", newThingsToBackup.toString());
    }

    public String getServerAddress() {
        return properties.getProperty("SERVERADDR");
    }

    public void setServerAddress(String value) {
        properties.setProperty("SERVERADDR", value);
    }

    public int getPort() {
        String value = properties.getProperty("PORT");
        return Integer.parseInt(value);
    }

    public void setPort(int value) {
        String portValue = Integer.toString(value);
        properties.setProperty("PORT", portValue);
    }

    public boolean networkTransfer() {
        String value = properties.getProperty("REMOTETRANSFER");
        return Boolean.parseBoolean(value);
    }

    public void setNetworkTransfer(boolean value) {
        String property = Boolean.toString(value);
        properties.setProperty("REMOTETRANSFER", property);
    }

    public boolean keepBackupFile() {
        String value = properties.getProperty("KEEPBACKUPFILE");
        return Boolean.parseBoolean(value);
    }

    public void setKeepBackupFile(boolean value) {
        String property = Boolean.toString(value);
        properties.setProperty("KEEPBACKUPFILE", property);
    }

    public String backupNameFormat() {
        String value = properties.getProperty("BACKUPNAMEFORMAT");
        //  --------------- TODO ----------------
        //  Here I'd like to come up with a formatting system or just use a standard one.
        //  I'd like to allow the user to have the time and date in the file name.

        return value + "_Backup";
    }

    public void setBackupNameFormat(String value) {
        properties.setProperty("BACKUPNAMEFORMAT", value);
    }

    public boolean isLoaded() {
        return loaded;
    }
}
