package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MoovBox extends Box implements ContainerBox {
    public MoovBox(int offset,int length){
        super(offset,length);
    }
    TrakBox trakBox;


    @Override
    public TrakBox getChildBox(RandomAccessFile raf) {
        int trakoffset;
        int tarklen;
        try {
            raf.seek(this.offset);
            raf.skipBytes(8);
            int len1 = raf.readInt();
            raf.skipBytes(len1 - 4);
            int len2 = raf.readInt();
            raf.skipBytes(len2 - 4);
            int len3 = raf.readInt();
            raf.skipBytes(len3-4);
            trakoffset = this.offset+len1+len2+len3+8;
            tarklen = raf.readInt();
            System.out.println("trak的偏移量是："+trakoffset);
            trakBox = new TrakBox(trakoffset, tarklen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trakBox;
    }
}
