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
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.*;
import com.org.walk.course.dto.CourseConfigDto;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.file.dto.FileDto;
import com.org.walk.user.UserEntity;
import com.querydsl.core.util.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final Logger log_error = LogManager.getLogger("com.error");
    private final Logger log_file = LogManager.getLogger("com.file");

    @Autowired
    FileRepository fileRepository;

    @Autowired
    CourseRepository courseRepository;

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

            Optional<CourseEntity> courseEntity = courseRepository.findById(fileDto.getCourseId());

            if(courseEntity.isPresent()) {
                CourseEntity course = courseEntity.get();
                // course file update
                course.updateCourseFile(files.getFileId());
                courseRepository.save(course);
            }

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
    public File getPreviewFile(Long courseId) throws Exception {

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

        FileEntity fileEntity = fileRepository.findByCourseId(courseId);

        if ( ObjectUtils.isEmpty(fileEntity)) {
            return null;
        }

        S3Object object = s3Client.getObject(bucketName, fileEntity.getFileLoc());

        InputStream input = object.getObjectContent();

        File tempFile = File.createTempFile(fileEntity.getFileLoc().substring(fileEntity.getFileLoc().lastIndexOf("/"),fileEntity.getFileLoc().length()),"");
        tempFile.deleteOnExit();

        Path path_t = Paths.get(tempFile.getPath());

        Files.copy(input, path_t, StandardCopyOption.REPLACE_EXISTING);

        return tempFile;
    }

}
