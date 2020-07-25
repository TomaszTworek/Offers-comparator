
const button = document.getElementById('loading-pdf'),
loader = document.querySelector('#loader'),
id = button.dataset.id;

const btnExcel = document.getElementById('btn-excel'),
id2 = btnExcel.dataset.id;


function handleClick(){
      button.innerHTML = "<span>Loading...</span><i class='fa fa-spinner fa-spin '>";
                       fetch(`/printPdf/${id}`)
                      .then(response => response.blob())
                      .then(blob => {
                          button.innerHTML = "<i class='material-icons'>&#xe90d;</i> <span>Print to PDF</span>";
                          download(blob);
                      });
}



function handleExcelClick(){
      btnExcel.innerHTML = "<span>Loading...</span><i class='fa fa-spinner fa-spin '>";
                       fetch(`/printExcel/${id}`)
                            .then(response => response.blob())
                                 .then(blob => {
                                 btnExcel.innerHTML = "<i class='material-icons'>&#xE24D;</i><span>Export to Excel</span>";
                                 download(blob);

                      });
}


/* jQuery autocomplete turn on */
$(document).ready(function () {
    $('#autocomplete-input').devbridgeAutocomplete({
        serviceUrl: '/suggestion',
        paramName: 'userSearch',
        minChars: 3,
        autoSelectFirst: true,
    });

});
$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});







