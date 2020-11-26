# 파일 상단에 특정 코드를 삽입하는 스크립트입니다.
insertion = '<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>'
dir = Dir["*.jsp", "pages/*.jsp"]

dir.each do |i|
    f = File.open(i, 'rt+')
    raw = f.read
    f.close

    # raw.gsub!(/(.*)\.(?:jsp)\.(?:jsp)/i) do |match|
        # $1 + ".jsp"
    # end
	
    f = File.open(i, 'wt+')
    f.puts insertion
	f.puts raw
    f.close
end
