${r"<@macro.iframe js_config=js_config js_url=js_url >"}
<script language="text/javascript" src="/statics/js/${pkg_name}/operator.js"></script>
<div title="${model_title}信息" class="tab-container">
    <form id="Data_Form" method="post" >
        <input type="hidden" id="id" name="id" value="${r"${data.id}"}" />
        <table class="dialog-table">
        <#list modelFields as modelField>
            <tr>
                <td class="lable">${modelField.title!""}</td>
                <td>
                    <#if (modelField.fieldType="java.lang.Boolean") >
                        <select id="${modelField.fieldName!""}" name="${modelField.fieldName!""}">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    <#elseif (modelField.fieldType="java.util.Date")>
                        <input type="text" id="${modelField.fieldName!""}" name="${modelField.fieldName!""}" style="width:133px" class="Wdate easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" />
                    <#else>
                        <input type="text" id="${modelField.fieldName!""}" name="${modelField.fieldName!""}" class="easyui-validatebox wid200"></input>
                    </#if>
                </td>
            </tr>
        </#list>
        </table>
        <div class="dialog-bottom-toolbar">
            <a id="Submit_Button"  class="easyui-linkbutton" iconCls="icon-save">保存</a>
            <a id="cancel_button" onclick="UI.close('Data_Dialog')"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
        </div>
    </form>
</div>
<!--表单字段--结束 -->
${r"</@macro.iframe>"}