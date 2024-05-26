package com.btb.migblog.services.leonardo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeonardoService {

    private RestClient restClient;
    private final ObjectMapper objectMapper;

    private final Map<String, String> models = new HashMap<>();

    @Value("${leonardo.api.key}")
    private String apiToken;

    @PostConstruct
    void init() {
        restClient = RestClient.builder()
                .baseUrl("https://cloud.leonardo.ai/api/rest/v1")
                .defaultHeaders(headers -> {
                    headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
                    headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
                    headers.add("authorization", "Bearer " + apiToken);
                }).build();

        // prefill models map
        models.put("Leonardo Anime XL", "e71a1c2f-4f80-4800-934f-2c68979d8cc8");
        models.put("Leonardo Lightning XL", "b24e16ff-06e3-43eb-8d33-4416c2d75876");
        models.put("SDXL 1.0", "16e7060a-803e-4df3-97ee-edcfa5dc9cc8");
        models.put("Leonardo Kino XL", "aa77f04e-3eec-4034-9c07-d0f619684628");
        models.put("Leonardo Vision XL", "5c232a9e-9061-4777-980a-ddc8e65647c6");
        models.put("Leonardo Diffusion XL", "1e60896f-3c26-4296-8ecc-53e2afecc132");
        models.put("AlbedoBase XL", "2067ae52-33fd-4a82-bb92-c2c55e7d2786");
        models.put("RPG v5", "f1929ea3-b169-4c18-a16c-5d58b4292c69");
        models.put("SDXL 0.9", "b63f7119-31dc-4540-969b-2a9df997e173");
        models.put("3D Animation Style", "d69c8273-6b17-4a30-a13e-d6637ae1c644");
        models.put("DreamShaper v7", "ac614f96-1082-45bf-be9d-757f2d31c174");
        models.put("Absolute Reality v1.6", "e316348f-7773-490e-adcd-46757c738eb7");
        models.put("Anime Pastel Dream", "1aa0f478-51be-4efd-94e8-76bfc8f533af");
        models.put("DreamShaper v6", "b7aa9939-abed-4d4e-96c4-140b8c65dd92");
        models.put("DreamShaper v5", "d2fb9cf9-7999-4ae5-8bfe-f0df2d32abf8");
        models.put("Leonardo Diffusion", "b820ea11-02bf-4652-97ae-9ac0cc00593d");
        models.put("RPG 4.0", "a097c2df-8f0c-4029-ae0f-8fd349055e61");
        models.put("Deliberate 1.1", "458ecfff-f76c-402c-8b85-f09f6fb198de");
        models.put("Vintage Style Photography", "17e4edbf-690b-425d-a466-53c816f0de8a");
        models.put("DreamShaper 3.2", "f3296a34-9aef-4370-ad18-88daf26862c3");
        models.put("Leonardo Select", "cd2b2a15-9760-4174-a5ff-4d2925057376");
        models.put("Leonardo Creative", "6bef9f1b-29cb-40c7-b9df-32b51c1f67d3");
        models.put("Battle Axes", "47a6232a-1d49-4c95-83c3-2cc5342f82c7");
        models.put("Pixel Art", "e5a291b6-3990-495a-b1fa-7bd1864510a6");
        models.put("Crystal Deposits", "102a8ee0-cf16-477c-8477-c76963a0d766");
        models.put("Chest Armor", "302e258f-29b5-4dd8-9a7c-0cd898cb2143");
        models.put("Magic Potions", "45ab2421-87de-44c8-a07c-3b87e3bfdf84");
        models.put("Magic Items", "2d18c0af-374e-4391-9ca2-639f59837c85");
        models.put("Character Portraits", "6c95de60-a0bc-4f90-b637-ee8971caf3b0");
        models.put("Amulets", "ff883b60-9040-4c18-8d4e-ba7522c6b71d");
        models.put("Christmas Stickers", "4b2e0f95-f15e-48d8-ada3-c071d6104db8");
        models.put("Cute Characters", "50c4f43b-f086-4838-bcac-820406244cec");
        models.put("Spirit Creatures", "5fdadebb-17ae-472c-bf76-877e657f97de");
        models.put("Cute Animal Characters", "6908bfaf-8cf2-4fda-8c46-03f892d82e06");
        models.put("Isometric Scifi Buildings", "7a65f0ab-64a7-4be2-bcf3-64a1cc56f627");
        models.put("Isometric Fantasy", "ab200606-5d09-4e1e-9050-0b05b839e944");
        models.put("Shields", "ee0fc1a3-aacb-48bf-9234-ada3cc02748f");
        models.put("Crystal Deposits Alternate", "5fce4543-8e23-4b77-9c3f-202b3f1c211e");
        models.put("Isometric Asteroid Tiles", "756be0a8-38b1-4946-ad62-c0ac832422e3");
        models.put("Leonardo Signature", "291be633-cb24-434f-898f-e662799936ad");
    }

    public LeonardoAPI.Models updateModels() {
        LeonardoAPI.Models response = restClient.get()
                .uri("/platformModels")
                .retrieve()
                .body(LeonardoAPI.Models.class);
        // update current list of models
        if (response != null && response.models() != null) {
            models.clear();
            response.models().forEach(model -> models.put(model.name(), model.id()));
        }
        return response;
    }

    @SneakyThrows
    public LeonardoAPI.PromptGenerationResponse improvePrompt(String prompt) {
        String message = objectMapper.writeValueAsString(new LeonardoAPI.PromptGeneration(prompt, null));

        return restClient.post()
                .uri("/prompt/improve")
                .contentType(MediaType.APPLICATION_JSON)
                .body(message)
                .retrieve()
                .body(LeonardoAPI.PromptGenerationResponse.class);
    }

    @SneakyThrows
    public LeonardoAPI.SDGenerationResponse createImage(LeonardoImageOptions prompt) {
        String message = objectMapper.writeValueAsString(prompt);

        return restClient.post()
                .uri("/generations")
                .contentType(MediaType.APPLICATION_JSON)
                .body(message)
                .retrieve()
                .body(LeonardoAPI.SDGenerationResponse.class);
        }

    @SneakyThrows
    public LeonardoAPI.GenerationsResponse getImage(String imageId) {
        boolean ready = false;
        LeonardoAPI.GenerationsResponse response;
        do {
            Thread.sleep(1000);
            response = restClient.get()
                .uri("/generations/" + imageId)
                .retrieve()
                .body(LeonardoAPI.GenerationsResponse.class);
            // check if valid response
            if (response != null && response.generationsByPk() != null) {
                ready = response.generationsByPk().status().equals("COMPLETE");
            }
        } while (!ready);

        return response;
    }
}
