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
	<input type="hidden" id="pageFuncId" value="${param.pageFuncId }">
	<jsp:include page="/head.do" flush="true" />
	<div class="container-fluid" >
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"> <i class="icon-th"></i>
					</span>
					<h5>菜单管理</h5>
					<div class="buttons">
						<a href="#" onclick="location.reload();" class="btn btn-mini"><i class="icon-refresh"></i> 刷 新</a>
					</div>
				</div>
				<div class="widget-content nopadding">

					<table class="table table-bordered table-striped with-check table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
								<th style="min-width: 200px">菜单名称</th>
								<th>代码</th>
								<th>有效</th>
								<th>链接地址</th>
								<th>排序</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="func" items="${funcs}" varStatus="status">
								<tr treeItemLevel="1">
									<td><input type="checkbox" name="ids" value="${func.id }" /></td>
									<td><c:if test='${"true" == func.hasChildren}'>
											<img align="absmiddle" style="cursor: pointer" onClick="displayChildren(this);" isAjax="true" isOpen="false" id="${func.id }" src="${pageContext.request.contextPath}/static/tree/image/plus.gif" />
										</c:if> <c:if test='${"true" != func.hasChildren}'>
											<img align="absmiddle" src="${pageContext.request.contextPath}/static/tree/image/empty.gif" />
										</c:if> <img align="absmiddle" border="0" src="${pageContext.request.contextPath}/static/tree/image/folder.gif" />&nbsp; ${func.name }</td>
									<td>${func.code }</td>
									<td>${func.isokStr }</td>
									<td>${func.urlpath }</td>
									<td><a href="funcOrder.do?pageFuncId=${param.pageFuncId }&type=up&id=${func.id }" rel="tooltip" data-placement="top" title="点击上升"><img src="${pageContext.request.contextPath}/static/unicorn/img/up.gif" /></a> &nbsp;&nbsp;&nbsp; <a href="funcOrder.do?pageFuncId=${param.pageFuncId }&type=down&id=${func.id }" rel="tooltip" data-placement="bottom" title="点击下降"><img src="${pageContext.request.contextPath}/static/unicorn/img/down.gif" /></a></td>
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
						<fs:operButtons />
					</div>
				</div>

			</div>
		</div>
		<!--/row-->


	</div>
	<!--/span-->

	<%@include file="/include/foot.jsp"%>

	<script src="${pageContext.request.contextPath}/static/tree/bootstraptree.js"></script>
	<script>
            basePath="${pageContext.request.contextPath}";
			ajaxUrl="${pageContext.request.contextPath}/sysmg/funcSon.do";
			ajaxPara="";
            </script>

	<script src="${pageContext.request.contextPath}/static/common/js/checkAll.js"></script>
	<script src="${pageContext.request.contextPath}/static/admin/sys/js/func.js"></script>
	<script src="${pageContext.request.contextPath}/static/common/js/sco.modal.js"></script>
	<script src="${pageContext.request.contextPath}/static/common/js/sco.confirm.js"></script>
</body>
</html>