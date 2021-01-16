/**
 * Will go through the backup process. It will take a list of given folders/files to backup, and will go through them,
 * creating zip a archive. This file is not concerend with what happens to the zip file.
 * (I.e. sent through the network, etc).
 *
 * This file would need, a zip library, and a library that can walk all the sub directories of a given directory.
 */
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;


public class Backup {

    public static void create_backup(String[] list, String bkp_destination){

    }

    private boolean make_zip(File[] files_to_add, String zip_destination) {
        return true;
    }

    private List<File> walkDirectory(ArrayDeque<File> stack, List<File> walkList){
        while (!(stack.isEmpty())) {
            File current = stack.pop();
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
