package com.example.ms1.Amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.example.ms1.service.FileStoreService;
import io.findify.s3mock.S3Mock;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;


import java.io.*;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmazonS3Testing {
    Logger logger = LoggerFactory.getLogger(AmazonS3Testing.class);
    public S3Mock s3Mock;
    @Autowired
    public AmazonS3 s3Client;

    public FileStoreService fileStoreService;

    private String accessKey;
    private String secretKey;
    private String bucketName;


    @BeforeAll
    public void init() {
        accessKey = "AKIA4I4NUVYKGUHIH7RH";
        secretKey = "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";
        bucketName = "ms1-testing-bucket";
        fileStoreService = new FileStoreService();

        this.s3Mock = S3Mock.create(8001, "/tmp/s3");
        this.s3Mock.start();
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_SOUTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();


        assertTrue(s3Client.doesBucketExistV2(bucketName));
    }


    @Test
    public void UploadFileToS3_ExpectTrueIfTheFileExistOnS3() {
        logger.info("Upload file in S3Mock.");
        File mockedFile = Mockito.mock(File.class);


        s3Client.putObject(bucketName, "test","file");
        assertTrue(s3Client.doesObjectExist(bucketName, "test"));
    }
}
