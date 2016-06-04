<html>
<head>
    <title>匆匆搜索</title>
    <script src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <link href="${request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .queryForm {
            background-color: #f1f1f1;
            border-bottom: 1px solid #666;
            border-color: #e5e5e5;
            padding: 0 20px;
        }

        .searchText {
            width: 490px;
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
            background-image: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f1f1f1));
            background-image: -webkit-linear-gradient(top, #f8f8f8, #f1f1f1);
            -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            background-color: #f8f8f8;
            background-image: linear-gradient(top, #f8f8f8, #f1f1f1);
            background-image: -o-linear-gradient(top, #f8f8f8, #f1f1f1);
            border: 1px solid #c6c6c6;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            color: #222;
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
            background-image: -webkit-gradient(linear, left top, left bottom, from(#ffffff), to(#f1f1f1));
            background-image: -webkit-linear-gradient(top, #ffffff, #f1f1f1);
            -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
            background-image: linear-gradient(top, #ffffff, #f1f1f1);
            background-image: -o-linear-gradient(top, #ffffff, #f1f1f1);
            border: 1px solid #c6c6c6;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            color: #222;
        }

        .result-item {
            padding: 10px 300px 10px 40px;
        }

        #tads a, #tadsb a, #res a, #rhs a, #taw a {
            text-decoration: none;
        }

        a:link {
            cursor: pointer;
        }

        a:link, #prs a:visited, #prs a:active {
            color: #353535;
        }

        .cite {
            color: #006621;
            font-style: normal;
            font-size: small;
            line-height: 18px;
        }

        .item-url {
            text-decoration: none;
        }

        .item-content {
            color: #545454;
            font-size: small;
            line-height: 16px;
        }

        #resultStats {
            line-height: 43px;
            -webkit-transition: all 220ms ease-in-out;
            color: #808080;
            padding: 0 8px 0 16px;
            font-size: small;
        }

        .copyright {
            color: #666;
            text-align: center;
        }

        .middle-search {
            text-align: center;
            margin: 0 auto;
            height: auto;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <div class=".middle-search">
        <form class="queryForm" action="${request.contextPath}/query" method="post">
            <table style="width:700px;border:none;">
                <tr>
                    <td><h3 class="title-init">匆匆搜索</h3></td>
                    <td><input type="text" name="q" class="searchText" value="${q}"/></td>
                    <td><input type="submit" value="嗖的一下" class="searchBtn"/></td>
                </tr>
            </table>
        </form>
    <#if page??>
        <div class="result-list">
            <div id="resultStats">找到约 ${page.total} 条结果</div>
            <#list page.list as info>
                <div class="result-item" data-hveid="27">
                    <h3 class="r"><a href="${info.url}" class="item-url" target="_blank">${info.title}</a></h3>

                    <div class="cite" style="white-space:nowrap">${info.url}</div>
                    <div>
                        <span class="item-content">${info.content}</span>
                    </div>
                </div>
            </#list>
        </div>
        <table class="gridtable" style="width:400px;text-align: center;border-collapse: separate;margin: 5px 40px;">
            <tr>
                <#if page.hasPreviousPage>
                    <td>
                        <a href="${request.contextPath}/query?page=1&rows=10&q=${q}">首页</a>
                    </td>
                    <td>
                        <a href="${request.contextPath}/query?page=${page.prePage}&rows=10&q=${q}">前一页</a>
                    </td>
                </#if>
                <#list page.navigatepageNums as nav>
                    <#if nav == page.pageNum>
                        <td style="font-weight: bold;">${nav}</td>
                    </#if>
                    <#if nav != page.pageNum>
                        <td>
                            <a href="${request.contextPath}/query?page=${nav}&rows=10&q=${q}">${nav}</a>
                        </td>
                    </#if>
                </#list>
                <#if page.hasNextPage>
                    <td>
                        <a href="${request.contextPath}/query?page=${page.nextPage}&rows=10&q=${q}">下一页</a>
                    </td>
                    <td>
                        <a href="${request.contextPath}/query?page=${page.pages}&rows=10&q=${q}">尾页</a>
                    </td>
                </#if>
            </tr>
        </table>
    </#if>
    </div>
    <div class="push copyright">
        Copyright (c) 2016 lcc523572741@qq.com
    </div>
</div>
</body>
</html>