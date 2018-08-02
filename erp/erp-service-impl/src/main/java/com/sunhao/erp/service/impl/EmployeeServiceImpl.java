package com.sunhao.erp.service.impl;

import com.sunhao.erp.entity.*;
import com.sunhao.erp.exception.ServiceException;
import com.sunhao.erp.mapper.EmployeeLoginLogMapper;
import com.sunhao.erp.mapper.EmployeeMapper;
import com.sunhao.erp.mapper.EmployeeRoleMapper;
import com.sunhao.erp.service.EmployeeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sunhao
 * @CreateDate: 2018/7/26 17:47
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    /**
     * 登录验证
     *
     * @param userTel
     * @param password
     * @return
     */
    @Override
    public void verify(String userTel, String oldPassword, String newPassword) throws ServiceException{

        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(userTel);

        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);

        Employee employee = null;

        if(employeeList != null && employeeList.size() > 0) {
            employee = employeeList.get(0);

            if(employee.getPassword().equals(DigestUtils.md5Hex(oldPassword))) {
              employee.setPassword(DigestUtils.md5Hex(newPassword));

             employeeMapper.updateByPrimaryKeySelective(employee);
            } else {
                throw new ServiceException("用户名或者密码错误！");
            }

        } else {
            throw new ServiceException("用户名或者密码错误！");
        }

    }

    /**
     * 查找员工信息集合，若有搜索条件，则加上搜索条件
     *
     * @param map
     * @return
     */
    @Override
    public List<Employee> findEMP(Map<String, Object> map) {

        return employeeMapper.findAllWithRolesByQueryParam(map);
    }

    /**
     * 禁用员工状态
     *
     * @param id
     */
    @Override
    public void updateEmployee(Integer id) {
        Employee employee1 =employeeMapper.selectByPrimaryKey(id);
        if (employee1.getState().equals(Employee.EMPLOYEE_STATE_NORMAL)) {
            Employee employee = new Employee();
            employee.setId(id);
            employee.setState(Employee.EMPLOYEE_STATE_UNNORMAL);

            employeeMapper.updateByPrimaryKeySelective(employee);
        } else {
            Employee employee = new Employee();
            employee.setId(id);
            employee.setState(Employee.EMPLOYEE_STATE_NORMAL);

            employeeMapper.updateByPrimaryKeySelective(employee);
        }

    }

    /**
     * 删除员工信息
     *
     * @param id
     */
    @Override
    public void delEmployee(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);

        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(id);
        employeeRoleMapper.deleteByExample(employeeRoleExample);
    }

    /**
     * 新增员工，并增加员工与角色关联关系
     *
     * @param employee
     * @param roleIds
     */
    @Override
    public void addEmployee(Employee employee, Integer[] roleIds) {
        String codePassword = DigestUtils.md5Hex(employee.getPassword());
        employee.setPassword(codePassword);
        employee.setState(Employee.EMPLOYEE_STATE_NORMAL);
        employee.setCreateTime(new Date());

        employeeMapper.insert(employee);

        for (Integer roleId : roleIds) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(roleId);

            employeeRoleMapper.insert(employeeRole);
        }
    }

    @Override
    public Employee findEmployeeByUserTel(String userTel) {
        if (userTel != null) {
            EmployeeExample employeeExample = new EmployeeExample();
            employeeExample.createCriteria().andEmployeeTelEqualTo(userTel);

            List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);

            if (employeeList != null && employeeList.size() > 0) {
                return employeeList.get(0);
            }
        }
        return null;
    }

    @Override
    public Employee findEMPById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改员工信息和修改员工和角色关联关系表
     *
     * @param roleIds
     * @param employee
     */
    @Override
    public void editEmployeewithRole(Integer[] roleIds, Employee employee) {
       employeeMapper.updateByPrimaryKeySelective(employee);

       EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
       employeeRoleExample.createCriteria().andEmployeeIdEqualTo(employee.getId());
       employeeRoleMapper.deleteByExample(employeeRoleExample);

        for (Integer id : roleIds) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setRoleId(id);
            employeeRole.setEmployeeId(employee.getId());

            employeeRoleMapper.insert(employeeRole);
        }
    }
}
