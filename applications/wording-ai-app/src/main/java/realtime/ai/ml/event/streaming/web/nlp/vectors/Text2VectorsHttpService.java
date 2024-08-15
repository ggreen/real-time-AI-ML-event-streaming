package realtime.ai.ml.event.streaming.web.nlp.vectors;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class Text2VectorsHttpService implements Text2Vectors{

    private final RestTemplate restTemplate;
    private final URI url;

    @Override
    public List<Double> convert(String text) {
        Double[] results = restTemplate.postForObject(url,text,Double[].class);


        return Arrays.stream(results).collect(Collectors.toList());
    }
}
