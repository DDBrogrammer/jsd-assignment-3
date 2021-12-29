package com.example.jsd_assignment_3.controllers;

import com.example.jsd_assignment_3.entities.File;
import com.example.jsd_assignment_3.entities.Setting;
import com.example.jsd_assignment_3.repositories.FileRepository;
import com.example.jsd_assignment_3.services.FileService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@Controller
@RequestMapping("/home/file")
public class FileController {
    @Value("${config.upload_folder}")
    String UPLOAD_FOLDER;
    @Autowired
    FileService fileService;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    private ServletContext servletContext;

    @PostMapping("/upload")
    public String doAddWithImage(File file, RedirectAttributes flashSession, @RequestParam(name = "upload_file") MultipartFile multipartFile) {
        if (fileService.save(file, multipartFile)) {
            flashSession.addFlashAttribute("success", "Upload successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Upload failed");
        }
        return "redirect:/home/file/list";
    }

    @GetMapping("/list")
    public String getFileList(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {
        int totalPage = fileService.getPageFile(activePage,10).getTotalPages();
        File file = new File();
        Setting setting = new Setting();
        model.addAttribute("upload_file", file);
        model.addAttribute("setting", setting);
        if (page < 0 || page > totalPage - 1) {

            Page<File> listFilePage = fileService.getPageFile(activePage,10);
            model.addAttribute("listFilePage", listFilePage);
            model.addAttribute("activePage", activePage);

        } else {
            Page<File> listFilePage = fileService.getPageFile(page,10);
            model.addAttribute("listFilePage", listFilePage);
            model.addAttribute("activePage", page);
        }

        return "home";
    }


//    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
//    public void download(@PathVariable long id, HttpServletResponse response) throws IOException {
//
//            java.io.File file = ResourceUtils.getFile("classpath:"+UPLOAD_FOLDER+fileService.getFileById(id).getPath());
//            File myFile=fileService.getFileById(id);
//            System.out.printf(myFile.getPath());
//            byte[] data =FileUtils.readFileToByteArray(file);
//            // Thiết lập thông tin trả về
//            response.setContentType(myFile.getMime());
//            response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
//            response.setContentLength(data.length);
//            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
//
//    }


    @GetMapping("/list/")
    public void downloadFile3(HttpServletResponse resonse,
                              @RequestParam(name = "id") long id) throws IOException {
        File dbFile= fileService.getFileById(id);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, dbFile.getName());
        System.out.println("fileName: " + dbFile.getPath());
        System.out.println("mediaType: " + mediaType);
        System.out.println("run download");
        java.io.File file = fileService.download(id);
        // Content-Type
        // application/pdf
        resonse.setContentType(mediaType.getType());

        // Content-Disposition
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

        // Content-Length
        resonse.setContentLength((int) file.length());

        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
    }





}
