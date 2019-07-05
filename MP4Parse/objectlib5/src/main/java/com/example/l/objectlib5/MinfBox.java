package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MinfBox extends Box implements ContainerBox {
    public MinfBox(int offset,int length){
        super(offset,length);
    }
    StblBox stblBox;
    @Override
    public StblBox getChildBox(RandomAccessFile raf)  {
        try {
            raf.seek(this.offset);
            raf.skipBytes(8);
            int len1 = raf.readInt();
            raf.skipBytes(len1 -4);
            int len2 = raf.readInt();
            offset = this.offset + len1 + len2 + 8;
            raf.skipBytes(len2 -4 );
            int len3 = raf.readInt();
            stblBox = new StblBox(offset,len3);
            System.out.println("stbl的偏移量是："+offset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stblBox;
    }
}
