package com.ss.batch.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//이용권 만룔를 할 때 
//이용권 순번, 패키지 순번, 사용자아이디
//상태 잔영이용권 수, NULL인 경우 무제한
//시작일시,종료 일시(NULL인 경우 무제한),
//만료일자,생성일시,수정일시
@Entity
@Table(name="pass")
@Data
public class PassEntity extends BaseEntity {

	

	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
//  @Column(name="pass_seq")
	private Long pass_seq;//이용권 순번
	private Long package_seq;
	private String user_id;
	//열거형 enum 쓸때 데이터베이스에 저장 할 때는
	//문자열로 저장 될 수 있도록 어노테이션을 사용한다.
	@Enumerated()
	private PassStatus status;
//	private String status;//READY,EXPired
	
	private Integer remaining_count;
	private LocalDateTime started_at;
	private LocalDateTime ended_at;
	private LocalDateTime expired_at;
//	private LocalDateTime created_at; BaseEntity를 상속받을지 그냥 중복으로 사용할지 결정
//	private LocalDateTime modified_at; 
	
}
