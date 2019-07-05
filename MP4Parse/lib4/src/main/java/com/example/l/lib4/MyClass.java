package com.example.l.lib4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MyClass {
    public static void main(String[] args) throws IOException {
        try {
            RandomAccessFile raf = new RandomAccessFile("/home/letv/Videos/v1", "r");
            raf.seek(7406);
            System.out.println(raf.readInt());
        }catch (FileNotFoundException e){
        }
    }
}
