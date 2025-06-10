

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


Text Summarization

```sql
SELECT pgml.transform( 
task => '{ "task": "summarization", "model": "google/pegasus-xsum"}'::JSONB, 
inputs => array[ 'As a Java developer exploring the world of generative AI, you’re probably aware of several frameworks that promise to make AI integration easy. I believe Spring AI stands out as the natural choice, especially for developers already working within the Spring ecosystem. Built on the same foundation as Spring Boot and Spring Data, Spring AI makes adding AI capabilities to your applications seamless and intuitive, without requiring you to learn an entirely new set of paradigms. Seamless Integration with the Spring Ecosystem One of Spring AI’s most significant advantages is its deep integration with the Spring ecosystem. If you’re already familiar with developing Spring Boot applications, Spring AI will feel like an extension of what you already know. The same concepts that drive Spring Data—such as dependency injection, annotations, and clean abstractions—apply to Spring AI as well. This consistency means you can dive into AI without needing to rethink how you develop your application. Unlike some tools, Spring AI doesn’t require complex configurations or workflow setups to get started. It fits neatly alongside your existing codebase, allowing you to reuse your existing beans, services, and repositories. Instead of bolting on an external AI platform, Spring AI integrates directly with the business logic and enterprise services you’ve already built, leveraging your investment in the Spring ecosystem. Simplifying AI for Enterprise Applications Spring AI focuses on simplifying AI operations for enterprise-grade applications. It’s ideal for developers who need to add straightforward AI features—like text generation, embeddings, and function calling—into their business applications. The beauty of Spring AI lies in its simplicity: you get the power of generative AI without the overhead of managing complex workflows or orchestrating multi-step processes. For most enterprise use cases, where AI serves to enhance features rather than power entire workflows, Spring AI delivers exactly what’s needed. Vector Store Integration: Extending Springs Data Handling Capabilities Another key strength of Spring AI is its integration with vector stores. Whether you’re using Postgres with pgVector, Redis, or any of the other supported vector-capable databases, Spring AI extends Spring’s well-known capabilities to handle vector embeddings and other AI-driven data storage needs. Spring AI makes it incredibly easy to switch between different vector store implementations. Using the same dependency injection patterns familiar to Spring developers, you can swap out one vector store for another simply by changing a Spring Boot Starter dependency—without touching your core application logic. This flexibility is a significant advantage for developers building AI-enhanced applications that require scalable, efficient storage and retrieval of embeddings or other AI-generated data. Embedding source data into a vector store is a powerful capability, but how do you handle different data structures before embedding? With Spring AI, you have access to ready-to-use document readers that allow you to process various formats, including PDF, Markdown, Microsoft Word and PowerPoint documents, HTML, and more. The Document Reader feature ensures that you can work with a wide variety of source formats. If a particular format isnt supported, creating your own implementation is straightforward.'])::json->0->>'summary_text' as summary_text;

```