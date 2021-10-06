package com.example.fileupload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DocService {
    private final DocRepository docRepository ;

    public DocService(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    public Doc saveDocFile(MultipartFile file) {
        String docName = file.getOriginalFilename() ;
        try{
            Doc doc = new Doc(docName ,file.getContentType(), file.getBytes()) ;
            return docRepository.save(doc) ;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null ;
    }

    public Optional<Doc> getFile(Integer id) {
        return docRepository.findById(id) ;
    }
    public List<Doc> getFiles() {
     return    docRepository.findAll() ;
    }
}
