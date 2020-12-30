import {Component} from "./Component.js";
import {App} from "../app.js";

export class LoginButton extends Component {
    run() {
        const loginButton = document.querySelector(".header-right-login-button");
        /**
         * @type {HTMLDivElement}
         */
        const loginView = document.querySelector(".floating-login-view-wrapper");
        const lightBox = document.querySelector("#light-box-container");

        if(!loginView) {
            return;
        }

        if(!lightBox) {
            return;
        }

        // 로그인 바가 열렸을 때 우측을 누르면 닫기
        window.addEventListener("click", (ev) => {
            if(ev.target == loginView && !$(".main .content_login").is(":visible")) {
                lightBox.classList.remove("active");
                loginView.style.transition = "all .8s ease-in-out";
                loginView.style.left = "9999px";
                $(loginView).removeClass("active");
            }
        });

        loginButton.addEventListener("click", () => {

            // 브라우저는 최적화 문제로 인해 모든 스타일의 속성을 즉각 계산하지 않습니다.
            // 따라서 스타일 값을 가져오려면 getComputedStyle 함수를 써야 합니다.
            const styled = getComputedStyle(loginView);

            // 스타일 값에는 px 단위가 들어가있습니다.
            // parseInt를 사용하면 단위 값이 제거되고 숫자만 남습니다.
            if(parseInt(styled.left) != 0) {
                // active 클래스를 추가하면 화면에 라이트 박스가 표시됩니다.
                lightBox.classList.add("active");
                loginView.style.transition = "all .4s ease-out";
                loginView.style.left = "1px";
                $(loginView).addClass("active");
            }
        });

        const btn = document.querySelector("#close-login-view");
        if(!btn) {
            return;
        }
        btn.addEventListener("click", () => {
            lightBox.classList.remove("active");
            loginView.style.transition = "all .8s ease-in-out";
            loginView.style.left = "9999px";
            $(loginView).removeClass("active");
        })    

		 this.loadNaverLogin();  
    }

    /**
     * 네이버 로그인 처리
     */
	loadNaverLogin() {
		const script = document.createElement('script');
		script.src = "https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js";
		script.onload = function() {
			
		  	var naver_id_login = new window.naver_id_login("7ImHSjL7FtzCQoKe84Jc", "https://biud436.com:9001/pages/callback.jsp");
		  	var state = naver_id_login.getUniqState();
		  	naver_id_login.setButton("green", 3,40);
		  	naver_id_login.setDomain("https://biud436.com:9001/");
		  	naver_id_login.setState(state);
		  	naver_id_login.init_naver_id_login();
	
		};
		
		document.head.appendChild(script); //or something of the likes
		
    }
    
    static id() {
        return LoginButton;
    }
}