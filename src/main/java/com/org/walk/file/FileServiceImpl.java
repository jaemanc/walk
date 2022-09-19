package com.org.walk.file;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.CoordinatesEntity;
import com.org.walk.course.dto.CourseConfigDto;
import com.org.walk.file.dto.FileDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final Logger log_error = LogManager.getLogger("com.error");
    private final Logger log_file = LogManager.getLogger("com.file");

    @Autowired
    FileRepository fileRepository;

    @Override
    public FileDto uploadFile(File file, String category) {

        try {

            String configFilePath = System.getProperty("user.home")+"/walkConfig.json";

            CourseConfigDto courseConfigDto = new ObjectMapper().readValue(new File(configFilePath), CourseConfigDto.class);

            String accessKey = courseConfigDto.getAccessKey();
            String secretKey = courseConfigDto.getSecretKey();
            String path = courseConfigDto.getPath();
            String bucketName = courseConfigDto.getBucketName();
            AWSCredentials tar_credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 tar_client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(tar_credentials))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("enpoint", "region"))
                    .withPathStyleAccessEnabled(true).build();


            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path+file.getName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.Private);
            tar_client.putObject(putObjectRequest);


            // DB에 file upload 기록.
            FileEntity fileEntity = FileEntity.builder()
                    .fileId()
                    .fileLoc()
                    .fileSize()
                    .fileLatitude()
                    .fileLongitude()
                    .coordinatesId()
                    .isDeleted('N')
                    .courseId()
                    .build();




        } catch ( Exception e) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(e.getStackTrace());
        }

        return null;
    }

}
