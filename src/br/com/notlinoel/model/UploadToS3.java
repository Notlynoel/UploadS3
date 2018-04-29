package br.com.notlinoel.model;

import java.io.File;
import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class UploadToS3 {
   
    public static void main(String[] args) throws InterruptedException,IOException{
        final AmazonS3Client amazonS3Client = new AmazonS3Client();
        long lengthOfFileToUpload;
        Bucket newBucket;
        File google;
        final String bucketName = "imagetrading";
        final String key = "google.jpg";
        {
        newBucket = amazonS3Client.createBucket((bucketName));
        URL urlToFile = UploadToS3.class.getResource("/google.PNG");
        String fullFilePath = urlToFile.getFile();
        google = new File(fullFilePath);
        lengthOfFileToUpload = google.length();
        
        }
        {
        PutObjectRequest putObjectRequest = new PutObjectRequest(newBucket.getName(),key,google);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(lengthOfFileToUpload);
            putObjectRequest.withMetadata(objectMetadata);
            amazonS3Client.putObject(putObjectRequest);
        }
        {
        URL url = amazonS3Client.generatePresignedUrl(bucketName, key,Date.from(Instant.now().plus(5,ChronoUnit.MINUTES)));
            System.out.println(url);
        }
        
    }
    
}﻿