${r"<#assign"} basicURL="/${model_name_uncapitalize}/">
${r"<#assign"} js_config=[
"'basicURL':'${r"${basicURL}"}'",
"'urlMap':{
'query':'${r"${basicURL}"}queryForPage.json',
'addView':'${r"${basicURL}"}add.htm',
'add':'${r"${basicURL}"}add.json',
'modifyView':'${r"${basicURL}"}modify.htm',
'modify':'${r"${basicURL}"}modify.json',
'delete':'${r"${basicURL}"}delete.json',
'batchUpdate':'${r"${basicURL}"}batchStatusChange.json'
}"]>
${r"<#assign"} js_url=['/statics/js/${pkgName}/index.js']>
${r"<@iframe.html js_config=js_config js_url=js_url >"}
<div class="easyui-tabs" id="Data_Tabs" fit="true" border="false">
    <div title="列表" class="tab-container">
        <div id="Query_Box" class="condition" border="false">
            <form class="query-form">
                <ul>
                    <li><label for="fullname">查询名称</label><input class="wid200" id="fullname"  name="fullname" type="text"></li>
                </ul>
                <div class="btn-w"><a id="Query_Button" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                    <a id="Reset_Button" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:Data_Globals.resetQuery()">重置</a></div>
            </form>
        </div>
        <table id="Data_Grid" fit="true" toolbar="#Data_Grid_TB">
            <thead>
            <tr>
            <#list modelFields as modelField>
                <th field="${modelField.fieldName!""}" sortable="true" width="${modelField.listWidth!""}">${modelField.title!""}</th>
            </#list>
            </tr>
            </thead>
        </table>
        <div id="Data_Grid_TB" class="toolbar">
            <a id="Data_Add" title="新增" class="b-barbutton b-add">新增</a>
        <#--<a id="Data_Enabled" title="批量启用" class="b-barbutton b-open">批量启用</a>-->
        <#--<a id="Data_Disabled" title="批量停用" class="b-barbutton b-stop">批量停用</a>-->
        </div>
    </div>
</div>
<div id="Data_Dialog"></div>
${r"</@iframe.html>"}