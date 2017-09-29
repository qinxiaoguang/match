
<%--
  Created by IntelliJ IDEA.
  User: qxg
  Date: 17-7-5
  Time: 下午9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>
<html>
<head>
    <title>主页</title>
    <mylink>
        <style>
            .members{
                border:1px solid #ccc;
                padding:8px;
                border-radius:5px;
                margin-bottom:8px;
            }

            span{
                cursor: pointer;
            }
        </style>
    </mylink>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">

        <div class="panel panel-default" style="margin-top:66px;width:666px;">
            <div class="panel-body">
                <b>详细信息</b>
            </div>
            <div class="panel-footer">
                <!--显示详细信息-->
                <div class="input-group">
                    <div class="input-group-addon">比赛信息</div>
                    <input class="form-control"type="text" readonly value="${detail.matchInfo.name}"/>
                </div>
                <div class="input-group">
                    <div class="input-group-addon">比赛主题</div>
                    <input class="form-control"type="text" readonly value="${detail.matchItem.title}"/>
                </div>

                <div class="members">
                    比赛队长: <span class="label label-info" >${detail.leader.username}&nbsp;${detail.leader.name}</span>
                </div>

                <div class="members">
                    比赛成员:
                    <c:forEach items="${detail.members}" var="user">
                        <span class="label label-info" >${user.username}&nbsp;${user.name}</span>&nbsp;
                    </c:forEach><br>
                </div>

                <div class="members">
                    指导教师:
                    <c:forEach items="${detail.teachers}" var="user">
                        <span class="label label-info" >${user.username}&nbsp;${user.name}</span>&nbsp;
                    </c:forEach><br>
                </div>

                <div class="input-group">
                    <div class="input-group-addon">申请表</div>
                    <span class="form-control"><a href='#'>${detail.apply.name}</a>  这里需要实现下载功能<br></span>
                </div>
            </div>
        </div>



        <c:if test="${pass != 'true'}">
            <button id="pass" class="btn btn-info" >审核通过</button>
        </c:if>
        <c:if test="${pass == 'true'}">
            <b>已审核</b>
        </c:if>
    </div>

    <!--模态框，显示详细信息 不用的时候删除-->
    <div class="match_info modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">类别添加</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="name" class="form-control" placeholder="请输入添加的比赛类别"/>
                    <select id="type">

                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="add" class="btn btn-primary">创建</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--模态框结束-->
</mydiv>

<myscript>
    <script>
        $(document).ready(function(){
            $("#pass").click(function(){
                //审核通过，点击后，可以进行确认，然后再通过，为了方便，先不确认
                $.ajax({
                    url:"/teacher/check/detail/pass/${itemId}",
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == 'true'){
                            alert("审核成功");
                            location.href="/check/teacher";
                        }else{
                            alert(json["error"]);
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
