package stock1337.stock1337.Service;

import stock1337.stock1337.Model.Departement;

import java.util.List;

public interface DepartementService {

    List<Departement> getAllDepartement();
    Departement saveDepartement(Departement departement);
    Departement updateDepartement(Long Id ,Departement departement);
    void deleteDepartement(Long id);
}
