$(function () {
    let CREATE_POST_URL = "http://localhost:8080/api/post";
    let GET_TIMELINE_URL = "http://localhost:8080/api/timeline/bao@mum.edu";
    console.log("Test1.......");

    // fetch post
    $.ajax({
        url: GET_TIMELINE_URL,
        type: "GET",
        dataType: "json",
        success: function (data) {
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

            var template = $('#handlebar').html();
            var templateScript = Handlebars.compile(template);
            var html = templateScript(data);
            $('#posts').append(html);
        }
    });

    // Create post
    $('#post').click(function () {
        let content = $('#exampleTextarea').val();
        let data = {
            "postId": "73367f9e3c1af178e3386f9eee24d9e4",
            "content": content,
            "postedDate": "2019-07-14T17:27:26.575",
            "postedBy": {
                "userId": "bang@mum.edu",
                "fullName": "Bang Le",
                "avatar": "100_5.jpg",
                "email": "bang@mum.edu",
                "dob": null
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
            error: function( jqXhr, textStatus, errorThrown ){
                console.log( errorThrown );
            }
        });
        location.reload();


    });
})
