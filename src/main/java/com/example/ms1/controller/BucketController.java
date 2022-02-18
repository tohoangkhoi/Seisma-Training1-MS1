package com.example.ms1.controller;


import com.example.ms1.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/csv")
@CrossOrigin("*")
public class BucketController {
    @Autowired
    private FileStoreService fileStoreService;


    @Value("${ms2_sqs_url}")
    private  String ms2QueueUrl;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;


    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        //Store the file s3, send the link to MS2 Queue.
        if(file.getOriginalFilename().contains(".csv")) {
            queueMessagingTemplate.convertAndSend(ms2QueueUrl, this.fileStoreService.uploadFile(file));
            return new ResponseEntity<String>( "Upload successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Please input a csv file", HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.fileStoreService.deleteFileFromS3Bucket(fileUrl);
    }


}
