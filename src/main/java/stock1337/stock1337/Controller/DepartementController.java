package stock1337.stock1337.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Service.DepartementService;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService departementService;

    @GetMapping("/all")
    public ResponseEntity<List<Departement>> getAll(){
        return ResponseEntity.ok(departementService.getAllDepartement());
    }

    @PostMapping("/addDepartment")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Departement> addDepartment(@RequestBody Departement departement){
        return ResponseEntity.ok(departementService.saveDepartement(departement));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Departement> update(@PathVariable Long id, @RequestBody Departement departement){
        return ResponseEntity.ok(departementService.updateDepartement(id, departement));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }
}
