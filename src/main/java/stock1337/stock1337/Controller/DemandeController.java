package stock1337.stock1337.Controller;


import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.Enums.CauseRefus;
import stock1337.stock1337.Model.Demande;
import stock1337.stock1337.Model.User;
import stock1337.stock1337.Service.DemandeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/demandes")
@RequiredArgsConstructor
public class DemandeController {

    private final DemandeService demandeservice;

    @PostMapping("/create")
    public ResponseEntity<Demande> create (
            @RequestParam Long ArticleId,
            @RequestParam Integer qte,
            @RequestBody User user
    ){
        return ResponseEntity.ok(demandeservice.createDemande(user, ArticleId, qte));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Demande>> getPendingDemandes(){
        return ResponseEntity.ok(demandeservice.getPendingDemandes());
    }

    @PutMapping("{id}/approve")
    public ResponseEntity<Demande> approve (
            @PathVariable Long id
    ){
        return ResponseEntity.ok(demandeservice.aprouveDemande(id));
    }

    @PutMapping("{id}/reject")
    public ResponseEntity<Demande> reject (
            @PathVariable Long id,
            @RequestParam CauseRefus cause
            ){
        return ResponseEntity.ok(demandeservice.rejectDemande(id, cause));
    }
}
