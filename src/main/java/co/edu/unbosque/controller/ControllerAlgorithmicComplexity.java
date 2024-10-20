package co.edu.unbosque.controller;
import co.edu.unbosque.service.ServiceAlgorithmicComplexity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "/AlgorithmicComplexity")
public class ControllerAlgorithmicComplexity implements ApiAlgorithmicComplexity{

    @Autowired
    private ServiceAlgorithmicComplexity serviceAlgorithmicComplexity;

    @Override
    public ResponseEntity<String> readFile(MultipartFile file) {
        try {
            String content = serviceAlgorithmicComplexity.readFile(file);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer el archivo.");
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> searchPattern( String pattern) {
        try {
            Map<String, Object> result = serviceAlgorithmicComplexity.kmpSearch(pattern);
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> searchBMPattern(String pattern) {
        try {
            Map<String, Object> result = serviceAlgorithmicComplexity.bmSearch(pattern);
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
