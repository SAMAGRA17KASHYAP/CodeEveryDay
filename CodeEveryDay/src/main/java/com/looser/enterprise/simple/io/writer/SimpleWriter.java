package com.looser.enterprise.simple.io.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.Locale;

public class SimpleWriter {
    public  static  void main(String[] argv){
        Path path = Paths.get("simple_write.txt");
        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);)
        {
            PrintWriter pw = new PrintWriter(writer);
            writer.write("Hello World!!!");
            pw.format("%d",Integer.MAX_VALUE);
            Calendar calendar = Calendar.getInstance();
            pw.format(Locale.US,"Man walkedon the moon %1$tB %1$tA %1$tY",calendar);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
