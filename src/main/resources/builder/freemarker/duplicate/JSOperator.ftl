$(function() {
    /* 提交按钮事件 */
    if ($('#Submit_Button')) {
        $('#Submit_Button').click(function() {
            Data_Globals.submit();
        });
    }
});