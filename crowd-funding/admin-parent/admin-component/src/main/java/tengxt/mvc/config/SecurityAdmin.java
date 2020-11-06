package tengxt.mvc.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import tengxt.entity.Admin;

import java.util.List;

/**
 *  为了能方便地获取到原始地Admin对象，因此创建一个SecurityAdmin类，继承User。
 */
public class SecurityAdmin extends User {

    private Admin originalAdmin;

    public SecurityAdmin(Admin admin, List<GrantedAuthority> authorities) {

        // 调用父类的构造方法
        super(admin.getUserName(), admin.getUserPswd(), authorities);

        // 将Admin对象放入对象
        this.originalAdmin = admin;

    }

    public Admin getOriginalAdmin() {
        return this.originalAdmin;
    }
}
