package com.looser.enterprise.simple.io.binary.stream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadingArchive {
    public static void main(String[] argv) {
        String pathname = "/home/samagra/git/CodeEveryDay/files/compressed.zip";
        try(InputStream input = new FileInputStream(new File(pathname));
            ZipInputStream zis = new ZipInputStream(input);
            DataInputStream dis = new DataInputStream(zis);
        ){
            ZipEntry entry = zis.getNextEntry();
            while(entry != null){
                if(entry.isDirectory()){
                    System.out.println("Entry is Directory:"+entry);
                }
                else{
                    if(entry.getName().endsWith(".bin")){
                        List<Integer> list = new ArrayList<>();
                            try {
                                while(true) {

                                    list.add(dis.readInt());
                                }
                            }
                            catch (EOFException e){

                            }
                        System.out.println("Size of list:"+list.size());
                    }
                    else{
                        System.out.println("Charaters read:" +dis.readUTF());
                    }
                }
                entry = zis.getNextEntry();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}