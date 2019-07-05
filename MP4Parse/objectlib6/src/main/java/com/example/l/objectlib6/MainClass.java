package com.example.l.objectlib6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = null;
        try {
            System.out.println("请输入MP4文件的路径：");
            Scanner scanner = new Scanner(System.in);
            String path = scanner.nextLine();
            raf = new RandomAccessFile(path,"r");
            MoovBox moovBox = GetMoovClass.getMoovBox(raf);
            TrakBox trakBox = moovBox.getTrakBox(raf);
            MdiaBox mdiaBox = trakBox.getMdiaClass(raf);
            MinfBox minfBox = mdiaBox.getMinfBox(raf);
            StblBox stblBox = minfBox.getStblBox(raf);
            StcoBox stcoBox = stblBox.getStcoBox(raf);
            StscBox stscBox = stblBox.getStscBox(raf);
            StsdBox stsdBox = stblBox.getStsdBox(raf);
            StssBox stssBox = stblBox.getStssBox(raf);
            StszBox stszBox = stblBox.getStszBox(raf);
            //1.调用方法得到chunknumber chunkoffset[]，samplenumber, 通过sampleperchunk[] sizepersample得到chunksize[]进而从mdat中取得原始视频数据
            int chunknumber = stcoBox.getChunknum(raf);
            int[] chunkoffset = stcoBox.getChunkOffset(raf,chunknumber);
            int[] sampleperchunk = stscBox.getSamplePerChunk(raf,chunknumber);
            int samplenumber = stszBox.getSampleNum(raf);
            int[] sizepersample = stszBox.getSizePerSample(raf,samplenumber);
            int[] sizeperchunk = new int[chunknumber];
            int mdatlen = 0;
            int num = 0;
            for (int i=0;i<chunknumber;i++){
                for (int j=0;j<sampleperchunk[i];j++){
                    sizeperchunk[i] += sizepersample[num+j];
                }
                num += sampleperchunk[i];
                mdatlen += sizeperchunk[i];
            }


            byte[] mdat = new byte[mdatlen];
            int k = 0;
            for (int i=0;i<chunknumber;i++){
                byte[] bData = new byte[sizeperchunk[i]];
                raf.seek(chunkoffset[i]);
                raf.read(bData);
                System.arraycopy(bData,0,mdat,k,sizeperchunk[i]);
                k += sizeperchunk[i];
            }
            //2.通过sizepernumber[],得到sampleoffset[],利用此数组  将原始数据的每一帧的前四个数据替换为00000001
            mdat[0] = 00;
            mdat[1] = 00;
            mdat[2] = 00;
            mdat[3] = 01;
            int[] sampleoffset = new int[samplenumber];
            int m = 0;
            for (int i=0;i<samplenumber - 1;i++) {

                sampleoffset[i] = m;
                m += sizepersample[i];
            }
            for (int i=0;i<samplenumber -1;i++){
                mdat[sampleoffset[i]] = 00;
                mdat[sampleoffset[i]+1] = 00;
                mdat[sampleoffset[i]+2] = 00;
                mdat[sampleoffset[i]+3] = 01;
            }

            //3.调用方法得到IDRnumber IDRSample[] sps[] pps[],在每个IDRSample之前加上00000001sps[]00000001pps[]写入文件
            int IDRnumber = stssBox.getIDRnum(raf);
            int[] IDRsample = stssBox.getIDRSample(raf,IDRnumber);

            int spslen = stsdBox.getSpslen(raf);
            int ppslen = stsdBox.getPpslen(raf,spslen);
            byte[] sps = stsdBox.getSps(raf,spslen);
            byte[] pps = stsdBox.getPps(raf,spslen,ppslen);
            byte[] bt1 = new byte[4];
            bt1[0] = 00;
            bt1[1] = 00;
            bt1[2] = 00;
            bt1[3] = 01;
            System.out.println("请输入视频流的路径:");
            Scanner scanner1 = new Scanner(System.in);
            String filepath = scanner1.nextLine();
            File file = new File(filepath);
            FileOutputStream fos = new FileOutputStream(file);
            for (int i=1;i<IDRnumber;i++){
                fos.write(bt1);
                fos.write(sps);
                fos.write(bt1);
                fos.write(pps);
                fos.write(mdat,sampleoffset[IDRsample[i-1]],sampleoffset[IDRsample[i]]-sampleoffset[IDRsample[i-1]]);
            }
            fos.write(bt1);
            fos.write(sps);
            fos.write(bt1);
            fos.write(pps);
            fos.write(mdat,sampleoffset[IDRsample[IDRnumber-1]],mdatlen-sampleoffset[IDRsample[IDRnumber-1]]);
            System.out.println("success");
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }
}
