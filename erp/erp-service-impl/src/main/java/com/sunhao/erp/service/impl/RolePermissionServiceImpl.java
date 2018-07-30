package com.sunhao.erp.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sunhao.erp.entity.*;
import com.sunhao.erp.exception.ServiceException;
import com.sunhao.erp.mapper.EmployeeRoleMapper;
import com.sunhao.erp.mapper.PermissionMapper;
import com.sunhao.erp.mapper.RoleMapper;
import com.sunhao.erp.mapper.RolePermissionMapper;
import com.sunhao.erp.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sunhao
 * @CreateDate: 2018/7/27 14:56
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 查找所有权限集合
     *
     * @return
     */
    @Override
    public List<Permission> findPermissionList() {
        PermissionExample permissionExample = new PermissionExample();

        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        List<Permission> endList = new ArrayList<>();
        threeList(permissionList, endList, 0);
        return endList;
    }

    private void threeList(List<Permission> sourceList, List<Permission> endList, int parentId) {
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(sourceList, new Predicate<Permission>() {
            @Override
            public boolean apply(@Nullable Permission permission) {
                return permission.getPid().equals(parentId);
            }
        }));

        for (Permission permission : tempList) {
            endList.add(permission);
            threeList(sourceList, endList, permission.getId());
        }

    }

    /**
     * 查找所有菜单权限
     *
     * @param pemissionTypeMenu
     * @return
     */
    @Override
    public List<Permission> findPermissionListByType(String pemissionTypeMenu) {

        PermissionExample permissionExample = new PermissionExample();

        permissionExample.createCriteria().andPermissionTypeEqualTo(pemissionTypeMenu);

        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 新增权限管理
     *
     * @param permission
     */
    @Override
    public void savePermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    /**
     * 删除权限管理
     * @param id
     */
    @Override
    public void permissionDel(Integer id) throws ServiceException {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(id);

        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        if(permissionList != null && permissionList.size() > 0) {
            throw new ServiceException("该权限下有子权限，不能删除!");
        }

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPermissionIdEqualTo(id);

        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

        if(rolePermissionList != null && rolePermissionList.size() > 0) {
            throw new ServiceException("该权限正在使用，不能删除!");
        }

        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查找角色集合
     *
     * @return
     */
    @Override
    public List<Role> findRoleList() {

        return roleMapper.findRoleListWithPermission();
    }

    /**
     * 新增角色
     *
     * @param role
     * @param permissionIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRole(Role role, Integer[] permissionIds) {
       roleMapper.insert(role);

       saveRoleAndPermission(role.getId(), permissionIds);

    }

    /**
     * 根据id删除角色
     *
     * @param id
     */
    @Override
    public void delRole(Integer id) throws ServiceException{
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andRoleIdEqualTo(id);
        List<EmployeeRole> employeeRoleList =employeeRoleMapper.selectByExample(employeeRoleExample);

        if (employeeRoleList != null) {
            throw new ServiceException("该角色正在使用，不能删除！");
        }


        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(id);

        rolePermissionMapper.deleteByExample(rolePermissionExample);

        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查找角色对象
     *
     * @param id
     * @return
     */
    @Override
    public Role findRoleById(Integer id) {
        return roleMapper.findOneRoleById(id);
    }

    /**
     * 查找权限Map集合，其中为true的在前端页面勾选上，false则不勾选
     *
     * @param permissionList
     * @return
     */
    @Override
    public Map<Permission, Boolean> findPermissionBooleanMap(List<Permission> permissionList) {
        List<Permission> permissionList1 = findPermissionList();

        Map<Permission, Boolean> maps = Maps.newLinkedHashMap();

        for(Permission permissions : permissionList1) {
            Boolean flag = false;

            for(Permission permission : permissionList) {
                if (permissions.getId().equals(permission.getId())) {
                    flag = true;
                    break;
                }
            }
            maps.put(permissions, flag);
        }

        return maps;
    }

    /**
     * 修改角色
     *
     * @param role
     * @param permissionId
     */
    @Override
    public void editRole(Role role, Integer[] permissionId) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());

        rolePermissionMapper.deleteByExample(rolePermissionExample);

        saveRoleAndPermission(role.getId(), permissionId);

        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 保存权限和角色的关联关系
     *
     * @param permissionIds
     */
    @Override
    public void saveRoleAndPermission(Integer id,Integer[] permissionIds) {

        for(Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(id);

            rolePermissionMapper.insertSelective(rolePermission);
        }
    }

    /**
     * 修该权限类型时，回显该权限类型的信息
     *
     * @param id
     * @return
     */
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 查找角色集合，没有条件
     *
     * @return
     */
    @Override
    public List<Role> AllRole() {
        RoleExample roleExample = new RoleExample();
        return roleMapper.selectByExample(roleExample);
    }

    /**
     * 修改权限
     *
     * @param permission
     */
    @Override
    public void updatePermission(Permission permission) {
        permission.setUpdateTime(new Date());
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

}
