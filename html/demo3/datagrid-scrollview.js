var scrollview = $.extend({}, $.fn.datagrid.defaults.view, {   
    render: function(target, container, frozen){   
//    	console.log("render");
        var state = $.data(target, 'datagrid');   
        var opts = state.options;   
        //这个地方要特别注意，并不是用的state.data.rows数据  
        //而是用的view.rows，而view.rows在onBeforeRender事件中被设置为undefined了  
        //onBeforeRender事件在scrollview中,即便是url方式有也只会被触发一次，所以在第一次rend时，是没有数据直接return了。  
        var rows = this.rows || [];   
        if (!rows.length) {   
            return;   
        }   
        var fields = $(target).datagrid('getColumnFields', frozen);   
           
        //如果是rend frozen部分，但是有没有行号和frozenColumns的话，那就直接返回  
        if (frozen){   
            if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))){   
                return;   
            }   
        }   
           
        var index = this.index;   
        var table = ['<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>'];   
        for(var i=0; i<rows.length; i++) {   
            var css = opts.rowStyler ? opts.rowStyler.call(target, index, rows[i]) : '';   
            var classValue = '';   
            var styleValue = '';   
            if (typeof css == 'string'){   
                styleValue = css;   
            } else if (css){   
                classValue = css['class'] || '';   
                styleValue = css['style'] || '';   
            }   
            var cls = 'class="datagrid-row ' + (index % 2 && opts.striped ? 'datagrid-row-alt ' : ' ') + classValue + '"';   
            var style = styleValue ? 'style="' + styleValue + '"' : '';   
            // get the class and style attributes for this row  
//          var cls = (index % 2 && opts.striped) ? 'class="datagrid-row datagrid-row-alt"' : 'class="datagrid-row"';  
//          var styleValue = opts.rowStyler ? opts.rowStyler.call(target, index, rows[i]) : '';  
//          var style = styleValue ? 'style="' + styleValue + '"' : '';  
            var rowId = state.rowIdPrefix + '-' + (frozen?1:2) + '-' + index;   
            table.push('<tr id="' + rowId + '" datagrid-row-index="' + index + '" ' + cls + ' ' + style + '>');   
            table.push(this.renderRow.call(this, target, fields, frozen, index, rows[i]));   
            table.push('</tr>');   
            index++;   
        }   
        table.push('</tbody></table>');   
           
        $(container).html(table.join(''));   
    },   
       
    /**
     * onBeforeRender事件，首先要明白两点：  
     * 1-调用loadData方法加载数据数据时，loadData内部rend之前会触发这个事件 
     * 2-url方式时，获取到远程数据之后，也是使用loadData方法加载数据的，所以url方式也会触发onBeforeRender事件 
     * @param  {DOM} target datagrid实例的宿主DOM对象  
     * @return {[type]}        [description] 
     */  
    onBeforeRender: function(target){   
//    	console.log("onBeforeRender");
        var state = $.data(target, 'datagrid');   
        var opts = state.options;   
        var dc = state.dc;   
        var view = this;   
        // 删除onLoadSuccess事件，防止被触发，将备份到state.onLoadSuccess上  
        state.onLoadSuccess = opts.onLoadSuccess;   
        opts.onLoadSuccess = function(){};   
           
        opts.finder.getRow = function(t, p){   
            var index = (typeof p == 'object') ? p.attr('datagrid-row-index') : p;   
            var row = $.data(t, 'datagrid').data.rows[index];   
            if (!row){//什么情况会取不到呢？  
                var v = $(t).datagrid('options').view;   
                row = v.rows[index - v.index];   
            }   
            return row;   
        };   
           
        dc.body1.add(dc.body2).empty();   
        this.rows = undefined;  // 把需要画的tr绑定到view.rows上了  
        this.r1 = this.r2 = []; // view.r1和viwe.r2分别存放对第一页tr和最后一页tr的引用  
        //这里不要想当然，只是绑定了事件，在第一次加载数据时，究竟是什么时候触发这个事件的呢  
        //这个问题得追溯到loadData方法了，每次loadData之后都会直接使用triggerHandler触发scroll的  
        dc.body2.unbind('.datagrid').bind('scroll.datagrid', function(e){   
            if (state.onLoadSuccess){   
                opts.onLoadSuccess = state.onLoadSuccess;   // 恢复onLoadSuccess事件  
                state.onLoadSuccess = undefined;   
            }   
            if (view.scrollTimer){// 清除定时器  
                clearTimeout(view.scrollTimer);   
            }   
            // 延时五十毫秒执行   
            view.scrollTimer = setTimeout(function(){   
                scrolling.call(view);   
            }, 50);   
        });   
           
        function scrolling(){   
//        	console.log("scrolling");
            if (dc.body2.is(':empty')){//dc.body2对应普通列数据，如果为空的话，说明没有数据。  
            	console.log("dc.body2.is(':empty')");
                //没有数据就尝试加载数据   
                reload.call(this);   
            } else {   
                var firstTr = opts.finder.getTr(target, this.index, 'body', 2);   
                var lastTr = opts.finder.getTr(target, 0, 'last', 2);   
                var headerHeight = dc.view2.children('div.datagrid-header').outerHeight();   
                var top = firstTr.position().top - headerHeight;   
                var bottom = lastTr.position().top + lastTr.outerHeight() - headerHeight;   
                   
                if (top > dc.body2.height() || bottom < 0){   
                	console.log("top > dc.body2.height() || bottom < 0");
                    reload.call(this);   
                } else if (top > 0){   
                	console.log("top > 0");
                    var page = Math.floor(this.index/opts.pageSize);   
                    this.getRows.call(this, target, page, function(rows){   
                        this.r2 = this.r1;   
                        this.r1 = rows;   
                        this.index = (page-1)*opts.pageSize;   
                        this.rows = this.r1.concat(this.r2);   
                        this.populate.call(this, target);   
                    });   
                } else if (bottom < dc.body2.height()){// 需要加载下一页的情况  
                	console.log("bottom < dc.body2.height()");
                    var page = Math.floor(this.index/opts.pageSize)+2;   
                    if (this.r2.length){   
                        page++;   
                    }   
                    this.getRows.call(this, target, page, function(rows){   
                        if (!this.r2.length){   
                            this.r2 = rows;   
                        } else {   
                            this.r1 = this.r2;   
                            this.r2 = rows;   
                            this.index += opts.pageSize;   
                        }   
                        this.rows = this.r1.concat(this.r2);   
                        this.populate.call(this, target);   
                    });   
                }   
            }   
               
            function reload(){   
//            	console.log("reload");
                var top = $(dc.body2).scrollTop();//被卷起的高度  
                var index = Math.floor(top/25);//获取被卷起的行索引，如：卷起一行半37.5，index为1  
                var page = Math.floor(index/opts.pageSize) + 1;//获取页数，如果每页10条，卷起262.5，page为2  
                   
                this.getRows.call(this, target, page, function(rows){   
                    this.index = (page-1)*opts.pageSize;//view.index存放的是page页第一行的索引  
                    this.rows = rows;//view.rows存放需要画的tr  
                    this.r1 = rows;   
                    this.r2 = [];   
                    this.populate.call(this, target);   
                    dc.body2.triggerHandler('scroll.datagrid');   
                });   
            }   
        }   
    },   
       
    getRows: function(target, page, callback){   
//    	console.log("getRows");
        var state = $.data(target, 'datagrid');   
        var opts = state.options;   
        var index = (page-1)*opts.pageSize;   
        var rows = state.data.rows.slice(index, index+opts.pageSize);   
        if (rows.length){//这是一次性加载完所有数据的方式，可以直接从本地javascript数组中取出数据  
            callback.call(this, rows);   
               
        } else {//懒加载方式  
//        	console.log("懒加载方式");
            var param = $.extend({}, opts.queryParams, {   
                page: page,   
                rows: opts.pageSize   
            });   
            if (opts.sortName){   
                $.extend(param, {   
                    sort: opts.sortName,   
                    order: opts.sortOrder   
                });   
            }   
            if (opts.onBeforeLoad.call(target, param) == false) return;   
               
            $(target).datagrid('loading');   
            var result = opts.loader.call(target, param, function(data){   
                $(target).datagrid('loaded');   
                var data = opts.loadFilter.call(target, data);   
                callback.call(opts.view, data.rows);   
//              opts.onLoadSuccess.call(target, data);  
            }, function(){   
                $(target).datagrid('loaded');   
                opts.onLoadError.apply(target, arguments);   
            });   
            if (result == false){   
                $(target).datagrid('loaded');   
            }   
        }   
    },   
       
    populate: function(target){   
//    	console.log("before populate");
        var state = $.data(target, 'datagrid');   
        var opts = state.options;   
        var dc = state.dc;   
        var rowHeight = 25;   
//        console.log($(dc.body2));
        if (this.rows.length){   
            opts.view.render.call(opts.view, target, dc.body2, false);   
            opts.view.render.call(opts.view, target, dc.body1, true);   
            // 看到了么，滚动条有那么大空间是怎么实现的了么？用的padding！  
            dc.body1.add(dc.body2).children('table.datagrid-btable').css({   
                paddingTop: this.index*rowHeight,   
                paddingBottom: state.data.total*rowHeight - this.rows.length*rowHeight - this.index*rowHeight   
            });   
            opts.onLoadSuccess.call(target, {   
                total: state.data.total,   
                rows: this.rows   
            });   
        }   
//        console.log("populate");
    }   
});   