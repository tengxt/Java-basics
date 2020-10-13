package tengxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tengxt.entity.Admin;
import tengxt.entity.AdminExample;
import tengxt.mapper.AdminMapper;
import tengxt.service.api.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public int saveAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }

    public List<Admin> queryAll() {
        return adminMapper.selectByExample(new AdminExample());
    }
}