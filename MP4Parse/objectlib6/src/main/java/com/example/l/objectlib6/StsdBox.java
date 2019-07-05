package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StsdBox extends Box {
    public StsdBox(int startoffset, int size, String type) {
        super(startoffset, size, type);
    }
    private int spslen,ppslen;
    private byte[] sps,pps;
    //获取sps的长度
    public int getSpslen(RandomAccessFile raf) throws IOException{
        raf.seek(this.getStartoffset() + 117);
        spslen = raf.readUnsignedByte();
        return spslen;
    }
    //获取pps的长度
    public int getPpslen(RandomAccessFile raf,int spslen) throws IOException{
        raf.seek(this.getStartoffset() + 118 + spslen +2);
        ppslen = raf.readUnsignedByte();
        return ppslen;
    }
    //取得sps数组
    public byte[] getSps(RandomAccessFile raf,int spslen) throws IOException{
        sps = new byte[spslen];
        raf.seek(this.getStartoffset() + 118);
            raf.read(sps);
        return sps;
    }
    //取得pps数组
    public byte[] getPps(RandomAccessFile raf,int spslen,int ppslen) throws IOException{
        pps = new byte[ppslen];
        raf.seek(this.getStartoffset() + 118 + spslen + 3);
        raf.read(pps);
        return pps;
    }
}