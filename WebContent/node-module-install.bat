@echo off
where node>nul 2>nul
if %errorlevel%==0 (
	echo "노드 모듈을 설치합니다"
    npm install
) else (
    echo "Node.js가 없습니다"
	echo "Node.js를 다운로드합니다"
    powershell wget "https://nodejs.org/dist/v14.15.0/node-v14.15.0-x64.msi" -OutFile "node-v14.15.0-x64.msi"
    start node-v14.15.0-x64.msi
)
pause