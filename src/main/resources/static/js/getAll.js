/* jQuery function to add loading spinner for Excel with setTimeOut*/
$('#loading').on('click', function () {
    $(this).button('loading');
    setTimeout(function () {
        $("#loading").button('reset');
    }, 1000);
});

/* JS function to find cookie created in backend*/
//var getCookie = function (name) {
//    var cookies = document.cookie.split(';');
//    for (var i = 0; i < cookies.length; ++i) {
//        var pair = cookies[i].trim().split('=');
//        if (pair[0] == name)
//            return pair[1];
//    }
//    return null;
//};
/* JS function to add loading spinner to PDF button*/
//const loadAnimationButton = document.querySelector('#loading-pdf');
//loadAnimationButton.addEventListener('click', function () {
//    loadAnimationButton.innerHTML = "<i class='fa fa-spinner fa-spin'></i><span>Loading...</span>";
//    alert('Początek pobierania');
//    setTimeout(checkDownloadCookie, 1000);
//});
//
//
//let downloadTimeout;
//const checkDownloadCookie = function () {
//    if (getCookie("myCookie") == 1) {
//        loadAnimationButton.innerHTML = "<i class='material-icons'>&#xe90d;</i><span>Print to PDF</span>";
//    } else {
//        downloadTimeout = setTimeout(checkDownloadCookie, 1000);
//    }
//};


/* Loading spinner vol.2*/


const button = document.getElementById('loading-pdf'),
loader = document.querySelector('.loader'),
id = button.dataset.id;



button.addEventListener('click', () => {
           loader.classList.remove('hidden');
                  button.classList.add('hidden');
                  fetch(`/printPdf/${id}`)
                      .then(response => response.blob())
                      .then(blob => {
                          var file = window.URL.createObjectURL(blob);
                          window.location.assign(file);

                            loader.classList.add('hidden');
                            button.classList.remove('hidden');
                      });
//                      .then(() => {
//                          loader.classList.add('hidden');
//                          button.classList.remove('hidden');
//                      })

});


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

/* Js for favourite icon*/

// const icon = document.querySelector('.fav-icon');
//     icon.addEventListener("click", () =>{
//         icon.innerHTML = "<span><img src='https://img.icons8.com/material/24/000000/like--v1.png' style='size: 24px' alt=''></span>";
//
// });
const tabsParent = document.querySelector('#tab'),
    tabs = document.querySelector('.zaza');

tabs.addEventListener('click', (event) => {
    const target = event.target;

   // if (target && target.classList.contains('fav-icon')) {
        tabs.forEach((tab) => {

            if(tab.classList.contains("fav-icon")){
               tab.innerHTML = "<span><img src='https://img.icons8.com/material/24/000000/like--v1.png' style='size: 24px' alt=''></span>";
            }
        });
   // }

});



