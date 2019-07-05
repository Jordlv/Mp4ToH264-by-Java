package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TrakBox extends Box {
    public TrakBox(int startoffset,int size,String type){
        super(startoffset,size,type);
    }

    public MdiaBox getMdiaClass(RandomAccessFile raf) throws IOException{
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int len1 = raf .readInt();
        raf.skipBytes(len1 -4);
        int mdiasize = raf.readInt();
        int mdiaoffset = this.getStartoffset() + len1 + 8;
        MdiaBox mdiaBox = new MdiaBox(mdiaoffset,mdiasize,"mdia");
        return mdiaBox;
    }
}
