package co.edu.unbosque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping(path = "/AlgorithmicComplexity")
public interface ApiAlgorithmicComplexity {

    @PostMapping("/read")
    ResponseEntity<String> readFile(@RequestParam("file") MultipartFile file);

    @PostMapping("/search")
    ResponseEntity<Map<String, Object>> searchPattern(@RequestParam("pattern") String pattern);

    @PostMapping("/searchBM")
    ResponseEntity<Map<String, Object>> searchBMPattern(@RequestParam("pattern") String pattern);

}

