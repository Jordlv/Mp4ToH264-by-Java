package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StsdBox extends Box {
    public StsdBox(int offset, int length){
        super(offset,length);
    }


    int spslen;
    int ppslen;
    byte[] spsbyte = new byte[spslen];
    byte[] ppsbyte = new byte[ppslen];
    public byte[] getSpsbyte(RandomAccessFile raf){
        try {
            raf.seek(this.offset+117);
            spslen = raf.readUnsignedByte();
            System.out.println(spslen);
            byte[] sps = new byte[spslen];
            raf.read(sps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spsbyte;
    }

    public byte[] getPpsbyte(RandomAccessFile raf){
        try {
            raf.skipBytes(2);
            ppslen = raf.readUnsignedByte();
            System.out.println(ppslen);
            byte[] pps = new byte[ppslen];
            raf.read(pps);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ppsbyte;
    }

}
