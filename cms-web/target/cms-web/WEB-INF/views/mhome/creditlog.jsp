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
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box collapsible">
				<div class="widget-title">
					<span class="icon"> <i class="icon-search"></i>
					</span>
					<h5>积分记录搜索</h5>
					<div class="buttons">
						<a href="#collapseOne" data-toggle="collapse" class="btn btn-mini"><i class="icon-retweet"></i>展开/关闭</a>
					</div>
				</div>
				<div class="collapse in" id="collapseOne">
					<div class="widget-content">
						<form class="form-search" action="creditlog.do">
							积分规则: <select name="creditruleid" style="width: 120px">
								<option value="">全部
									<c:forEach var="bean" items="${creditruleList}" varStatus="status">
										<option value="${bean.id }" ${bean.id==param.creditruleid?"selected":"" }>${bean.name }
									</c:forEach>
							</select> 类型: <select name="type" style="width: 70px">
								<option value="">全部</option>
								<option value="1" ${"1"==param.type?"selected":"" }>奖励</option>
								<option value="0" ${"0"==param.type?"selected":"" }>惩罚</option>
							</select> 每页显示条数： <select name="pageSize" id="pageSize" style="width: 70px">
								<option selected="selected" value="10">10</option>
								<option value="30" ${"30"==param.pageSize?"selected":"" }>30</option>
								<option value="50" ${"50"==param.pageSize?"selected":"" }>50</option>
								<option value="100" ${"100"==param.pageSize?"selected":"" }>100</option>
								<option value="200" ${"200"==param.pageSize?"selected":"" }>200</option>
								<option value="500" ${"500"==param.pageSize?"selected":"" }>500</option>
								<option value="1000" ${"1000"==param.pageSize?"selected":"" }>1000</option>

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
					<h5>积分记录</h5>
					<div class="buttons">
						<a href="#" onclick="location.reload();" class="btn btn-mini"><i class="icon-refresh"></i> 刷 新</a>
					</div>
				</div>
				<div class="widget-content nopadding">

					<table class="table table-bordered table-striped table-hover">
						<thead>
							<tr>

								<th><fs:order colName="积分规则" col="creditruleid" /></th>
								<th><fs:order colName="类型" col="type" /></th>
								<th><fs:order colName="积分" col="credit" /></th>
								<th><fs:order colName="经验" col="experience" /></th>
								<th><fs:order colName="时间" col="credittime" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="bean" items="${list}" varStatus="status">
								<tr>
									<TD>${bean.creditrule }</TD>
									<TD>${bean.typeStr }</TD>
									<TD>${bean.credit }</TD>
									<TD>${bean.experience }</TD>
									<TD>${bean.credittimeStr }</TD>
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

			</div>
		</div>
		<!--/row-->


	</div>
	<!--/span-->
	<%@include file="/include/foot.jsp"%>
</body>
</html>