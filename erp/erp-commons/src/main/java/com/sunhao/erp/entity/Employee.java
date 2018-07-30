package com.sunhao.erp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 
 */
public class Employee implements Serializable {


    public static final Integer EMPLOYEE_STATE_NORMAL = 1;
    public static final Integer EMPLOYEE_STATE_UNNORMAL = 0;
    /**
     * 员工ID
     */
    private Integer id;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 角色ID
     */
    private String employeeTel;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限ID
     */
    private Integer permissionId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态：1.禁用 2启用
     */
    private Integer state;
    /**
     * 角色集合
     */
    private List<Role> roleList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeTel() {
        return employeeTel;
    }

    public void setEmployeeTel(String employeeTel) {
        this.employeeTel = employeeTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Employee other = (Employee) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmployeeName() == null ? other.getEmployeeName() == null : this.getEmployeeName().equals(other.getEmployeeName()))
            && (this.getEmployeeTel() == null ? other.getEmployeeTel() == null : this.getEmployeeTel().equals(other.getEmployeeTel()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals(other.getPermissionId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()));
    }

        public List<Role> getRoleList() {
            return roleList;
        }

        public void setRoleList(List<Role> roleList) {
            this.roleList = roleList;
        }
}
