$(function () {
    let CREATE_POST_URL = "http://localhost:8080/api/post";
    console.log("Test1.......");
    // Create post
    $('#post').click(function () {
        console.log("Test2.......");
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
                $('#response pre').html( data );
            },
            error: function( jqXhr, textStatus, errorThrown ){
                console.log( errorThrown );
            }
        });
        console.log("post sent");
    });
})
