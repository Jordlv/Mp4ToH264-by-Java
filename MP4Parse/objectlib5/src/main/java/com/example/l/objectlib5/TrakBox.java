package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TrakBox extends Box implements ContainerBox {

    public TrakBox(int offset, int length) {
        super(offset, length);
    }

    MdiaBox mdiaBox;

    @Override
    public MdiaBox getChildBox(RandomAccessFile raf)  {
        try {
            raf.seek(this.offset);
            raf.skipBytes(8);
            int len1 = raf.readInt();
            raf.skipBytes(len1 - 4);
            int len2 = raf.readInt();
            int offset = this.offset + len1+8;
            mdiaBox = new MdiaBox(offset, len2);
            System.out.println("mdia的偏移量是:"+offset);
        }catch (IOException e){
            e.printStackTrace();
        }

        return mdiaBox;


    }
}
