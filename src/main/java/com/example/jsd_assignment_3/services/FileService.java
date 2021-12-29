package com.example.jsd_assignment_3.services;

import com.example.jsd_assignment_3.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    @Value("${config.upload_folder}")
    String UPLOAD_FOLDER;
    @Autowired
    private FileRepository fileRepository;;
    public String upload(MultipartFile file) {
        String relativeFilePath =null;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();//2021
        int month = localDate.getMonthValue();//06
        String subFolder = month+"_"+year+"/";//06_2021/
        String fullUploadDir = UPLOAD_FOLDER+subFolder;
        File checkDir = new File(fullUploadDir);
        if (!checkDir.exists() || checkDir.isFile()) {
            //tạo mới folder
            checkDir.mkdir();
        }
        try {
            relativeFilePath = subFolder + Instant.now().getEpochSecond() +file.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_FOLDER+relativeFilePath), file.getBytes());
        } catch (Exception e) {
            System.out.println("cannot upload file");
            e.printStackTrace();
            return null;
        }
        return relativeFilePath;
    }

    public File download(long id){
        com.example.jsd_assignment_3.entities.File myFile=fileRepository.getById(id);
        System.out.println(myFile.getName());
        System.out.println(myFile.getMime());
        System.out.println(UPLOAD_FOLDER+ myFile.getPath());
        File file= new File(UPLOAD_FOLDER+myFile.getPath());
        return file;
    }

    public boolean save(com.example.jsd_assignment_3.entities.File file, MultipartFile uploadFile) {
        LocalDateTime localDate = LocalDateTime.now();

        try {
            //upload ảnh

                //tiến hành upload
                String uploadPath = upload(uploadFile);
                file.setFileSize(uploadFile.getSize());
                file.setName(uploadFile.getOriginalFilename());
                file.setCreatedDateTime(localDate);
                file.setStatus("Active");
                file.setNumberOfDownload(0);
                file.setPath(uploadPath);
                file.setMime(uploadFile.getContentType());
                long version= specifyFileVersion(file.getName());
                String versionIds=file.getName()+": V_"+ String.valueOf(version);
                file.setVersion(version);
                file.setVersionIds(versionIds);

            fileRepository.save(file);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Page<com.example.jsd_assignment_3.entities.File> getPageFile(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return fileRepository.findAll(pageable);
    }
   public List<com.example.jsd_assignment_3.entities.File> getAllFiles(){
       return  fileRepository.findAll();
   }
   public long specifyFileVersion(String fileName){
        List<com.example.jsd_assignment_3.entities.File> files=getAllFiles();
        long id=1;

        for(com.example.jsd_assignment_3.entities.File f:files){
            System.out.println(f.getName());
            System.out.println(fileName);
            if(f.getName().equals(fileName)){

                id=id+1;
            }
        }
        return id;
   }
   public com.example.jsd_assignment_3.entities.File getFileById(long id){
       com.example.jsd_assignment_3.entities.File myFile=fileRepository.getById(id);
       return myFile;
   }
}
