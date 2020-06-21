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


document.getElementById('pdf-href').addEventListener("click", function () {
    document.querySelector('.bg-modal').style.display = "flex";
});
document.getElementById('excel-href').addEventListener("click", function () {
    document.querySelector('.bg-modal').style.display = "flex";
});

document.querySelector('.close').addEventListener("click", function () {
    document.querySelector('.bg-modal').style.display = "none";
});








