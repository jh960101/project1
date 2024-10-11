package com.ss.aws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {

	@GetMapping("/aws/v1")
	public String main(@RequestParam(defaultValue="1")Integer number) {
		 if(number == 1) {
			 log.info("/aws/v1 호출이 됩니다! info로그#############");
			 }else if(number == -1) {
			 log.error("/aws/v1 호출이 됩니다! error로그#############");
			 }else if(number == 0) {
			 log.warn("/aws/v1 호출이 됩니다! warn로그#############");
			 }
		 return "<h1> aws v1</h1>"; 
	}
	
}
/*
 * 1. 배포위치EC2 기존 만들었던 서버 모두 인스턴스 종료!
 * AWS 프리티어 사용자(회원 가입한 지 1년이 안된 고객)
 * 750시간,30기가 까지는 무료 사용!
 * 유동적인 ipv4 
 * 
 * 1.EC2 고정 ip할당 받기
 * 2. 모바텀으로 ssh 연결하기
 * 3. git EC2(내 컴퓨터내에 다운로드 되어 있는지 확인)
 *	 git --version  
 *   git 버전이 나올 시 설치 x
 *   만약 버전이 안나오면
 *   git을 apt를 이용해서 git을 다운받으면 됨
 *   apt 사용시 apt목록을 업데이트
 *
 *	실제 파일을 다운로드 받으면 폴더 안으로 들어가서 gradle과 관련된 파일을 찾는다.
 *  gradle을 실행할 수 있도록 권한을 설정
 *  chmod u+x 파일명 하면된다.
 *  
 *  폴더 삭제
 *  rm -rf 파일명
 *  -r 폴더와 폴더 하위 폴더 모두삭제
 *  -f 강제 삭제 (파일삭제시 확인안함)
 *  
 * gradle을 이용해서 빌드를 하게 되면 java파일을 컴파일할 jdk가 없다고 뜬다.
 * apt를 이용해서 자바 JDK설치
 * sudo apt update
 * sudo apt-cache search jdk | greap openjdk-11
 * sudo apt install openjdk-11-jdk
 * .gradlew build
 * 
 *백그라운드에서 계속 동작 할 수 있도록 설정
 * nohup 명령어
 * 리눅스에서 프로세스를 실행하 터미널의 세션이 끊어지더라도 지속적으로 동작할 수있도록 실행해 주는 명령어
 * nohup java -jar 파일명.jar &
 * SSH창을 닫고 나서 url을 이용해서 접속시도!
 * 그 로그값들은 libs 폴더 안에 .jar파일이랑 같이 있다.
 * 기본적인 이름 nohup.out
 * cat nohup.out 파일을 봐도 되지만 너무 길다
 * 뒤에서 부터 10개 확인 할 수 있도록
 * tail -f nohup.out
 *  -f 실시간으로 로그 값으 출력해주는 옵션!
 *  백그라운드에서 동작하는 프로세스들은 pid 부여된다.
 *  내가 종료를 누르고 싶을 경우에는 pid를 찾아서 kill -9 pid(4914)
 *  
 *  
 * 
 */