package com.shinhan.solsolhigh.s3.upload;


import com.shinhan.solsolhigh.common.util.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
class S3Uploader {

    private final S3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) throws UnsupportedEncodingException {
        String fileName = dirName + "/" + UUIDGenerator.getUUID().toString() + URLEncoder.encode(uploadFile.getName(), StandardCharsets.UTF_8); // UUID + fileName으로 작성
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                PutObjectRequest.builder().bucket(bucket).key(fileName).build(), Path.of(uploadFile.toURI())
        );
        return getUrl(fileName);
    }

    private String getUrl(String fileName) {
        return "https://" + bucket + "/" + fileName;
    }

    public void deleteFile(String uri, String dirPath) {
        String key = getKey(uri, dirPath);
        deleteS3(key);
    }

    private String getKey(String uri, String dirPath) {
        try {
            String uuid = uri.split(dirPath)[1];
            return URLDecoder.decode(dirPath + uuid, StandardCharsets.UTF_8);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("S3 delete object Failed (uri form issue)", e);
            return "";
        }
    }

    // S3 에서 delete 에 실패한 것은 critical 한 오류가 아니므로 logging 진행 이후 처리
    private void deleteS3(String key) {
        try {
            amazonS3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(key).build());
        } catch (Exception e) {
            log.error("S3 delete object Failed", e);
        }
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

}
