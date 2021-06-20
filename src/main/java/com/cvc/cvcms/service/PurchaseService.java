package com.cvc.cvcms.service;

import com.cvc.cvcms.pojo.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/2 21:03
 * @Description:
 */
@Service
public interface PurchaseService {

    /**
     * 新增供应商
     * @param: [name, phone, address, remark]
     * @return: int
     */
    boolean saveSupplier(String name,String phone,String address,String remark);

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
     * @return: boolean
     */
    boolean updateSupplier(Supplier supplier);

    /**
     * 根据id删除供应商
     * @param: [id]
     * @return: boolean
     */
    boolean deleteSupplier(Integer id);
}
