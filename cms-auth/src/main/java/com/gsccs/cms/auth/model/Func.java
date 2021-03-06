package com.gsccs.cms.auth.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Func implements Serializable{
    
	private String hasChildren;
    private String id;
    private String code;
    @JsonIgnore
    private String sql;
    private String name;
    private String isok;
    @JsonIgnore
    private String isokStr;
    private Integer ordernum;
    @JsonIgnore
    private String urlpath;
    private String parid;
    //
    private Integer level;
    private boolean checked;
    private String menuUrl;

    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getIsok() {
        return isok;
    }

  
    public void setIsok(String isok) {
        this.isok = isok == null ? null : isok.trim();
    }

  
    public Integer getOrdernum() {
        return ordernum;
    }

    
    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }
    
    public String getUrlpath() {
		return urlpath;
	}


	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}


	public String getParid() {
        return parid;
    }

    
    public void setParid(String parid) {
        this.parid = parid == null ? null : parid.trim();
    }

	public String getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsokStr() {
		isokStr="否";
		if ("1".equals(isok)) {
			isokStr="是";
		}
		return isokStr;
	}

	public void setIsokStr(String isokStr) {
		this.isokStr = isokStr;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	public boolean isChecked() {
		return checked;
	}


	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	public String getMenuUrl() {
		if(StringUtils.isNotEmpty(urlpath)){
			menuUrl = urlpath + (urlpath.indexOf("?") > 0 ? "": "?") + "&pageFuncId="+id;
		}
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
	
}