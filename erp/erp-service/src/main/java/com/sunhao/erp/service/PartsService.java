package com.sunhao.erp.service;


import com.github.pagehelper.PageInfo;
import com.sunhao.erp.entity.Parts;
import com.sunhao.erp.entity.Type;

import java.util.List;

/**
 * @author sunhao
 * @CreateDate: 2018/7/23 21:42
 */
public interface PartsService {

    /**
     * 根据id查找配件对象
     * @param id
     * @return
     */
    Parts findById(Integer id);

    /**
     * 分页查询
     * @param pageNo
     * @return
     */
    PageInfo<Parts> findAllParts(Integer pageNo, String partsName, Integer partsType);

    /**
     * 查找所有类型
     * @return
     */
    List<Type> findAllType();

    /**
     * 配件入库
     * @param parts
     */
    void saveParts(Parts parts);

    /**
     *删除配件
     * @param id
     */
    void delPartsById(Integer id);

    /**
     * 修改配件
     * @param parts
     */
    void editPartsById(Parts parts);
}
