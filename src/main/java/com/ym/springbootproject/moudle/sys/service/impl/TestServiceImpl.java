package com.ym.springbootproject.moudle.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.springbootproject.moudle.sys.entity.FtpData;
import com.ym.springbootproject.moudle.sys.entity.SysRole;
import com.ym.springbootproject.moudle.sys.mapper.TestMapper;
import com.ym.springbootproject.moudle.sys.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, FtpData> implements TestService {

    @Autowired
   private TestMapper testMapper;

    @Override
    public List<Map> selectData() {

        return testMapper.selectData();
    }

    @Override
    public List<SysRole> selectSysRoleList() {

        return testMapper.selectSysRoleList();
    }
}
