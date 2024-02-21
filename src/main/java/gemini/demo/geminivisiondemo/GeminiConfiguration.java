package gemini.demo.geminivisiondemo;

import com.google.cloud.vertexai.Transport;
import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class GeminiConfiguration {

    @Value("${openai-server.google.service-account-key}")
    private String key;

    private ServiceAccountCredentials getCredentials() throws IOException {
        return ServiceAccountCredentials.fromStream(new ByteArrayInputStream(key.getBytes()));
    }

    @Bean
    public GenerativeModel generativeModel() throws IOException {
        GenerationConfig generationConfig = GenerationConfig.newBuilder()
                .setMaxOutputTokens(2048)
                .setTemperature(0.4F)
                .setTopK(32)
                .setTopP(1F)
                .build();

        VertexAI vertex = new VertexAI("gcp-ai-freddycopilot", "us-central1", this.getCredentials());

        GenerativeModel model = new GenerativeModel("gemini-1.0-pro-vision", generationConfig, vertex, Transport.REST);

        System.out.println("Initialized generative model.");

        return model;
    }
}
