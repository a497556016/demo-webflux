axios.defaults.timeout = 60000;

var zhHans = {
    close: 'Close',
    dataIterator: {
        pageText: '{0}-{1} of {2}',
        noResultsText: 'No matching records found',
        loadingText: 'Loading items...',
    },
    dataTable: {
        itemsPerPageText: '每页数目:',
        ariaLabel: {
            sortDescending: ': Sorted descending. Activate to remove sorting.',
            sortAscending: ': Sorted ascending. Activate to sort descending.',
            sortNone: ': Not sorted. Activate to sort ascending.',
        },
        sortBy: '根据排序',
    },
    dataFooter: {
        pageText: '{0}-{1} of {2}',
        itemsPerPageText: 'Items per page:',
        itemsPerPageAll: 'All',
        nextPage: 'Next page',
        prevPage: '上一页',
        firstPage: 'First page',
        lastPage: 'Last page',
    },
    datePicker: {
        itemsSelected: '{0} selected',
    },
    noDataText: '没有数据',
    carousel: {
        prev: 'Previous visual',
        next: 'Next visual',
    },
    calendar: {
        moreEvents: '{0} more',
    },
}


var CreateApp = function (component) {
    return new Vue(Object.assign({
        vuetify: new Vuetify({
            lang: {
                locales: { zhHans },
                current: 'zhHans',
            },
        }),
    }, component))
}