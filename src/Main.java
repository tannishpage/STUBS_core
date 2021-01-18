import java.io.File;
import java.io.IOError;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // A main function
        Configuration configuration = new Configuration();
        if (!configuration.isLoaded()) {
            if (Main.missingSettingsFileFix()) {
                configuration = new Configuration();
            } else {
                System.out.println("You are missing the settings file.");
                return;
            }

        }
        String[] list = configuration.getThingsToBackup();
        String backupLoc = configuration.getBackupDestination() + File.separator + configuration.backupNameFormat();
        Backup.create_backup(list, backupLoc);
        System.out.print("Press any key to exit...");
        System.in.read();
    }

    public static boolean missingSettingsFileFix() {
        return false;
    }
}
