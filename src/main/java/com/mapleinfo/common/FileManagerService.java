package com.mapleinfo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	
		// 경로
		public static final String FILE_UPLOAD_PATH = "/Users/geonhyo/koo/6_maple_project/workspace/images/";

		//파일 업로드
		public String uploadFile( String loginId , MultipartFile file) {
			
			String directoryName = loginId + "_" + System.currentTimeMillis(); 
			
			String filePath = FILE_UPLOAD_PATH + directoryName + "/";

			File directory = new File(filePath);
			if (directory.mkdir() == false) {
				return null; 
			}

			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filePath + file.getOriginalFilename());
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
				return null; 
			}

			return "/images/" + directoryName + "/" + file.getOriginalFilename();
		}

		// 파일 삭제
		public void deleteFile(String imagePath) { 
			
			Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 파일삭제 시도] 이미지 파일 삭제 실패 path:{}", path.toString());
					return;
				}

				path = path.getParent();
				if (Files.exists(path)) {
					try {
						Files.delete(path);
					} catch (IOException e) {
						log.info("[FileManagerService 폴더삭제 시도] 디렉토리(폴더) 삭제 실패 path:{}", path.toString());
					}
				}
			}
		}
	
}
