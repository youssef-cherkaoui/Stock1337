package stock1337.stock1337.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock1337.stock1337.Model.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
