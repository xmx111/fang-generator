<div class="row">
    <div class="col-md-12 grid-main">
        <div class="row search-form-default">
            <div class="col-md-12">
                <div class="row">
                    <form id="${connect_name}-form" action="#">
                        <div class="col-md-9">
                            <div class="input-group">
                                <input type="text" name="code" class="form-control" placeholder="代码">
                                <span class="input-group-addon fix-border fix-padding"></span>
                                <input type="text" name="name" class="form-control" placeholder="名称">
                                <span class="input-group-addon fix-border fix-padding"></span>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="btn-group">
                                <button type="button" class="btn btn-success btn-query"><span class="icon-search"></span> 查询</button>
                                <button type="button" class="btn btn-success btn-repeat"><span class="icon-repeat"></span> 重置</button>
                                <button type="button" class="btn btn-success btn-export"><span class="icon-file-excel"></span> 导出</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="row grid-default">
            <div class="col-md-12">
                <table id="${connect_name}-grid"></table>
            </div>
        </div>
    </div>
</div>
<script src="/statics/js/${pkg_name}/${connect_name}-index.js" type="text/javascript"></script>