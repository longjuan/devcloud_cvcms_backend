package com.cvc.cvcms.service.impl;

import com.cvc.cvcms.dao.ComplainDao;
import com.cvc.cvcms.pojo.Complain;
import com.cvc.cvcms.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/9 9:09
 * @Description:
 */
@Service
@Transactional
public class ComplainServiceImpl implements ComplainService {

    @Autowired
    ComplainDao complainDao;

    @Override
    public boolean addComplain(Complain complain) {
        return complainDao.addComplain(complain) > 0;
    }

    @Override
    public boolean updateComplain(Complain complain) {
        return complainDao.updateComplain(complain) > 0;
    }

    @Override
    public List<Complain> getAll() {
        return complainDao.getAll();
    }

    @Override
    public Complain getComplainById(Integer userId) {
        return complainDao.getComplainById(userId);
    }
}
