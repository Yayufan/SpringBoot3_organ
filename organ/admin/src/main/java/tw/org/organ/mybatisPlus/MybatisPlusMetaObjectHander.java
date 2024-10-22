package tw.org.organ.mybatisPlus;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import tw.org.organ.system.pojo.VO.SysUserVO;

@Slf4j
@Component
public class MybatisPlusMetaObjectHander implements MetaObjectHandler {

	/**
	 * Mybatis Plus 的字段自動填充處理器
	 */

	@Override
	public void insertFill(MetaObject metaObject) {
		// TODO 當觸發新增時的字段填充
		System.out.println(metaObject.getOriginalObject());

		// 獲取新增&更新這筆資料的管理者資訊
		// 獲得當前使用者的session
		SaSession session = StpUtil.getSession();

		// 定義當前使用者的資料
		SysUserVO sysUserVO = null;

		// 如果從緩存獲取的使用者資料不為空,則為變量從新賦值
		if (session.get("userInfo") != null) {
			// 獲取當前使用者的資料
			sysUserVO = (SysUserVO) session.get("userInfo");
			// 獲取當前使用的權限列表
			
			log.info("開始插入填充...");
			// this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class,
			// LocalDateTime.now());
			this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
			this.strictInsertFill(metaObject, "createBy", String.class, sysUserVO.getNickName());
			
		}

//		log.info("開始插入填充...");
//		// this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class,
//		// LocalDateTime.now());
//		this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
//		this.strictInsertFill(metaObject, "createBy", String.class, sysUserVO.getNickName());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// TODO 當觸發更新時的字段填充

		System.out.println("開始更新填充");

		// 獲取新增&更新這筆資料的管理者資訊
		// 獲得當前使用者的session
		SaSession session = StpUtil.getSession();

		// 定義當前使用者的資料
		SysUserVO sysUserVO = null;

		// 如果從緩存獲取的使用者資料不為空,則為變量從新賦值
		if (session.get("userInfo") != null) {
			// 獲取當前使用者的資料
			sysUserVO = (SysUserVO) session.get("userInfo");
			// 獲取當前使用的權限列表
			

			System.out.println("當前sysUserVO內對象  " + sysUserVO);

			log.info("開始更新填充...");
			this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
			this.strictUpdateFill(metaObject, "updateBy", String.class, sysUserVO.getNickName());
			
		}
;

	}

}