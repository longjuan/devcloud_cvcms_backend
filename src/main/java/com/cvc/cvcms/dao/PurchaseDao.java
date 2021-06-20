package com.cvc.cvcms.dao;

import com.cvc.cvcms.pojo.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/2 20:57
 * @Description:
 */
@Repository
@Mapper
public interface PurchaseDao {
    /**
     * 新增供应商
     * @param: [name, phone, address, remark]
     * @return: int
     */
    int saveSupplier(String name,String phone,String address,String remark);

    /**
     * 获取所有供应商
     * @param: []
     * @return: java.util.List<com.cvc.cvcms.pojo.Supplier>
     */
    List<Supplier> getAllSupplier();

    /**
     * 根据名称查询供应商
     * @param: [name]
     * @return: com.cvc.cvcms.pojo.Supplier
     */
    Supplier getSupplierByName(String name);

    /**
     * 根据名称修改供应商
     * @param: [supplier]
     * @return: int
     */
    int updateSupplier(Supplier supplier);

    /**
     * 根据id删除供应商
     * @param: [id]
     * @return: int
     */
    int deleteSupplier(Integer id);
}
