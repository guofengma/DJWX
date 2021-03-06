<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>管理中心</title>
<!-- ECharts单文件引入 -->
<script src="static/table/echarts.js"></script>
	<script src="${pageContext.request.contextPath}/static/common/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<%@include file="/include/bsie_head.jsp"%>
</head>
<body>
<input id="P1P1" value="${P1P1}" hidden >
<input id="P2P1" value="${P2P1}" hidden >
<input id="P2P2" value="${P2P2}" hidden >
<input id="P3P1" value="${P3P1}" hidden >
<input id="P4P1" value="${P4P1}" hidden >

<input id="P2P1Val" value="" hidden >
<script type="text/javascript">
$(document).ready(function(){
//图二数据拼接
	var weekZh = new Array("周日","周一","周二","周三","周四","周五","周六","周日","周一","周二","周三","周四","周五","周六");
	var weekNum=$("#P2P1").val();
	var P2P1Val=new Array(weekZh[weekNum],weekZh[parseInt(weekNum)+1],weekZh[(parseInt(weekNum)+2)],weekZh[(parseInt(weekNum)+3)],weekZh[(parseInt(weekNum)+4)],weekZh[(parseInt(weekNum)+5)],weekZh[(parseInt(weekNum)+6)]);
	$("#P2P1Val").val(P2P1Val);
	console.log($("#P2P2").val().split(","));
});
</script>
	<div>
		<div class="row-fluid">
			<div class="widget-box collapsible">
				<div class="widget-title">
					<span class="icon"> <i class="icon-search"></i>
					</span>
					<h5>
					    ${current_user.name }!
						&nbsp;&nbsp;欢迎使用党建管理平台
					</h5>
				</div>
			</div>
		</div>
		<!--/row-->

	<div class="row-fluid clearfix">
		<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		<div style="width: 100%;">
			<div id="table1" style="width:49%;height: 300px;float: left;    border:1px solid rgb(218, 218, 218);"></div>
			<div id="table2" style="width:49%;height: 300px;float: left;    border:1px solid  rgb(218, 218, 218);"></div>
			<div id="table3" style="width:49%;height: 300px;float: left;    border:1px solid  rgb(218, 218, 218);"></div>
			<div id="table4" style="width:49%;height: 300px;float: left;    border:1px solid  rgb(218, 218, 218);"></div>
		</div>
	
	<!-- ECharts单文件引入 -->
	<script type="text/javascript">
		// 路径配置
		require.config({
			paths : {
				echarts : 'static/table/dist'
			}
		});
		// 使用
		require([ 'echarts', 'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('table1'));
			var option = {
			title : {
		        text: '党员群众关系比例',
		        x:'center'
		    },
			tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['党员','群众']
		    },
		    toolbox: {
		        show : true,
		        feature : {
					dataView : {show: true, readOnly: false},
					restore : {show: true},
					saveAsImage : {show: true}
				}
		    },
		    calculable : true,
		    series : [
		        {
		            name:'人数占比',
		            type:'pie',
		            radius : ['50%', '70%'],
		            itemStyle : {
		                normal : {
		                    label : {
		                        show : false
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : true,
		                        position : 'center',
		                        textStyle : {
		                            fontSize : '30',
		                            fontWeight : 'bold'
		                        }
		                    }
		                }
		            },
		            data:[
		                {value:$("#P1P1").val(), name:'党员'},
		                {value:(192398-$("#P1P1").val()), name:'群众'}
		            ]
		        }
		    ]
		};
			// 为echarts对象加载数据
			myChart.setOption(option);
		});
		// 使用
		require([ 'echarts', 'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
		], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('table2'));
			var option = {
					title : {
						text : '最近一周文章发送数量',
						x:'center'
					},
					tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
					    data : ['发送数量' ],
						y:'bottom'
				    },
				    toolbox: {
				        show : true,
				        feature : {
							mark : {
								show : false
							},
							dataView : {
								show : true,
								readOnly : true
							},
							magicType : {
								show : false,
								type : [ 'line', 'bar', 'stack', 'tiled' ]
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
					}
				    },
				    calculable : true,
				    xAxis : [
				        {
				        	type : 'category',
							boundaryGap : false,
							data :  $("#P2P1Val").val().split(",")
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'发送数量',
				            type:'line',
				            stack: '总量',
				            itemStyle: {normal: {areaStyle: {type: 'default'}}},
				            data:$("#P2P2").val().split(",")
				        }
				    ]
				};
			// 为echarts对象加载数据
			myChart.setOption(option);
		});
		// 使用
		require([ 'echarts', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('table3'));
			var option = {
				title:{
					text:'党员年龄分布',
					x:'center'
				},
				tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			    	data : [ '党员数量' ],
					y:'bottom'
			    },
			    toolbox: {
			        show : true,
			        feature : {
			        	mark : {
							show : false
						},
						dataView : {
							show : true,
							readOnly : true
						},
						magicType : {
							show : false,
							type : [ 'line', 'bar', 'stack', 'tiled' ]
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
			        }
			    },
			    calculable : true,
			    xAxis : [
			             {
				        		type : 'category',
								data : [ "<20岁","20~30岁","30~40岁","40~50岁","50~60岁",">60岁" ]
				        }
			    ],
			    yAxis : [
			             {
					        	type : 'value'
					        }
			    ],
			    series : [
			        {
			            name:'党员数量',
			            type:'bar',
			            stack: '总量',
			            itemStyle : { normal: {label : {show: true}}},
			            data:$("#P3P1").val().split(",")
			        }
			    ]
			};
			// 为echarts对象加载数据
			myChart.setOption(option);
		});
		// 使用
		require([ 'echarts', 'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		], function(ec) {
			// 基于准备好的dom，初始化echarts图表
			var myChart = ec.init(document.getElementById('table4'));
			var option = {
				    title : {
				        text: '党员文化程度分布',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'vertical',
				        x : 'left',
				        data:['本科','大专','高中','初中','小学','其他']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				          dataView : {show: true, readOnly: false},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    series : [
				        {
				            name:'党员数量占比',
				            type:'pie',
				            radius : [30, 110],
				            center : ['50%', 200],
				            roseType : 'area',
				            x: '50%',               // for funnel
				            max: 40,                // for funnel
				            sort : 'ascending',     // for funnel
				            data:[
				                {value:2353, name:'本科'},
				                {value:1699, name:'大专'},
				                {value:1461, name:'高中'},
				                {value:1211, name:'初中'},
				                {value:687, name:'小学'},
				                {value:1520, name:'其他'}
				            ]
				        }
				    ]
				};
				                    
			// 为echarts对象加载数据
			myChart.setOption(option);
		});
		
		
		
	
		
	</script>
		</div>
	<!--/.fluid-container-->

	<%@include file="/include/foot.jsp"%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/common/js/jquery.jqChart.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/common/js/jquery.jqRangeSlider.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/smoothness/jquery-ui-1.8.21.css" />

	<script src="${pageContext.request.contextPath}/static/common/js/jquery.jqChart.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/common/js/jquery.jqRangeSlider.min.js" type="text/javascript"></script>

</body>
</html>