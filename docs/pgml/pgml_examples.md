


```sql

```SELECT
    'What is pgvector?'::text AS question,
    pgml.embed('sentence-transformers/all-MiniLM-L6-v2', 'What is pgvector?')::vector AS query_embedding
```


```shell
select pgml.embed('sentence-transformers/all-MiniLM-L6-v2', 'What is pgvector?');
```
