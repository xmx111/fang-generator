<#assign js_url=['/statics/js/${pkg_name}/operator.js']>
${r"<@iframe.html js_config=[] js_url=js_url >"}
<div title="${model_title}信息" class="tab-container">
    <form id="Data_Form" method="post" >
        <input type="hidden" id="id" name="id" value="${r"${data.id}"}" />
        <table class="dialog-table">
        <#list modelFields as modelField>
            <#if (modelField.fieldName!="gmtCreate" && modelField.fieldName!="gmtModified") >
            <tr>
                <td class="lable">${modelField.title!""}</td>
                <td>
                <#if (modelField.fieldType="java.lang.Boolean") >
                    ${r"<@dicSelect"} keyValueType="isBoolean" id="${modelField.fieldName!""}"  name="${modelField.fieldName!""}" class="wid100" ${r"/>"}
                <#elseif (modelField.fieldType="java.util.Date")>
                    <input type="text" id="${modelField.fieldName!""}" name="${modelField.fieldName!""}" style="width:133px" class="Wdate easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" />
                <#else>
                    <#if (modelField.fieldName="status") >
                    ${r"<@dicSelect"} keyValueType="status" id="status"  name="status" class="wid100" ${r"/>"}
                    <#else>
                    <input type="text" id="${modelField.fieldName!""}" name="${modelField.fieldName!""}" class="easyui-validatebox wid200"></input>
                    </#if>
                </#if>
                </td>
            </tr>
            </#if>
        </#list>
        </table>
        <div class="dialog-bottom-toolbar">
            <a id="Submit_Button"  class="easyui-linkbutton" iconCls="icon-save">保存</a>
            <a id="cancel_button" onclick="UI.close('Data_Dialog')"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
        </div>
    </form>
</div>
<!--表单字段--结束 -->
${r"</@iframe.html>"}