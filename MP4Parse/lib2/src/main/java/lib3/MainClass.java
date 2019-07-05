package lib3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws IOException {
        try {
            System.out.println("请输入你要转换的MP4文件路径:");
            Scanner scan = new Scanner(System.in);
            String MP4path = scan.nextLine();
            RandomAccessFile raf = new RandomAccessFile(MP4path, "r");
            StblBox stb = VideoParseClass.OpenBox(raf);

            System.out.println("请输入输出视频码流文件的路径:");
            Scanner scanner = new Scanner(System.in);
            String path1 = scanner.nextLine();
            WriteDataClass.GetResult(stb,raf,path1);

        } catch (FileNotFoundException e) {
            System.out.print("file not found");
        }
    }
}
