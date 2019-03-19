package com.looser.enterprise.simple.io.binary.stream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;

public class ReadingBinaryData {
    public static void main(String[] argv){
        String pathname = "/home/samagra/git/CodeEveryDay/files/ints.bin.gz";
        Path path = Paths.get(pathname);
        try(InputStream Input = new FileInputStream(new File(pathname));
            GZIPInputStream gzis = new GZIPInputStream(Input);
            DataInputStream dis = new DataInputStream(gzis);){

            List<Integer> ints = new ArrayList<>();

            try {
                while(true)
                   ints.add(dis.readInt());
            }
            catch (EOFException e){

            }
            System.out.println("Size of list:"+ints.size());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            long size = Files.size(path);
            System.out.println("Size of file:"+size);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
