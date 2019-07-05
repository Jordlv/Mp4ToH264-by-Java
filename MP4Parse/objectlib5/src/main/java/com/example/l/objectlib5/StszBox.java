package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StszBox extends Box {
    public StszBox(int offset, int length){ super(offset,length); }
    int[] offset_per_sample;
    int samplenum;

    public int getSamplenum(RandomAccessFile raf)throws IOException{
        raf.seek(offset+16);
        samplenum = raf.readInt();
        return samplenum;
    }
    public int[] getOffset_per_sample(RandomAccessFile raf) throws IOException {
        int m = 0;
            raf.seek(offset+16);
            samplenum = raf.readInt();
            offset_per_sample = new int[samplenum];
            for (int i = 0; i < samplenum - 1; i++) {
                int samplelen = raf.readInt();
                offset_per_sample[i] = m;
                System.out.println(offset_per_sample[i]);
                m += samplelen;
            }

        return offset_per_sample;
    }
    public int[] getSize_per_sample(RandomAccessFile raf) throws IOException {
            raf.seek(offset+16);
            samplenum = raf.readInt();
            int[] size_per_sample = new int[samplenum];
            for (int i = 0; i < samplenum - 1; i++) {
                size_per_sample[i] = raf.readInt();
            }
        return size_per_sample;
    }

}
