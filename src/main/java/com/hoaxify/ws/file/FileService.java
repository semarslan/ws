package com.hoaxify.ws.file;

import com.hoaxify.ws.configuration.AppConfiguration;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService {

    AppConfiguration appConfiguration;
    Tika tika;

    FileAttachmentRepository fileAttachmentRepository;

    public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getUploadPath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return fileName;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void deleteFile(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(),oldImageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(String value) {
        byte[] base64encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64encoded);
    }

    public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getUploadPath() + "/" + fileName);
        try {
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setName(fileName);
        fileAttachment.setDate(new Date());
        return fileAttachmentRepository.save(fileAttachment);
    }

    /**
     * 24 saatten eski olan ve
     * hoaxla ilişkisi bulunmayan dosyaları silen metod.
     */
    @Scheduled(fixedRate = 24*60*60*1000 )
    public void cleanupStorage() {
        Date twentyFourHourAgo = new Date(System.currentTimeMillis() - (24*60*60*1000)); //24 saatin ms karşılığı
        List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHourAgo);
        for (FileAttachment file : filesToBeDeleted) {
            // delete file in folder
            deleteFile(file.getName());

            // delete from file in db
            fileAttachmentRepository.deleteById(file.getId());

        }
    }
}
