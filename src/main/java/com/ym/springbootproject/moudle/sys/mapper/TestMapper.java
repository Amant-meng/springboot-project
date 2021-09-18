package com.ym.springbootproject.moudle.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ym.springbootproject.moudle.sys.entity.FtpData;
import com.ym.springbootproject.moudle.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TestMapper extends BaseMapper<FtpData> {

    List<Map> selectData();

    List<SysRole> selectSysRoleList();
}
