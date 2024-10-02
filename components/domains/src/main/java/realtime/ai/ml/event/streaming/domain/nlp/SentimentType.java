package realtime.ai.ml.event.streaming.domain.nlp;

public enum SentimentType
{
    VERY_NEGATIVE(0),
    NEGATIVE(1),
    NEUTRAL(2),
    POSITIVE(3),
    VERY_POSITIVE(4);
    private final int value;


    SentimentType(int value)
    {
        this.value = value;
    }
}
