<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fs" uri="/fs-tags" %>
<!DOCTYPE html>
<html lang="en"><head>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="author" content="">

	<%@include file="/include/bsie_head.jsp" %>

  </head>

<body>
	<%@include file="/include/loading.jsp" %>
          <div class="container-fluid" >
      <div class="row-fluid">

<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-edit"></i>
								</span>
								<h5>查看简历</h5>
							</div>
							<div class="widget-content nopadding">
							
								<form class="form-horizontal" method="post" action="resumeProDo.do" name="form" id="form"  onsubmit="return formCheck();">
									
                                    <div id="nameDiv" class="control-group">
                                        <label class="control-label" style="width:160px">职位</label>
                                        <div class="controls" style="margin-left: 190px;">
							${resume.job }
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">姓名</label>
                                        <div class="controls" style="margin-left: 190px;">
										${resume.name }
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">照片</label>
                                        <div class="controls" style="margin-left: 190px;">
							<c:if test='${resume.img!=null && resume.img!=""}'>
							<a href="${pageContext.request.contextPath}${resume.img }" target="_blank" rel="tooltip" data-placement="top" title="点击查看原图"><img src="${pageContext.request.contextPath}${resume.img }" width="80" height="100"/></a>
							</c:if>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">性别</label>
                                        <div class="controls" style="margin-left: 190px;">
                                         ${resume.sexStr }
                                        </div>
                                    </div>
                                    <div  class="control-group">
                                        <label class="control-label" style="width:160px">出生日期</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.birthdayStr }
                                        </div>
                                    </div>
                                    <div id="templetDiv" class="control-group">
                                        <label class="control-label" style="width:160px">Email</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.email }
                                        </div>
                                    </div>
                                    <div  class="control-group">
                                        <label class="control-label" style="width:160px">QQ</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.qq }
                                        </div>
                                    </div>
                                    <div  class="control-group">
                                        <label class="control-label" style="width:160px">电话</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.tel }
                                        </div>
                                    </div>
                                    <div  class="control-group">
                                        <label class="control-label" style="width:160px">联系地址</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.address }
                                        </div>
                                    </div>
                                    <div  class="control-group">
                                        <label class="control-label" style="width:160px">简介</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.content }
                                        </div>
                                    </div>
                                    <div  class="control-group">
                                        <label class="control-label" style="width:160px">附件</label>
                                        <div class="controls" style="margin-left: 190px;">
							<c:if test='${resume.attch!=null && resume.attch!=""}'>
							<a href="${pageContext.request.contextPath}${resume.attch }" target="_blank">附件下载</a>
							</c:if>
                                        </div>
                                    </div>
                                    
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">添加时间</label>
                                        <div class="controls" style="margin-left: 190px;">
                                        <fmt:formatDate value="${resume.addtime}" pattern="yyyy-MM-dd" />
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">IP</label>
                                        <div class="controls" style="margin-left: 190px;">
								${resume.ip }
								</div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">会员</label>
                                        <div class="controls" style="margin-left: 190px;">
							${resume.membername}
								</div>
                                    </div>
                                   
                                    <div id="recontentDiv" class="control-group">
                                        <label class="control-label" style="width:160px">回复内容</label>
                                        <div class="controls" style="margin-left: 190px;">
									${resume.recontent }
                                        </div>
                                    </div>
						<c:if test="${resume.retime != null}">
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">回复时间</label>
                                        <div class="controls" style="margin-left: 190px;">
                                        <fmt:formatDate value="${resume.retime}" pattern="yyyy-MM-dd" />
                                        </div>
                                    </div>
                                     </c:if>
                                    
						<c:if test="${resume.reusername != null}">
                                    <div class="control-group">
                                        <label class="control-label" style="width:160px">回复人</label>
                                        <div class="controls" style="margin-left: 190px;">
                                        	${resume.reusername}
                                        </div>
                                    </div>
						</c:if>
                                    <div class="control-group">
                                        <div class="controls" style="margin-left: 190px;">
											<a  onclick="history.back(-1)" class="btn"  >返 回</a>
                                        </div>
                                    </div>
                                </form>
							</div>
	
						</div>
      </div><!--/row-->
          
          
        </div><!--/span-->
<%@include file="/include/foot.jsp" %>
							
</body></html>