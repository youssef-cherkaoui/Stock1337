package stock1337.stock1337.Service;

import stock1337.stock1337.Model.Admin;

import java.util.List;

public interface AdminService {

    Admin createAdmin(Admin admin);
    Admin getAdminById(Long id);
    List<Admin> getAllAdmins();
    Admin updateAdmin(Long id, Admin admin);
    void deleteAdmin(Long id);
    Admin getByEmail(String email);
}
