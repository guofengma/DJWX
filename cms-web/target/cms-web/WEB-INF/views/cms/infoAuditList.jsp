<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fs" uri="/fs-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="author" content="">

<%@include file="/include/bsie_head.jsp"%>

</head>

<body>
	<%@include file="/include/loading.jsp"%>
	<div>
		<div class="row-fluid">
			<div class="widget-box collapsible">
				<div class="widget-title">
					<span class="icon"> <i class="icon-search"></i>
					</span>
					<h5>信息搜索 ${channel.name}</h5>
					<div class="buttons">
						<a href="#collapseOne" data-toggle="collapse" class="btn btn-mini"><i
							class="icon-retweet"></i>展开/关闭</a>
					</div>
				</div>
				<div class="collapse in" id="collapseOne">
					<div class="widget-content">
						<form class="form-search" action="infoList.do">
							<input type="hidden" id="pageFuncId" name="pageFuncId"
								value="${param.pageFuncId }" /> <input type="hidden"
								id="channel" name="channel" value="${param.channel }" />
							信息标题： <input name="title"
								type="text" maxlength="500" class="input-medium search-query"
								value="${param.title }" /> 
							每页显示条数： <select name="pageSize" id="pageSize"
								style="width: 70px">
								<option selected="selected" value="10">10</option>
								<option value="30" ${"30"==param.pageSize?"selected":"" }>
									30</option>
								<option value="50" ${"50"==param.pageSize?"selected":"" }>
									50</option>
								<option value="100" ${"100"==param.pageSize?"selected":"" }>
									100</option>
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
					<h5>信息</h5>
					<div class="buttons">
						<a href="#" onclick="location.reload();" class="btn btn-mini"><i
							class="icon-refresh"></i> 刷 新</a>
					</div>
				</div>
				<div class="widget-content nopadding">
					<table
						class="table table-bordered table-striped with-check table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
								<th><fs:order colName="html索引号" col="htmlIndexnum" /></th>
								<th><fs:order colName="信息标题" col="title" /></th>
								<th><fs:order colName="审核" col="auditState" /></th>
								<th><fs:order colName="添加时间" col="addtime" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="bean" items="${list}" varStatus="status">
								<tr>
									<td><input type="checkbox" name="ids" value="${bean.id }" /></td>
									<td>${bean.htmlIndexnum }</td>
									<td> <a rel="tooltip" data-placement="top" title="点击打开信息页面"
										href='infoPreview.do?id=${bean.id }'
										target="_blank"></a>
										${bean.title }</td>
									
									<TD><c:if test='${"0" == bean.auditState }'>
											未审核
										</c:if> 
										<c:if test='${"1" == bean.auditState }'>
											已审核
										</c:if>
										<c:if test='${"2" == bean.auditState }'>
											<a rel="tooltip" data-placement="top" href='#'
												onclick='javaScript:void();' title="${bean.auditResult }">驳回</a>
										</c:if>
									</TD>
									<TD>${bean.addtimeStr }</TD>
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
	<script src="${pageContext.request.contextPath}/static/admin/cms/js/info.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/common/js/sco.modal.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/common/js/sco.confirm.js"></script>

</body>
</html>