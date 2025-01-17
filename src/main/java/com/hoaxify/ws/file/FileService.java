package com.hoaxify.ws.file;

import com.hoaxify.ws.configuration.AppConfiguration;
import com.hoaxify.ws.user.User;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
        File target = new File(appConfiguration.getProfileStoragePath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return fileName;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param oldImageName
     *  old profile pic delete of user.
     */
    public void deleteProfilelImage(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getProfileStoragePath(),oldImageName));

    }

    public String  detectType(String base64) {
        byte[] base64encoded = Base64.getDecoder().decode(base64);
        return detectType(base64encoded);
    }
    public String detectType(byte[] arr) {
        return tika.detect(arr);
    }

    public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            outputStream.close();
            fileType = detectType(arr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setName(fileName);
        fileAttachment.setDate(new Date());
        fileAttachment.setFileType(fileType);
        return fileAttachmentRepository.save(fileAttachment);
    }

    /**
     * @param oldImageName
     * Hoax'a attachment file'ları siler.
     */
    public void deleteAttachmentFile(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(),oldImageName));
    }

    /**
     * 24 saatten eski olan ve
     * hoaxla ilişkisi bulunmayan dosyaları silen metod.
     *
     */
    @Scheduled(fixedRate = 24*60*60*1000 )
    public void cleanupStorage() {
        Date twentyFourHourAgo = new Date(System.currentTimeMillis() - (24*60*60*1000)); //24 saatin ms karşılığı
        List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHourAgo);
        for (FileAttachment file : filesToBeDeleted) {
            // delete file in folder
            deleteAttachmentFile(file.getName());

            // delete from file in db
            fileAttachmentRepository.deleteById(file.getId());

        }
    }

    public void deleteAllStoredFilesForUser(User inDB) {
        deleteProfilelImage(inDB.getImage());
        List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByHoaxUser(inDB);
        for (FileAttachment file: filesToBeRemoved) {
            deleteAttachmentFile(file.getName());
        }
    }
}
