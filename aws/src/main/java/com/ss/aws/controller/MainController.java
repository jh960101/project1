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
 * 
 */