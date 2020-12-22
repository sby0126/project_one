(function () {

    class MyForm {
        constructor() {
            const form = document.createElement("form");
            form.action = "/members/kakaoLogin.do";

            /**
             * @type {HTMLFormElement}
             */
            this._o = form;

            this._list = {};
        }

        /**
         * @param {HiddenInput} input 
         */
        add(input, value = null) {
            this._o.appendChild(input.element);

            const {
                name
            } = input.element;

            this._list[name] = input;

            input.value = "";
        }

        set(id, value) {
            /**
             * @type {HiddenInput}
             */
            const input = this._list[id];
            if (!input) {
                return;
            }
            input.value = value;
        }

        submit() {
            this._o.submit();
        }

        get element() {
            return this._o;
        }
    }


    class HiddenInput {
        /**
         * @param {String} id 
         */
        constructor(id) {
            this._o = document.createElement("input");
            this._o.type = "hidden";
            this._o.name = id;
        }

        set value(value) {
            this._o.value = value;
        }

        get element() {
            return this._o;
        }        
    }

    /**
     * @return {MyForm}
     */
    function createSingUpForm() {
        const form = new MyForm();

        form.add(new HiddenInput("id"));
        form.add(new HiddenInput("name"));
        form.add(new HiddenInput("email1"));
        form.add(new HiddenInput("email2"));
        form.add(new HiddenInput("joinDate"), new Date());
        form.add(new HiddenInput("tel"), "");
        form.add(new HiddenInput("pw"));
        form.add(new HiddenInput("address1"), "");
        form.add(new HiddenInput("address2"), "");
        form.add(new HiddenInput("zipcode"), "00000");

        document.body.appendChild(form.element);

        return form;
    }

    function kakaoLogin() {
        Kakao.Auth.login({
            success: function (authObj) {
                Kakao.Auth.setAccessToken(authObj.access_token);

                let myId = "";
                let myEmail = "";
                let myProfileImageUrl = "";
                let myThumbnailImage = "";

                Kakao.API.request({
                    url: '/v2/user/me',
                    success: function (response) {
                        const {
                            id,
                            connected_at,
                            kakao_account,
                            properties
                        } = response;

                        if (kakao_account.is_email_valid) {
                            myId = id;
                            myEmail = kakao_account.email;
                            myProfileImageUrl = properties.profile_image_url;
                            myThumbnailImage = properties.thumbnail_image;

                            const form = createSingUpForm();

                            // 아이디 설정
                            form.set("id", myId);
                            
                            // 이메일 설정
                            const splitEmail = myEmail.split("@");
                            form.set("email1", splitEmail[0]);
                            form.set("email2", splitEmail[1]);

                            form.set("pw", myId);

                            // 별명 설정
                            form.set("name", properties.nickname);

                            form.submit();
                        }
                    },
                    fail: function (error) {
                        console.log(error);
                    }
                });
            },
            fail: function (err) {
                console.warn(err);
            },
        })
    }

    window.kakaoLogin = kakaoLogin;

})();