
var currentPage = 1;
$(document).ready(function () {
    getPage(currentPage);

    $('#pagingButtons').twbsPagination({
        totalPages: Math.ceil(getCountryCount()/5),
        visiblePages: 7,
        onPageClick: function (event, page) {
            getPage(page);
        }
    });
});


function getPage(page) {
    $.getJSON("api/countries?page=" + page,
        function (data) {
            currentPage = page;
            $('#country_table').find('tbody tr').remove();
            $.each(data, function (i, country) {
                var currencyString = "";
                $.each(country.currencies, function (i, currency) {
                    if (currencyString.length > 0) {
                        currencyString += ", "
                    }
                    if (currency.name !== null) {
                        currencyString += currency.name + " (" + currency.code;
                        if (currency.symbol !== null) {
                            currencyString +=", " + currency.symbol;
                        }
                        currencyString += ")";
                    }
                });
                $('#country_table').find('tbody').append("<tr>" +
                    "<td>" + country.name + "</td>" +
                    "<td>" + currencyString + "</td></tr>");
            });
        });
}


function getCountryCount() {
    var count;
    $.ajax({
        url: "api/countries/count",
        type: 'get',
        dataType: 'text',
        async: false,
        success: function(data) {
            count = data;
        }
    });
    return count;
}
