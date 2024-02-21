package gemini.demo.geminivisiondemo;


import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gemini-pro-vision")
public class GeminiVisionController {
    @Autowired
    private GenerativeModel generativeModel;

    @PostMapping
    public String file(@RequestParam("file") MultipartFile file, @RequestParam("query") String query) throws IOException {

        GenerateContentResponse generateResponse = generativeModel.generateContent(ContentMaker.fromMultiModalData(
                PartMaker.fromMimeTypeAndData(file.getContentType(), file.getBytes()),
                query));

        generateResponse.getAllFields().forEach((k, v) -> System.out.println(k + " : " + v));
        return ResponseHandler.getText(generateResponse);
    }
}
