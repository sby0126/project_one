/**
 * 이 함수는 가상 요소의 속성 값을 변경합니다.
 * 
 * 가상 요소는 자바스크립트로 선택할 수 없기 때문에 해당 스타일을 직접 찾아 변경해야 합니다.
 * 성능적으로 좋지 않습니다만 제이쿼리 없이 동작합니다.
 * 
 * @author 어진석
 * 
 */

/**
 * 카멜 표기법에서 스네이크 표기법으로 변환합니다.
 * 
 * @author 어진석
 * @param {String} name 
 */
function toSnakeCase(name) {
    const names = name.split("");
    let result = "";
    
    names.forEach((c, i) => {
       if(/[a-z]/.exec(c)) {
         result += c;
       } else {
         result += `-${c.toLowerCase()}`;
       }
    });
 
    return result;
 }

/**
 * 
 * @author 어진석
 * @param {String} selector 선택자를 기입하세요.
 * @param {String} property 속성 값을 스네이크 하이픈 표기법으로 기입하세요.
 * @param {String} value 
 */
export function cssRuleSet(selector, property, value) {
    
    // try {
        const root = document.styleSheets;

        // 스네이크 표기법으로 변환
        if((property || "").indexOf("-") === -1) {
            property = toSnakeCase(property);
        }

        for (let i = 0; i < root.length; i++) {

            const elem = root[i];
            if(!elem) {
                continue;
            }

            // CORS로 인한 보안 문제 해결
            if(elem.href) {
                const url = new URL(elem.href);
                if(url.host !== location.host) {
                    continue;
                }
            }
 
            let files = elem.rules || elem.cssRules;

            if(!files) {
                continue;
            }

            for(let j = 0; j < files.length; j++) {
                if(!files[j]) {
                    continue;
                }
                /**
                 * @type {CSSStyleSheet}
                 */
                const styled = files[j].styleSheet;

                if(!styled) {
                    continue;
                }

                if(!styled.rules) {
                    continue;
                }
                
                for(let rule of styled.rules) {
                    if(rule.selectorText === selector) {
                        rule.style.setProperty(property, value);
                    }
                }
            }

        }

    // } catch (e) {
    //     console.warn(e);
    // }
}
