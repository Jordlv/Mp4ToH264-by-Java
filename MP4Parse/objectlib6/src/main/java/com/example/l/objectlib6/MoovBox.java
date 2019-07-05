package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MoovBox extends Box {
    public MoovBox(int startoffset,int size,String type){
        super(startoffset,size,type);
    }
    public TrakBox getTrakBox(RandomAccessFile raf) throws IOException{
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int len1 = raf.readInt();
        raf.skipBytes(len1 - 4);
        int len2 = raf.readInt();
        raf.skipBytes(len2 - 4);
        int len3 = raf.readInt();
        raf.skipBytes(len3 -4 );
        int traksize = raf.readInt();
        int trakoffset = this.getStartoffset() + len1 +len2 +len3 + 8;
        TrakBox trakBox = new TrakBox(trakoffset,traksize,"trak");
        return trakBox;
    }
}
