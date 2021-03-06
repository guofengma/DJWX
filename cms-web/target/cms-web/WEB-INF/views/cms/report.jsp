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
<jsp:include page="/head.do" flush="true" />
          <div class="container-fluid" >
      <div class="row-fluid">
<div class="widget-box collapsible">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-search"></i>
								</span>
								<h5>申报项目搜索</h5>
								<div class="buttons"><a href="#collapseOne" data-toggle="collapse" class="btn btn-mini"><i class="icon-retweet"></i>展开/关闭</a></div>
							</div>
							<div class="collapse in" id="collapseOne">
							<div class="widget-content">
							<form class="form-search" action="report.do" method="post">
			<input type="hidden" id="pageFuncId" name="pageFuncId" value="${param.pageFuncId }"/>
			
			查询码:
								
								<input name="querycode" type="text" maxlength="50"
									class="input-medium search-query" value="${param.querycode }"   />
								名称:
								
								<input name="name" type="text" maxlength="100"
									class="input-medium search-query" value="${param.name }"   />
								联系人:
								
								<input name="linkman" type="text" maxlength="50"
									class="input-medium search-query" value="${param.linkman }"   />
								签发人:
								
								<input name="issuer" type="text" maxlength="50"
									class="input-medium search-query" value="${param.issuer }"   />
								办理状态:
								<select name="state" style="width:90px">
								<option value="">全部
								<option value="0"  ${"0"==param.state?"selected":"" }>办理中
								<option value="1"  ${"1"==param.state?"selected":"" }>已办结
								</select>
			
			
		
								每页显示条数：
								<select name="pageSize" id="pageSize" style="width:70px">
									<option selected="selected" value="10">
										10
									</option>
									<option value="30" ${"30"==param.pageSize?"selected":"" }>
										30
									</option>
									<option value="50" ${"50"==param.pageSize?"selected":"" }>
										50
									</option>
									<option value="100" ${"100"==param.pageSize?"selected":"" }>
										100
									</option>
									<option value="200" ${"200"==param.pageSize?"selected":"" }>
										200
									</option>
									<option value="500" ${"500"==param.pageSize?"selected":"" }>
										500
									</option>
									<option value="1000" ${"1000"==param.pageSize?"selected":"" }>
										1000
									</option>

								</select>
								
							<button type="submit" class="btn"><i class="icon-search"></i> 搜 索</button>
							</form>
							</div>
							</div>
							</div>
<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-th"></i>
								</span>
								<h5>申报项目</h5>
								<div class="buttons"><a href="#" onclick="location.reload();" class="btn btn-mini"><i class="icon-refresh"></i> 刷 新</a></div>
							</div>
							<div class="widget-content nopadding">
							
								<table class="table table-bordered table-striped with-check table-hover">
									<thead>
										<tr>
											<th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
											<th>
							<fs:order colName="查询码" col="querycode"/>
						</th>
						<th>
							<fs:order colName="名称" col="name"/>
						</th>
						<th>
							
							<fs:order colName="联系人" col="linkman"/>
						</th>
						<th>
							
							<fs:order colName="签发人" col="issuer"/>
						</th>
						<th>
							
							<fs:order colName="申报时间" col="addtime"/>
						</th>
						<th>
							
							<fs:order colName="办理状态" col="state"/>
						</th>
										</tr>
									</thead>
									<tbody>
           					 			<c:forEach var="bean" items="${list}" varStatus="status">
										<tr>
											<td><input type="checkbox" name="ids" value="${bean.id }"/></td>
											<TD  >${bean.querycode }</TD>
						<TD>${bean.name }</TD>
						<TD  >${bean.linkman }</TD>
						<TD >${bean.issuer }</TD>
						<TD ><fmt:formatDate value="${bean.addtime}" pattern="yyyy-MM-dd" /></TD>
						<TD >
							${"1"==bean.state?"已办结":"办理中" }
						</TD>
										</tr>
           					 			</c:forEach>
									</tbody>
								</table>		
							<div id="errorDiv" class="alert alert-error hide">
					            <span id="errorInfo"></span>
					          </div>			
							</div>
							
								<div class="navbar">	
								<div class="navbar-inner">
									${pageStr }
								</div>
								</div>	
								<div class="navbar">	
								<div class="navbar-inner">
									<fs:operButtons />
								</div>
								</div>
							
						</div>
      </div><!--/row-->
          
          
        </div><!--/span-->

	<%@include file="/include/foot.jsp" %>
<script src="${pageContext.request.contextPath}/static/common/js/checkAll.js"></script>
            <script src="${pageContext.request.contextPath}/static/admin/cms/js/report.js"></script>
            <script src="${pageContext.request.contextPath}/static/common/js/sco.modal.js"></script>
            <script src="${pageContext.request.contextPath}/static/common/js/sco.confirm.js"></script>

</body></html>