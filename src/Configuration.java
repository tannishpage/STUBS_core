import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

/**
 * Settings/Configuration Interface
 */
public class Configuration {
    private final Properties properties;
    private final String configName = ".." + File.separator + "SETTINGS";
    private boolean loaded = false;

    public Configuration() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(configName));
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
          //p.store(new FileOutputStream("C:\\Users\\punug\\Documents\\STUBS_core\\SETTINGS"), "test")
       Configuration conf = new Configuration();
       String s = "Backup_Desktop_{ddMMyyyy}_{hhmmss}";
       String[] b = conf.processFormatters(s);
       for (String a : b) {
           //System.out.println(a);
           DateTimeFormatter dtf = DateTimeFormatter.ofPattern(a.substring(1, a.length() - 1));
           LocalDateTime now = LocalDateTime.now();
           s = s.replace(a, now.format(dtf));
           //System.out.println(now.format(dtf));
       }
       System.out.println(s);
       //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyy");
       //LocalDateTime now = LocalDateTime.now();
       //System.out.println(now.format(dtf));
    } */

    private String[] processFormatters(String s) {
        boolean haveFormatter = true;
        List<String> formatters = new ArrayList<>();
        int prevStart = 0;
        int start;
        int end;
        while (haveFormatter) {
            start = s.indexOf("{", prevStart);
            if (start == -1) {
                haveFormatter = false;
                continue;
            }
            end = s.indexOf("}", start);
            prevStart = start + 1;
            formatters.add(s.substring(start, end + 1));
        }
        return formatters.toArray(new String[0]);
    }
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
        String[] formatters = this.processFormatters(value);
        if (formatters.length == 0) {
            return value;
        }
        for (String formatter : formatters) {
            //System.out.println(a);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter.substring(1, formatter.length() - 1));
            LocalDateTime now = LocalDateTime.now();
            value = value.replace(formatter, now.format(dtf));
            //System.out.println(now.format(dtf));
        }
        return value;
    }

    public void setBackupNameFormat(String value) {
        properties.setProperty("BACKUPNAMEFORMAT", value);
    }

    public void saveProperties() throws IOException {
        properties.store(new FileOutputStream(this.configName), "Formatting Instructions: hh = hours, mm = minutes, ss = seconds, dd = days, MM = months, yy or yyyy = year");
    }

    public boolean isLoaded() {
        return loaded;
    }
}
