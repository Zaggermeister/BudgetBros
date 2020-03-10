$(()=>{
    $("#btnSubmit").on('click',function(e){
        e.preventDefault();
        let username=$("#userName").val();
        let surname=$("#userSurname").val();
        let email=$("#userEmail").val();
        let password=$("#userPassword").val();
        let passcheck=$("#userPass2").val();
        let endpoint = window.location.protocol + '//' + window.location.hostname + (window.location.port ? `:${window.location.port}`: '') +  '/api/v1/user';

    $.ajax({
        method: "POST",
        url: endpoint,
        contentType: 'application/json',
        data: JSON.stringify({ name: username, surname: surname, email: email })
    }).done(function ( msg )  {
        alert( "You have been registered!");
    });
        console.log(createUserObj(username,surname,email,password));


    });
    $('#userPassword, #userPass2').on('keyup', function () {
        if ($('#userPassword').val() != '' && $('#userPass2').val() != '' && $('#userPassword').val() == $('#userPass2').val()) {
          $("#btnSubmit").attr("disabled",false);
          $('#cPwdValid').show();
          $('#cPwdInvalid').hide();
          $('#cPwdValid').html('Valid').css('color', 'green');
          $('.pwds').removeClass('is-invalid')
        } else {
          $("#btnSubmit").attr("disabled",true);
          $('#cPwdValid').hide();
          $('#cPwdInvalid').show();
          $('#cPwdInvalid').html('Not Matching').css('color', 'red');
          $('.pwds').addClass('is-invalid')
          }
    });

    let registerForm=document.getElementById("registerForm");

    registerForm.querySelectorAll('.form-control').forEach(input => {
        input.addEventListener(('input'), () => {
          if (input.checkValidity()) {
            input.classList.remove('is-invalid')
            input.classList.add('is-valid');
          } else {
            input.classList.remove('is-valid')
            input.classList.add('is-invalid');
          }
          var is_valid = $('.form-control').length === $('.form-control.is-valid').length;
          $("#btnSubmit").attr("disabled", !is_valid);
        });
    });


});

function createUserObj(username,surname,email,password)
{
    let arr=[];
    arr["username"]=username;
    arr["surname"]=surname;
    arr["email"]=email;
    arr["password"]=password;
    return arr;
}
