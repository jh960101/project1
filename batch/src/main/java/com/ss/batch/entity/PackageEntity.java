package com.ss.batch.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="package")
public class PackageEntity extends BaseEntity{

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long packseq; //패키지 ID
	
	@Column(name = "package_name")
	private String packageName; //패키지 이름
	
	private Integer count; //횟수
	
	private Integer period; //기간
}
