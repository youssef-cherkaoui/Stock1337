package stock1337.stock1337.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.Model.Departement;
import stock1337.stock1337.Repository.DepartementRepository;
import stock1337.stock1337.Service.DepartementService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    @Override
    public List<Departement> getAllDepartement() {
        return departementRepository.findAll();
    }

    @Override
    public Departement saveDepartement(Departement departement) {
        return departementRepository.save(departement);
    }

    @Override
    public Departement updateDepartement(Long id,Departement details) {
        Departement dep = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        dep.setName(details.getName());
        return departementRepository.save(dep);
    }

    @Override
    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }
}
