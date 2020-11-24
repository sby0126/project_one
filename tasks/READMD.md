```sql
create table tblCustomer (
    CTMID varchar2(15),
    CTMPW varchar2(64), -- 암호화된 비밀번호
    CTMNO number(8) not null,
    CTMNM varchar(15) not null,
    ADDR varchar2(30) not null,
    TEL varchar2(15),
    EMAIL varchar2(20) not null,
    IS_ADMIN char(1) default 'N', -- 관리자 여부 (Y 또는 N)
    JOINDATE date not null,
    SALT varchar2(16) not null, -- SALT 키를 이용하면 암호화된 비밀번호를 얻을 수 있습니다.
    lastLoginTime number(13) -- 마지막 로그인 시간
    failedLoginCount number(2) -- 로그인 실패 횟수
    IS_LOCK char(1) default 'N'
);

alter table tblCustomer add constraint tblCustomer_CTMID_pk primary key(CTMID);
```

SALT 값은 다음과 같습니다. (16바이트)

```txt
85456115419f5dd4
112e8240b3e42f93
a5c74f812d4dc13d
e87a4464fd60daa2
5667b4b360141072
```

```jsp
<%
	Date now = new Date();
	long timestamp = (long)now.getTime();
%>
```

10분 이내 로그인 실패 횟수 3회 이상 시 휴먼 또는 잠긴 계정으로 변경