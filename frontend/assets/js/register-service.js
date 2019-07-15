$(document).ready(function () {
    $('#registerAccount').click(function () {
        const form = $('#kt_apps_user_add_user_form');
        console.log(form);
        if (form[0][4].value !== form[0][5].value) {
            console.log("Password not match!!!!");
            return;
        }
        let data = {
            firstName: form[0][0].value,
            lastName: form[0][1].value,
            phone: form[0][2].value,
            email: form[0][3].value,
            password: form[0][4].value,
        }

        $.ajax({
            url: 'http://localhost:8080/register',
            dataType: 'json',
            type: 'post',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem(TOKEN_NAME)
            },
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function( data, textStatus ){
                console.log(data);
                window.location = './login-page.html';
                // localStorage.setItem(TOKEN_NAME, data.access_token);
            },
            error: function( jqXhr, textStatus, errorThrown ){
                console.log('There is an error.');
                console.log(textStatus);
                console.log(jqXhr);
                console.log(errorThrown);
            }
        });

    });
});
