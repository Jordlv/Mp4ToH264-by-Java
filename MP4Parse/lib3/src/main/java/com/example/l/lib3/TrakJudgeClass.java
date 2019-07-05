package com.example.l.lib3;

import java.io.IOException;
import java.io.RandomAccessFile;


public class TrakJudgeClass {
    public static Boolean IsVedioTrak (int trakoffset, RandomAccessFile raf) throws IOException {
        Boolean bl = false;
        raf.seek(trakoffset+8);
        int tkhdlen = raf.readInt();
        raf.skipBytes(tkhdlen -4);//跳过tkhdbox
        raf.skipBytes(8);//跳过mida描述信息
        int mdhdlen = raf.readInt();
        raf.skipBytes(mdhdlen+12);
        byte[] handlerType = new byte[4];
        raf.read(handlerType);
        if (new String(handlerType).equals("vide")){
            bl = true;
        }
        return bl;
    }
}