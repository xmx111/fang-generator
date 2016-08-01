$(function() {
    /* 提交按钮事件 */
    $('#${connect_name}-operator btn-save').click(function(){
        Global.post($('#${connect_name}-operator').attr('action'), $('#${connect_name}-operator').serialize());
    });
});