<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fs" uri="/fs-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
	<%@include file="/include/bsie_head.jsp" %>
</head>

<body>
<div class="container-fluid" >
	<div class="row-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"><i class="icon-th"></i></span>
				<h5>党员分组</h5>
				<div class="buttons"><a href="#" onclick="location=location;" class="btn btn-mini"><i class="icon-refresh"></i> 刷 新</a></div>
				<input type="hidden" id="pageFuncId" name="pageFuncId" value="${param.pageFuncId }"/>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered table-striped with-check table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
							<th>分组名称</th>
							<th>排序号</th>
						</tr>
					</thead>
					<tbody>
           				<c:forEach var="bean" items="${list}" varStatus="status">
           					<c:if test="${bean.type == 0}">
							<tr>
								<td><input type="checkbox" name="ids" value="${bean.id }"/></td>
								<td>${bean.name }</td>
								<td>${bean.ordernum }</td>
							</tr>
							</c:if>
           				</c:forEach>
						</tbody>
					</table>				
				</div>
			</div>

			<div class="widget-box">
				<div id="errorDiv" class="alert alert-error hide">
					<span id="errorInfo"></span>
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
<script src="${pageContext.request.contextPath}/static/member/js/group.js"></script>
<script src="${pageContext.request.contextPath}/static/common/js/sco.modal.js"></script>
<script src="${pageContext.request.contextPath}/static/common/js/sco.confirm.js"></script>

</body>
</html>