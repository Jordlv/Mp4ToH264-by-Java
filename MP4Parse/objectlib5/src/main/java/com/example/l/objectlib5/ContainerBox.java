package com.example.l.objectlib5;


import java.io.RandomAccessFile;

public interface ContainerBox {
    Box getChildBox(RandomAccessFile raf);
}
