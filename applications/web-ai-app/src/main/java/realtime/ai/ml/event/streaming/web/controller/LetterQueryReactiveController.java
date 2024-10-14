package realtime.ai.ml.event.streaming.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import realtime.ai.ml.event.streaming.web.repository.LetterRepository;
import realtime.ai.ml.event.streaming.web.repository.entity.LetterPost;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadFactory;


@RestController
@RequestMapping("letters/stream")
@Slf4j
public class LetterQueryReactiveController {
    private final ThreadFactory threadFactory;
    private final LetterRepository letterRepository;
    private final int limit;
    private final long refreshRateSecs;

    public LetterQueryReactiveController(@Qualifier("applicationTaskExecutor")
                                         ThreadFactory threadFactory,
                                         LetterRepository letterRepository,
                                         @Value("${web.ai.letter.query.limit:5}")
                                         int limit,
                                         @Value("${web.ai.letter.query.refresh.rate.secs:2}")
                                         long refreshRateSecs) {
        this.threadFactory = threadFactory;
        this.letterRepository = letterRepository;
        this.limit = limit;
        this.refreshRateSecs = refreshRateSecs;
    }


    @GetMapping("{receiver}")
    public Flux<ServerSentEvent<List<LetterPost>>> streamLetters(@PathVariable String receiver) {
        log.info("id: {}",receiver);

        var scheduler = Schedulers.newParallel(5,threadFactory);

        return Flux.interval(Duration.ofSeconds(refreshRateSecs),scheduler)
                .map(sequence -> ServerSentEvent.<List<LetterPost>> builder()
                        .data(this.letterRepository.findByLetterReceiverOrderByLetterTimeMsDesc(receiver, Limit.of(limit)))
                        .build());
    }
}
