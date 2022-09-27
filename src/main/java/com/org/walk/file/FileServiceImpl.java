package com.org.walk.file;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.CoordinatesEntity;
import com.org.walk.course.dto.CourseConfigDto;
import com.org.walk.file.dto.FileDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final Logger log_error = LogManager.getLogger("com.error");
    private final Logger log_file = LogManager.getLogger("com.file");

    @Autowired
    FileRepository fileRepository;

    @Override
    public FileDto uploadFile(MultipartFile file, FileDto fileDto) throws Exception {

        try {

            String configFilePath = System.getProperty("user.home")+"/walkConfig.json";

            CourseConfigDto courseConfigDto = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
                    .readValue(new File(configFilePath), CourseConfigDto.class);

            String accessKey = courseConfigDto.getAccessKey();
            String secretKey = courseConfigDto.getSecretKey();
            String path = courseConfigDto.getPath();
            String bucketName = courseConfigDto.getBucketName();
            String endPoint = courseConfigDto.getEndPoint();
            String region = courseConfigDto.getRegion();
            AWSCredentials tar_credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(tar_credentials))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint,region))
                    .withPathStyleAccessEnabled(true).build();

            File _file = new File(System.getProperty("user.home") + "/" + file.getOriginalFilename());
            // file.transferTo(_file);
            if (_file.createNewFile()) {
                try(FileOutputStream fos = new FileOutputStream(_file)) {
                    fos.write(file.getBytes());
                } catch (Exception r){
                    System.out.println(" 파일 생성 실패.");
                    r.printStackTrace();
                }
            }

            File targetFile = new File(_file.getPath());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path+_file.getName()+_file.getName(), targetFile);
            putObjectRequest.withCannedAcl(CannedAccessControlList.Private);

//          PutObjectResult result = s3Client.putObject(putObjectRequest);
//          System.out.println("meta data :: " + result.getMetadata());

            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();

            Upload upload = tm.upload(bucketName, path+_file.getName(), new File(_file.getPath()));

            // 전송 이후 삭제
            _file.delete();

            // DB에 file upload 기록.
            FileEntity fileEntity = FileEntity.builder()
                    .fileId(null)
                    .fileLoc(path+_file.getName())
                    .fileSize(fileDto.getFileSize())
                    .fileLatitude(fileDto.getFileLatitude())
                    .fileLongitude(fileDto.getFileLongitude())
                    //.coordinatesId(fileDto.getCoordinatesId())
                    .isDeleted('N')
                    .courseId(fileDto.getCourseId())
                    .userId(fileDto.getUserId())
                    .build();

            FileEntity files =  fileRepository.save(fileEntity);

            System.out.println("entity inserted :: " + files.toString());

        } catch ( Exception e) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(e.getStackTrace());
        }

        return null;
    }

    @Override
    public FileDto getPreviewFile(Long courseId) throws Exception {



        return null;
    }

}
