package org.lms.security;

import org.lms.entity.UserRoleMappings;
import org.lms.entity.UserRoles;
import org.lms.entity.Users;
import org.lms.mapper.UserRoleMappingsMapper;
import org.lms.mapper.UserRolesMapper;
import org.lms.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UsersMapper usersMapper;
    private UserRolesMapper userRolesMapper;
    private UserRoleMappingsMapper userRoleMappingsMapper;

    @Autowired
    public CustomUserDetailsService(UsersMapper usersMapper,
                                    UserRolesMapper userRolesMapper,
                                    UserRoleMappingsMapper userRoleMappingsMapper) {
        this.usersMapper = usersMapper;
        this.userRolesMapper = userRolesMapper;
        this.userRoleMappingsMapper = userRoleMappingsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersMapper.findByUsername(username);
        if(users==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserRoleMappings userRoleMappings = userRoleMappingsMapper.selectByPrimaryKey(users.getId());
        Integer roleId = userRoleMappings.getRoleId();

        List<UserRoles> userRoles = userRolesMapper.findListById(roleId);
        List<SimpleGrantedAuthority> list = userRoles.stream().
                map(role -> new SimpleGrantedAuthority(role.getName())).
                toList();
        return new User(
                users.getUsername(),
                users.getPassword(),
                list);
    }
}
