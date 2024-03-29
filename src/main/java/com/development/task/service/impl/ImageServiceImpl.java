package com.development.task.service.impl;

import com.development.task.domain.exception.ImageUploadException;
import com.development.task.domain.task.TaskImage;
import com.development.task.service.ImageService;
import com.development.task.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    @Override
    public String upload(final TaskImage image) {
        try{
            createBucket();
        }catch (Exception e){
            throw new ImageUploadException("image upload filed: "+e.getMessage());
        }
        MultipartFile file = image.getFile();
        if(file.isEmpty() || file.getOriginalFilename()==null){
            throw new ImageUploadException("image must have name");
        }
        String fileName = generateFileName(file);
        InputStream stream;
        try{
            stream = file.getInputStream();
        }catch (Exception e){
            throw new ImageUploadException("image upload field: " + e.getMessage());
        }
        saveImage(stream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void saveImage(final InputStream stream,final String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(stream,stream.available(),-1)
                .bucket(minioProperties.getBucket()).object(fileName).build());
    }

    private String generateFileName(final MultipartFile file) {
        String ext = getExtension(file);
        return UUID.randomUUID()+ "." + ext;
    }

    private String getExtension(final  MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket()).build());
        if(!found){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
        }
    }
}
