package com.example.demo.file;

import java.io.IOException;
import java.util.Map;

public interface IWriteFileService {
    void writeFile(String name, Map<String, Integer> data) throws IOException;

    byte[] getFileContent(Map<String, Integer> data);
}
