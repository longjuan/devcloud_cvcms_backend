package com.cvc.cvcms;

import com.cvc.cvcms.controller.UserManageController;
import com.cvc.cvcms.dao.*;
import com.cvc.cvcms.pojo.Complain;
import com.cvc.cvcms.pojo.Order;
import com.cvc.cvcms.pojo.Promotion;
import com.cvc.cvcms.pojo.Supplier;
import com.cvc.cvcms.service.*;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
class CvcmsApplicationTests {
    //注释测试代码检查

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
    }

}
