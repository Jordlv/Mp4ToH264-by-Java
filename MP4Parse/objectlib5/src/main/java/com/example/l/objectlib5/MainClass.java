package com.example.l.objectlib5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
        StcoBox stcoBox = null;
        StscBox stscBox = null;
        StszBox stszBox = null;
        StssBox stssBox = null;
        StsdBox stsdBox = null;
        try {
            System.out.println("请输入你要转换的MP4文件路径:");
            Scanner scan = new Scanner(System.in);
            String MP4path = scan.nextLine();
            final RandomAccessFile raf = new RandomAccessFile(MP4path, "r");
            MoovBox moovBox = GetMoovClass.getmoovBox(raf);
            TrakBox trakBox = moovBox.getChildBox(raf);
            MdiaBox mdiaBox = trakBox.getChildBox(raf);
            MinfBox minfBox = mdiaBox.getChildBox(raf);
            StblBox stblBox = minfBox.getChildBox(raf);
            raf.seek(stblBox.offset);
            int  stbllen = raf.readInt();
            System.out.println("stbl总长度为"+stbllen);
            int soffset = stblBox.offset + 8;
            raf.seek(stblBox.offset + 8);
            while (soffset < (stblBox.offset+stbllen)){
                byte[] bType = new byte[4];
                int len = raf.readInt();
                raf.read(bType);
                System.out.println(new String(bType) + "偏移量为"+soffset);
                if (new String(bType).equals("stco")){
                    stcoBox = new StcoBox(soffset,len);
                }else if(new String(bType).equals("stsc")){
                    stscBox = new StscBox(soffset,len);
                }else if (new String(bType).equals("stsz")){
                    stszBox = new StszBox(soffset,len);
                }else if (new String(bType).equals("stss")){
                    stssBox = new StssBox(soffset,len);
                }else if (new String(bType).equals("stsd")){
                    stsdBox = new StsdBox(soffset,len);
                }
                soffset += len;
                raf.seek(soffset);//跳过当前box
            }

            int chunknumber = stcoBox.getChunknum(raf);
            int[] chunkoffset = stcoBox.getChunkoffset(raf);
            int[] sampleperchunk = stscBox.getSample_per_chunk(raf,chunknumber);
            int[] sizepersample = stszBox.getSize_per_sample(raf);
            int[] chunksize = new int[chunknumber];
            int mdatlen = 0;
            for(int i=0;i<chunknumber;i++){
                for (int j=0;j<sampleperchunk[i];j++){
                    chunksize[i] += sizepersample[j];
                }
                mdatlen += chunksize[i];
            }
            byte[] mdat = new byte[mdatlen];
            int k = 0;
            for (int i=0;i<chunknumber;i++){
                byte[] bData = new byte[chunksize[i]];
                raf.seek(chunkoffset[i]);
                raf.read(bData);
                System.arraycopy(bData,0,mdat,k,chunksize[i]);
                k += chunksize[i];
            }
            int[] offsetpersample = stszBox.getOffset_per_sample(raf);
            int samplenumber = stszBox.getSamplenum(raf);

            for (int i=0;i<samplenumber -1;i++){
                mdat[offsetpersample[i]] = 00;
                mdat[offsetpersample[i]+1] = 00;
                mdat[offsetpersample[i]+2] = 00;
                mdat[offsetpersample[i]+3] = 01;
            }

            int Isamplenum = stssBox.IDRnum;
            int[] Isample = stssBox.getIDRsample(raf);


            byte[] bt1 = new byte[4];
            bt1[0] = 00;
            bt1[1] = 00;
            bt1[2] = 00;
            bt1[3] = 01;
            byte[] sps = stsdBox.getSpsbyte(raf);
            byte[] pps = stsdBox.getPpsbyte(raf);

            System.out.println("请输入输出视频码流文件的路径:");
            Scanner scanner = new Scanner(System.in);
            String path1 = scanner.nextLine();
            File file = new File(path1);
            FileOutputStream fos = new FileOutputStream(file);
            for (int i=1;i<Isamplenum;i++){
                fos.write(bt1);
                fos.write(sps);
                fos.write(bt1);
                fos.write(pps);
                fos.write(mdat,offsetpersample[Isample[i-1]],offsetpersample[Isample[i]]-offsetpersample[Isample[i-1]]);
            }
            fos.write(bt1);
            fos.write(sps);
            fos.write(bt1);
            fos.write(pps);
            fos.write(mdat,offsetpersample[Isample[Isamplenum-1]],mdatlen-offsetpersample[Isample[Isamplenum-1]]);
            System.out.println("success");
        } catch (FileNotFoundException e) {
            System.out.print("file not found");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
