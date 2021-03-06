<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="bean" items="${list}" varStatus="status">
<tr treeItemLevel="${level }">
	<td>
	<input type="checkbox" name="ids" value="${bean.id }" uniform="true"/>
	</td>
	<td>
	<c:forEach begin="1" end="${level}" var="i">
	<img align="absmiddle" src="${pageContext.request.contextPath}/static/tree/image/empty.gif" />
	</c:forEach>
	<c:if test='${"true" == bean.hasChildren}'>
	<img align="absmiddle" style="cursor:pointer" onClick="displayChildren(this);" isAjax="true" isOpen="false" id="${bean.id }" src="${pageContext.request.contextPath}/static/tree/image/plus.gif" />
	</c:if>
	<c:if test='${"true" != bean.hasChildren}'>
	<img align="absmiddle" src="${pageContext.request.contextPath}/static/tree/image/empty.gif" />
	</c:if>
	<img align="absmiddle" border="0" src="${pageContext.request.contextPath}/static/tree/image/folder.gif" />&nbsp;
	${bean.name }</td>
	<td>${bean.corptitle }</td>
	<td>${bean.isokStr }</td>
	<td>
	<a href="unitOrder.do?pageFuncId=${param.pageFuncId }&type=up&id=${bean.id }" rel="tooltip" data-placement="top" title="点击上升"><img src="${pageContext.request.contextPath}/static/unicorn/img/up.gif" /></a>
	&nbsp;&nbsp;&nbsp;
	<a href="unitOrder.do?pageFuncId=${param.pageFuncId }&type=down&id=${bean.id }" rel="tooltip" data-placement="bottom" title="点击下降"><img src="${pageContext.request.contextPath}/static/unicorn/img/down.gif" /></a>
	</td>
</tr>
</c:forEach>