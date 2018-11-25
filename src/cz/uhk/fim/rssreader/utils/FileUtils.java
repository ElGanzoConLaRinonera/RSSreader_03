package cz.uhk.fim.rssreader.utils;

import cz.uhk.fim.rssreader.model.RSSSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static final String CONFIG_FILE = "config.cfg";

    public static String loadStringFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static void saveStringToFile(String filepath, byte[] data) throws IOException {
        Path path = Paths.get(filepath);
        Files.write(path, data);
    }

    public static List<RSSSource> loadSource() {
        List<RSSSource> sources = new ArrayList<>();
        try {
        new BufferedReader(new StringReader(loadStringFromFile(CONFIG_FILE))).lines().forEach(source -> {
            String[] parts = source.split(";");
            sources.add(new RSSSource(parts[0], parts[1]));
        });
    } catch(
    IOException e )

    {
        e.printStackTrace();
    }
    return sources;
}


    public static void saveSource(List<RSSSource>sources){
        StringBuilder builder = new StringBuilder();
         for(int i = 0; i < sources.size();i++){
            builder.append(String.format("%s;%s", sources.get(i).getName(),sources.get(i).getSource()));
            builder.append( i!= sources.size()-1 ? "\n" : "");
         }
         try{
             saveStringToFile(CONFIG_FILE, builder.toString().getBytes("UTF-8"));
         } catch(IOException e){
             e.printStackTrace();
         }

    }

}
