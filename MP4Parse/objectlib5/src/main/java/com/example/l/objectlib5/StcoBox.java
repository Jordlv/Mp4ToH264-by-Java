package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StcoBox extends Box {

    public StcoBox(int offset,int len){
        super(offset,len);
    }


    int chunknum;
    int[] chunkoffset;

    public int getChunknum(RandomAccessFile raf) throws IOException{
        raf.seek(this.offset + 12);
        chunknum = raf.readInt();
        return chunknum;
    }

    public int[] getChunkoffset(RandomAccessFile raf) {
        try {
            raf.seek(this.offset + 12);
            //chunk数量，见boxstco
            chunknum = raf.readInt();
            chunkoffset = new int[chunknum];
            for (int i = 0; i < chunknum; i++) {
                chunkoffset[i] = raf.readInt();
                System.out.println("第" + i + "个chunk的偏移量为" + chunkoffset[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunkoffset;
    }
}

