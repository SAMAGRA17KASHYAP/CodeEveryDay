package com.looser.enterprise.simple.io.binary.stream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreatingArchive {

    public static void main(String[] argv){
        String pathname="/home/samagra/git/CodeEveryDay/files/compressed.zip";
        try(OutputStream output = new FileOutputStream(new File(pathname));
            ZipOutputStream zos = new ZipOutputStream(output);
            DataOutputStream dos = new DataOutputStream(zos);
        ){
            ZipEntry  entry = new ZipEntry("bin/");
            zos.putNextEntry(entry);
            entry = new ZipEntry("bin/number.bin");
            zos.putNextEntry(entry);

            IntStream.range(0,1000).forEach(i->
            {try{dos.writeInt(i);} catch (Exception e){}});

            entry = new ZipEntry("text/");
            zos.putNextEntry(entry);
            entry = new ZipEntry("text/sec.txt");
            zos.putNextEntry(entry);
            dos.writeUTF("This needs to be done betters");

        }
        catch (Exception e){

        }
    }
}
