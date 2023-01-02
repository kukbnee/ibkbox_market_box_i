## SSL 인증서 설치 방법

- 참고 URL : https://v0o0v.tistory.com/6

1. 터미널 실행

2. [JAVA_PATH]\bin\keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 36500

3. DEV properties 에 SSL 설정 추가
   server.ssl.key-store=keystore.p12
   server.ssl.key-store-password=qwer123!
   server.ssl.keyStoreType=PKCS12
   server.ssl.key-alias=tomcat
