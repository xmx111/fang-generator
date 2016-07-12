$(function() {
    $('#Data_Grid').datagrid({
        frozenColumns : [ [
            {title : '', field : 'ck', checkbox : true},
            {title : '操作', field : 'oper', width : 80, align:'center', formatter : formatOpera}
        ] ],
        rownumbers : true
    });

    /* 查询按钮事件 */
    if ($('#Query_Button')) {
        $("#Query_Button").click(function () {
            Data_Globals.query();
        });
    }

    /* 新增按钮事件 */
    if ($('#Data_Add')) {
        $('#Data_Add').click(function () {
            Data_Globals.operator({
                title: '新增信息',
                type:'add'
            });
        });
    }

    /* 批量启用 */
    if ($('#Data_Enabled')) {
        $('#Data_Enabled').click(function() {
            Data_Globals.batchUpdate({params:{status:1}, msg:"批量启用"});
        });
    }

    /* 批量禁用 */
    if ($('#Data_Disabled')) {
        $('#Data_Disabled').click(function() {
            Data_Globals.batchUpdate({params:{status:0},msg:"批量禁用"});
        });
    }

    // 初始化grid数据
    Data_Globals.query();
});

function formatOpera(value, data, index) {
    var opt = new Array();
    var edit = '<a class="o-link o-edit" title="编辑" href="javascript:edit('+index+')">编辑</a>';
    var del = '<a title="删除" href="javascript:del('+data.id+')">删除</a>';
    opt.push(edit);
    opt.push(del);
    return opt.join(operInterval);
}

function edit(index){
    var rowData = $('#Data_Grid').datagrid('getData').rows[index];
    Data_Globals.operator({
        title:'编辑信息',
        type:'modify',
        data:'id='+rowData.id
    });
}

function del(id){
    if(confirm("立即删除此条记录吗？")!=true){
        return;
    }
    $.ajax({
        url: config.urlMap['delete'],
        type: 'POST',
        data:'id='+id,
        timeout: 10000,
        error: function(){
            alert("连接服务器超时，请检查网络连接或联系管理员");
        },
        success: function(data){
            $.messager.show({'title' : '操作结果','msg' : data.message});
            Data_Globals.query();
        }
    });
}
