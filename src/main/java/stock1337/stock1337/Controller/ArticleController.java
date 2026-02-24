package stock1337.stock1337.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.DTO.ArticleRequest;
import stock1337.stock1337.Model.Article;
import stock1337.stock1337.Repository.ArticleRepository;
import stock1337.stock1337.Service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @GetMapping("/search")
    public ResponseEntity<List<Article>> saerchArticles (
            @RequestParam(required = false) Long stockId,
            @RequestParam(required = false) Long depaId){


        return ResponseEntity.ok(articleService.searchArticle(stockId, depaId));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleRequest request){
        return ResponseEntity.ok(articleService.createArticle(request));
    }

    @GetMapping("/Low-Stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Article>> getLowStockArticles(){
        return ResponseEntity.ok(articleService.getLowStockArticles());
    }
}
