@echo off
where node>nul 2>nul
if %errorlevel%==0 (
    node shop.js --output
) else (
    echo "Node.js를 다운로드합니다."
    powershell wget "https://nodejs.org/dist/v14.15.0/node-v14.15.0-x64.msi" -OutFile "node-v14.15.0-x64.msi"
    start node-v14.15.0-x64.msi
)
pause