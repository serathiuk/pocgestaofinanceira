package com.serathiuk.erp.legado;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@Configuration
public class AwsConfig {

	public static final String PROP_SQS_RESP_CRIACAO_PAGAR_RECEBER_VENDA = "${serathiukerp.sqs.endpoint}/RespostaCriacaoPagarReceberVenda";
	public static final String PROP_SQS_CRIAR_PAGAR_RECEBER = "${serathiukerp.sqs.endpoint}/CriarPagarReceber";
	
	@Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.accesskey}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secretkey}")
    private String secretAccessKey;

    @Value("${cloud.aws.endpoint.uri}")
    private String sqsUrl;
    
    @Value("${spring.profiles.active:prod}")
    private String ambiente;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
    	if(isDev()) {
    		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey));
    	} else {
    		return InstanceProfileCredentialsProvider.getInstance();
    	}
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
    	if(isDev()) {
	        return AmazonSQSAsyncClientBuilder.standard()
	                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrl, region))
	                .withCredentials(awsCredentialsProvider())
	                .build();
    	} else {
	        return AmazonSQSAsyncClientBuilder.standard()
	                .withCredentials(awsCredentialsProvider())
	                .build();    		
    	}
    }
    
    @Bean
    @Primary
    public AmazonSNSAsync amazonSNSAsync() {
    	if(isDev()) {
	        return AmazonSNSAsyncClientBuilder.standard()
	                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrl, region))
	                .withCredentials(awsCredentialsProvider())
	                .build();
    	} else {
	        return AmazonSNSAsyncClientBuilder.standard()
	                .withCredentials(awsCredentialsProvider())
	                .build();    		
    	}
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }
    
    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate() {
        return new NotificationMessagingTemplate(amazonSNSAsync());
    }
    
	private boolean isDev() {
		return "dev".equalsIgnoreCase(ambiente);
	}
    

}
