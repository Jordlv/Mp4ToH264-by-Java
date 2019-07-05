package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StszBox extends Box {
    public StszBox(int startoffset, int size, String type) {
        super(startoffset, size, type);
    }
    private int samplenum;
    private int[] sizepersample;
    //获取 sample 的数目
    public int getSampleNum(RandomAccessFile raf) throws IOException{
        raf.seek(this.getStartoffset() + 16);
        samplenum = raf.readInt();
        return samplenum;
    }

    //获取每一sample的字节数
    public int[] getSizePerSample(RandomAccessFile raf,int samplenum) throws IOException{
        sizepersample = new int[samplenum];
        raf.seek(this.getStartoffset() + 20);
        for (int i=0;i<samplenum;i++){
            sizepersample[i] = raf.readInt();
        }
        return sizepersample;
    }
}