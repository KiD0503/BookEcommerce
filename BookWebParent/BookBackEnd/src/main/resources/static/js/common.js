$(document).ready(function (){
    $("#logoutLink").on("click", function (e){
        e.preventDefault();
        document.logoutForm.submit();
    });
    customizeDropDownMenu();
});

function customizeDropDownMenu() {
    $(".navbar .dropdown").hover(
        function() {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function() {
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    );
    $(".dropdown > a").click(function (){
        location.href = this.href;
    })
}
// Function confirm delete user
$(document).ready(function () {
    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        // alert($(this).attr("href"));
        $("#yesButton").attr("href", link.attr("href"));
        userId = link.attr("userId")
        $("#confirmText").text("Are you want to delete this user ID " + userId + "?");
        $("#confirmModal").modal();
    })
});
//Function clear Filter
function clearFilter() {
    window.location = "[[@{/users}]]"
}
//Function logout
$(document).ready(function () {
    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    });
});

