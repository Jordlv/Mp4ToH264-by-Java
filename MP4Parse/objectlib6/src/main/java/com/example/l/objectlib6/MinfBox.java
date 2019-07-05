package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MinfBox extends Box {
    public MinfBox(int startoffset,int size,String type){
        super(startoffset,size,type);
    }
    public StblBox getStblBox(RandomAccessFile raf) throws IOException{
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int len1 = raf.readInt();
        raf.skipBytes(len1-4);
        int len2 = raf.readInt();
        raf.skipBytes(len2-4);
        int stblsize = raf.readInt();
        int stbloffset = this.getStartoffset() + len1 + len2 + 8;
        StblBox stblBox = new StblBox(stbloffset,stblsize,"stbl");
        return stblBox;
    }
}
