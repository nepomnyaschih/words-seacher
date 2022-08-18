package com.example.demo.file;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

@Service
public class FileService implements IReadFileService, IWriteFileService {

    @Override
    public String readFile(String fileName)
            throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        return readFile(inputStream);
    }

    @Override
    public String readFile(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Override
    public byte[] getFileContent(Map<String, Integer> data) {
        StringBuilder str = new StringBuilder();
        for (String s : data.keySet()) {
            str.append(" ").append(s).append(": ").append(data.get(s)).append("\n");
        }
        return str.toString().getBytes();
    }

    @Override
    public void writeFile(String name, Map<String, Integer> data) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(name);
        outputStream.write(getFileContent(data));
        outputStream.close();
    }
}
