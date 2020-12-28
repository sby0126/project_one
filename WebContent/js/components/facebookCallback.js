FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
});

/**
 * 페이스북으로 로그인하기
 */
function statusChangeCallback(response) {

    // status는 앱 사용자의 로그인 상태를 지정합니다. 상태는 다음 중 하나일 수 있습니다.
        // connected - 사용자가 Facebook에 로그인하고 앱에 로그인했습니다.
        // not_authorized - 사용자가 Facebook에는 로그인했지만 앱에는 로그인하지 않았습니다.
        // unknown - 사용자가 Facebook에 로그인하지 않았으므로 사용자가 앱에 로그인했거나 FB.logout()이 호출되었는지 알 수 없어, Facebook에 연결할 수 없습니다.
    // connected 상태인 경우 authResponse가 포함되며 다음과 같이 구성되어 있습니다.
        // accessToken - 앱 사용자의 액세스 토큰이 포함되어 있습니다.
        // expiresIn - 토큰이 만료되어 갱신해야 하는 UNIX 시간을 표시합니다.
        // signedRequest - 앱 사용자에 대한 정보를 포함하는 서명된 매개변수입니다.
        // userID - 앱 사용자의 ID입니다.
    // 앱에서 앱 사용자의 로그인 상태를 알게 되면 다음 중 하나를 수행할 수 있습니다.
    // 사용자가 Facebook과 앱에 로그인한 경우 앱의 로그인된 환경으로 리디렉션됩니다.
    // 사용자가 앱에 로그인하지 않았거나 Facebook에 로그인하지 않은 경우 FB.login()을 사용하여 로그인 대화 상자에 메시지를 표시하거나 로그인 버튼을 표시합니다.

    
    // {
    //     status: 'connected',
    //     authResponse: {
    //         accessToken: '...',
    //         expiresIn:'...',
    //         signedRequest:'...',
    //         userID:'...'
    //     }
    // }

    if(!response) {
        console.warn("페이스북 API가 응답하지 않습니다.");
        return;
    }

    const status = response.status;
    let isReadyFacebook = false;

    // 페이스북의 상태를 체크합니다.
    switch(status) {
        default:
        case "unknown":
            console.warn("사용자가 Facebook에 로그인하지 않았으므로 사용자가 앱에 로그인했거나 FB.logout()이 호출되었는지 알 수 없어, Facebook에 연결할 수 없습니다.");
            break;
        case "not_authorized":
            console.warn("사용자가 Facebook에는 로그인했지만 앱에는 로그인하지 않았습니다.");
            break;
        case "connected":
            isReadyFacebook = true;
            break;
    }

    if(isReadyFacebook) {
        const {authResponse} = response;
        const {accessToken, expiresIn, signedRequest, userID} = authResponse;

        console.log(authResponse);

    }

}

function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }