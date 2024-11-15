package tw.org.organ.scheduler;

import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import lombok.RequiredArgsConstructor;
import tw.org.organ.mapper.ArticleMapper;
import tw.org.organ.pojo.entity.Article;

@RequiredArgsConstructor
@Component
public class AsyncRedisArticleViewsToMysqlTask {

	private final ArticleMapper articleMapper;
	private final RedissonClient redissonClient;

	// 使用 Cron 表達式設置定時任務 (每分鐘第零秒執行此任務，測試時使用)
//	@Scheduled(cron = "0 * * * * ?")
	// 使用 Cron 表達式設置定時任務 (每天凌晨2點執行 cron = "0 0 2 * * ?" )
	@Scheduled(cron = "0 0 2 * * ?")
	public void runTaskWithCronExpression() {
		System.out.println("任務按照Cron表達式執行: " + System.currentTimeMillis());

		// 执行同步逻辑
		RKeys keys = redissonClient.getKeys();

		// 使用正則表達式匹配符合條件的鍵
		Iterable<String> matchingKeys = keys.getKeysByPattern("Article:*:views:*");

		for (String key : matchingKeys) {
			// 獲取對應的值
			Long articleViews = redissonClient.getAtomicLong(key).get();

			// System.out.println("key為: " + key);
			// System.out.println("value為: " + articleViews);

			// 解析 key
			String[] parts = key.split(":");
			// String category = parts[1];
			String id = parts[3];

			Long longTypeId = Long.valueOf(id);

			// id 從String 轉為Long

			// 這邊使用updateWrapper 是因為這個東西不會觸發自動填充
			LambdaUpdateWrapper<Article> articleUpdateWrapper = new LambdaUpdateWrapper<>();
			articleUpdateWrapper.eq(Article::getArticleId, longTypeId) // 設置條件
					.set(Article::getViews, articleViews); // 設置要更新的字段和值

			// 執行更新操作
			articleMapper.update(articleUpdateWrapper);

		}
	}
}
