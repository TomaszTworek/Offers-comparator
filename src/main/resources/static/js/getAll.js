/* jQuery function to add loading spinner for Excel with setTimeOut*/
//$('#loading').on('click', function () {
//    $(this).button('loading');
//    setTimeout(function () {
//        $("#loading").button('reset');
//    }, 1000);
//});





/* Loading spinner vol.2*/


const button = document.getElementById('loading-pdf'),
loader = document.querySelector('#loader'),
id = button.dataset.id;



function handleClick(){

      button.innerHTML = "<span>Loading...</span><i class='fa fa-spinner fa-spin '>";
                       fetch(`/printPdf/${id}`)
                      .then(response => response.blob())
                      .then(blob => {

                          button.innerHTML = "<i class='material-icons'>&#xe90d;</i> <span>Print to PDF</span>";

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



