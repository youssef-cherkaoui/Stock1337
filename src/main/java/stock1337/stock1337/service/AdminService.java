package stock1337.stock1337.service;

import stock1337.stock1337.model.Admin;

import java.util.List;

public interface AdminService {

    Admin createAdmin(Admin admin);
    Admin getAdminById(Long id);
    List<Admin> getAllAdmins();
    Admin updateAdmin(Long id, Admin admin);
    void deleteAdmin(Long id);
    Admin getByEmail(String email);
}
