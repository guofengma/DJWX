<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fs" uri="/fs-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>信息管理系统</title>
<meta name="description" content="">
<meta name="author" content="">

<%@include file="/include/bsie_head.jsp"%>

</head>

<body>
	<%@include file="/include/loading.jsp"%>
	<jsp:include page="/head.do" flush="true" />
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box collapsible">
				<div class="widget-title">
					<span class="icon"> <i class="icon-search"></i>
					</span>
					<h5>角色搜索</h5>
					<div class="buttons">
						<a href="#collapseOne" data-toggle="collapse" class="btn btn-mini"><i class="icon-retweet"></i>展开/关闭</a>
					</div>
				</div>
				<div class="collapse in" id="collapseOne">
					<div class="widget-content">
						<form class="form-search" action="role.do" method="post">
							<input type="hidden" id="pageFuncId" name="pageFuncId" value="${param.pageFuncId }" /> 角色: <input name="name" type="text" maxlength="500" class="input-medium search-query" value="${param.name }" /> 每页显示条数： <select name="pageSize" id="pageSize" style="width: 70px">
								<option selected="selected" value="10">10</option>
								<option value="30" ${"30"==param.pageSize?"selected":"" }>30</option>
								<option value="50" ${"50"==param.pageSize?"selected":"" }>50</option>
							</select>

							<button type="submit" class="btn">
								<i class="icon-search"></i> 搜 索
							</button>
						</form>
					</div>
				</div>
			</div>
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"> <i class="icon-th"></i>
					</span>
					<h5>角色</h5>
					<div class="buttons">
						<a href="#" onclick="location.reload();" class="btn btn-mini"><i class="icon-refresh"></i> 刷 新</a>
					</div>
				</div>
				<div class="widget-content nopadding">

					<table class="table table-bordered table-striped with-check table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
								<th><fs:order colName="角色ID" col="id" /></th>
								<th><fs:order colName="角色名称" col="name" /></th>
								<th><fs:order colName="是否有效" col="isok" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="bean" items="${list}" varStatus="status">
								<tr>
									<td><input type="checkbox" name="ids" value="${bean.id }" /></td>
									<td>${bean.id }</td>
									<td>${bean.name }</td>
									<td>${bean.isokStr }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div id="errorDiv" class="alert alert-error hide">
						<span id="errorInfo"></span>
					</div>
				</div>

				<div class="navbar">
					<div class="navbar-inner">${pageStr }</div>
				</div>
				<div class="navbar">
					<div class="navbar-inner">
						<fs:operButtons />
					</div>
				</div>

			</div>
		</div>
		<!--/row-->


	</div>
	<!--/span-->

	<%@include file="/include/foot.jsp"%>
	<script src="${pageContext.request.contextPath}/static/common/js/checkAll.js"></script>
	<script src="${pageContext.request.contextPath}/static/admin/sys/js/role.js"></script>
	<script src="${pageContext.request.contextPath}/static/common/js/sco.modal.js"></script>
	<script src="${pageContext.request.contextPath}/static/common/js/sco.confirm.js"></script>
	
</body>
</html>