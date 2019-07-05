package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StscBox extends Box {
    public StscBox(int startoffset, int size, String type) {
        super(startoffset, size, type);
    }
    private int[] sample_per_chunk;
    //获取chunk的sample数目数组
    public int[] getSamplePerChunk(RandomAccessFile raf,int chunknum) throws IOException{
        sample_per_chunk = new int[chunknum];
        raf.seek(this.getStartoffset());
        int stscsize = raf.readInt();
        if (stscsize < 29) {
            raf.seek(this.getStartoffset() + 20);
            int samplenum = raf.readInt();
            for (int i = 0; i < chunknum; i++) {
                sample_per_chunk[i] = samplenum;
            }
        } else {
            raf.seek(this.getStartoffset() + 28);
            int chunksize1 = raf.readInt() - 1;
            raf.seek(this.getStartoffset() + 20);
            int num1 = raf.readInt();
            for (int i = 0; i < chunksize1; i++) {
                sample_per_chunk[i] = num1;
            }
            raf.seek(this.getStartoffset() + 32);
            int num2 = raf.readInt();
            sample_per_chunk[chunknum - 1] = num2;
        }
        return sample_per_chunk;
    }
}
