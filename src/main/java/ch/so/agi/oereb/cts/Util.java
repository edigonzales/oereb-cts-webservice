package ch.so.agi.oereb.cts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Util {
    public static String loadString(String resourcePath) throws IOException {
        InputStream is = Util.class.getClassLoader().getResourceAsStream(resourcePath);
        Path exportDir = Files.createTempDirectory("oereb-cts-ws");
        Path exportedFile = exportDir.resolve(new File(resourcePath).getName());
        Files.copy(is, exportedFile, StandardCopyOption.REPLACE_EXISTING);
        return Files.readString(exportedFile);
    }
    
    
    public static File copyResourceToTmpDir(String resourcePath) throws IOException {
        Path exportedFile = null;
        InputStream is = Util.class.getClassLoader().getResourceAsStream(resourcePath);
        Path exportDir = Files.createTempDirectory("oerebcts");
        exportedFile = exportDir.resolve(new File(resourcePath).getName());
        Files.copy(is, exportedFile, StandardCopyOption.REPLACE_EXISTING);
        return exportedFile.toFile();
    }

}
