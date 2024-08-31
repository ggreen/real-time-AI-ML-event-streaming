package realtime.ai.ml.event.streaming.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("embedding")
public class EmbeddingController {
    private final EmbeddingModel embeddingModel;

    @PostMapping
    public List<Double> embed(@RequestBody  String text) {
        return embeddingModel.embed(text);
    }
}
