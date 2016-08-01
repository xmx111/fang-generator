$(document).ready(function () {
    var grid = $("#${connect_name}-grid");
    grid.initJqGrid({
        url: ctx + '${pkg_name}/query.json',
        addUrl : '${pkg_name}/operator.htm',
        editUrl : '${pkg_name}/operator.htm',
        deleteUrl : '${pkg_name}/delete.json',
        colModel: [
            {name:'id', index: 'id', hidden:true}
        <#list modelFields as modelField>
            <#if (modelField.fieldName!="id" && modelField.fieldName!="uuid" && modelField.fieldName!="createTime" && modelField.fieldName!="modifyTime")>
            ,{name: '${modelField.fieldName!""}', index: '${modelField.fieldName!""}', width: 90}
            </#if>
        </#list>
        ]
        // ,footerrow : true, userDataOnFooter: true
    });
});