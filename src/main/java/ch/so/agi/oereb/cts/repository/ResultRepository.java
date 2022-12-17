package ch.so.agi.oereb.cts.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import ch.so.agi.oereb.cts.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ResultRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //@Value("${app.repositoryStorage}")
    private String repositoryStorage = "/Users/stefan/tmp/oerebctsws/";

    public void save(List<Result> results) throws IOException {
        log.info(repositoryStorage);

        if (results.size() == 0) {
            log.warn("Empty result list.");
            return;
        }

        String outfileName = results.get(0).getIdentifier().toLowerCase() + "__"
                + results.get(0).getClassName().replace(".", "_").toLowerCase() + ".txt";
                
        File outfile = Paths.get(repositoryStorage, outfileName).toFile();
//        if (outfile.exists()) {
//            outfile.delete();
//        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outfile))) {
            oos.writeObject(results);
            oos.flush(); 
        }
    }
    
    public List<Result> findByIdentifier(String identifier) throws IOException {
        List<Path> files = new ArrayList<Path>();
        try (Stream<Path> walk = Files.walk(new File(repositoryStorage).toPath())) {
            files = walk
                    .filter(p -> !Files.isDirectory(p))   
                    .filter(f -> f.toFile().getName().toLowerCase().startsWith(identifier.toLowerCase() + "__"))
                    .collect(Collectors.toList());        
        }
        
        List<Result> results = new ArrayList<Result>();
        for (Path filePath : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
                results.addAll((List<Result>) ois.readObject());
                ois.close();
            } catch (ClassNotFoundException e) {
                throw new IOException(e.getMessage());
            }
        }
        return results;
    }
    
    public List<Result> findAll() throws IOException {
        List<Path> files = new ArrayList<Path>();
        try (Stream<Path> walk = Files.walk(new File(repositoryStorage).toPath())) {
            files = walk
                    .filter(p -> !Files.isDirectory(p))   
                    .filter(f -> f.toFile().getName().toLowerCase().contains( "__"))
                    .filter(f -> f.toFile().getName().toLowerCase().endsWith(".txt"))
                    .collect(Collectors.toList());        
        }
        
        List<Result> results = new ArrayList<Result>();
        for (Path filePath : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
                results.addAll((List<Result>) ois.readObject());
                ois.close();
            } catch (ClassNotFoundException e) {
                throw new IOException(e.getMessage());
            }
        }
        return results;
    }
    

}
