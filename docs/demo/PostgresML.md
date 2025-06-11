

Sentimental Analysis

```sql
select sentiment.positivity::json->0->>'label' as "label",
            sentiment.positivity::json->0->'score' as "score"
            from
            (SELECT pgml.transform(
                task   => 'text-classification',
                inputs => ARRAY[
                    'I Love Spring'
                ]
            ) AS positivity) sentiment;
```


Answer question based on Context 

```sql
SELECT pgml.transform(
    'question-answering',
    inputs => ARRAY[
        '{"question": "Am I dreaming?", "context": "I got a good nights sleep last night, and started a simple tutorial over my cup of morning coffee. The capabilities seem unreal, compared to what I came to expect from the simple SQL standard I studied so long ago. The answer is staring me in the face, and I feel the uncanny call from beyond the screen calling me to check the results."}'
    ]
) AS result;
```




```sql
SELECT pgml.transform(
    'question-answering',
    inputs => ARRAY[
        '{"question": "Who is the Greatest BasketBall Player of Time?", "context": "Player LeBron James has NBA Championships: 4  NBA Finals MVP: 4  Regular Season MVP: 4  All-Star Appearances: 20  All-NBA Selections: 20  All-Defensive Teams: 6  Rookie of the Year: 1  NBA Cup MVP: 1  All-Star Game MVP: 3  Olympic Gold Medals: 3  NBA All-Time Leading Scorer: 6-time NBA Champion (1991-1993, 1996-1998): He led the Chicago Bulls to six titles., Player Michael Jordan has 6-time NBA Finals MVP (1991-1993, 1996-1998): He was named Finals MVP in each of his championship seasons. 5-time NBA MVP (1988, 1991, 1992, 1996, 1998): He was named league MVP five times. 14-time NBA All-Star: He was selected to the All-Star Game 14 times. 10-time All-NBA First Team: He was selected to the All-NBA First Team ten times. 9-time NBA All-Defensive First Team: He was selected to the All-Defensive First Team nine times. 10-time NBA Scoring Champion: He led the league in scoring ten times. NBA Rookie of the Year (1985): He won the Rookie of the Year award in his debut season. NBA Defensive Player of the Year (1988): He was named Defensive Player of the Year."}'
    ]
) AS result;
```

Player LeBron James has NBA Championships: 4  NBA Finals MVP: 4  Regular Season MVP: 4  All-Star Appearances: 20  All-NBA Selections: 20  All-Defensive Teams: 6  Rookie of the Year: 1  NBA Cup MVP: 1  All-Star Game MVP: 3  Olympic Gold Medals: 3  NBA All-Time Leading Scorer: 6-time NBA Champion (1991-1993, 1996-1998): He led the Chicago Bulls to six titles., Player Michael Jordan has 6-time NBA Finals MVP (1991-1993, 1996-1998): He was named Finals MVP in each of his championship seasons. 5-time NBA MVP (1988, 1991, 1992, 1996, 1998): He was named league MVP five times. 14-time NBA All-Star: He was selected to the All-Star Game 14 times. 10-time All-NBA First Team: He was selected to the All-NBA First Team ten times. 9-time NBA All-Defensive First Team: He was selected to the All-Defensive First Team nine times. 10-time NBA Scoring Champion: He led the league in scoring ten times. NBA Rookie of the Year (1985): He won the Rookie of the Year award in his debut season. NBA Defensive Player of the Year (1988): He was named Defensive Player of the Year.

```sql
SELECT pgml.transform(
    'question-answering',
    inputs => ARRAY[
        '{"question": "Am I dreaming?", "context": "I got a good nights sleep last night, and started a simple tutorial over my cup of morning coffee. The capabilities seem unreal, compared to what I came to expect from the simple SQL standard I studied so long ago. The answer is staring me in the face, and I feel the uncanny call from beyond the screen calling me to check the results."}'
    ]
) AS result;
```


```sql
SELECT pgml.predict(
  'llama3',
  messages := '[{"role": "user", "content": "Tell me a short, funny programming joke."}]'
);
```

```sql
SELECT pgml.load_dataset('diabetes');
SELECT * FROM pgml.diabetes LIMIT 10;

-- train a simple model on the data
SELECT * FROM pgml.train('Diabetes Progression', 'regression', 'pgml.diabetes', 'target');

-- check out the predictions
SELECT age, sex, pgml.predict('Diabetes Progression', ARRAY[age, sex, bmi, bp, s1, s2, s3, s4, s5, s6]) AS prediction
FROM pgml.diabetes 
LIMIT 10;

-- Check predictions against a specific model id
SELECT model_id, target, pgml.predict(model_id, ARRAY[age, sex, bmi, bp, s1, s2, s3, s4, s5, s6]) AS prediction
FROM pgml.diabetes
CROSS JOIN LATERAL (
    SELECT pgml.models.id AS model_id FROM pgml.models
    INNER JOIN pgml.projects
    ON pgml.models.project_id = pgml.projects.id
    WHERE pgml.projects.name = 'Diabetes Progression'
    LIMIT 1
) models
LIMIT 10;
```