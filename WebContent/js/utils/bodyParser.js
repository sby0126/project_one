/**
 * HTML 파일을 파싱하여 <body></body> 부분만 반환합니다.
 * 
 * @param {String} raw 
 * @return html
 */
export function parseBodyFromString(raw) {
    const parser = new DOMParser();
    let doc = parser.parseFromString(raw, "text/html");

    if(!doc) return "";
    if(!doc.documentElement) return "";
    if(!doc.documentElement.querySelector("body")) return "";
    
    return doc.documentElement.querySelector("body").innerHTML;
}

export function parseScriptFromString(raw) {
    const parser = new DOMParser();
    let doc = parser.parseFromString(raw, "text/html");

    if(!doc) return "";
    if(!doc.documentElement) return "";
    if(!doc.documentElement.querySelector("body")) return "";
    
    return doc.documentElement.querySelector("body").querySelector("script");    
}