package stock1337.stock1337.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock1337.stock1337.model.Departement;
import stock1337.stock1337.repository.DepartementRepository;
import stock1337.stock1337.service.DepartementService;

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
