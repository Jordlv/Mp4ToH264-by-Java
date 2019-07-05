package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MdiaBox extends Box {
    public MdiaBox(int startoffset,int size,String type){
        super(startoffset,size,type);
    }
    public MinfBox getMinfBox(RandomAccessFile raf)throws IOException{
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int len1 = raf.readInt();
        raf.skipBytes(len1 - 4);
        int len2 = raf.readInt();
        raf.skipBytes(len2 -4);
        int minfsize = raf.readInt();
        int minfoffset = this.getStartoffset() + len1 + len2 + 8;
        MinfBox minfBox = new MinfBox(minfoffset,minfsize,"minf");
        return minfBox;
    }


}
