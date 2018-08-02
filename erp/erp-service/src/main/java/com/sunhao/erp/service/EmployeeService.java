package com.sunhao.erp.service;

import com.sunhao.erp.entity.Employee;
import com.sunhao.erp.entity.Role;
import com.sunhao.erp.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author sunhao
 * @CreateDate: 2018/7/26 17:43
 */
public interface EmployeeService {
    /**
     * 登录验证
     * @param userTel
     * @param password
     * @param loginIp
     * @return
     */
    void verify(String userTel, String oldPassword, String  newPassword) throws ServiceException;

    /**
     * 查找员工信息集合，若有搜索条件，则加上搜索条件
     * @param map
     * @return
     */
    List<Employee> findEMP(Map<String,Object> map);

    /**
     * 禁用员工状态
     * @param id
     */
    void updateEmployee(Integer id);

    /**
     * 删除员工信息
     * @param id
     */
    void delEmployee(Integer id);

    /**
     * 新增员工，并增加员工与角色关联关系
     * @param employee
     * @param roleIds
     */
    void addEmployee(Employee employee, Integer[] roleIds);

    /**
     * 根据手机号查找员工对象
     * @param userTel
     * @return
     */
    Employee findEmployeeByUserTel(String userTel);

    /**
     * 修改员工信息，根据id查找员工信息
     * @param id
     * @return
     */
    Employee findEMPById(Integer id);


    /**
     * 修改员工信息和修改员工和角色关联关系表
     * @param roleIds
     * @param employee
     */
    void editEmployeewithRole(Integer[] roleIds, Employee employee);
}
