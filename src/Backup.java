/**
 * Will go through the backup process. It will take a list of given folders/files to backup, and will go through them,
 * creating zip a archive. This file is not concerend with what happens to the zip file.
 * (I.e. sent through the network, etc).
 *
 * This file would need, a zip library, and a library that can walk all the sub directories of a given directory.
 */
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Backup {

/*    public static void main(String[] args) throws IOException {
        Backup.create_backup(new String[] {"C:\\Users\\punug\\Desktop\\", "C:\\Users\\punug\\Pictures\\", "C:\\Users\\punug\\Documents\\", "C:\\Users\\punug\\Downloads\\"}, "D:\\Backup_test");
    } */

    public static void create_backup(String[] list, String bkp_destination) throws IOException {
        Backup b = new Backup();
        File bkp_directory = new File(bkp_destination);
        bkp_directory.mkdir();
        for (String dir : list) {
            System.out.print("Backing Up " + dir + "...");
            File backupFolder = new File(dir);
            String folder_destination = bkp_directory.getAbsolutePath() + File.separator + backupFolder.getName() + ".zip";
            List<File> walk = b.walkDirectory(backupFolder);
            b.make_zip(walk, folder_destination, dir);
            System.out.println("Done");
        }
        System.out.print("Creating single archive...");
        List<File> walk = b.walkDirectory(bkp_directory);
        b.make_zip(walk, bkp_directory.getParent() + File.separator + bkp_directory.getName() + ".zip", bkp_directory.getAbsolutePath() + File.separator);
        System.out.println("Done");
    }

    private boolean make_zip(List<File> files_to_add, String zip_destination, String removeFromPath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zip_destination);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (File f : files_to_add){
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

        return true;
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
