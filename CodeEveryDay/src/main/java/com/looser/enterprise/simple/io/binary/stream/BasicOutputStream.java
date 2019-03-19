package com.looser.enterprise.simple.io.binary.stream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class BasicOutputStream {

    public static void main(String[] argv){
        String pathname = "/home/samagra/git/CodeEveryDay/files/ints.bin";
        Path path = Paths.get(pathname);
        try(OutputStream output = new FileOutputStream(new File(pathname));){
            DataOutputStream dos = new DataOutputStream(output);
            //dos.writeInt(50);
            //dos.writeUTF("Hello");
            IntStream.range(0,1000).forEach(i-> {
                try {
                    dos.writeInt(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try {
            long size =Files.size(path);
            System.out.println("Size of file:"+size);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
