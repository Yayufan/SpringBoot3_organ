package tw.org.organ.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	@Value("${redisson.address}")
	private String redissonAddress;

	@Value("${redisson.database}")
	private int database;

	// 業務用的Redis (Redisson客戶端)
	@Bean
	RedissonClient bussinessRedissonClient() {
		Config config = new Config();
		//配置單實例redis , 同時這也是分佈式鎖建議的創建客戶端方式, 
		//分佈式鎖下,要實現紅鎖(RedLock)每台redis都得是master
		config.useSingleServer().setAddress(redissonAddress).setDatabase(database);
		return Redisson.create(config);
	}

}
