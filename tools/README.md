# Introduction

```base.js``` 파일은 크롬 드라이버를 이용하여 실제 크롬을 실행한 후, 동적으로 렌더링되는 HTML 요소를 크롤링합니다.

```download.js``` 파일은 크롤링된 이미지를 다운로드하는 역할을 하는 모듈 파일입니다.

실행하려면 base.js를 다음과 같이 실행해야 합니다. ```type```는 ```shop, item, sale``` 등이 있습니다.

```bat
    node base.js --type=sale --download
```

실행하려면 프로젝트 메인 루트에서 파워쉘을 실행한 후, 다음 노드 모듈을 설치해야 합니다.

```bat
node install selenium-webdriver
```

```dbconnect.js```는 오라클 DB에 연결하기 위한 파일입니다. 실행하려면 다음과 같이 하세요.

```bat
    node dbconnect.js
```

