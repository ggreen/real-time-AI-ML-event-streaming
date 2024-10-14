package realtime.ai.ml.event.streaming.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@RestController
@RequestMapping("letters/posts")
@Slf4j
@RequiredArgsConstructor
public class LetterPostController {
    private final Publisher<Letter> publisher;

    @PostMapping
    public void postLetter(@RequestBody Letter letter) {
        publisher.send(letter);
        log.info("SENT {}",letter);
    }
}
