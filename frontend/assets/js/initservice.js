$(function () {
    let CREATE_POST_URL = "http://localhost:8080/api/post";
    let GET_TIMELINE_URL = "http://localhost:8080/api/timeline/";
    let token = localStorage.getItem('access_token');
    let userName = parseJwt(token).user_name;
    var timeline;

    // fetch timeline
    $.ajax({
        url: GET_TIMELINE_URL + userName,
        type: "GET",
        dataType: "json",
        success: function (data) {
            timeline = data;
            Handlebars.registerHelper('fromNow', function(date) {
                if (moment) {
                    return moment(date).fromNow();
                } else {
                    return date;
                }
            });

            Handlebars.registerHelper('numComments', function(comments) {
                return comments.length;
            });

            //get posts
            var template = $('#handlebar').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#posts').append(html);

            // get friends
            var template = $('#handlebar-friends').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#friends').append(html);

            // get following
            var template = $('#handlebar-followings').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#followings').append(html);

            // get requested
            var template = $('#handlebar-requested').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(timeline);
            $('#requested').append(html);
        }
    });
    // Create post

    $('#post').click(function () {
        let content = $('#exampleTextarea').val();
        let data = {
            "content": content,
            "postedDate": moment( new Date()).format("YYYY-MM-DDTHH:mm:ss.SSS"),
            "postedBy": {
                "email": userName,
            },
            "numOfLike": 0,
            "numOfLove": 0,
            "likeUsers": [
            ],
            "loveUsers": [
            ],
            "commentInfos": [
            ]
        };

        $.ajax({
            url: CREATE_POST_URL,
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function( data, textStatus ){
                console.log("post sent");
            },
            error: function( error ){
                console.log( error );
            }
        });

        // sentNoti(data);
        location.reload();

    });



    function parseJwt (token) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    }
    

    function sentNoti(data) {
        $.ajax({
            url: "http://localhost:8899/posts/add",
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function( res, textStatus ){
                console.log("noti sent");

            },
            error: function( error ){
                console.log( error );
            }
        });
    }

});
