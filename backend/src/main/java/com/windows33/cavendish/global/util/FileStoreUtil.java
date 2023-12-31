package com.windows33.cavendish.global.util;

import com.windows33.cavendish.domain.board.entity.BoardImage;
import com.windows33.cavendish.global.exception.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Slf4j
@Component
public class FileStoreUtil {

    private final String currentPath = System.getProperty("user.dir");

    /**
     * 파일 업로드
     */
    public List<String> uploadFiles(String fileType, List<MultipartFile> multipartFiles) {
        List<String> filePaths = new ArrayList<>();
        String uploadFilePath = currentPath + "\\" + "CavendishStore" + "\\" + fileType + "\\" + getDate();

        for (MultipartFile multipartFile : multipartFiles) {
            if(multipartFile.isEmpty()) continue;

            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = uploadFilePath + "\\" + uploadFileName;

            // 폴더 생성
            File Folder = new File(uploadFilePath);
            if (!Folder.exists()) {
                try {
                    Folder.mkdirs();
                }
                catch(Exception e) {
                    log.error("File: Unable to create folder");
                    throw new FileException(File.class);
                }
            }

            // 파일 저장
            try {
                multipartFile.transferTo(new File(uploadFileUrl));
            } catch (IOException e) {
                log.error("File: Filed upload failed", e);
                throw new FileException(MultipartFile.class);
            }

            filePaths.add(uploadFileUrl);
        }

        return filePaths;
    }

    /**
     * 파일 단일 업로드
     */
    public List<String> uploadFiles(String fileType, MultipartFile multipartFile) {
        List<MultipartFile> list = new ArrayList<>();
        list.add(multipartFile);

        return uploadFiles(fileType, list);
    }

    /**
     * 파일 삭제
     */
    public List<Integer> deleteFiles(List<BoardImage> images) {
        List<Integer> result = new ArrayList<>();

        for(BoardImage image : images) {
            try {
                File file = new File(image.getImagePath());

                if(file.exists()) {
                    file.delete();
                    result.add(image.getId());
                } else {
                    log.error("File: File not found");
                    throw new FileException(File.class);
                }
            } catch(Exception e) {
                log.error("File: Path not valid");
                throw new FileException(File.class);
            }
        }

        return result;
    }

    /**
     * 파일 단일 삭제
     */
    public List<Integer> deleteFiles(BoardImage image) {
        List<BoardImage> list = new ArrayList<>();
        list.add(image);

        return deleteFiles(list);
    }

    // 날짜 문자열 생성(연/월/일)
    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "\\");
    }

    // UUID 생성
    private String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }

}
