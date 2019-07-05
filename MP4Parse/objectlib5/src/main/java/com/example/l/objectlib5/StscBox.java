package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;


public class StscBox extends Box {
    public StscBox(int offset, int length){
        super(offset,length);
    }

    public int[] getSample_per_chunk(RandomAccessFile raf,int chunknum) throws IOException {

            int[] sample_per_chunk = new int[chunknum];
            raf.seek(this.offset);
            int stscsize = raf.readInt();
            if (stscsize < 29) {
                raf.seek(this.offset + 20);
                int samplenum = raf.readInt();
                for (int i = 0; i < chunknum; i++) {
                    sample_per_chunk[i] = samplenum;
                }
            } else {
                raf.seek(this.offset + 28);
                int chunksize1 = raf.readInt() - 1;
                raf.seek(this.offset + 20);
                int num1 = raf.readInt();
                for (int i = 0; i < chunksize1; i++) {
                    sample_per_chunk[i] = num1;
                }
                raf.seek(this.offset + 32);
                int num2 = raf.readInt();
                sample_per_chunk[chunknum - 1] = num2;
            }
            for (int i = 0; i < chunknum; i++) {
                System.out.println("第" + i + "个chunk的sample数为" + sample_per_chunk[i]);
            }
        return sample_per_chunk;
    }
}

