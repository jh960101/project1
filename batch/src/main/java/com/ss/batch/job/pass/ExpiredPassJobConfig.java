package com.ss.batch.job.pass;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ss.batch.entity.PassEntity;
import com.ss.batch.entity.PassStatus;

//이용권이 만료 되었을 때 배치 작업을 설정하는 클래스
@Configuration
public class ExpiredPassJobConfig {

	// 데이터를 한꺼번에 처리할 수 있는 사이즈
	// 사이즈는 맘대로 설정 하면 된다.
	private final int CHUNK_SIZE =5;
	
	// JOB을 생성하는 팩토리(클래스)를 생성한다.
	private final JobBuilderFactory jobBuilderFactory;
	// Setep을 생성할 수 있는 팩토리(배치 작업의 단계)
	private final StepBuilderFactory stepBuilderFactory;
	//JPA와 데이터베이스를 연결 관리하는 객체
	private final EntityManagerFactory entityManagerFactory;
	
	public ExpiredPassJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			EntityManagerFactory entityManagerFactory) {
	
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.entityManagerFactory = entityManagerFactory;
	}
	//JOB
	// 배치 작업을 말하고 여러개의 step을 가질 수 있다.
	// 실행시 여러 step순서대로 처리를 한다.
	@Bean
	public Job expriedPassJob() {
		
		return this.jobBuilderFactory.get("expriedPassJob")//배치 작성을 생성해서 이름을 저장
									 .start(expiredPassStep())//step을 실행하는 메서드!
									 .build();
	}
	//step
	//<PassEntity,PassEntity>
	// 첫번째 제네릭 타입 - 데이터베이스에서 데이터를 읽어 올 때 타입
	// 두번째 제네릭 타입- 데이터베이스에서 데이터를 처리하거나 올 때 타입 수정된 데이터나 추가된 데이터를 저장
	// 
	@Bean
	public Step expiredPassStep() {
		return this.stepBuilderFactory
				   .get("expiredPassStep")
				   .<PassEntity,PassEntity>chunk(CHUNK_SIZE)
				   .reader(expirePassItemReader()) //읽어오기
				   .processor(expirePassItemProcessor()) //데이터를 처리
				   .writer(expirePItemWriter()) //저장
				   .build();
	}
	/*
	 * JpaCusorItemReader
	 * JpaPagingItemReader 만 지원! spring 4.3에서 추가된
	 * 페이징 기법보다 높은 성능으로 데이터 변경에 무관한 무결성
	 * 조회가 가능하다.
	 * 
	 * Map.of()
	 * java의 Map객체를 자동으로 생성하는것
	 */
	@Bean
	@StepScope // step실행 될 때 마다 새로운 객체를 생성하도록 설정하는 어노테이션
	public JpaCursorItemReader<PassEntity>
		expirePassItemReader(){
		return new JpaCursorItemReaderBuilder<PassEntity>()
				.name("expirePassItemReader")// ItemReader 여러개중 리더를 구분
				.entityManagerFactory(entityManagerFactory)// JPA를 통해서 데이터베이스 연결하고 데이터베이스 관리
				//상태(status)가 진행중이며 종료일시(endedAt)이 현재시점보다 과거일 만료 대상
				//,JPQL 쿼리
				.queryString("select p from PassEntity p where p.status = :status and p.ended_at <=:endedAt")
				.parameterValues(Map.of("status",PassStatus.PROGESES,"endedAt",LocalDateTime.now()))
				.build();			
	}
	
	@Bean
	public ItemProcessor<PassEntity,PassEntity> expirePassItemProcessor(){
		//인터페이스를 이용해서 itemprocessor생성
		//익명클래스를 이용해서 사용한다. 람다식 표현
		return new ItemProcessor<PassEntity, PassEntity>() {
			
			@Override
			public PassEntity process(PassEntity item) throws Exception {
				//실제 처리하는 내용
				//상태 현재 이용중에서 만료!
				//만료 일자도 현재 날짜를 기준으로! 수정
				item.setStatus(PassStatus.EXPIRED);
				item.setExpired_at(LocalDateTime.now());
				return item;
			}
		};
	}
	@Bean
	public JpaItemWriter<PassEntity>
	expirePItemWriter(){
		JpaItemWriter<PassEntity> writer= new JpaItemWriter<PassEntity>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}
}

//람다식
//@Bean
//public ItemProcessor<PassEntity, PassEntity> expirePassesItemProcessor() {
//    return passEntity -> {
//        passEntity.setStatus(PassStatus.EXPIRED);
//        passEntity.setExpiredAt(LocalDateTime.now());
//        return passEntity;
//    };
//}


//@Bean
//public JpaItemWriter<PassEntity> expirePassesItemWriter() {
//    return new JpaItemWriterBuilder<PassEntity>()
//            .entityManagerFactory(entityManagerFactory)
//            .build();
//}