package stock1337.stock1337.service;

import stock1337.stock1337.model.Departement;

import java.util.List;

public interface DepartementService {

    List<Departement> getAllDepartement();
    Departement saveDepartement(Departement departement);
    Departement updateDepartement(Long Id ,Departement departement);
    void deleteDepartement(Long id);
}
