package com.ym.springbootproject.moudle.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ym.springbootproject.moudle.sys.entity.FtpData;
import com.ym.springbootproject.moudle.sys.entity.SysRole;

import java.util.List;
import java.util.Map;


public interface TestService extends IService<FtpData> {

    List<Map> selectData();

    List<SysRole> selectSysRoleList();
}
