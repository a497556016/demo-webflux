axios.defaults.timeout = 60000;

var zhHans = {
    close: '关闭',
    dataIterator: {
        pageText: '{0}-{1}条 总数 {2}',
        noResultsText: '没有匹配的数据',
        loadingText: '加载数据...',
    },
    dataTable: {
        itemsPerPageText: '显示条数:',
        ariaLabel: {
            sortDescending: ': Sorted descending. Activate to remove sorting.',
            sortAscending: ': Sorted ascending. Activate to sort descending.',
            sortNone: ': Not sorted. Activate to sort ascending.',
        },
        sortBy: '根据排序',
    },
    dataFooter: {
        pageText: '{0}-{1}条 总数 {2}',
        itemsPerPageText: '显示条数:',
        itemsPerPageAll: '所有',
        nextPage: '下一页',
        prevPage: '上一页',
        firstPage: '首页',
        lastPage: '末页',
    },
    datePicker: {
        itemsSelected: '选择{0}条',
    },
    noDataText: '没有数据',
    carousel: {
        prev: 'Previous visual',
        next: 'Next visual',
    },
    calendar: {
        moreEvents: '{0} 更多',
    },
}


var CreateApp = function (component) {
    return new Vue(Object.assign({
        vuetify: new Vuetify({
            lang: {
                locales: { zhHans },
                current: 'zhHans',
            },
            theme: {
                dark: false,
            },
        }),
    }, component))
}