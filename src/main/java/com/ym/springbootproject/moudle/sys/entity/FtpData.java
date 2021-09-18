package com.ym.springbootproject.moudle.sys.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * FTP数据包解析实体类
 * @author yangmeng
 * @date 2021/06/08
 */
@TableName(value = "ftp_data")
public class FtpData {

    private static final long serialVersionUID = 1L;

    private String id;
    private String cjdw;
    private String ajmc;
    private String bcjr;
    private String cjr;
    private String ftpwjlj;
    private String wjmc;
    private Date rksj;
    private String del_flag;
    private Date gxsj;
    private String createXm;
    private String createLxdh;
    private String createGajgdm;
    private String createGajgmc;
    private String createSfzh;
    private String updateXm;
    private String updateLxdh;
    private String updateGajgdm;
    private String updateGajgmc;
    private String updateSfzh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCjdw() {
        return cjdw;
    }

    public void setCjdw(String cjdw) {
        this.cjdw = cjdw;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getBcjr() {
        return bcjr;
    }

    public void setBcjr(String bcjr) {
        this.bcjr = bcjr;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getFtpwjlj() {
        return ftpwjlj;
    }

    public void setFtpwjlj(String ftpwjlj) {
        this.ftpwjlj = ftpwjlj;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getCreateXm() {
        return createXm;
    }

    public void setCreateXm(String createXm) {
        this.createXm = createXm;
    }

    public String getCreateLxdh() {
        return createLxdh;
    }

    public void setCreateLxdh(String createLxdh) {
        this.createLxdh = createLxdh;
    }

    public String getCreateGajgdm() {
        return createGajgdm;
    }

    public void setCreateGajgdm(String createGajgdm) {
        this.createGajgdm = createGajgdm;
    }

    public String getCreateGajgmc() {
        return createGajgmc;
    }

    public void setCreateGajgmc(String createGajgmc) {
        this.createGajgmc = createGajgmc;
    }

    public String getCreateSfzh() {
        return createSfzh;
    }

    public void setCreateSfzh(String createSfzh) {
        this.createSfzh = createSfzh;
    }

    public String getUpdateXm() {
        return updateXm;
    }

    public void setUpdateXm(String updateXm) {
        this.updateXm = updateXm;
    }

    public String getUpdateLxdh() {
        return updateLxdh;
    }

    public void setUpdateLxdh(String updateLxdh) {
        this.updateLxdh = updateLxdh;
    }

    public String getUpdateGajgdm() {
        return updateGajgdm;
    }

    public void setUpdateGajgdm(String updateGajgdm) {
        this.updateGajgdm = updateGajgdm;
    }

    public String getUpdateGajgmc() {
        return updateGajgmc;
    }

    public void setUpdateGajgmc(String updateGajgmc) {
        this.updateGajgmc = updateGajgmc;
    }

    public String getUpdateSfzh() {
        return updateSfzh;
    }

    public void setUpdateSfzh(String updateSfzh) {
        this.updateSfzh = updateSfzh;
    }
}
