package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StssBox extends Box {
    public StssBox(int startoffset, int size, String type) {
        super(startoffset, size, type);
    }
    private int IDRnum;
    private int[] IDRSample;
    // 取得IDR帧的数量
    public int getIDRnum(RandomAccessFile raf) throws IOException{
        raf.seek(this.getStartoffset() + 12);
        IDRnum = raf.readInt();
        return IDRnum;
    }

    //取得IDR帧是哪一个sample的数组
    public int[] getIDRSample(RandomAccessFile raf,int IDRnum) throws IOException{
        IDRSample = new int[IDRnum];
        raf.seek(this.getStartoffset() + 16);
        for (int i=0;i<IDRnum;i++){
            IDRSample[i] = raf.readInt() - 1;
        }
        return IDRSample;
    }
}