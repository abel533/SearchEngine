<html>
<head>
    <title>匆匆搜索</title>
    <script src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <link href="${request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .title-init {
            padding: 120px 0 30px;
        }

        .searchText {
            width: 690px;
            height: 35px;
            line-height: 35px;
            font-size: 18px;
            padding: 5px 9px;
        }

        .searchText:hover {
            border-width: 1px;
            border-color: #a0a0a0 #b9b9b9 #b9b9b9 #b9b9b9 !important;
        }

        .searchBtn {
            width: 100px;
            height: 35px;
            line-height: 27px;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#f5f5f5), to(#f1f1f1));
            background-image: -webkit-linear-gradient(top, #f5f5f5, #f1f1f1);
            -webkit-border-radius: 2px;
            -webkit-user-select: none;
            background-color: #f2f2f2;
            border: 1px solid #f2f2f2;
            border-radius: 2px;
            color: #757575;
            cursor: default;
            font-family: arial, sans-serif;
            font-size: 13px;
            font-weight: bold;
            margin: 11px 4px;
            min-width: 54px;
            padding: 0 16px;
            text-align: center;
        }

        .searchBtn:hover {
            background-image: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f1f1f1));
            background-image: -webkit-linear-gradient(top, #f8f8f8, #f1f1f1);
            -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            background-color: #f8f8f8;
            background-image: linear-gradient(top, #f8f8f8, #f1f1f1);
            background-image: -o-linear-gradient(top, #f8f8f8, #f1f1f1);
            border: 1px solid #c6c6c6;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            color: #222;
        }

        .copyright {
            color: #666;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <div class="middle">
        <h1 class="title-init">匆匆搜索</h1>

        <form action="${request.contextPath}/query" method="post">
            <table style="width:100%;border:none;">
                <tr>
                    <td><input type="text" name="q" class="searchText"/></td>
                    <td><input type="submit" value="嗖的一下" class="searchBtn"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="push copyright">
    Copyright (c) 2016 lcc523572741@qq.com
</div>
</div>
</body>
</html>