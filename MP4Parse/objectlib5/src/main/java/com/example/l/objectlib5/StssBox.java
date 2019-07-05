package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StssBox extends Box {

    public StssBox(int offset, int length){
        super(offset,length);
    }
    int IDRnum;
    int[] IDRsample = new int[IDRnum];

    public int[] getIDRsample(RandomAccessFile raf){
        try{
            raf.seek(this.offset +12 );

        IDRnum = raf.readInt();
        int[] Isample = new int[IDRnum];
        for (int i=0;i<IDRnum;i++){
            IDRsample[i] = raf.readInt() -1;
        }
    }catch (IOException e){
        e.printStackTrace();
        }
        return IDRsample;
    }
}
