<#assign count=0>
<#assign open=false>
<form id="${connect_name}-operator" action="/${pkg_name}/save.json" method="post">
    <input type="hidden" id="id" name="id" value="${r"${data.id}"}" />
    <div class="form-input">
    <#list modelFields as modelField>
        <#if (modelField.fieldName!="id" && modelField.fieldName!="uuid" && modelField.fieldName!="createTime" && modelField.fieldName!="modifyTime")>
            <#if (count%2 == 0)>
        <div class="row">
                <#assign open=true>
            </#if>
            <div class="col-md-6">
                <div class="input-group">
                    <span class="input-group-addon">${modelField.title!""}</span>
                    <#if (modelField.fieldType=="java.lang.Boolean") >
                    <select id="${modelField.fieldName!""}" name="${modelField.fieldName!""}">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                    <#elseif (modelField.fieldType=="java.util.Date")>
                    <input type="text" name="${modelField.fieldName!""}" class="form-control form-date" placeholder="${modelField.title!""}" readonly>
                    <#else>
                    <input type="text" name="${modelField.fieldName!""}" class="form-control" placeholder="${modelField.title!""}">
                    </#if>
                </div>
            </div>
            <#if (count%2 == 1 || !modelField_has_next)>
        </div><br>
                <#assign open=false>
            </#if>
            <#assign count=count+1>
        </#if>
        <#if (open==true && !modelField_has_next)>
        </div><br>
        </#if>
    </#list>
    </div>
    <div class="form-footer">
        <button type="button" class="btn btn-default btn-close" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary btn-save">保存</button>
    </div>
</form>
<script language="text/javascript" src="/statics/js/${pkg_name}/${connect_name}-operator.js"></script>