package com.sunhao.erp.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunhao.erp.entity.Parts;
import com.sunhao.erp.entity.Type;
import com.sunhao.erp.entity.TypeExample;
import com.sunhao.erp.mapper.PartsMapper;
import com.sunhao.erp.mapper.TypeMapper;
import com.sunhao.erp.service.PartsService;
import com.sunhao.erp.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunhao
 * @CreateDate: 2018/7/23 21:50
 */
@Service
public class PartsServiceImpl implements PartsService {

    Logger logger = LoggerFactory.getLogger(PartsService.class);

    @Autowired
    private PartsMapper partsMapper;


    @Autowired
    private TypeMapper typeMapper;

    /**
     * id查找Parts对象
     * @param id
     * @return
     */
    @Override
    public Parts findById(Integer id) {
        return partsMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<Parts> findAllParts(Integer pageNo, String partsName, Integer partsType) {

        PageHelper.startPage(pageNo, Constant.PAGE_SIZE);

        List<Parts> partsList = partsMapper.findPartsWithType(partsName, partsType);

        PageInfo<Parts> pageInfo = new PageInfo<>(partsList);
        return pageInfo;
    }

    @Override
    public List<Type> findAllType() {
        TypeExample typeExample = new TypeExample();

        return typeMapper.selectByExample(typeExample);
    }

    /**
     * 配件入库
     *
     * @param parts
     */
    @Override
    public void saveParts(Parts parts) {
        partsMapper.insertSelective(parts);

        logger.debug("新增的配件：{}", parts);
    }

    /**
     * 删除配件
     *
     * @param id
     */
    @Override
    public void delPartsById(Integer id) {
        Parts parts =  partsMapper.selectByPrimaryKey(id);

        if(parts != null) {
            partsMapper.deleteByPrimaryKey(id);
        }
        logger.debug("删除的配件：{}", parts);
    }

    /**
     * 修改配件
     * @param parts
     */
    @Override
    public void editPartsById(Parts parts) {

        partsMapper.updateByPrimaryKeySelective(parts);

        logger.debug("修改的配件：{}", parts);
    }

}
