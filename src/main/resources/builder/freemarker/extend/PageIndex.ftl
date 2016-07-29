${r"<#assign"} basicURL="/${pkgName}/">
${r"<#assign"} js_config=[
"'basicURL':'${r"${basicURL}"}'",
"'urlMap':{
'query':'${r"${basicURL}"}doQueryForPage.json',
'add':'${r"${basicURL}"}doAdd.json',
'update':'${r"${basicURL}"}doModify.json',
'delete':'${r"${basicURL}"}doDelete.json',
'batchUpdate':'${r"${basicURL}"}doBatchStatusChange.json'
}"]>
${r"<#assign"} js_url='[/statics/js/${pkgName}/${model_name_uncapitalize}Index.js]'>
${r"<@macro.iframe js_config=js_config js_url=js_url >"}
<div class="easyui-tabs" id="Data_Tabs" fit="true" border="false">
    <div title="列表" class="tab-container">
        <div class="easyui-layout" fit="true" border="false">
            <div id="Query_Box" region="north" class="condition" border="false">
                <div class="query-form">
                    <ul>
                        <li><label for="fullname">查询名称</label><input class="wid200" id="fullname"  name="fullname" type="text"></li>
                    </ul>
                    <div class="btn-w"><a id="Query_Button" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                        <a id="Reset_Button" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:Data_Globals.resetQuery()">重置</a></div>
                </div>
            </div>

            <div region="center" border="false">
                <div  class="easyui-layout" fit="true" border="false">
                    <div region="center"  border="false">
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
            </div>
        </div>
    </div>
</div>
<div id="Data_Dialog"></div>
${r"</@macro.iframe>"}