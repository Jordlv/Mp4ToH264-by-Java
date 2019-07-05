package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StcoBox extends Box {
    public StcoBox(int startoffset, int size, String type) {
        super(startoffset, size, type);
    }
    private int chunknum;
    private int[] chunkoffset;
    //获取chunk的数目
    public int getChunknum(RandomAccessFile raf) throws IOException {
        raf.seek(this.getStartoffset() + 15);
        chunknum = raf.readUnsignedByte();
        return chunknum;
    }

    //获取chunk的偏移量数组
    public int[] getChunkOffset(RandomAccessFile raf,int chunknum) throws IOException {
        chunkoffset = new int[chunknum];
        raf.seek(this.getStartoffset() + 16);
        for (int i=0;i<chunknum;i++){
            chunkoffset[i] = raf.readInt();
        }
        return chunkoffset;
    }
}
