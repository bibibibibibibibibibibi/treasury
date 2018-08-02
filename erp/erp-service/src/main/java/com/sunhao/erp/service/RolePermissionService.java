package com.sunhao.erp.service;

import com.sunhao.erp.entity.Permission;
import com.sunhao.erp.entity.Role;
import com.sunhao.erp.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author sunhao
 * @CreateDate: 2018/7/27 14:52
 */
public interface RolePermissionService {


    /**
     * 查找所有权限集合
     * @return
     */
    List<Permission> findPermissionList();

    /**
     * 查找所有菜单权限
     * @param pemissionTypeMenu
     * @return
     */
    List<Permission> findPermissionListByType(String pemissionTypeMenu);

    /**
     * 新增权限管理
     * @param permission
     */
    void savePermission(Permission permission);

    /**
     * 删除权限管理
     * @param id
     */
    void permissionDel(Integer id) throws ServiceException;

    /**
     * 查找角色集合
     * @return
     */
    List<Role> findRoleList();

    /**
     * 新增角色
     * @param role
     * @param permissionId
     */
    void saveRole(Role role, Integer[] permissionId);

    /**
     * 根据id删除角色
     * @param id
     */
    void delRole(Integer id) throws ServiceException;

    /**
     * 根据id查找角色对象
     * @param id
     * @return
     */
    Role findRoleById(Integer id);

    /**
     * 查找权限Map集合，其中为true的在前端页面勾选上，false则不勾选
     * @param permissionList
     * @return
     */
    Map<Permission,Boolean> findPermissionBooleanMap(List<Permission> permissionList);

    /**
     * 修改角色
     * @param role
     * @param permissionId
     */
    void editRole(Role role, Integer[] permissionId);

    /**
     * 保存权限和角色的关联关系
     * @param permissionIds
     * @param id
     */
    void saveRoleAndPermission(Integer id, Integer[] permissionIds);

    /**
     * 修该权限类型时，回显该权限类型的信息
     * @param id
     * @return
     */
    Permission findPermissionById(Integer id);


    /**
     * 查找角色集合，没有条件
     * @return
     */
    List<Role> AllRole();

    /**
     * 修改权限
     * @param permission
     */
    void updatePermission(Permission permission);

    /**
     * 根据员工id查找角色集合
     * @param id
     * @return
     */
    List<Role> findRoleListById(Integer id);

    /**
     * 根据角色id查找对应的权限集合
     * @param id
     * @return
     */
    List<Permission> findPermissionByRoleId(Integer id);
}
