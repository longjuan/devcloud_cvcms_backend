package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.PurchaseDao;
import com.cvc.cvcms.pojo.Supplier;
import com.cvc.cvcms.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/2 21:04
 * @Description:
 */
@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseDao purchaseDao;

    @Override
    public boolean saveSupplier(String name, String phone, String address, String remark) {
        return purchaseDao.saveSupplier(name,phone,address,remark) > 0 ;
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return purchaseDao.getAllSupplier();
    }

    @Override
    public Supplier getSupplierByName(String name) {
        return purchaseDao.getSupplierByName(name);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
            return purchaseDao.updateSupplier(supplier) > 0 ;
    }

    @Override
    public boolean deleteSupplier(Integer id) {
        return purchaseDao.deleteSupplier(id) > 0;
    }
}
