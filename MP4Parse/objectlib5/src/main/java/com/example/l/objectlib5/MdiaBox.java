package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MdiaBox extends Box implements ContainerBox {
    public MdiaBox(int offset,int length){
        super(offset,length);
    }
        MinfBox minfBox;
    @Override
    public MinfBox getChildBox(RandomAccessFile raf) {
        try {
            raf.seek(this.offset);
            raf.skipBytes(8);
            int len1 = raf.readInt();
            raf.skipBytes(len1 -4);
            int len2 = raf.readInt();
            offset = this.offset + len1 + len2 + 8;
            raf.skipBytes(len2 -4 );
            int len3 = raf.readInt();
            minfBox = new MinfBox(offset,len3);
            System.out.println("minf的偏移量是"+offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return minfBox;
    }
}
