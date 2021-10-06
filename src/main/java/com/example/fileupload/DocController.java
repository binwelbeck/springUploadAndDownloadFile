package com.example.fileupload;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DocController {

    private final DocService docRepository ;

    public DocController(DocService docRepository) {
        this.docRepository = docRepository;
    }
    @GetMapping("/")
    public String get(Model model) {
        List<Doc> docs = docRepository.getFiles() ;
        model.addAttribute("docs", docs) ;
        return "doc";
    }
    @PostMapping("/uploadFiles")
    public String uploadMultiPartFile(@RequestParam("files") MultipartFile[] files) {
    for (var file : files)
        docRepository.saveDocFile(file) ;
    return "redirect:/";
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId ) {
        Doc doc = docRepository.getFile(fileId).get() ;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData())) ;
    }


}
