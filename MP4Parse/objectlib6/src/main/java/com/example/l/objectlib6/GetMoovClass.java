package com.example.l.objectlib6;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GetMoovClass {
    public static MoovBox getMoovBox(RandomAccessFile raf) throws IOException {
        MoovBox moovBox = null;
        int offset = 0;//定义偏移量
        while (offset < raf.length()) {
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            String str = new String(bType);
            if (str.equals("moov")) {
                moovBox = new MoovBox(offset, len, str);
            }
            offset = offset + len;
            raf.skipBytes(len - 8);// 跳过当前box
        }
        return moovBox;
    }
}
