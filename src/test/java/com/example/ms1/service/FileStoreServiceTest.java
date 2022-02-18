package com.example.ms1.service;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileStoreServiceTest {

    private FileStoreService fileStoreService;

    @BeforeAll
    void setUp() {
        fileStoreService = new FileStoreService();
    }

    @Test
    public void testConvertFileToMultiPart() throws IOException {
        MockMultipartFile mockedFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        assertNotNull(fileStoreService.convertMultiPartToFile(mockedFile));
    }
}
