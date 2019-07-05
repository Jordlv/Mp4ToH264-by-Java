package com.example.l.lib3;

import java.io.IOException;
import java.io.RandomAccessFile;

public class VideoParseClass {


    public static StblBox OpenBox(RandomAccessFile raf) throws IOException {

        System.out.println("文件总长为："+raf.length());
        int offset = 0;//定义偏移量
        int moovoffset = 0;
        int trakoffset = 0;
        int mdiaoffset = 0;
        int minfoffset = 0;
        int stbloffset = 0;
        StblBox stblBox = new StblBox(0,0,0,0,0);
        while (offset < raf.length()) {
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            System.out.println(new String(bType) + "偏移量为："+offset);
            if(new String(bType).equals("moov")){
                moovoffset = offset;
            }
            offset = offset + len;
            raf.skipBytes(len - 8);// 跳过当前box
        }
        System.out.println(moovoffset);
        raf.seek(moovoffset);
        int moovlen = raf.readInt();
        System.out.println("moov总长度为"+moovlen);
        offset = moovoffset+8;
        raf.seek(moovoffset + 8);
        while (offset < (moovoffset+moovlen)){
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            System.out.println(new String(bType) + "偏移量为"+offset);
            if (new String(bType).equals("trak")){
                trakoffset = offset;
                Boolean bl = TrakJudgeClass.IsVedioTrak(trakoffset,raf);
                if (bl) {
                    break;
                }
            }
            offset = offset +len;
            raf.seek(offset);//跳过当前box
        }
        System.out.println(trakoffset);
        raf.seek(trakoffset);
        int  traklen = raf.readInt();
        System.out.println("trak总长度为"+traklen);
        offset = trakoffset + 8;
        raf.seek(trakoffset + 8);
        while (offset < (trakoffset+traklen)){
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            System.out.println(new String(bType) + "偏移量为"+offset);
            if (new String(bType).equals("mdia")){
                mdiaoffset = offset;
            }
            offset = offset +len;
            raf.seek(offset);//跳过当前box
        }
        System.out.println(mdiaoffset);
        raf.seek(mdiaoffset);
        int  mdialen = raf.readInt();
        System.out.println("mdia总长度为"+mdialen);
        offset = mdiaoffset + 8;
        raf.seek(mdiaoffset + 8);
        while (offset < (mdiaoffset+mdialen)){
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            System.out.println(new String(bType) + "偏移量为"+offset);
            if (new String(bType).equals("minf")){
                minfoffset = offset;
            }
            offset = offset +len;
            raf.seek(offset);//跳过当前box
        }
        System.out.println(minfoffset);
        raf.seek(minfoffset);
        int  minflen = raf.readInt();
        System.out.println("minf总长度为"+minflen);
        offset = minfoffset + 8;
        raf.seek(minfoffset + 8);
        while (offset < (minfoffset+minflen)){
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            System.out.println(new String(bType) + "偏移量为"+offset);
            if (new String(bType).equals("stbl")){
                stbloffset = offset;
            }
            offset = offset +len;
            raf.seek(offset);//跳过当前box
        }
        System.out.println(stbloffset);
        raf.seek(stbloffset);
        int  stbllen = raf.readInt();
        System.out.println("stbl总长度为"+stbllen);
        offset = stbloffset + 8;
        raf.seek(stbloffset + 8);
        while (offset < (stbloffset+stbllen)){
            byte[] bType = new byte[4];
            int len = raf.readInt();
            raf.read(bType);
            System.out.println(new String(bType) + "偏移量为"+offset);
            if (new String(bType).equals("stco")){
                stblBox.setStcooffset(offset);
            }else if(new String(bType).equals("stsc")){
                stblBox.setStscoffset(offset);
            }else if (new String(bType).equals("stsz")){
                stblBox.setStszoffset(offset);
            }else if (new String(bType).equals("stss")){
                stblBox.setStssoffset(offset);
            }else if (new String(bType).equals("stsd")){
                stblBox.setStsdoffset(offset);
            }
            offset = offset +len;
            raf.seek(offset);//跳过当前box
        }
        System.out.println("stco的偏移量为"+stblBox.getStcooffset());
        System.out.println("stsc的偏移量为"+stblBox.getStscoffset());
        System.out.println("stsz的偏移量为"+stblBox.getStszoffset());
        System.out.println("stss的偏移量为"+stblBox.getStssoffset());
        System.out.println("stsd的偏移量为"+stblBox.getStsdoffset());
        return stblBox;
    }
}

