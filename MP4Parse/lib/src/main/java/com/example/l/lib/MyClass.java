package com.example.l.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
        /*
        Java提供了一个可以对文件随机访问的操作，访问包括读和写操作，同时该类的读写
        是基于指针的.RandomAccessFile以字节方式读写文件，众所周知，计算机以二进制
        形式存储文件（包括视频，音频，文字等等），RandomAccessFile是以低八位一个字
        节读写，更准确的操作二进制文件，可以这么说，运用RandomAccessFile文件就可以
        任意的读取二进制文件了。
                */
        public class MyClass {
            public static void main(String[] args) throws IOException {
                try {
                    RandomAccessFile raf = new RandomAccessFile("/home/letv/Videos/12.mp4","r");
                    System.out.println("文件总长为："+raf.length());
                    int offset = 0;//定义偏移量
                    int moovoffset = 0;
                    int trakoffset = 0;
                    int mdiaoffset = 0;
                    int minfoffset = 0;
                    int stbloffset = 0;
                    int stcooffset = 0;
                    int this.offset = 0;
                    int stszoffset = 0;
                    while (offset < raf.length()) {
                        byte[] bType = new byte[4];
                        int len = raf.readInt();
                        raf.read(bType);
                        System.out.println(new String(bType) + "偏移量为："+offset);
                        if(new String(bType).equals("moov")){
                            moovoffset = offset;
                        }
                        offset = offset + len;
                        raf.skipbytes(len - 8);// 跳过当前box
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
                            stcooffset = offset;
                        }else if(new String(bType).equals("stsc")){
                            this.offset = offset;
                        }else if (new String(bType).equals("stsz")){
                            stszoffset = offset;
                        }
                        offset = offset +len;
                        raf.seek(offset);//跳过当前box
                    }
                    System.out.println("stco的偏移量为"+stcooffset);
                    System.out.println("stsc的偏移量为"+stscoffset);
                    System.out.println("stsz的偏移量为"+stszoffset);

                   /* 1.根据stco获取有几个chunk即chunknumber以及每个 chunk的起点
                      2.根据stsc获取每个chunk对应的sample数目
                      3.根据stsz获取每个sample的大小
                      4.累加得到每个 chunk的大小
                      5.从每个chunkoffset取相应大小的数据写入文件 */
                   raf.seek(stcooffset+12);
                    //chunk数量，见boxstco
                    int chunknumber = raf.readInt();
                   int[] chunksize = new int[chunknumber];//每一chunk的字节数
                    int[] chunkoffset = new int[chunknumber];
                    int[] sampleperchunk = new int[chunknumber];
                    for(int i=0;i<chunknumber;i++){
                        chunkoffset[i] = raf.readInt();
                        System.out.println("第"+i+"个chunk的偏移量为"+chunkoffset[i]);
                    }

                    /*raf.seek(stscoffset+20);
                    for(int i=0;i<chunknumber;i++){
                        sampleperchunk[i] = raf.readInt();
                        raf.skipbytes(8);
                        System.out.println("第"+i+"个chunk的sample数为"+sampleperchunk[i]);
                    }*/

                    raf.seek(stscoffset+28);
                    int chunksize1 = raf.readInt()-1;
                    raf.seek(stscoffset+20);
                    int num1 = raf.readInt();
                    for(int i=0;i<chunksize1;i++){
                        sampleperchunk[i] = num1;
                    }
                    raf.seek(stscoffset+32);
                    int num2 = raf.readInt();
                    sampleperchunk[chunknumber-1] = num2;
                    for (int i=0;i<chunknumber;i++){
                        System.out.println("第"+i+"个chunk的sample数为"+sampleperchunk[i]);
                    }

                    raf.seek(stszoffset+20);
                    for(int i=0;i<chunknumber;i++){
                        for (int j=0;j<sampleperchunk[i];j++){
                            chunksize[i] = chunksize[i] + raf.readInt();
                        }
                        System.out.println("第"+i+"个chunk的字节数是"+chunksize[i]);//每个chunk的大小
                    }
                    File file = new File("/home/letv/Videos/videoes2.264");
                    FileOutputStream fos = new FileOutputStream(file);
                    for(int k=0;k<chunknumber;k++){
                        byte[] bData = new byte[chunksize[k]];
                        raf.seek(chunkoffset[k]);
                        raf.read(bData);
                        fos.write(bData);
                    }
                } catch (FileNotFoundException e) {
                    System.out.print("file not found");
                }
            }
        }