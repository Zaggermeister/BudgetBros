$(()=>{
    $("#btnSubmit").on('click',function(e){
        e.preventDefault();
        let email = $("#email").val();
        let password = $("#password").val();

        let endpoint = window.location.protocol + '//' + window.location.hostname + (window.location.port ? `:${window.location.port}`: '') +  '/api/v1/login/' + email + "/" + password;

    $.ajax({
        method: "GET",
        url: endpoint,
        contentType: 'application/json',
    })
    .done(function ( msg )  {
        alert( "You have been logged in!");
        console.log(msg);
        window.location = window.location.protocol + '//' + window.location.hostname + (window.location.port ? `:${window.location.port}`: '') + "/" + msg.userId

    })
    .fail(function(xhr, status, error) {
            if(xhr.status == 404){
             alert('Error - Incorrect Password!');
            }
            else{
             alert('Error - Incorrect Username!');
            }
        });
    });

});