import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class BackupTesting {

    @Test
    public void backupTest() throws IOException {
        Configuration configuration = new Configuration();
        String[] list = configuration.getThingsToBackup();
        String backupLoc = configuration.getBackupDestination() + File.separator + configuration.backupNameFormat();
        Backup.create_backup(list, backupLoc);
    }
}
