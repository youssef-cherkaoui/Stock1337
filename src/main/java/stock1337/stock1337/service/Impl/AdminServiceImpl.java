package stock1337.stock1337.service.Impl;


import stock1337.stock1337.enums.Role;
import stock1337.stock1337.model.Admin;
import stock1337.stock1337.repository.AdminRepository;
import stock1337.stock1337.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        admin.setRole(Role.ADMIN);
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("No Admin found with id: " + id));
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {

        Admin existAdmin = getAdminById(id);
        existAdmin.setName(admin.getName());
        existAdmin.setEmail(admin.getEmail());
        existAdmin.setPassword(admin.getPassword());
        return adminRepository.save(existAdmin);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin getByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow( () -> new RuntimeException("No Admin found with email: " + email));
    }

}
