package com.example.demo.file;

import java.io.IOException;
import java.io.InputStream;

public interface IReadFileService {

    String readFile(String name) throws IOException;

    String readFile(InputStream inputStream) throws IOException;

}
