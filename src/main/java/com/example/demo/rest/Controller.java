package com.example.demo.rest;

import com.example.demo.file.FileService;
import com.example.demo.search.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class Controller {

    final private SearchService searchService;
    final private FileService fileService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String mainPage() {
        return "<form method=\"POST\" enctype=\"multipart/form-data\" id=\"fileUploadForm\" action=\"/\">\n" +
                "    FILE:<input type=\"file\" name=\"file\"/><br/><br/>\n" +
                "    WORDS:<input type=\"words\" name=\"words\"/><br/>\n" +
                "    IGNORECASE:<input type=\"checkbox\" name=\"ignoreCase\"/><br/><br/>\n" +
                "    <input type=\"submit\" value=\"Submit\" id=\"btnSubmit\"/>\n" +
                "</form>";
    }

    @RequestMapping(value = { "/" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpEntity<byte[]> call(
            @RequestParam("file") MultipartFile file,
            @RequestParam String words,
            @RequestParam(defaultValue = "false") boolean ignoreCase

    ) {

        var w = words.replaceAll("[^A-z,]", "").split(",");
        List<String> wordList = Arrays.asList(w);

        try {
            var fileData = fileService.readFile(file.getInputStream());
            var res = searchService.call(fileData, wordList, ignoreCase);
            var resFileData = fileService.getFileContent(res);

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_PDF);
            header.set(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=result.txt");
            header.setContentLength(resFileData.length);

            return new HttpEntity<>(resFileData, header);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/readFile")
    public String file(
            @RequestParam String w1,
            @RequestParam String w2
    )  {
        try {
            String data =  fileService.readFile("inputFile");
            var res = searchService.call(data, List.of(w1, w2), false);
            fileService.writeFile("outputFile", res);
            return res.toString();
        } catch (IOException e){
            return "errrr";
        }
    }

}
