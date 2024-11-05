# java-spring-boot-practice (jwt.ver)

## Memo

./gradlew bootJar
// jar 파일 생성

cd build/libs 

java -jar shop-0.0.1-SNAPSHOT.jar 
// 웹서버 실행


// full text index (ngram parser)
// db 쿼리 콘솔
CREATE FULLTEXT INDEX title_search
ON shop.item(title) WITH PARSER ngram;


// N+1 문제 해결법
SELECT * FROM sales INNER JOIN `member` ON sales.member_id = `member`.id;

// JWT를 localStorage에 저장하면 XSS 공격 막아줘야함
// JWT를 쿠키에 저장하면 CSRF 공격 막아줘야함