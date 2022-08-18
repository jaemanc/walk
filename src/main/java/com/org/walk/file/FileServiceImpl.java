package com.org.walk.file;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.org.walk.file.dto.FileDto;
import com.org.walk.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public List<FileDto> uploadFiles(File[] files, String category) {


        String tar_access_key = "aa";
        String tar_secret_key = "ss";
        String tar_path = "private/";
        String tar_bucket_name = "bucket name";
        AWSCredentials tar_credentials = new BasicAWSCredentials(tar_access_key, tar_secret_key);
        AmazonS3 tar_client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(tar_credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("enpoint", "region"))
                .withPathStyleAccessEnabled(true).build();
        // File src_file = new File(src_path);

        PutObjectRequest putObjectRequest = new PutObjectRequest(tar_bucket_name, tar_path, "ㅁㄴㅇㄹ");

        putObjectRequest.withCannedAcl(CannedAccessControlList.Private);

        tar_client.putObject(putObjectRequest);

        return null;
    }

    @Override
    public void uploadFilesHist(UserDto userDto) {

        // DB에 기록.



    }
}
