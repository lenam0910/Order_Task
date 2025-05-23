package com.Od_Tasking.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@Getter
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient client = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            return client;
        } catch (Exception e) {
            throw new RuntimeException("MinIO initialization failed", e);
        }
    }


//    @PostConstruct
//    public void initBucket() {
//        try {
//            MinioClient client = MinioClient.builder()
//                    .endpoint(url)
//                    .credentials(accessKey, secretKey)
//                    .build();
//            boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!bucketExists) {
//                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//                log.info("Bucket {} created successfully", bucketName);
//            }
//        } catch (Exception e) {
//            log.error("Failed to initialize bucket: {}", e.getMessage());
//            throw new RuntimeException("MinIO initialization failed", e);
//        }
//    }
}