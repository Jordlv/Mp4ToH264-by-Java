package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;


public class StblBox extends Box {
    public StblBox(int startoffset,int size,String type){
        super(startoffset,size,type);
    }
    public StcoBox getStcoBox(RandomAccessFile raf) throws IOException{
        StcoBox stcoBox = null;
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int j = 0;
        for (int i=0;i<7;i++){
            int len = raf.readInt();
            byte[] btype = new byte[4];
            raf.read(btype);
            String str = new String(btype);
            if (str.equals("stco")){
                stcoBox = new StcoBox(this.getStartoffset()+8+j,len,"stco");
                break;
            }else {
                raf.skipBytes(len - 8);
            }
            j += len;
        }
        return stcoBox;
    }
    public StscBox getStscBox(RandomAccessFile raf) throws IOException{
        StscBox stscBox = null;
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int j = 0;
        for (int i=0;i<7;i++){
            int len = raf.readInt();
            byte[] btype = new byte[4];
            raf.read(btype);
            String str = new String(btype);
            if (str.equals("stsc")){
                stscBox = new StscBox(this.getStartoffset()+8+j,len,"stsc");
                break;
            }else {
                raf.skipBytes(len - 8);
            }
            j += len;
        }
        return stscBox;
    }

    public StsdBox getStsdBox(RandomAccessFile raf) throws IOException{
        StsdBox stsdBox = null;
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int j = 0;
        for (int i=0;i<7;i++){
            int len = raf.readInt();
            byte[] btype = new byte[4];
            raf.read(btype);
            String str = new String(btype);
            if (str.equals("stsd")){
                stsdBox = new StsdBox(this.getStartoffset()+8+j,len,"stsd");
                break;
            }else {
                raf.skipBytes(len - 8);
            }
            j += len;
        }
        return stsdBox;
    }

    public StssBox getStssBox(RandomAccessFile raf) throws IOException{
        StssBox stssBox = null;
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int j = 0;
        for (int i=0;i<7;i++){
            int len = raf.readInt();
            byte[] btype = new byte[4];
            raf.read(btype);
            String str = new String(btype);
            if (str.equals("stss")){
                stssBox = new StssBox(this.getStartoffset()+8+j,len,"syss");
                break;
            }else {
                raf.skipBytes(len - 8);
            }
            j += len;
        }
        return stssBox;
    }

    public StszBox getStszBox(RandomAccessFile raf) throws IOException{
        StszBox stszBox = null;
        raf.seek(this.getStartoffset());
        raf.skipBytes(8);
        int j = 0;
        for (int i=0;i<7;i++){
            int len = raf.readInt();
            byte[] btype = new byte[4];
            raf.read(btype);
            String str = new String(btype);
            if (str.equals("stsz")){
                stszBox = new StszBox(this.getStartoffset()+8+j,len,"stco");
                break;
            }else {
                raf.skipBytes(len - 8);
            }
            j += len;
        }
        return stszBox;
    }
}
