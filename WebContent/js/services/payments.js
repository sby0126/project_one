/***
 * @link https://docs.iamport.kr/implementation/payment?lang=ko#server-side-logic
 */


/**
 * 가맹점 식별 코드
 */
const config = {
    IMP_ID: `imp00000000`
};

/**
 * @class Payments
 * @author 어진석
 */
export class Payments {
    constructor() {
        this.initWithScripts();
    }

    async initWithScripts() {  
        this.loadScript("https://cdn.iamport.kr/js/iamport.payment-1.1.5.js").then(ret => {
            this.initWithIMP();
        })
    }

    initWithIMP() {
        this._IMP = window.IMP;

        if(!this._IMP) {
            throw new Error("아임포트 라이브러리가 셋업되지 않았습니다.");
        }

        this._IMP.init(config.IMP_ID);
    }

    /**
     * 스크립트를 동적으로 로드합니다.
     * 
     * @param {String} src 
     */
    loadScript(src) {

        return new Promise((resolve, reject) => {
            const script = document.createElement("script");
            script.src = src;
            
            script.onload = function(ev) {
                resolve(ev);
            }
            
            script.onerror = function(err) {
                reject(err);
            }

            document.head.insertBefore(script);

        });
    }

    synchronizeDB(rsp) {

        const {
            imp_uid, // 아임포트 거래 고유 번호
            merchant_uid, // 가맹점에서 생성/관리하는 고유 주문번호
            paid_amount, // 결제 예정 금액
            name, // 주문명            
            buyer_name, // 주문자 이름
            paid_at // 결제 시간
        } = rsp;

        // 주문 번호(merchant_uid)를 DB로 보냅니다 (Node.js 사용)
        $.ajax({
            url: `http://biud436.com/payments/complete`,
            method: "POST",
            data: {
                imp_uid, // 아임포트 거래 고유 번호
                merchant_uid, // 가맹점에서 생성/관리하는 고유 주문번호
                paid_amount, // 결제 예정 금액
                name, // 주문명
                buyer_name, // 주문자 이름
                paid_at // 결제 시간                
            },
            success: function(data) {
                console.log(data + "에 대한 정보가 DB에 동기화 되었습니다.");
            },
            error: function(err) {
                console.warn(err);
            }
        })
    }

    /**
     * 결제 요청
     * 
     * @param {Object} param 
     */
    requestPay(param) {

        let defaultPaymentParam = { // param
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "ORD20180131-0000011",
            name: "노르웨이 회전 의자",
            amount: 64900,
            buyer_email: "gildong@gmail.com",
            buyer_name: "홍길동",
            buyer_tel: "010-4242-4242",
            buyer_addr: "서울특별시 강남구 신사동",
            buyer_postcode: "01181"
        };

        Object.assign(defaultPaymentParam, param);

        return new Promise((resolve, reject) => {
            IMP.request_pay(defaultPaymentParam, rsp => {
                if (rsp.success) {
                    // 위변조 방지를 위해 DB에 결제 정보 업로드
                    this.synchronizeDB(rsp);

                    resolve(rsp);
                } else {
                    const message = `[${rsp.merchant_uid}에 대한 오류] ${rsp.error_msg}`;
                    reject(message);
                }
              });
        })
    }


}