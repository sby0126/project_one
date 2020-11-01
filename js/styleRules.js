/**
 * 가상 요소는 자바스크립트로 선택할 수 없기 때문에 해당 스타일을 직접 찾아 변경합니다.
 * 
 * @author Eo Jinseok
 * 
 * @param {String} selector
 * @param {String} property
 * @param {String} value
 */
export function cssRuleSet(selector, property, value) {
    
    try {
        const root = document.styleSheets;

        for (let i = 0; i < root.length; i++) {

            const elem = root[i];
            const files = elem.rules;

            for(let j = 0; j < files.length; j++) {

                /**
                 * @type {CSSStyleSheet}
                 */
                const styled = files[j].styleSheet;
                
                for(let rule of styled.rules) {
                    if(rule.selectorText === selector) {
                        rule.style.setProperty(property, value);
                    }
                }
            }

        }

    } catch (e) {
        console.warn(e);
    }
}
