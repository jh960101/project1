package com.ss.batch.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//예약 테이블에서 예약 내용을 가지고 수업 전에 알림 보내는 역할로 사용!
//카카오톡 메시지로 보낼려면 UUID랑 Notification uuid를 이용해서 전송!
@Data
@Entity
@Table(name="notification")
public class NotificationEntity extends BaseEntity{

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long notificationSeq; //알람 순서
	private String uuid; 		  //카카오톡 알림
	private String event;         //수업 전 알림을 보내기 위해서 event
	private String text;		  //알림 내용
	private boolean sent;		  //발송 여부 false ,true
	private LocalDateTime sentAt; //발송 시간
	
	
}
