package com.looser.enterprise.simple.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReaderInActionWithStream {

    public static void main(String[] argv){
        Path path = Paths.get("/home/samagra/git/CodeEveryDay/pom.xml");
        try(Stream<String> lines=Files.newBufferedReader(path).lines()){
            lines.forEach(System.out::println);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
