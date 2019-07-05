package com.example.l.objectlib5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GetMoovClass {
    public static MoovBox getmoovBox(RandomAccessFile raf) {
        int offset = 0;
        MoovBox moovBox = null;
       try{ while (offset < raf.length()) {
           byte[] bType = new byte[4];
           int len = raf.readInt();
           raf.read(bType);
           System.out.println(new String(bType) + "偏移量为：" + offset);
           if (new String(bType).equals("moov")) {
               moovBox = new MoovBox(offset,len);
           }
           offset = offset + len;
           raf.skipBytes(len - 8);// 跳过当前box
       }

        }catch (IOException e){
           e.printStackTrace();
       }
        return moovBox;
    }

}
