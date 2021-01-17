import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Will go through the backup process. It will take a list of given folders/files to backup, and will go through them,
 * creating zip a archive. This file is not concerend with what happens to the zip file.
 * (I.e. sent through the network, etc).
 */
public class Backup {
  /* public static void main(String[] args) throws IOException {
        Backup.create_backup(new String[] {"C:\\Users\\punug\\Desktop\\", "C:\\Users\\punug\\Pictures\\", "C:\\Users\\punug\\Documents\\", "C:\\Users\\punug\\Downloads\\"}, "D:\\Backup_test");
    } */

    public static void create_backup(String[] list, String bkpDestination) throws IOException {
        Backup b = new Backup();
        File bkpDirectory = new File(bkpDestination);
        if (bkpDirectory.mkdir()){
            for (String dir : list) {
                System.out.print("Backing Up " + dir + "...");
                File backupFolder = new File(dir);
                String folder_destination = bkpDirectory.getAbsolutePath() + File.separator + backupFolder.getName() + ".zip";
                List<File> walk = b.walkDirectory(backupFolder);
                b.make_zip(walk, folder_destination, dir);
                System.out.println("Done");
            }
            System.out.print("Creating single archive...");
            List<File> walk = b.walkDirectory(bkpDirectory);
            b.make_zip(walk, bkpDirectory.getParent() + File.separator + bkpDirectory.getName() + ".zip", bkpDirectory.getAbsolutePath() + File.separator);
            System.out.println("Done");
            System.out.print("Cleaning up...");
            if (b.cleanUp(bkpDirectory)) {
                System.out.println("Done");
            } else {
                System.out.println("Failed");
                System.out.println("Couldn't clean up backup destination. Manually remove.");
            }
        } else {
            System.out.println("Failed to create backup. Could not make directory in given location");
        }
    }

    private void make_zip(List<File> filesToAdd, String zipDestination, String removeFromPath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipDestination);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (File f : filesToAdd){
            if (f.isDirectory()){
                if(f.getName().endsWith(File.separator)){
                    zipOut.putNextEntry(new ZipEntry(f.getAbsolutePath().replace(removeFromPath, "")));
                    zipOut.closeEntry();
                } else {
                    zipOut.putNextEntry(new ZipEntry(f.getAbsolutePath().replace(removeFromPath, "") + File.separator));
                    zipOut.closeEntry();
                }
            } else {
                FileInputStream fis = new FileInputStream(f);
                ZipEntry zipEntry = new ZipEntry( f.getAbsolutePath().replace(removeFromPath, ""));
                zipOut.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0){
                    zipOut.write(buffer, 0, length);
                }
                fis.close();
            }
        }
        zipOut.close();
        fos.close();
    }

    private boolean cleanUp(File bkpDirectory){
        File[] files = bkpDirectory.listFiles();
        if (files == null){
            return false;
        }
        for (File f : files){
            if (!f.delete()){
                return false;
            }
        }
        return bkpDirectory.delete();
    }

    private List<File> walkDirectory(File start){
        List<File> walkList = new ArrayList<>();
        ArrayDeque<File> stack = new ArrayDeque<>();
        stack.add(start);

        while (!(stack.isEmpty())) {
            File current = stack.removeLast();
            walkList.add(current);
            if (current.isDirectory()) {
                if (current.listFiles() != null) {
                    for (File f : current.listFiles()) {
                        if (f.isDirectory()) {
                            stack.add(f);
                        } else {
                            walkList.add(f);
                        }
                    }
                }
            }
        }
        return walkList;
    }
}
