package com.ym.springbootproject.moudle.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ym.springbootproject.moudle.file.entity.UploadEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileUploadMapper extends BaseMapper<UploadEntity> {

}
