package com.example.l.lib3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class WriteDataClass {
    public static void GetResult(StblBox stb, RandomAccessFile raf, String filepath) throws IOException {

             /* 1.根据stco获取有几个chunk即chunknumber以及每个 chunk的起点
                      2.根据stsc获取每个chunk对应的sample数目
                      3.根据stsz获取每个sample的大小
                      4.累加得到每个 chunk的大小
                      5.从每个chunkoffset取相应大小的数据写入文件 */
        int stcooffset = stb.getStcooffset();
        int stscoffset = stb.getStscoffset();
        int stszoffset = stb.getStszoffset();
        int stssoffset = stb.getStssoffset();
        int stsdoffset = stb.getStsdoffset();

        raf.seek(stcooffset + 12);
        //chunk数量，见boxstco
        int chunknumber = raf.readInt();
        int[] chunksize = new int[chunknumber];//每一chunk的字节数
        int[] chunkoffset = new int[chunknumber];
        int[] sampleperchunk = new int[chunknumber];
        for (int i = 0; i < chunknumber; i++) {
            chunkoffset[i] = raf.readInt();
            //System.out.println("第" + i + "个chunk的偏移量为" + chunkoffset[i]);
        }

                    /*raf.seek(stscoffset+20);
                    for(int i=0;i<chunknumber;i++){
                        sampleperchunk[i] = raf.readInt();
                        raf.skipbytes(8);
                        System.out.println("第"+i+"个chunk的sample数为"+sampleperchunk[i]);
                    }*/
        raf.seek(stscoffset);
        int stscsize = raf.readInt();
        if (stscsize < 29) {
            raf.seek(stscoffset + 20);
            int samplenum = raf.readInt();
            for (int i = 0; i < chunknumber; i++) {
                sampleperchunk[i] = samplenum;
            }
        } else {
            raf.seek(stscoffset + 28);
            int chunksize1 = raf.readInt() - 1;
            raf.seek(stscoffset + 20);
            int num1 = raf.readInt();
            for (int i = 0; i < chunksize1; i++) {
                sampleperchunk[i] = num1;
            }
            raf.seek(stscoffset + 32);
            int num2 = raf.readInt();
            sampleperchunk[chunknumber - 1] = num2;
        }
        //for (int i = 0; i < chunknumber; i++) {
            //System.out.println("第" + i + "个chunk的sample数为" + sampleperchunk[i]);
        //}

        int mdatlen = 0;
        int samplenum = raf.readInt();
        for (int i = 0; i < chunknumber; i++) {
            for (int j = 0; j < sampleperchunk[i]; j++) {
                chunksize[i] = chunksize[i] + raf.readInt();
            }
            //System.out.println("第" + i + "个chunk的字节数是" + chunksize[i]);//每个chunk的大小
            mdatlen += chunksize[i];
        }
        System.out.println(mdatlen);
        byte[] mdat = new byte[mdatlen];
        int k = 0;
        for (int i=0;i<chunknumber;i++){
            byte[] bData = new byte[chunksize[i]];
            raf.seek(chunkoffset[i]);
            raf.read(bData);
            System.arraycopy(bData,0,mdat,k,chunksize[i]);
            k += chunksize[i];
        }
        //现在要将每个sample的前四个字节改为00000001
        mdat[0] = 00;
        mdat[1] = 00;
        mdat[2] = 00;
        mdat[3] = 01;
        raf.seek(stszoffset+20);
        int[] sampleoffset = new int[samplenum];
        int m = 0;
        for (int i=0;i<samplenum - 1;i++) {

            int samplelen = raf.readInt();
            sampleoffset[i] = m;
            //System.out.println(sampleoffset[i]);
            m += samplelen;
        }
        for (int i=0;i<samplenum -1;i++){
            mdat[sampleoffset[i]] = 00;
            mdat[sampleoffset[i]+1] = 00;
            mdat[sampleoffset[i]+2] = 00;
            mdat[sampleoffset[i]+3] = 01;
        }

        raf.seek(stsdoffset+117);
        int spslen = raf.readUnsignedByte();
        //System.out.println(spslen);
        byte[] sps = new byte[spslen];
        raf.read(sps);
        raf.skipBytes(2);
        int ppslen = raf.readUnsignedByte();
        //System.out.println(ppslen);
        byte[] pps = new byte[ppslen];
        raf.read(pps);
        raf.seek(stssoffset +12 );
        int Isamplenum = raf.readInt();
        int[] Isample = new int[Isamplenum];
        for (int i=0;i<Isamplenum;i++){
            Isample[i] = raf.readInt() -1;
        }

        byte[] bt1 = new byte[4];
        bt1[0] = 00;
        bt1[1] = 00;
        bt1[2] = 00;
        bt1[3] = 01;

        File file = new File(filepath);
        FileOutputStream fos = new FileOutputStream(file);
        for (int i=1;i<Isamplenum;i++){
            fos.write(bt1);
            fos.write(sps);
            fos.write(bt1);
            fos.write(pps);
            fos.write(mdat,sampleoffset[Isample[i-1]],sampleoffset[Isample[i]]-sampleoffset[Isample[i-1]]);
        }
        fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[Isamplenum-1]],mdatlen-sampleoffset[Isample[Isamplenum-1]]);
        System.out.println("success");
        /*fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[0]],mdat.length);//只有1个I帧

        fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[0]],sampleoffset[Isample[1]]);
        fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[1]],mdat.length-sampleoffset[Isample[1]]);//有2个I帧

        fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[0]],sampleoffset[Isample[1]]);
        fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[1]],sampleoffset[Isample[2]]-sampleoffset[Isample[1]]);
        fos.write(bt1);
        fos.write(sps);
        fos.write(bt1);
        fos.write(pps);
        fos.write(mdat,sampleoffset[Isample[2]],mdat.length-sampleoffset[Isample[2]]);*/
        raf.close();
        fos.close();
    }
}
