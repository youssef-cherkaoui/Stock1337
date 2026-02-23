package stock1337.stock1337.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.Model.Article;
import stock1337.stock1337.Repository.ArticleRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("/search")
    public ResponseEntity<List<Article>> getArticlesByDepartement(
            @RequestParam(required = false) Long depaId){
        if(depaId != null){
            return ResponseEntity.ok(articleRepository.findByDepartementId(depaId));
        }
        return ResponseEntity.ok(articleRepository.findAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Article> addArticle(@RequestBody Article article){
        return ResponseEntity.ok(articleRepository.save(article));
    }

    @GetMapping("/Low-Stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Article>> getLowStockArticles(){
        return ResponseEntity.ok(articleRepository.FindLowStockArticles());
    }
}
